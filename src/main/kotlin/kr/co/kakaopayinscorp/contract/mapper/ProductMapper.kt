package kr.co.kakaopayinscorp.contract.mapper

import kr.co.kakaopayinscorp.contract.domain.entity.product.ProductEntity
import kr.co.kakaopayinscorp.contract.domain.service.product.GetProduct

class ProductMapper {
    companion object {

        /**
         * 상품 엔티티 -> 상품 서비스
         */
        fun mappingGetProduct(entity: ProductEntity): GetProduct {
            return GetProduct.Builder().apply {
                if(entity.productId != null) {
                    productId = entity.productId
                }
                if(entity.productName != null) {
                    productName = entity.productName
                }
                if(entity.minSubscriptionPeriod != null) {
                    minSubscriptionPeriod = entity.minSubscriptionPeriod
                }
                if(entity.maxSubscriptionPeriod != null) {
                    maxSubscriptionPeriod = entity.maxSubscriptionPeriod
                }
                if(entity.productUseYn != null) {
                    productUseYn = entity.productUseYn
                }
                if(entity.securities != null) {
                    securities = entity.securities?.map {
                        SecurityMapper.mappingGetSecurity(it)
                    }
                }
            }.build()
        }
    }
}