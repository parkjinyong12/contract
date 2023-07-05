package kr.co.kakaopayinscorp.contract.service.estimation

import kr.co.kakaopayinscorp.contract.domain.service.estimation.EstimationPremiumResult
import kr.co.kakaopayinscorp.contract.domain.service.estimation.GetEstimationPremium
import kr.co.kakaopayinscorp.contract.domain.service.product.GetProduct
import kr.co.kakaopayinscorp.contract.domain.service.security.GetSecurity
import kr.co.kakaopayinscorp.contract.exception.ContractDataInconsistencyException
import kr.co.kakaopayinscorp.contract.exception.ContractTermsInconsistencyException
import kr.co.kakaopayinscorp.contract.service.product.ProductService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.math.roundToInt

@Service
class EstimationPremiumService {

    @Autowired
    lateinit var productService: ProductService

    private val logger = LoggerFactory.getLogger(EstimationPremiumService::class.java)

    fun getEstimationPremium(model: GetEstimationPremium): EstimationPremiumResult {

        // 상품 조회하고
        // 담보 조회해서

        // 해당 상품의 담보가 맞는지 확인하고
        // 계약 기간 확인하고
        // 계산
        var productSecurityMap = mutableMapOf <String, GetSecurity>()
        var productSecurityMatchList = mutableListOf <GetSecurity>()
        return productService.getProduct(model.productId).let { productModel ->
            // 인입된 담보Id가 상품에 포함된 담보 정보와 일치하지 않는 경우
            if(productModel.securities != null) {
                productModel.securities!!.map {
                    productSecurityMap[it.securityId.toString()] = it
                    it.securityId.toString()
                }.toHashSet().let { productSecurityIds ->
                    model.securityIdList.map { inputSecurityId ->
                        if (!productSecurityIds.contains(inputSecurityId.toString())) {
                            throw ContractDataInconsistencyException("인입된 담보ID는 해당 상품에 속한 담보ID가 아닙니다.")
                        } else {
                            if(productSecurityMap[inputSecurityId.toString()] != null) {
                                productSecurityMatchList.add(productSecurityMap[inputSecurityId.toString()]!!)
                            }
                        }
                    }
                }
            }
            // 계약기간 유효성 검증
            if ((productModel.maxSubscriptionPeriod != null
                        && productModel.maxSubscriptionPeriod != null)
                && model.contractPeriod > productModel.maxSubscriptionPeriod!!
                || model.contractPeriod < productModel.minSubscriptionPeriod!!
            ) {
                throw ContractTermsInconsistencyException(
                    "계약 요청기간이 상품에 명시된 기간보다 적거나 큽니다. " +
                            "최소 계약기간: [${productModel.minSubscriptionPeriod}]개월, " +
                            "최대 계약기간: [${productModel.maxSubscriptionPeriod}]개월"
                )
            }
            var totalInsuranceFee = 0.0
            productSecurityMatchList.forEach { matchSecurity ->
                if(matchSecurity.subscriptionAmount != null && matchSecurity.standardAmount != null) {
                    totalInsuranceFee += matchSecurity.subscriptionAmount!!.toDouble() / matchSecurity.standardAmount!!.toDouble()
                }
            }
            totalInsuranceFee *= model.contractPeriod
            totalInsuranceFee = (totalInsuranceFee * 100.0).roundToInt() / 100.0
            EstimationPremiumResult.Builder().apply {
                this.totalInsuranceFee = totalInsuranceFee.toString()
                this.selectedSecurityList = productSecurityMatchList
            }.build()
        }
    }
}