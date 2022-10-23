package com.sweet.cloves.robbyassessment.network.response.base

data class Error(val statusCode: Int = 0,
                 val message: String? = null)