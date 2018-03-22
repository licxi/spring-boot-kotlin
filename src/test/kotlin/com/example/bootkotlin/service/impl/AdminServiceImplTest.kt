package com.example.bootkotlin.service.impl

import com.example.bootkotlin.entity.master.Admin
import com.example.bootkotlin.service.AdminService
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class AdminServiceImplTest {

    @Autowired
    lateinit var adminService: AdminService

    @Test
    fun login() {
        val admin = Admin(name = "superAdmin", password = "\$2a\$10\$HdeTx4GbNht3OFjafNgWBen017HciUvNDRHMc6cIaaur5tfSA.tdi")
        println(adminService.login(admin))
    }

    @Test
    fun save() {
        val admin = Admin(name = "superAdmin", nickname = "超级管理员管理员", password = "417708459")
        adminService.register(admin)
    }
}
