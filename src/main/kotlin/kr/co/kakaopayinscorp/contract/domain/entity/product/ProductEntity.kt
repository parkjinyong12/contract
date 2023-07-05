package kr.co.kakaopayinscorp.contract.domain.entity.product

import jakarta.persistence.*
import kr.co.kakaopayinscorp.contract.domain.entity.security.SecurityEntity
import java.time.LocalDateTime

@Entity(name = "product")
class ProductEntity {

    /**
     * 상품 번호
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var productId: Long = 0

    /**
     * 상품 명
     */
    var productName: String? = null

    /**
     * 최소 가입기간
     */
    var minSubscriptionPeriod: Int = 9999

    /**
     * 최대 가입기간
     */
    var maxSubscriptionPeriod: Int = 0

    /**
     * 상품 사용가능 여부
     */
    var productUseYn: Boolean = false

    /**
     * 생성시간
     */
    var productCreateDatetime: LocalDateTime = LocalDateTime.now()

    @OneToMany
    @JoinColumn(name = "productId")
    var securities: List<SecurityEntity>? = listOf<SecurityEntity>()
}