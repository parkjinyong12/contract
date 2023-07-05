package kr.co.kakaopayinscorp.contract.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.io.FileNotFoundException

@ControllerAdvice
class ExceptionHandlers {

    /**
     * 계약 데이터 불일치
     */
    @ExceptionHandler(ContractDataInconsistencyException::class)
    fun handleContractDataInconsistencyException(e: ContractDataInconsistencyException): ResponseEntity<*> {
        return ResponseEntity<Any?>(e.message, HttpStatus.VARIANT_ALSO_NEGOTIATES)
    }

    /**
     * 계약 만료
     */
    @ExceptionHandler(ContractExpirationException::class)
    fun handleContractExpirationException(e: ContractExpirationException): ResponseEntity<*> {
        return ResponseEntity<Any?>(e.message, HttpStatus.BAD_REQUEST)
    }

    /**
     * 계약 조건 불일치
     */
    @ExceptionHandler(ContractTermsInconsistencyException::class)
    fun handleContractTermsInconsistencyException(e: ContractTermsInconsistencyException): ResponseEntity<*> {
        return ResponseEntity<Any?>(e.message, HttpStatus.BAD_REQUEST)
    }

    /**
     * 인입된 데이터가 내부 데이터와 내용이 다름
     */
    @ExceptionHandler(DataValidationException::class)
    fun handleDataValidationException(e: DataValidationException): ResponseEntity<*> {
        return ResponseEntity<Any?>(e.message, HttpStatus.BAD_REQUEST)
    }

    /**
     * 기타 예외
     */
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<*> {
        return ResponseEntity<Any?>(e.message, HttpStatus.BAD_REQUEST)
    }
}