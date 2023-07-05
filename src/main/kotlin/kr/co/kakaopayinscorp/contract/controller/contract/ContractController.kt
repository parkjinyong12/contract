package kr.co.kakaopayinscorp.contract.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kr.co.kakaopayinscorp.contract.config.ConstObject
import kr.co.kakaopayinscorp.contract.domain.entity.contract.ContractEntity
import kr.co.kakaopayinscorp.contract.domain.view.Response
import kr.co.kakaopayinscorp.contract.domain.view.contract.CreateContractView
import kr.co.kakaopayinscorp.contract.domain.view.contract.GetContractView
import kr.co.kakaopayinscorp.contract.domain.view.contract.UpdateContractView
import kr.co.kakaopayinscorp.contract.mapper.ContractMapper
import kr.co.kakaopayinscorp.contract.service.contract.ContractManageService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/contract"])
@Tag(name = "계약", description = "계약 관련 API")
class ContractController {

    private val logger = LoggerFactory.getLogger(ContractController::class.java)

    @Autowired
    lateinit var contractManageService: ContractManageService

    /**
     * 계약 생성
     */
    @RequestMapping(method = [RequestMethod.POST])
    @Operation(summary = "계약 생성", description = "계약 정보를 생성합니다.")
    fun createContract(@RequestBody view: CreateContractView, httpServletRequest: HttpServletRequest, httpServletResponse: HttpServletResponse): Response {
        val model = ContractMapper.mappingCreateContract(view)
        contractManageService.createContract(model)
        return Response(ConstObject.RESPONSE_CODE_OK, ConstObject.RESPONSE_CODE_OK_MESSAGE)
    }

    /**
     * 계약 조회
     */
    @RequestMapping(method = [RequestMethod.GET])
    @Operation(summary = "계약 조회", description = "계약 정보를 조회합니다.")
    @Parameter(name = "contractId", description="계약번호")
    fun getContract(@RequestParam(value = "contractId") contractId: String, httpServletRequest: HttpServletRequest, httpServletResponse: HttpServletResponse): ContractEntity {
        val view = GetContractView().apply {
            this.contractId = contractId
        }
        val model = ContractMapper.mappingGetContract(view)
        return contractManageService.getContract(model)
    }

    /**
     * 계약 수정
     */
    @RequestMapping(method = [RequestMethod.PATCH])
    @Operation(summary = "계약 수정", description = "계약 정보를 수정합니다." )
    fun updateContract(@RequestBody view: UpdateContractView, httpServletRequest: HttpServletRequest, httpServletResponse: HttpServletResponse): Response {
        val model = ContractMapper.mappingUpdateContract(view)
        contractManageService.updateContract(model)
        return Response(ConstObject.RESPONSE_CODE_OK, ConstObject.RESPONSE_CODE_OK_MESSAGE)
    }
}