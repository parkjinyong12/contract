package kr.co.kakaopayinscorp.contract.mapper

import kr.co.kakaopayinscorp.contract.domain.entity.contract.ContractEntity
import kr.co.kakaopayinscorp.contract.domain.service.contract.CreateContract
import kr.co.kakaopayinscorp.contract.domain.service.contract.GetContract
import kr.co.kakaopayinscorp.contract.domain.service.contract.UpdateContract
import kr.co.kakaopayinscorp.contract.domain.view.contract.CreateContractView
import kr.co.kakaopayinscorp.contract.domain.view.contract.GetContractView
import kr.co.kakaopayinscorp.contract.domain.view.contract.UpdateContractView
import java.time.LocalDateTime

class ContractMapper {

    companion object {
        /**
         * 계약 생성 뷰 -> 계약 생성 서비스
         */
        fun mappingCreateContract(view: CreateContractView): CreateContract {
            return CreateContract.Builder().apply {
                if(view.productId != null) {
                    productId = view.productId.toLong()
                } else {
                    throw NullPointerException("입력값이 존재하지 않습니다. 입력이 필요한 필드: contractPeriod")
                }

                if(view.subscriptionSecurityIds != null) {
                    subscriptionSecurityIds = view.subscriptionSecurityIds.map {
                        it.toLong()
                    }
                } else {
                    throw NullPointerException("입력값이 존재하지 않습니다. 입력이 필요한 필드: contractPeriod")
                }

                if(view.insuranceStartDate != null) {
                    insuranceStartDate = view.insuranceStartDate
                } else  {
                    throw NullPointerException("입력값이 존재하지 않습니다. 입력이 필요한 필드: contractPeriod")
                }

                if(view.contractPeriod != null) {
                    contractPeriod = view.contractPeriod!!.toInt()
                } else {
                    throw NullPointerException("입력값이 존재하지 않습니다. 입력이 필요한 필드: contractPeriod")
                }
            }.build()
        }

        /**
         * 계약 생성 뷰 -> 계약 생성 서비스
         */
        fun mappingUpdateContract(view: UpdateContractView): UpdateContract {
            return UpdateContract.Builder().apply {
                if(view.contractId != null) {
                    contractId = view.contractId!!.toLong()
                } else {
                    throw NullPointerException("입력값이 존재하지 않습니다. 입력이 필요한 필드: contractId")
                }

                if(view.addSecurityIds != null) {
                    addSecurityIds = view.addSecurityIds.map {
                        it.toLong()
                    }
                }
                if(view.removeSecurityIds != null) {
                    removeSecurityIds = view.removeSecurityIds.map {
                        it.toLong()
                    }
                }
                if(view.contractPeriod != null) {
                    contractPeriod = view.contractPeriod!!.toInt()
                }
                if(view.contractStatus != null) {
                    contractStatus = view.contractStatus
                }
            }.build()
        }

        /**
         * 계약 생성 서비스 -> 계약 엔티티
         */
        fun mappingContractEntity(model: CreateContract): ContractEntity {
            val entity = ContractEntity()
            if(model.productId != null) {
                entity.productId = model.productId!!
            }
            if(model.contractPeriod != null) {
                entity.contractPeriod = model.contractPeriod!!
            }
            if(model.totalInsuranceFee != null) {
                entity.totalInsuranceFee = model.totalInsuranceFee!!
            }
            entity.contract_create_datetime = LocalDateTime.now()
            entity.contract_update_datetime = LocalDateTime.now()
            return entity
        }

        /**
         * 계약조회 뷰 -> 계약조회 서비스
         */
        fun mappingGetContract(view: GetContractView): GetContract {
            return GetContract.Builder().apply {
                if (view.contractId != null) {
                    contractId = view.contractId!!.toLong()
                } else {
                    throw NullPointerException("입력값이 존재하지 않습니다. 입력이 필요한 필드: contractId")
                }
            }.build()
        }
    }
}