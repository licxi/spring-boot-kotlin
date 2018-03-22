package com.example.bootkotlin.controller

import com.example.bootkotlin.entity.master.Admin
import com.example.bootkotlin.entity.master.Exam
import com.example.bootkotlin.entity.ResultResponse
import com.example.bootkotlin.service.ExamService
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpSession

@Controller
@RequestMapping("/admin/exam")
class ExamController(private val examService: ExamService) :BaseController(){
//    @Autowired
//    lateinit var examService: ExamService

    @GetMapping("")
    fun examList(@RequestParam("page", required = false) page: Int?,
                 @RequestParam("size", required = false) size: Int?,
                 @RequestParam("title", required = false) title: String?,
                 map: MutableMap<String, Any?>): String {
        println("分页数据（$page:$size）")
        var p = page ?: 1
        var s = size ?: 2

        val t = title ?: ""
        if (p <= 0) {
            p = 1
        }
        if (s <= 0) {
            s = 2
        }
        val exams = examService.examList(p - 1, s, t)
        //val e = exams.toMutableList()
//        e.add(Exam(1, "这是考试", Date(), Date(Date().time + 3600 * 10), 1, "超级管理员", 10))
//        e.add(Exam(2, "这是考试", Date(), Date(Date().time + 3600 * 10), 0, "管理员", 10))
//        e.add(Exam(3, "这是考试", Date(), Date(Date().time + 3600 * 10), 0, "超级管理员", 10))
//        e.add(Exam(4, "这是考试", Date(), Date(Date().time + 3600 * 10), 1, "管理员", 10))


        map["exams"] = exams
        map["title"] = t
        return "admin/exam/exam_list"
    }

    @GetMapping("/add")
    fun toAddExam(): String {
        return "admin/exam/exam_add"
    }

    @GetMapping("/edit/{id}")
    fun toEditExam(@PathVariable("id", required = true) id: Long, modelMap: ModelMap): String {
        modelMap["exam"] = examService.findOneById(id = id)
        //modelMap["exam"] = Exam(1, "这是考试", Date(), Date(Date().time + 3600 * 100), 1, "超级管理员", 10)
        return "admin/exam/exam_add"
    }


    @ResponseBody
    @PostMapping("/save")
    fun saveExam(exam: Exam?, session: HttpSession?): ResultResponse<out Any> {
        return if (exam != null && session != null && exam.endTime?.after(exam.startTime)!!) {
            if (exam.id == null) {
                val admin = session.getAttribute("admin") as Admin
                exam.author = admin.nickname
            }
            if (examService.save(exam)) {
                ResultResponse(200, "更新成功了", result = exam)
            } else {
                ResultResponse(-1, "更新失败了")
            }
        } else {
            ResultResponse(-1, "更新失败了")
        }
    }

    @ResponseBody
    @PostMapping("delete/{id}")
    fun deleteExam(@PathVariable("id", required = true) examId: Long): ResultResponse<out Any> {
        return if (examService.delete(examId)) {
            ResultResponse(200, "删除成功")
        } else {
            ResultResponse(-1, "删除失败")
        }
    }
}