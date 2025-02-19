package com.crud_javatokotiln.global

import com.fasterxml.jackson.annotation.JsonProperty

data class ApiResponse<T>(
    @get:JsonProperty("isSuccess")
    val isSuccess: Boolean,
    val data: T?,
    val message: String
) {
    companion object {
        @JvmStatic
        fun <T> ok(data: T): ApiResponse<T> = ApiResponse(true, data, "Success")

        @JvmStatic
        fun <T> created(data: T): ApiResponse<T> = ApiResponse(true, data, "Created")

        @JvmStatic
        fun <T> fail(message: String): ApiResponse<T> = ApiResponse(false, null, message)
    }
}
