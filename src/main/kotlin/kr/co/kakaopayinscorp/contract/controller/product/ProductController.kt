package kr.co.kakaopayinscorp.contract.controller.product

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kr.co.kakaopayinscorp.contract.domain.service.product.GetProduct
import kr.co.kakaopayinscorp.contract.service.product.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/product"])
@Tag(name = "상품", description = "상품 관련 API")
class ProductController {

    @Autowired
    lateinit var productService: ProductService

    @RequestMapping(method = [RequestMethod.GET])
    @Operation(summary = "상품 조회", description = "상품 정보를 조회합니다.")
    fun getProduct(httpServletRequest: HttpServletRequest, httpServletResponse: HttpServletResponse): List<GetProduct> {
        return productService.getProducts()
    }
}