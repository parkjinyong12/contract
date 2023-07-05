package kr.co.kakaopayinscorp.contract.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
    @Bean
    fun openAPI(): OpenAPI = OpenAPI()
        .components(Components())
        .info(apiInfo())

    private fun apiInfo() = Info()
        .title("카카오손해보험 사전과제 테스트")
        .description("보험사 계약관리 API시스템 구축")
        .version("1.0.0")
        .contact(Contact().name("박진용").email("bh9578@gmail.com"))
}