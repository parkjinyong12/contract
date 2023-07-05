package kr.co.kakaopayinscorp.contract.controller.estimation

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultHandler
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.util.LinkedMultiValueMap
import java.util.*
import java.util.stream.Stream

@SpringBootTest
@AutoConfigureMockMvc
class EstimationControllerTest {

    private val logger = LoggerFactory.getLogger(EstimationControllerTest::class.java)

    @Autowired
    lateinit var mockMvc: MockMvc

    companion object {
        @JvmStatic
        fun getEstimatedPremium(): Stream<Arguments?>? {
            return Stream.of(
                Arguments.arguments(
                    "1",
                    Arrays.asList("1","2"),
                    "3"
                ),
            )
        }
    }

    @ParameterizedTest
    @MethodSource("getEstimatedPremium")
    fun getEstimatedPremium(productId: String, securityIdList: List<String>, contractPeriod: String) {
        val params = LinkedMultiValueMap<String, String>()
        params["productId"] = productId
        params["securityIdList"] = securityIdList
        params["contractPeriod"] = contractPeriod
        mockMvc.perform(MockMvcRequestBuilders.get("/estimation/premium")
            .contentType(MediaType.APPLICATION_JSON)
            .params(params))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(ResultHandler {
                logger.info("예상 보험료 조회 테스트 성공: {}",it.response.contentAsString)
            }).andReturn()
    }

}