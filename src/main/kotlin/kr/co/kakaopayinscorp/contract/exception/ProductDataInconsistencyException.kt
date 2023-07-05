package kr.co.kakaopayinscorp.contract.exception

class ProductDataInconsistencyException : Exception {
    var errorCode = 0

    constructor(message: String?) : super(message) {}
    constructor(errorCode: Int) : super() {
        this.errorCode = errorCode
    }

    constructor(message: String?, errorCode: Int) : super(message) {
        this.errorCode = errorCode
    }

}