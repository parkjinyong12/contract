package kr.co.kakaopayinscorp.contract.domain.entity.security

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity(name = "security")
class SecurityEntity {

    /**
     * 담보번호
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var securityId: Long = 0

    /**
     * 담보명
     */
    var securityName: String? = null

    /**
     * 담보가 속한 상품의 번호. 상품에 속한 담보로 기획(추후, 상품에 종속되지 않도록 상품과 분리할 예정)
     */

    var productId: Long? = null
    /**
     * 가입금액
     */

    var subscriptionAmount: Long = 0
    /**
     * 기준금액
     */

    var standardAmount: Long = 0
    /**
     * 정보 생성 시간
     */

    var securityCreateDatetime: LocalDateTime = LocalDateTime.now()
}