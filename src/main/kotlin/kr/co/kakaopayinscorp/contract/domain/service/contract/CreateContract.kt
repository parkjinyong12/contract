package kr.co.kakaopayinscorp.contract.domain.service.contract

data class CreateContract(
    /**
     * 상품번호
     */
    var productId: Long,
    /**
     * 가입담보
     */
    var subscriptionSecurityIds: List<Long>,
    /**
     * 계약기간
     */
    var contractPeriod: Int,
    /**
     * 보험시작일
     */
    var insuranceStartDate: String,
    /**
     * 보험종료일
     */
    var insuranceEndDate: String? = null,
    /**
     * 총 보험료
     */
    var totalInsuranceFee: Double? = null,
){
    private constructor(builder: Builder): this(
        productId = builder.productId,
        subscriptionSecurityIds = builder.subscriptionSecurityIds,
        contractPeriod = builder.contractPeriod,
        insuranceStartDate = builder.insuranceStartDate,
        insuranceEndDate = builder.insuranceEndDate,
        totalInsuranceFee = builder.totalInsuranceFee
    )
    class Builder {
        var productId: Long = 0
        lateinit var subscriptionSecurityIds: List<Long>
        var contractPeriod: Int = 0
        lateinit var insuranceStartDate: String
        var insuranceEndDate: String? = null
        var totalInsuranceFee: Double? = null

        fun productId(data: () -> Long) {
            productId(data)
        }
        fun subscriptionSecurityIds(data: () -> List<Long>) {
            subscriptionSecurityIds(data)
        }
        fun contractPeriod(data: () -> Int) {
            contractPeriod(data)
        }
        fun insuranceStartDate(data: () -> String) {
            insuranceStartDate(data)
        }
        fun insuranceEndDate(data: () -> String) {
            insuranceEndDate(data)
        }
        fun totalInsuranceFee(data: () -> Double) {
            totalInsuranceFee(data)
        }
        fun build() = CreateContract(
            productId,
            subscriptionSecurityIds,
            contractPeriod,
            insuranceStartDate,
            insuranceEndDate,
            totalInsuranceFee
        )
    }
}