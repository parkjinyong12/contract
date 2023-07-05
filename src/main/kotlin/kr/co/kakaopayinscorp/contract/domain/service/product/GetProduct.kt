package kr.co.kakaopayinscorp.contract.domain.service.product

import kr.co.kakaopayinscorp.contract.domain.service.security.GetSecurity

data class GetProduct(

    /**
     * 상품 번호
     */
    var productId: Long?,
    /**
     * 상품 명
     */
    var productName: String?,
    /**
     * 최소 가입기간
     */
    var minSubscriptionPeriod: Int? = null,
    /**
     * 최대 가입기간
     */
    var maxSubscriptionPeriod: Int? = null,
    /**
     * 상품 사용가능 여부
     */
    var productUseYn: Boolean = false,

    /**
     * 상품담보
     */
    var securities: List<GetSecurity>? = null
){
    private constructor(builder: Builder): this(
        productId = builder.productId,
        productName = builder.productName,
        minSubscriptionPeriod = builder.minSubscriptionPeriod,
        maxSubscriptionPeriod = builder.maxSubscriptionPeriod,
        productUseYn = builder.productUseYn,
        securities = builder.securities
    )
    class Builder {
        var productId: Long? = null
        var productName: String? = null
        var minSubscriptionPeriod: Int? = null
        var maxSubscriptionPeriod: Int? = null
        var productUseYn: Boolean = false
        var securities: List<GetSecurity>? = null

        fun productId(data: () -> Long) {
            productId(data)
        }
        fun productName(data: () -> String) {
            productName(data)
        }
        fun minSubscriptionPeriod(data: () -> Int) {
            minSubscriptionPeriod(data)
        }
        fun maxSubscriptionPeriod(data: () -> Int) {
            maxSubscriptionPeriod(data)
        }
        fun productUseYn(data: () -> Boolean) {
            productUseYn(data)
        }
        fun securities(data: () -> GetSecurity) {
            securities(data)
        }
        fun build() = GetProduct(
            productId,
            productName,
            minSubscriptionPeriod,
            maxSubscriptionPeriod,
            productUseYn,
            securities
        )
    }
}