package com.example.bootkotlin.repository.master

import com.example.bootkotlin.config.AssignColumn
import com.example.bootkotlin.entity.master.Question
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface QuestionRepo : JpaRepository<Question, Long> {
    fun findAllByExamId(examId: Long, pageable: Pageable): Page<Question>
    fun findAllByExamIdAndTitleContaining(examId: Long, title: String, pageable: Pageable): Page<Question>
    fun deleteByExamId(examId: Long): Int

    fun <T : AssignColumn> findOneById(id: Long): T
}