package com.example.bootkotlin.handler

import com.example.bootkotlin.entity.ResultResponse
import org.apache.catalina.servlet4preview.http.HttpServletRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.NoHandlerFoundException
import java.lang.Exception
import javax.servlet.http.HttpServletResponse

/**
 * 全局异常处理
 */
@RestControllerAdvice
class GlobalExceptionHandler {
    private val logger: Logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler
    fun processException(e: Exception, request: HttpServletRequest, response: HttpServletResponse): ResultResponse<out Any> {
        e.printStackTrace()
        e.toString()
        logger.error(e.message)
        if (e is AccessDeniedException) {
            return ResultResponse(403, "权限不足，不能操作${e.message}")
        }
        if (e is NoHandlerFoundException) {
            return ResultResponse(404, "你请求的网址不纯在")
        }
        return ResultResponse(500, e.message!!)
    }
}