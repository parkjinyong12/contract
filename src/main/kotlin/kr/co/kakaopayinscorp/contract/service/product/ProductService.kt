package kr.co.kakaopayinscorp.contract.service.product

import kr.co.kakaopayinscorp.contract.domain.service.product.GetProduct
import kr.co.kakaopayinscorp.contract.exception.ProductDataInconsistencyException
import kr.co.kakaopayinscorp.contract.mapper.ProductMapper
import kr.co.kakaopayinscorp.contract.repository.product.ProductRepository
import kr.co.kakaopayinscorp.contract.repository.security.SecurityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProductService {

    @Autowired
    lateinit var productRepository: ProductRepository

    @Autowired
    lateinit var securityRepository: SecurityRepository

    fun getProducts(): List<GetProduct> {
        return productRepository.findAll().map { productEntity ->
            ProductMapper.mappingGetProduct(productEntity)
        }
    }

    fun getProduct(productId: Long): GetProduct {
        return productRepository.findById(productId).let { optional ->
            if(optional.isPresent) {
                ProductMapper.mappingGetProduct(optional.get())
            } else {
                // 상품Id가 실제로 존재하지 않는 상품Id인 경우
                throw ProductDataInconsistencyException("인입된 데이터가 실제 정보와 일치하지 않습니다.")
            }
        }
    }
}