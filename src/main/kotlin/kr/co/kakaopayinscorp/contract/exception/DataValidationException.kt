package kr.co.kakaopayinscorp.contract.exception

/**
 * 인입된 데이터가 내부 데이터와 내용이 다를 시 예외 처리
 */
class DataValidationException : Exception {
    var errorCode = 0

    constructor(message: String?) : super(message) {}
    constructor(errorCode: Int) : super() {
        this.errorCode = errorCode
    }

    constructor(message: String?, errorCode: Int) : super(message) {
        this.errorCode = errorCode
    }

}