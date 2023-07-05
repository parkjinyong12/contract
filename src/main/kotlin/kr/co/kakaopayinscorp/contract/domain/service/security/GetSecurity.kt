package kr.co.kakaopayinscorp.contract.domain.service.security

import kr.co.kakaopayinscorp.contract.domain.service.product.GetProduct
import java.time.LocalDateTime

data class GetSecurity(
    /**
     * 담보번호
     */
    var securityId: Long? = null,
    /**
     * 담보명
     */
    var securityName: String? = null,
    /**
     * 가입금액
     */
    var subscriptionAmount: Long? = null,
    /**
     * 기준금액
     */
    var standardAmount: Long? = null,
    /**
     * 생성시각
     */
    var securityCreateDatetime: LocalDateTime? = null
){
    private constructor(builder: Builder): this(
        securityId = builder.securityId,
        securityName = builder.securityName,
        subscriptionAmount = builder.subscriptionAmount,
        standardAmount = builder.standardAmount,
        securityCreateDatetime = builder.securityCreateDatetime
    )
    class Builder {
        var securityId: Long? = null
        var securityName: String? = null
        var subscriptionAmount: Long? = null
        var standardAmount: Long? = null
        var securityCreateDatetime: LocalDateTime? = null

        fun securityId(data: () -> Long) {
            securityId(data)
        }
        fun securityName(data: () -> String) {
            securityName(data)
        }
        fun subscriptionAmount(data: () -> Long) {
            subscriptionAmount(data)
        }
        fun standardAmount(data: () -> Long) {
            standardAmount(data)
        }
        fun securityCreateDatetime(data: () -> LocalDateTime) {
            securityCreateDatetime(data)
        }
        fun build() = GetSecurity(
            securityId,
            securityName,
            subscriptionAmount,
            standardAmount,
            securityCreateDatetime
        )
    }
}
