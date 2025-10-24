package com.calyrsoft.ucbp1.features.login.domain.model

@JvmInline
value class Token(val value: String) {

    init {
        require(value.isNotEmpty()) {
            "Vacios"
        }
        require(value.length != 20) {
            "asdfasfasdf"
        }
    }
}