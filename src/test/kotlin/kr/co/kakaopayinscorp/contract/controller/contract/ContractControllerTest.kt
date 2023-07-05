package kr.co.kakaopayinscorp.contract.controller.contract

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultHandler
import org.springframework.test.web.servlet.ResultMatcher
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.util.LinkedMultiValueMap
import java.util.*
import java.util.stream.Stream

@SpringBootTest
@AutoConfigureMockMvc
class ContractControllerTest {

    private val logger = LoggerFactory.getLogger(ContractControllerTest::class.java)

    @Autowired
    lateinit var mockMvc: MockMvc
    companion object {
        @JvmStatic
        fun createContract(): Stream<Arguments> {
            return Stream.of(
                Arguments.arguments(
                    "1",
                    Arrays.asList("1","2"),
                    "3",
                    "2023-06-01"
                ),
            )
        }
        @JvmStatic
        fun updateContract(): Stream<Arguments> {
            return Stream.of(
                Arguments.arguments(
                    "6",
                    Arrays.asList("1"),
                    Arrays.asList("2"),
                    "3",
                    "001"
                ),
            )
        }
        @JvmStatic
        fun getContract(): Stream<Arguments> {
            return Stream.of(
                Arguments.arguments(
                    "6"
                ),
            )
        }
    }

    @ParameterizedTest
    @MethodSource("createContract")
    fun createContract(productId: String, subscriptionSecurityIds: List<String>, contractPeriod: String, insuranceStartDate: String) {
        val params = mutableMapOf<String, Any>()
        params["productId"] = productId
        params["subscriptionSecurityIds"] = subscriptionSecurityIds
        params["contractPeriod"] = contractPeriod
        params["insuranceStartDate"] = insuranceStartDate
        mockMvc.perform(MockMvcRequestBuilders.post("/contract")
            .contentType(MediaType.APPLICATION_JSON)
            .content(ObjectMapper().writeValueAsString(params)))
            .andExpect(status().isOk)
            .andDo(ResultHandler {
                logger.info("계약 생성 테스트 성공")
            })
    }

    @ParameterizedTest
    @MethodSource("updateContract")
    fun updateContract(
        contractId: String,
        addSecurityIds: List<String>,
        removeSecurityIds: List<String>,
        contractPeriod: String,
        contractStatus: String
    ) {
        val params = mutableMapOf<String, Any>()
        params["contractId"] = contractId
        params["addSecurityIds"] = addSecurityIds
        params["removeSecurityIds"] = removeSecurityIds
        params["contractPeriod"] = contractPeriod
        params["contractStatus"] = contractStatus
        mockMvc.perform(MockMvcRequestBuilders.patch("/contract")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .content(ObjectMapper().writeValueAsString(params)))
            .andExpect(status().isOk)
            .andDo(ResultHandler {
                logger.info("계약 수정 테스트 성공")
            })
    }

    @ParameterizedTest
    @MethodSource("getContract")
    fun getContract(productId: String) {
        val params = LinkedMultiValueMap<String, String>()
        params["contractId"] = productId
        mockMvc.perform(MockMvcRequestBuilders.get("/contract")
            .contentType(MediaType.APPLICATION_JSON)
            .params(params))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(ResultHandler {
                logger.info("계약 생성 테스트 성공")
            })
    }
}