package kr.co.kakaopayinscorp.contract.service.contract

import kr.co.kakaopayinscorp.contract.config.ConstObject
import kr.co.kakaopayinscorp.contract.domain.entity.contract.ContractEntity
import kr.co.kakaopayinscorp.contract.domain.entity.contractsecurity.ContractSecurityEntity
import kr.co.kakaopayinscorp.contract.domain.entity.product.ProductEntity
import kr.co.kakaopayinscorp.contract.domain.service.contract.CreateContract
import kr.co.kakaopayinscorp.contract.domain.service.contract.GetContract
import kr.co.kakaopayinscorp.contract.domain.service.contract.UpdateContract
import kr.co.kakaopayinscorp.contract.exception.*
import kr.co.kakaopayinscorp.contract.mapper.ContractMapper
import kr.co.kakaopayinscorp.contract.repository.contract.ContractRepository
import kr.co.kakaopayinscorp.contract.repository.contractsecurity.ContractSecurityRepository
import kr.co.kakaopayinscorp.contract.repository.product.ProductRepository
import kr.co.kakaopayinscorp.contract.repository.security.SecurityRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.regex.Pattern
import kotlin.math.roundToInt

/**
 * 계약 관리 서비스
 */
@Service
@Transactional(rollbackFor = [ContractDataInconsistencyException::class, ProductDataInconsistencyException::class, ContractTermsInconsistencyException::class])
class ContractManageService {

    private val logger = LoggerFactory.getLogger(ContractManageService::class.java)

    @Autowired
    lateinit var contractRepository: ContractRepository

    @Autowired
    lateinit var productRepository: ProductRepository

    @Autowired
    lateinit var contractSecurityRepository: ContractSecurityRepository

    @Autowired
    lateinit var securityRepository: SecurityRepository

    fun createContract(model: CreateContract): ContractEntity {

        val dataMap = mutableMapOf<String,Any>()
        val inputSecurityIdsSet = model.subscriptionSecurityIds.toSet()

        /**
         * 날짜에 대한 유효성 검사(YYYY-MM-DD)
         */
        if(!(model.insuranceStartDate != null && model.insuranceStartDate!!.length == 10 &&
            Pattern.compile("^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$")
            .matcher(model.insuranceStartDate).matches())) {
            throw DataValidationException("날짜 형식이 유효하지 않습니다. 유효한 형식(YYYY-MM-DD")
        }

        /**
         * 해당 상품정보, 담보정보가 있는지 확인하고, 데이터 맵에 담는다.
         */
        if(model.subscriptionSecurityIds != null && model.subscriptionSecurityIds!!.isNotEmpty()) {
            securityRepository.existsBySecurityIdIn(model.subscriptionSecurityIds!!).let {
                if(it.isEmpty) {
                    throw ProductDataInconsistencyException("인입된 데이터가 실제 정보와 일치하지 않습니다.")
                }
            }
        }
        if(model.productId != null) {
            productRepository.findById(model.productId!!).let { optional ->
                if(optional.isPresent) {
                    optional.get().securities?.map {
                        // 인입된 담보Id가 상품에 포함된 담보 정보와 일치하지 않는 경우
                        if(!inputSecurityIdsSet.contains(it.securityId)) {
                            throw ContractDataInconsistencyException("인입된 담보ID는 해당 상품에 속한 담보ID가 아닙니다.")
                        }
                    }?: throw ContractDataInconsistencyException("해당 상품ID로 검색된 상품 정보에는 담보 정보가 존재하지 않습니다.")
                    dataMap[ConstObject.DATA_MAP_KEY_NAME_PRODUCT] = optional.get()
                } else {
                    // 상품Id가 실제로 존재하지 않는 상품Id인 경우
                    throw ProductDataInconsistencyException("인입된 데이터가 실제 정보와 일치하지 않습니다.")
                }
            }
        }

        val contractEntity = ContractMapper.mappingContractEntity(model)

        /**
         * 최초 계약 생성 시, 정상 계약으로 간주한다.
         */
        contractEntity.contractStatus = ConstObject.CONSTRACT_STATUS_NORMAL_CONTRACT

        /**
         * 보험계약 종료일 계산
         */
        val yymmdd = model.insuranceStartDate!!.split("-")
        val insuranceStartDate:LocalDate = LocalDate.of(yymmdd[0].toInt(),yymmdd[1].toInt(),yymmdd[2].toInt())

        contractEntity.insuranceStartDate = insuranceStartDate
        contractEntity.insuranceEndDate = if(model.contractPeriod != null && model.contractPeriod!!.toInt() > 0) {
            insuranceStartDate.plusMonths(model.contractPeriod!!.toLong()).minusDays(1)
        } else {
            throw DataValidationException("계약기간 형식이 유효하지 않습니다.")
        }

        /**
         * 1. 계약생성 요청 내용이 최소최대 계약기간 내 상품인지 확인
         * 2. 보험료 계산
         */
        dataMap[ConstObject.DATA_MAP_KEY_NAME_PRODUCT]!!.let { productEntity ->
            productEntity as ProductEntity

            // 계약기간 유효성 검증
            if(model.contractPeriod > productEntity.maxSubscriptionPeriod || model.contractPeriod < productEntity.minSubscriptionPeriod) {
                throw ContractTermsInconsistencyException(
                    "계약 요청기간이 상품에 명시된 기간보다 적거나 큽니다. " +
                            "최소 계약기간: [${productEntity.minSubscriptionPeriod}]개월, " +
                            "최대 계약기간: [${productEntity.maxSubscriptionPeriod}]개월"
                )
            }

            var totalInsuranceFee: Double = 0.0
            productEntity.securities!!.filter { securityEntity ->
                inputSecurityIdsSet.contains(securityEntity.securityId)
            }.map { securityEntity ->
                // 보험료 계산
                totalInsuranceFee += securityEntity.subscriptionAmount.toDouble() / securityEntity.standardAmount.toDouble()
            }
            contractEntity.totalInsuranceFee = (totalInsuranceFee * 100.0).roundToInt() / 100.0
        }

        // 계약정보 저장
        val savedContractEntity = contractRepository.save(contractEntity)

        inputSecurityIdsSet.forEach { inputSecurityId ->
            contractSecurityRepository.save(
                ContractSecurityEntity().apply {
                    contractId = savedContractEntity.contractId
                    securityId = inputSecurityId
                }
            )
        }
        return savedContractEntity
    }

