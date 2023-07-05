package kr.co.kakaopayinscorp.contract.exception

/**
 * 계약 데이터 불일치시 예외처리
 */
class ContractDataInconsistencyException : Exception {
    var errorCode = 0

    constructor(message: String?) : super(message) {}
    constructor(errorCode: Int) : super() {
        this.errorCode = errorCode
    }

    constructor(message: String?, errorCode: Int) : super(message) {
        this.errorCode = errorCode
    }

}