package com.example.bootkotlin.controller

import com.example.bootkotlin.entity.ResultResponse
import com.example.bootkotlin.entity.master.Question
import com.example.bootkotlin.service.QuestionService
import com.example.bootkotlin.utils.ExcelUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.util.Base64Utils
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.IOException
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession


@Controller
@RequestMapping("/admin/question")
class QuestionController {

    @Autowired
    lateinit var questionService: QuestionService

    @GetMapping("/modify")
    @ResponseBody
    fun modify(): String {
        val question = Question(id = 7, title = "这是更新后的题目")
        val result = questionService.save(question)
        return result.toString()
    }

    @GetMapping("/examId/{examId}")
    fun questionList(@PathVariable("examId", required = true) examId: Long,
                     @RequestParam("page", required = false) page: Int?,
                     @RequestParam("size", required = false) size: Int?,
                     @RequestParam("title", required = false) title: String?,
                     modelMap: ModelMap): String {
        var p = page ?: 1
        var s = size ?: 2
        if (p <= 0) {
            p = 1
        }
        if (s <= 0) {
            s = 2
        }
        modelMap["questions"] = if (title == null) {
            questionService.findAllByExamId(examId, p - 1, s)
        } else {
            questionService.findAllByExamIdAndTitleContaining(examId, title, p - 1, s)
        }
        modelMap["examId"] = examId
        modelMap["title"] = title
        return "/admin/question/question_list"
    }

    @GetMapping("/add")
    fun toAddQuestion(@RequestParam("examId", required = true) examId: Long, modelMap: ModelMap): String {
        modelMap["examId"] = examId
        return "admin/question/question_add"
    }

    @GetMapping("/import")
    fun toImportQuestion(@RequestParam("examId", required = true) examId: Long, modelMap: ModelMap): String {
        modelMap["examId"] = examId
        return "admin/question/question_import"
    }

    @PostMapping("/import")
    @ResponseBody
    fun doImportQuestion(@RequestParam("examId", required = true) examId: Long,
                         @RequestParam("excelData", required = true) file: MultipartFile,
                         session: HttpSession,
                         request: HttpServletRequest
                         , modelMap: ModelMap): ResultResponse<out Any> {
        //file.transferTo()
        //ExcelUtil.importQuestionsFromExcel(file =file.originalFilename)
        //var response: ResultResponse<out Any>
        println(file.name)
        println(file.originalFilename)
        //val path = session.servletContext.getRealPath("\\") + "\\upload\\excel"// 图片保存的路径
        val path = "E:\\upload\\excel"// 图片保存的路径
        val fileName = file.originalFilename // 获取文件名
        val name = fileName.substring(0, fileName.indexOf("."))
        val fileType = fileName.substring(fileName.indexOf("."), fileName.length) //e: .xls
        println(fileType)
        if (fileType != ".xlsx" && fileType != ".xls") {
            return ResultResponse(-1, "文件格式错误")
        }

        val date = Date()
        //saveName = Base64.getEncoder().encodeToString((name + date.time).getBytes())
        //saveName = Base64.getEncoder(name.toByte()).
        val saveName = Base64Utils.encodeToString((name + date.time).toByteArray())!!
                .replace(".", "a")// 防止出现"."

        println(path)
        val uploadFile = File(path, saveName + fileType)
        try {
            file.transferTo(uploadFile)
            val question = ExcelUtil.importQuestionsFromExcel(uploadFile)
            // //设置考试id
            if (question != null) {
                question.forEach { it.examId = examId }
                questionService.save(question)
            } else {
                return ResultResponse(-1, "上传文件中没有试题，或者试题格式不正确，导致不能读取")
            }
            println(question)

        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            uploadFile.delete()
        }
        modelMap["examId"] = examId
        return ResultResponse(200, "试题添加成功")
    }

    @GetMapping("/edit/{id}/{examId}")
    fun toEditQuestion(@PathVariable("id", required = true) id: Long, @PathVariable("examId", required = true) examId: Long, modelMap: ModelMap): String {
        modelMap["question"] = questionService.findOneById(id = id)
        modelMap["examId"] = examId
        return "admin/question/question_add"
    }

    @ResponseBody
    @PostMapping("/save")
    fun saveQuestion(question: Question?, session: HttpSession?): ResultResponse<out Any> {
        return if (question != null && session != null) {
            //todo 加入合法性判断
            if (questionService.save(question)) {
                ResultResponse(200, "更新成功了", result = question)
            } else {
                ResultResponse(-1, "更新失败了")
            }
        } else {
            ResultResponse(-1, "更新失败了")
        }
    }
}