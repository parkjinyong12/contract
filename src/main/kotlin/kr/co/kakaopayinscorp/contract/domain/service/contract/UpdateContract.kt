package kr.co.kakaopayinscorp.contract.domain.service.contract

data class UpdateContract(
    /**
     * 수정할 계약번호
     */
    var contractId: Long,
    /**
     * 추가될 담보
     */
    var addSecurityIds: List<Long>? = null,
    /**
     * 삭제될 담보
     */
    var removeSecurityIds: List<Long>? = null,
    /**
     * 변경될 계약기간
     */
    var contractPeriod: Int? = null,
    /**
     * 계약상태 변경
     */
    val contractStatus: String? = null,
){
    private constructor(builder: Builder): this(
        contractId = builder.contractId,
        addSecurityIds = builder.addSecurityIds,
        removeSecurityIds = builder.removeSecurityIds,
        contractPeriod = builder.contractPeriod,
        contractStatus = builder.contractStatus
    )
    class Builder {
        var contractId: Long = 0
        var addSecurityIds: List<Long>? = null
        var removeSecurityIds: List<Long>? = null
        var contractPeriod: Int? = null
        var contractStatus: String? = null

        fun contractId(data: () -> Long) {
            contractId(data)
        }
        fun addSecurityIds(data: () -> Long) {
            addSecurityIds(data)
        }
        fun removeSecurityIds(data: () -> List<Long>) {
            removeSecurityIds(data)
        }
        fun contractPeriod(data: () -> Int) {
            contractPeriod(data)
        }
        fun contractStatus(data: () -> Boolean) {
            contractStatus(data)
        }
        fun build() = UpdateContract(
            contractId,
            addSecurityIds,
            removeSecurityIds,
            contractPeriod,
            contractStatus
        )
    }
}