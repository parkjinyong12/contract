package kr.co.kakaopayinscorp.contract.mapper

import kr.co.kakaopayinscorp.contract.domain.entity.security.SecurityEntity
import kr.co.kakaopayinscorp.contract.domain.service.security.GetSecurity

class SecurityMapper {
    companion object {

        /**
         * 담보 엔티티 -> 담보 서비스
         */
        fun mappingGetSecurity(entity: SecurityEntity): GetSecurity {
            return GetSecurity.Builder().apply {
                securityId = entity.securityId
                securityName = entity.securityName
                subscriptionAmount = entity.subscriptionAmount
                standardAmount = entity.standardAmount
                securityCreateDatetime = entity.securityCreateDatetime
            }.build()
        }
    }
}