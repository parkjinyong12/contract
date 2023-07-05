package kr.co.kakaopayinscorp.contract.domain.view.contract

import io.swagger.v3.oas.annotations.media.Schema

data class CreateContractView(
    /**
     * 상품번호
     */
    @Schema(example = "상품번호")
    val productId: String? = null,
    /**
     * 가입담보
     */
    @Schema(example = "가입담보번호 리스트")
    val subscriptionSecurityIds: List<String>? = null,
    /**
     * 계약기간
     */
    @Schema(example = "계약기간(월)")
    var contractPeriod: String? = null,
    /**
     * 보험시작일
     */
    @Schema(example = "보험시작일(YYYY-MM-DD)")
    val insuranceStartDate: String? = null,
)