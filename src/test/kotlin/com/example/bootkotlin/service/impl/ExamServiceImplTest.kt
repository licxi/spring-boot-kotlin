package com.example.bootkotlin.service.impl

import com.example.bootkotlin.service.ExamService
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner::class)
@SpringBootTest
class ExamServiceImplTest {

    @Autowired
    lateinit var examService: ExamService

    @Test
    fun save() {
        //val exam = Exam(title="这是考试", startTime = Date(), endTime = Date(Date().time + 3600 * 10), status = 1, author = "超级管理员")
//        val a = examService.save(exam)
//        println("插入状态:$a")
//        assertTrue("插入失败",a)
//        exam.id=1
//        exam.title = "期末考试"
//        val exam = Exam()
//        exam.id=1
//        exam.title="这是更新后的题目"
//        val b = examService.save(exam)
//        println("更新状态:$b")
//        assertTrue("更新失败",b)
        println("nihao")
    }

    @Test
    fun findAll() {
        val pages = examService.examList(3, 8)
        println("分页查询当前的页面：" + pages?.number)
        println("分页查询多少列数据：" + pages?.size)
        println("数据库数据总数" + pages?.totalElements)
        println("每页${pages?.size}列时有多少页数据" + pages?.totalPages)
        println("实际查询出来多少列" + pages?.numberOfElements)
    }

    @Test
    fun findAllByTitle() {
        val pages = examService.examList(0, 2)
        println("分页查询当前的页面：" + pages?.number)
        println("分页查询多少列数据：" + pages?.size)
        println("数据库数据总数" + pages?.totalElements)
        println("每页${pages?.size}列时有多少页数据" + pages?.totalPages)
        println("实际查询出来多少列" + pages?.numberOfElements)
        println(pages?.content)
    }
}
