package com.example.bootkotlin.repository.master

import com.example.bootkotlin.entity.master.Admin
import org.springframework.data.jpa.repository.JpaRepository

interface AdminRepo : JpaRepository<Admin, Long> {
    fun findOneByName(name: String): Admin
}