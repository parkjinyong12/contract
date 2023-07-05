package kr.co.kakaopayinscorp.contract.domain.service.contract

data class GetContract(
    var contractId: Long
){

    private constructor(builder: Builder): this(
        contractId = builder.contractId
    )
    class Builder {
        var contractId: Long = 0

        fun contractId(data: () -> Long) {
            contractId(data)
        }

        fun build() = GetContract(
            contractId
        )
    }


}