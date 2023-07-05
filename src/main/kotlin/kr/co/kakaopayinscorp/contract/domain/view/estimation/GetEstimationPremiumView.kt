package kr.co.kakaopayinscorp.contract.domain.view.estimation

import io.swagger.v3.oas.annotations.media.Schema

data class GetEstimationPremiumView(
    /**
     * 상품 번호
     */
    @Schema(example = "상품 번호")
    var productId: String? = null,
    /**
     * 담보 번호 리스트
     */
    @Schema(example = "담보 번호 리스트")
    var securityIdList: List<String>? = null,
    /**
     * 계약 기간
     */
    @Schema(example = "계약 기간")
    var contractPeriod: String? = null,
)