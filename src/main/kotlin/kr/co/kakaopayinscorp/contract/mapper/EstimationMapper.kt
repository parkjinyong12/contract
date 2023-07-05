package kr.co.kakaopayinscorp.contract.mapper

import kr.co.kakaopayinscorp.contract.domain.service.estimation.EstimationPremiumResult
import kr.co.kakaopayinscorp.contract.domain.service.estimation.GetEstimationPremium
import kr.co.kakaopayinscorp.contract.domain.view.estimation.GetEstimationPremiumView

class EstimationMapper {
    companion object {
        fun mappingGetEstimationPremium(view: GetEstimationPremiumView): GetEstimationPremium {
            return GetEstimationPremium.Builder().apply {
                if(view.productId != null) {
                    productId = view.productId!!.toLong()
                } else {
                    throw NullPointerException("입력값이 존재하지 않습니다. 입력이 필요한 필드: productId")
                }
                if(view.securityIdList != null && view.securityIdList!!.isNotEmpty()) {
                    securityIdList = view.securityIdList!!.map {
                        it.toLong()
                    }
                } else {
                    throw NullPointerException("입력값이 존재하지 않거나 사이즈가 0입니다. 입력이 필요한 필드: securityIdList")
                }
                if(view.contractPeriod != null) {
                    contractPeriod = view.contractPeriod!!.toInt()
                } else {
                    throw NullPointerException("입력값이 존재하지 않습니다. 입력이 필요한 필드: contractPeriod")
                }
            }.build()
        }
    }
}