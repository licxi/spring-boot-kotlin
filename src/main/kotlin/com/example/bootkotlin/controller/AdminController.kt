package com.example.bootkotlin.controller

import com.example.bootkotlin.entity.master.Admin
import com.example.bootkotlin.entity.ResultResponse
import com.example.bootkotlin.service.AdminService
import com.example.bootkotlin.service.ExamService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

@Controller
@RequestMapping("/admin")
class AdminController : BaseController() {
    @Autowired
    lateinit var adminService: AdminService

    @Autowired
    lateinit var examService: ExamService

    @Autowired
    lateinit var bCryptPasswordEncoder: BCryptPasswordEncoder


    @GetMapping("", "/login")
    fun login(): String {
        val auth = SecurityContextHolder.getContext().authentication
        val principal = auth.principal
        if (principal is UserDetails) {
            return "redirect:/admin/home"
        }

        return "admin/login"
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    @GetMapping("/home")
    fun home(session: HttpSession, map: MutableMap<String, Any?>): String {
        /*val admin = session.getAttribute("admin")
        return if (admin == null || admin !is Admin) {
            "redirect:/admin"
        } else {
            map["admin"] = admin
            map["exam_count"] = examService.examsCount(false)
            map["processing_exam_count"] = examService.examsCount(true)
            "admin/index"
        }*/
        val auth = SecurityContextHolder.getContext().authentication
        val principal = auth.principal
        if (principal is UserDetails) {
            map["admin"] = adminService.findOneByName(principal.username)
            map["exam_count"] = examService.examsCount(false)
            map["processing_exam_count"] = examService.examsCount(true)
            return "admin/index"
        }

        return "admin/login"
    }

    @ResponseBody
    @GetMapping("login_failure")
    fun loginFailure(): ResultResponse<Any> {
        return ResultResponse(code = -1, message = "账号或密码错误")
    }

    @GetMapping("/logout")
    fun logout(request: HttpServletRequest, response: HttpServletResponse): String {
        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication != null) {
            SecurityContextLogoutHandler().logout(request, response, authentication)
        }
        return "redirect:/admin/login"
    }

    @ResponseBody
    @GetMapping("/Access_Denied")
    fun accessDenied(): ResultResponse<Any> {
        return ResultResponse(code = 403, message = "权限不足，不能操作")
    }

    @ResponseBody
    @PostMapping("/modifyPassword")
    fun modifyPassword(@RequestParam("old_password") oldPassword: String,
                       @RequestParam("new_password") newPassword: String,
                       session: HttpSession): ResultResponse<out Any> {
        val admin = session.getAttribute("admin")
        return if (admin != null && admin is Admin) {
            if (admin.password.equals(oldPassword)) {
                admin.password = newPassword
                if (adminService.modifyInfo(admin)) {
                    ResultResponse(message = "密码修改成功")
                } else {
                    ResultResponse(code = -1, message = "密码修改失败")
                }
            } else {
                ResultResponse(0, "当前密码错误")
            }
        } else {
            ResultResponse(0, "你还没登录，不能修改密码")
        }

    }

    @ResponseBody
    @PostMapping("/modifyNickname")
    fun modifyNickname(@RequestParam("nickname") nickname: String,
                       session: HttpSession): ResultResponse<out Any> {
        val admin = session.getAttribute("admin")
        return if (admin != null && admin is Admin) {
            if (adminService.modifyInfo(admin)) {
                ResultResponse(message = "名称修改成功")
            } else {
                ResultResponse(code = -1, message = "名称修改失败")
            }
        } else {
            ResultResponse(0, "你还没登录，不能修改密码")
        }

    }


//    @ResponseBody
//    @PostMapping("/login")
//    fun login(admin: Admin, session: HttpSession): ResultResponse<out Any> {
//        val admin = adminService.login(admin)
//        return if (admin != null && admin is Admin) {
//            session.setAttribute("admin", admin) //保存登录信息
//            //adminService.saveLastLoginTimeById(admin.id!!, Date().time)
//            admin.lastLoginTime = Date().time
//            adminService.modifyInfo(admin)
//            ResultResponse(code = 200, message = "登录成功", result = admin)
//        } else {
//            ResultResponse(code = -1, message = "账号或密码错误")
//        }
//    }

    @ResponseBody
    @GetMapping("/login_success")
    fun loginSuccess(request: HttpServletRequest, session: HttpSession, authentication: Authentication?): ResultResponse<out Any> {
        if (authentication == null) {
            return ResultResponse(code = -1, message = "账号或密码错误")
        }
        val loginUser = authentication.principal
        return if (loginUser != null && loginUser is UserDetails) {
            val admin = adminService.findOneByName(loginUser.username)
            //adminService.saveLastLoginTimeById(admin.id!!, Date().time)
            admin?.lastLoginTime = Date()
            session.setAttribute("admin", admin) //保存登录信息
            adminService.modifyInfo(admin!!)
            ResultResponse(code = 200, message = "登录成功", result = admin)
        } else {
            ResultResponse(code = -1, message = "账号或密码错误")
        }
    }

    @PostMapping("/register")
    fun register(admin: Admin): ResultResponse<out Any> {

        //todo 判断输入是否正确
        return if (adminService.register(admin)) {
            ResultResponse(200, "注册成功")
        } else {
            ResultResponse(400, "注册失败")
        }
    }
}