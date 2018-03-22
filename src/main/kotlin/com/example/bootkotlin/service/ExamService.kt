package com.example.bootkotlin.service

import com.example.bootkotlin.entity.master.Exam
import org.springframework.data.domain.Page

interface ExamService {
    fun save(exam: Exam?): Boolean
    fun examsCount(isProcessing: Boolean): Long
    fun examList(): List<Exam>
    fun findOneById(id: Long): Exam
    //fun examList(page: Int, size: Int): Page<Exam>?
    fun examList(page: Int, size: Int, title: String = ""): Page<Exam>?

    fun delete(id: Long): Boolean
}