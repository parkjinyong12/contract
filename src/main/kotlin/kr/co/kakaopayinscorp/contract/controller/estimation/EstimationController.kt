package kr.co.kakaopayinscorp.contract.controller.estimation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kr.co.kakaopayinscorp.contract.domain.entity.contract.ContractEntity
import kr.co.kakaopayinscorp.contract.domain.service.estimation.EstimationPremiumResult
import kr.co.kakaopayinscorp.contract.domain.service.estimation.GetEstimationPremium
import kr.co.kakaopayinscorp.contract.domain.view.estimation.GetEstimationPremiumView
import kr.co.kakaopayinscorp.contract.mapper.ContractMapper
import kr.co.kakaopayinscorp.contract.mapper.EstimationMapper
import kr.co.kakaopayinscorp.contract.service.estimation.EstimationPremiumService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/estimation"], produces = [MediaType.APPLICATION_JSON_VALUE])
@Tag(name = "예상 보험료", description = "예상 보험료 계산 관련 API")
class EstimationController {

    private val logger = LoggerFactory.getLogger(EstimationController::class.java)

    @Autowired
    lateinit var estimationPremiumService: EstimationPremiumService
    /**
     * 총 보험료 계산
     */
    @RequestMapping(method = [RequestMethod.GET], value = ["/premium"])
    @Operation(summary = "가입전 예상 총 보험료 계산", description = "예상 총 보험료 계산 결과를 반환합니다. <br>상품 코드는 GET /product 전체 상품조회 기능을 이용 바랍니다.")
    fun getEstimatedPremium(@Parameter(description = "상품 코드") @RequestParam productId: String,
                            @Parameter(description = "담보 코드 리스트") @RequestParam securityIdList: List<String>,
                            @Parameter(description = "계약 기간") @RequestParam contractPeriod: String, httpServletRequest: HttpServletRequest, httpServletResponse: HttpServletResponse): EstimationPremiumResult {
        val view = GetEstimationPremiumView().apply {
            this.productId = productId
            this.securityIdList = securityIdList
            this.contractPeriod = contractPeriod
        }
        val model = EstimationMapper.mappingGetEstimationPremium(view)
        return estimationPremiumService.getEstimationPremium(model)
    }
}