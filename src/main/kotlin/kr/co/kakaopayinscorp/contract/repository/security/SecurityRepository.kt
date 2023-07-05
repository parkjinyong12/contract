package kr.co.kakaopayinscorp.contract.repository.security

import kr.co.kakaopayinscorp.contract.domain.entity.security.SecurityEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface SecurityRepository: JpaRepository<SecurityEntity, Long> {
    fun existsBySecurityIdIn(securityIds: List<Long>): Optional<Boolean>
}