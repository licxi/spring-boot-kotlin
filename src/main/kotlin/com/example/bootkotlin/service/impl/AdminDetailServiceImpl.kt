package com.example.bootkotlin.service.impl

import com.example.bootkotlin.entity.master.Admin
import com.example.bootkotlin.service.AdminService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.*


@Service("adminDetailService")
class AdminDetailServiceImpl : UserDetailsService {

    @Autowired
    lateinit var adminService: AdminService

    override fun loadUserByUsername(username: String?): UserDetails {
        val admin = adminService.findOneByName(username)
        if (admin == null) {
            throw UsernameNotFoundException("没有该用户")
        } else {
            return User(admin.name, admin.password, getGrantedAuthorities(admin))
        }

    }

    /**
     * 设置权限
     */
    private fun getGrantedAuthorities(admin: Admin): List<GrantedAuthority>? {
        val authorities = ArrayList<GrantedAuthority>()
        val roles = admin.roles
        roles?.forEach { authorities.add(SimpleGrantedAuthority("ROLE_" + it.name)) }
        return authorities
    }
}