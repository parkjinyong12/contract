package kr.co.kakaopayinscorp.contract.controller.product

import org.aspectj.lang.annotation.Before
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultHandler
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*
import java.util.stream.Stream

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    private val logger = LoggerFactory.getLogger(ProductControllerTest::class.java)

    @Autowired
    lateinit var mockMvc: MockMvc

    companion object {
        @JvmStatic
        fun getProduct(): Stream<Arguments?>? {
            return Stream.of(
                Arguments.arguments()
            )
        }
    }

    @ParameterizedTest
    @MethodSource("getProduct")
    fun getProduct() {
        mockMvc.perform(MockMvcRequestBuilders.get("/product"))
            .andExpect(status().isOk)
            .andDo(ResultHandler {
                logger.info("상품 조회 테스트 성공: {}",it.response.contentAsString)
            })
    }
}