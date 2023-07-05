package kr.co.kakaopayinscorp.contract.domain.view.contract

import io.swagger.v3.oas.annotations.media.Schema

data class UpdateContractView(
    /**
     * 수정할 계약번호
     */
    @Schema(example = "수정할 계약번호")
    val contractId: String? = null,
    /**
     * 추가될 담보
     */
    @Schema(example = "추가될 담보번호 리스트")
    val addSecurityIds: List<String>? = null,
    /**
     * 삭제될 담보
     */
    @Schema(example = "삭제할 담보번호 리스트")
    val removeSecurityIds: List<String>? = null,
    /**
     * 변경될 계약기간
     */
    @Schema(example = "변경할 계약기간")
    var contractPeriod: String? = null,
    /**
     * 계약상태 변경
     */
    @Schema(example = "변경할 계약상태 코드")
    val contractStatus: String? = null,
)