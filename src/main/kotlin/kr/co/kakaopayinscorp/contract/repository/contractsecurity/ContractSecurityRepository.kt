package kr.co.kakaopayinscorp.contract.repository.contractsecurity

import kr.co.kakaopayinscorp.contract.domain.entity.contractsecurity.ContractSecurityEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ContractSecurityRepository: JpaRepository<ContractSecurityEntity, Long> {

}