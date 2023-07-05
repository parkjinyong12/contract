package kr.co.kakaopayinscorp.contract.domain.entity.contractsecurity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity(name = "contractSecurity")
class ContractSecurityEntity {
    /**
     * 계약담보 번호
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var contract_security_id: Long = 0

    /**
     * 계약번호
     */
    var contractId: Long? = null

    /**
     * 담보번호
     */
    var securityId: Long? = null
}