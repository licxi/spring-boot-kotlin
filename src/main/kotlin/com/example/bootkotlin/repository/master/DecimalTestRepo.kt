package com.example.bootkotlin.repository.master

import com.example.bootkotlin.entity.master.DecimalTest
import org.springframework.data.jpa.repository.JpaRepository

interface DecimalTestRepo : JpaRepository<DecimalTest, Long> {
    fun <T> findMoneyById(id: Long, clazz: Class<T>): T
}