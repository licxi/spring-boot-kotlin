package com.example.bootkotlin.repository.master

import com.example.bootkotlin.entity.master.Exam
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface ExamRepo : JpaRepository<Exam, Long> {
    fun findAllByTitleContaining(title: String, pageable: Pageable): Page<Exam>?
}