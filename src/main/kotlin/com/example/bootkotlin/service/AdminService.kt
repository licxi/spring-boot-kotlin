package com.example.bootkotlin.service

import com.example.bootkotlin.entity.master.Admin
import org.springframework.transaction.annotation.Transactional
import java.util.*


/**
 *  1 ） 获取单个对象的方法用 get 做前缀。
2 ） 获取多个对象的方法用 list 做前缀。
3 ） 获取统计值的方法用 count 做前缀。
4 ） 插入的方法用 save/insert 做前缀。
5 ） 删除的方法用 remove/delete 做前缀。
6 ） 修改的方法用 update 做前缀。
 */
@Transactional(readOnly = true)
interface AdminService {
    fun login(admin: Admin): Any?
    fun register(admin: Admin): Boolean
    fun saveLastLoginTimeById(id: Long, date: Date): Boolean
    fun modifyInfo(admin: Admin): Boolean
    fun findOneByName(name: String?): Admin?
}