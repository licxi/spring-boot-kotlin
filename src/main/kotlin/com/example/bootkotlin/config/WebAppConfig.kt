package com.example.bootkotlin.config

import com.example.bootkotlin.interceptor.TokenInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

@Configuration
class WebAppConfig : WebMvcConfigurerAdapter() {

    override fun addInterceptors(registry: InterceptorRegistry?) {
        registry?.addInterceptor(TokenInterceptor())?.addPathPatterns("/user")
        super.addInterceptors(registry)
    }
}