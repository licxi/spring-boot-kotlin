package com.example.bootkotlin.service.impl

import com.example.bootkotlin.entity.master.AnswerOnly
import com.example.bootkotlin.entity.master.Question
import com.example.bootkotlin.repository.master.QuestionRepo
import com.example.bootkotlin.service.QuestionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service("questionService")
@Transactional
class QuestionServiceImpl : QuestionService {

    @Autowired
    lateinit var questionRepo: QuestionRepo

    override fun findAllByExamId(examId: Long, page: Int, size: Int): Page<Question> {
        return questionRepo.findAllByExamId(examId, PageRequest(page, size))
    }

    override fun findAllByExamIdAndTitleContaining(examId: Long, title: String, page: Int, size: Int): Page<Question> {
        return questionRepo.findAllByExamIdAndTitleContaining(examId, title, PageRequest(page, size))
    }

    @Transactional(readOnly = false)
    override fun save(question: Question): Boolean {
        return questionRepo.save(question) != null
    }

    @Transactional(readOnly = false)
    override fun save(questions: Iterable<Question>): Boolean {
        return questionRepo.save(questions) != null
    }

    @Transactional(readOnly = false)
    override fun deleteAllByExamId(examId: Long): Int {
        return questionRepo.deleteByExamId(examId)
    }

    @Transactional(readOnly = false)
    override fun deleteById(id: Long): Boolean {
        return try { //根据是否抛出异常判断删除结果
            questionRepo.delete(id)
            true
        } catch (e: Exception) {
            println(e.message)
            //e.printStackTrace()
            false
        }
    }

    override fun findOneById(id: Long): Question? {
        return questionRepo.findOne(id)
    }

    override fun findById(id: Long): AnswerOnly = questionRepo.findOneById(7)

}