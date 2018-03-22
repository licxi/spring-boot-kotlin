package com.example.bootkotlin.service.impl

import com.example.bootkotlin.entity.master.Question
import com.example.bootkotlin.service.QuestionService
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class QuestionServiceImplTest {

    @Autowired
    lateinit var questionService: QuestionService

    @Test
    fun testFinAllByExamId() {
        println(questionService.findAllByExamId(1, 0, 2))
    }

    @Test
    fun testFindOneById() {
        println(questionService.findOneById(1))
    }

    @Test
    fun testSave() {
        val question = Question(title = "今天是什么节日", type = 1, answer1 = "答案一", answer2 = "答案二", answer3 = "答案三", answer4 = "答案四", rightAnswer = "A", examId = 1)
        val result = questionService.save(question)
        println("插入结果$result")
    }

    @Test
    fun testModify() {
        val question = Question(id = 7, title = "这是更新后的题目")
        val result = questionService.save(question)
        println("更新结果$result")
    }

    @Test
    fun testSaveList() {
        val question = Question(title = "为什么会发生这种事", type = 2, answer1 = "答案一", answer2 = "答案二", answer3 = "答案三", answer4 = "答案四", rightAnswer = "B", examId = 1)
        val question2 = Question(title = "这是题目吗", type = 3, rightAnswer = "正确", examId = 1)
        val questions = mutableListOf<Question>()
        questions.add(question)
        questions.add(question2)
        val result = questionService.save(questions)
        println("插入结果$result")
    }

    @Test
    fun testDeleteById() {
        val result =
                try {
                    questionService.deleteById(1)
                } catch (e: Exception) {
                    e.printStackTrace()
                    false
                }
        println("删除结果$result")
    }

    @Test
    fun testDeleteByExamId() {
        val result =
                try {
                    questionService.deleteAllByExamId(2)
                } catch (e: Exception) {
                    e.printStackTrace()
                    0
                }
        println("删除结果$result")
    }
}