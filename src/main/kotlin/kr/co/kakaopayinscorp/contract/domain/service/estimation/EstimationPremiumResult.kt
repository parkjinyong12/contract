package kr.co.kakaopayinscorp.contract.domain.service.estimation

import kr.co.kakaopayinscorp.contract.domain.service.product.GetProduct
import kr.co.kakaopayinscorp.contract.domain.service.security.GetSecurity

data class EstimationPremiumResult(
    val totalInsuranceFee: String,
    val selectedSecurityList: List<GetSecurity>,
){
    constructor(builder: Builder): this(
        totalInsuranceFee = builder.totalInsuranceFee,
        selectedSecurityList = builder.selectedSecurityList
    )
    class Builder {
        lateinit var totalInsuranceFee: String
        lateinit var product: GetProduct
        lateinit var selectedSecurityList: List<GetSecurity>

        fun totalInsuranceFee(data: () -> String) {
            totalInsuranceFee(data)
        }
        fun selectedSecurityList(data: () -> List<GetSecurity>) {
            selectedSecurityList(data)
        }

        fun build() = EstimationPremiumResult(
            totalInsuranceFee, selectedSecurityList
        )
    }
}