package com.example.bootkotlin.service

import com.example.bootkotlin.entity.master.AnswerOnly
import com.example.bootkotlin.entity.master.Question
import org.springframework.data.domain.Page
import org.springframework.transaction.annotation.Transactional


@Transactional(readOnly = true)
interface QuestionService {
    fun findAllByExamId(examId: Long, page: Int, size: Int): Page<Question>
    fun findAllByExamIdAndTitleContaining(examId: Long, title: String, page: Int, size: Int): Page<Question>
    fun findOneById(id: Long): Question?
    fun save(question: Question): Boolean
    fun save(questions: Iterable<Question>): Boolean
    @Transactional(readOnly = false)
    fun deleteAllByExamId(examId: Long): Int

    @Transactional(readOnly = false)
    fun deleteById(id: Long): Boolean

    fun findById(id: Long): AnswerOnly
}
