package kr.co.kakaopayinscorp.contract.repository.product

import kr.co.kakaopayinscorp.contract.domain.entity.product.ProductEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository: JpaRepository<ProductEntity, Long> {

}