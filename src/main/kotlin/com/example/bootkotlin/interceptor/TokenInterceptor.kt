package com.example.bootkotlin.interceptor

import com.example.bootkotlin.controller.generalKey
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import java.io.PrintWriter
import java.lang.Exception
import java.security.SignatureException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

open class TokenInterceptor : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest?, response: HttpServletResponse?, handler: Any?): Boolean {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        response?.characterEncoding = "UTF-8"
        response?.contentType = "application/json"
        var out: PrintWriter? = null
        val authHeader = request?.getHeader("Authorization")
        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            throw ServletException("缺少认证信息")
        }
        val token = authHeader.substring(7)
        try {
            val claims = Jwts.parser().setSigningKey(generalKey()).parseClaimsJws(token).body
            request.setAttribute("claims", claims)
        } catch (e: SignatureException) {
            out = response?.writer
            out?.write("{签名错误}")
            out?.flush()
            return false
        } catch (e: ExpiredJwtException) {
            e.printStackTrace();
            out = response?.writer
            out?.write("{时间错误}")
            out?.flush()
            return false;
        } catch (e: MalformedJwtException) {
            out = response?.writer
            out?.write("{签名格式错误}")
            out?.flush()
            return false;
        } catch (e: Exception) {
            out = response?.writer
            out?.write("{验证失败}")
            out?.flush()
        } finally {
            out?.close()
        }
        return true
    }

    override fun postHandle(request: HttpServletRequest?, response: HttpServletResponse?, handler: Any?, modelAndView: ModelAndView?) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun afterCompletion(request: HttpServletRequest?, response: HttpServletResponse?, handler: Any?, ex: Exception?) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}