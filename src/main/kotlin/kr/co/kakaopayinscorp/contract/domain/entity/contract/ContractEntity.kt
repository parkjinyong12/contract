package kr.co.kakaopayinscorp.contract.domain.entity.contract

import jakarta.persistence.*
import kr.co.kakaopayinscorp.contract.domain.entity.contractsecurity.ContractSecurityEntity
import java.time.LocalDate
import java.time.LocalDateTime

@Entity(name = "contract")
class ContractEntity {
    /**
     * 계약번호
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var contractId: Long = 0

    /**
     * 상품번호
     */
    var productId: Long = 0

    /**
     * 계약기간
     */
    var contractPeriod: Int = 0

    /**
     * 보험시작일
     */
    lateinit var insuranceStartDate: LocalDate

    /**
     * 보험종료일
     */
    lateinit var insuranceEndDate: LocalDate

    /**
     * 총 보험료
     */
    var totalInsuranceFee: Double = 0.0

    /**
     * 계약상태(정상계약/청약철회/기간만료)
     */
    lateinit var contractStatus: String

    /**
     * 생성시각
     */
    var contract_create_datetime: LocalDateTime = LocalDateTime.now()

    /**
     * 수정시각
     */
    var contract_update_datetime: LocalDateTime = LocalDateTime.now()

    /**
     * 계약 담보
     */
    @OneToMany
    @JoinColumn(name = "contractId")
    var contractSecurities: List<ContractSecurityEntity>? = listOf<ContractSecurityEntity>()
}