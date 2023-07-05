package kr.co.kakaopayinscorp.contract

import kr.co.kakaopayinscorp.contract.controller.contract.ContractControllerTest
import kr.co.kakaopayinscorp.contract.controller.estimation.EstimationControllerTest
import kr.co.kakaopayinscorp.contract.controller.product.ProductControllerTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.filter.CharacterEncodingFilter
import java.util.stream.Stream

@SpringBootTest
@AutoConfigureMockMvc
class ContractApplicationTests {

	@Autowired
	lateinit var mockMvc: MockMvc
	@Autowired
	lateinit var ctx: WebApplicationContext

	@BeforeEach
	fun setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
			.addFilter<DefaultMockMvcBuilder>(CharacterEncodingFilter("UTF-8", true))
			.build()
	}

	companion object
	{
		fun lottoNumbersAndRank(): Stream<Arguments?>? {
		return Stream.of(
			Arguments.arguments("1", arrayOf("1","2"), "3", "2023-06-01")
		)
	}

	}

	fun contextLoads() {
//		/**
//		 * 상품 테스트
//		 */
//		val productController = ProductControllerTest()
//		// 상품 조회
//		productController.getProduct(mockMvc)
//		/**
//		 * 계약 테스트
//		 */
//		val contractController = ContractControllerTest()
//		// 계약 생성
//		contractController.createContract(mockMvc)
//		// 계약 수정
//		contractController.updateContract(mockMvc)
//		/**
//		 * 예상 보험료 계산 테스트
//		 */
//		val estimationController = EstimationControllerTest()
//		estimationController.getEstimatedPremium(mockMvc)

	}
}
