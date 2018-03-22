package com.example.bootkotlin.handler

import org.springframework.security.core.Authentication
import org.springframework.security.web.DefaultRedirectStrategy
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class LoginSuccessHandler : SavedRequestAwareAuthenticationSuccessHandler() {
    private val redirectStrategy = DefaultRedirectStrategy()
    override fun onAuthenticationSuccess(request: HttpServletRequest?, response: HttpServletResponse?, authentication: Authentication?) {
        //super.onAuthenticationSuccess(request, response, authentication)
        request?.setAttribute("authentication", authentication)
        redirectStrategy.sendRedirect(request, response, "/admin/login_success")//自定义登录成功处理
    }
}
