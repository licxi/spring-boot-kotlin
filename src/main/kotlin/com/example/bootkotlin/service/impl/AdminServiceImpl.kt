package com.example.bootkotlin.service.impl

import com.example.bootkotlin.entity.master.Admin
import com.example.bootkotlin.repository.master.AdminRepo
import com.example.bootkotlin.service.AdminService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Example
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service("adminService")
class AdminServiceImpl : AdminService {

    @Autowired
    private lateinit var adminRepo: AdminRepo

    @Autowired
    lateinit var bCryptPasswordEncoder: BCryptPasswordEncoder


    override fun login(admin: Admin): Any? {
        val example: Example<Admin> = Example.of(admin)
        return try {
            adminRepo.findOne(example)
        } catch (e: Exception) {
            null
        }
    }


    //@PreAuthorize(AdminRole.addAdmin)
    @Transactional(readOnly = false)
    override fun register(admin: Admin): Boolean {
        admin.password = bCryptPasswordEncoder.encode(admin.password)
        return adminRepo.save(admin) != null
    }

    /*
    记录登录时间
     */
    override fun saveLastLoginTimeById(id: Long, date: Date): Boolean {
        val admin = adminRepo.findOne(id)
        admin.lastLoginTime = date //修改登录时间
        return adminRepo.save(admin) != null
    }

    override fun modifyInfo(admin: Admin): Boolean {
        return adminRepo.save(admin) != null
    }

    //
    override fun findOneByName(name: String?): Admin? {
        return adminRepo.findOneByName(name!!)
    }
}