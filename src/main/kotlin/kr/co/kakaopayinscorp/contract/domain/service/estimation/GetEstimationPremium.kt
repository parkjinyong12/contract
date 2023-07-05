package kr.co.kakaopayinscorp.contract.domain.service.estimation

import io.swagger.v3.oas.annotations.media.Schema

data class GetEstimationPremium (
    /**
     * 상품 번호
     */
    @Schema(example = "상품 번호")
    var productId: Long,
    /**
     * 담보 번호 리스트
     */
    @Schema(example = "담보 번호 리스트")
    var securityIdList: List<Long>,
    /**
     * 계약 기간
     */
    @Schema(example = "계약 기간")
    var contractPeriod: Int
){
    constructor(builder: Builder): this(
        productId = builder.productId,
        securityIdList = builder.securityIdList,
        contractPeriod = builder.contractPeriod
    )
    class Builder {
        var productId: Long = 0
        lateinit var securityIdList: List<Long>
        var contractPeriod: Int = 0

        fun productId(data: () -> Long) {
            productId(data)
        }
        fun securityIdList(data: () -> List<Long>) {
            securityIdList(data)
        }
        fun contractPeriod(data: () -> Int) {
            contractPeriod(data)
        }

        fun build() = GetEstimationPremium(
            productId, securityIdList, contractPeriod
        )
    }
}