    fun getContract(model: GetContract): ContractEntity {
        return contractRepository.findById(model.contractId).let {
            if(it.isPresent) {
                it.get()
            } else {
                throw Exception("계약정보 없음")
            }
        }
    }

    fun updateContract(model: UpdateContract) {
        return contractRepository.findById(model.contractId!!).let { contractOptional ->
            if (contractOptional.isPresent) {

                productRepository.findById(contractOptional.get().productId).let { productOptional ->
                    if (productOptional.isPresent) {

                        if(contractOptional.get().contractStatus == ConstObject.CONSTRACT_STATUS_CONTRACT_EXPIRATION){
                            throw ContractExpirationException("계약이 만료되어 변경업무 처리가 불가능합니다.")
                        } else {
                            /**
                             * 계약 내 담보 추가 또는 삭제
                             */
                            updateContractSecurity(model, contractOptional.get(), productOptional.get())
                            /**
                             * 계약기간 & 계약상태 변경
                             */
                            updateContractPeriodAndContractStatus(model, contractOptional.get(), productOptional.get())
                        }
                    }
                }
            } else {
                // 계약이 존재하지 않는 경우
                throw ContractDataInconsistencyException("인입된 계약과 일치하는 계약 정보가 없습니다.")
            }
        }
    }

    /**
     * 계약기간 변경
     */
    private fun updateContractPeriodAndContractStatus(
        model: UpdateContract,
        contractEntity: ContractEntity,
        productEntity: ProductEntity
    ) {
        if(model.contractPeriod != null) {
            if(model.contractPeriod!! > productEntity.maxSubscriptionPeriod || model.contractPeriod!! < productEntity.minSubscriptionPeriod) {
                throw ContractTermsInconsistencyException(
                    "계약 요청기간이 상품에 명시된 기간보다 적거나 큽니다. " +
                            "최소 계약기간: [${productEntity.minSubscriptionPeriod}]개월, " +
                            "최대 계약기간: [${productEntity.maxSubscriptionPeriod}]개월"
                )
            }
            contractEntity.contractPeriod = model.contractPeriod!!
        }
        if(model.contractStatus != null) {
            contractEntity.contractStatus = model.contractStatus
        }
        contractRepository.save(contractEntity)
    }

