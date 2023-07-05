package kr.co.kakaopayinscorp.contract.repository.contract

import kr.co.kakaopayinscorp.contract.domain.entity.contract.ContractEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ContractRepository: JpaRepository<ContractEntity, Long> {

}