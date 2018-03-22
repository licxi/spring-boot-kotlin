package com.example.bootkotlin.handler

import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.DefaultRedirectStrategy
import org.springframework.security.web.access.AccessDeniedHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AccessDeniedHandlerImpl : AccessDeniedHandler {
    private val redirectStrategy = DefaultRedirectStrategy()
    override fun handle(request: HttpServletRequest?, response: HttpServletResponse?, accessDeniedException: AccessDeniedException?) {
        redirectStrategy.sendRedirect(request, response, "/admin/Access_Denied")
    }
}