    /**
     * 계약 내 담보 추가 또는 삭제
     */
    private fun updateContractSecurity(
        model: UpdateContract,
        contractEntity: ContractEntity,
        productEntity: ProductEntity
    ) {
        // 추가하려는 담보
        val inputAddSecurityIdsSet = if(model.addSecurityIds != null && model.addSecurityIds!!.isNotEmpty()) {
            model.addSecurityIds!!.toSet()
        } else {
            emptySet()
        }
        // 제거하려는 담보
        val inputRemoveSecurityIdsSet = if(model.removeSecurityIds != null && model.removeSecurityIds!!.isNotEmpty()) {
            model.removeSecurityIds!!.toSet()
        } else {
            emptySet()
        }

        val addSecuritySet = mutableSetOf<Long>()
        val removeSecuritySet = mutableSetOf<ContractSecurityEntity>()
        if(inputAddSecurityIdsSet.isNotEmpty() || inputRemoveSecurityIdsSet.isNotEmpty()) {
            val dataMap = mutableMapOf<String, Any>()

            if (productEntity.securities != null) {
                // 추가될 담보가 상품 내 존재하는 지 확인하고 추가될 담보 Set에 담는다.
                productEntity.securities!!.map {
                    it.securityId.toString()
                }.toHashSet().let { productSecurityIdSet ->
                    inputAddSecurityIdsSet.forEach { addSecurityId ->
                        if(!productSecurityIdSet.contains(addSecurityId.toString())) {
//                            throw ContractDataInconsistencyException("인입된 추가요청 담보는 해당 계약 내 상품에 속한 담보가 아닙니다.")
                        } else {
                            logger.info("추가될 담보. 담보 아이디: [{}]",addSecurityId)
                            addSecuritySet.add(addSecurityId)
                        }
                    }
                }

                // 상품 담보 정보를 담는다.
                val productSecurityEntityMap = mutableMapOf<Long, ContractSecurityEntity>()
                productEntity.securities!!.map { securityEntity ->
                    productSecurityEntityMap[securityEntity.securityId] =
                        ContractSecurityEntity().apply {
                            contractId = contractEntity.contractId
                            securityId = securityEntity.securityId
                        }
                }
                dataMap[ConstObject.DATA_MAP_KEY_NAME_CONTRACT_SECURITY_MAP] = productSecurityEntityMap
            }

            val contractSecurityMap = mutableMapOf<String, ContractSecurityEntity>()

            (contractEntity.contractSecurities!!)
//                ?: throw ContractDataInconsistencyException("해당 계약에는 관련 담보 정보가 존재하지 않습니다."))
            .map {
                contractSecurityMap[it.securityId.toString()] = it
            }

            // 제거될 담보가 계약 내 존재하는 지 확인하고 제거될 담보 Set에 담는다.
            inputRemoveSecurityIdsSet.forEach { removeSecurityId ->
                if (!contractSecurityMap.keys.toSet().contains(removeSecurityId.toString())) {
//                    throw ContractDataInconsistencyException("인입된 제거요청 담보는 해당 계약에 속한 담보가 아닙니다.")
                } else {
                    logger.info("제거될 담보. 담보 아이디: [{}]",removeSecurityId)
                    removeSecuritySet.add(contractSecurityMap[removeSecurityId.toString()]!!)
                }
            }

            // 추가될 담보들이 이미 계약에 포함되어 있다면, 추가될 담보 Set에서 제거
            val addExceptSecuritySet = mutableSetOf<Long>()
            addSecuritySet.forEach { addSecurityId ->
                if(contractSecurityMap.keys.toSet().contains(addSecurityId.toString())) {
                    logger.info("해당 담보는 이미 계약에 포함되어 있어, 추가 Set에서 제외하겠습니다. 담보 아이디: {}", addSecurityId)
                    addExceptSecuritySet.add(addSecurityId)
                }
            }
            // 추가 제외될 담보들 추가될 담보 Set에서 제거
            addExceptSecuritySet.forEach {
                addSecuritySet.remove(it)
            }

            // 담보 추가 및 삭제 처리
            if (dataMap[ConstObject.DATA_MAP_KEY_NAME_CONTRACT_SECURITY_MAP] != null) {
                val productSecurityMap =
                    dataMap[ConstObject.DATA_MAP_KEY_NAME_CONTRACT_SECURITY_MAP] as Map<Long, ContractSecurityEntity>
                addSecuritySet.forEach { addSecurityId ->
                    if (productSecurityMap[addSecurityId] != null) {
                        productSecurityMap[addSecurityId] as ContractSecurityEntity
                        contractSecurityRepository.save(
                            productSecurityMap[addSecurityId]!!
                        )
                    }
                }
                removeSecuritySet.forEach { removeContractSecurity ->
                    contractSecurityRepository.delete(
                        removeContractSecurity
                    )
                }
            }
        }
    }
}