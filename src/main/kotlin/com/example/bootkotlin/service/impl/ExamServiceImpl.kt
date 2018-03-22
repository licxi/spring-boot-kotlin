package com.example.bootkotlin.service.impl

import com.example.bootkotlin.entity.master.Exam
import com.example.bootkotlin.repository.master.ExamRepo
import com.example.bootkotlin.role.AdminRole
import com.example.bootkotlin.service.ExamService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Example
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service("examService")
@Transactional(readOnly = true)
class ExamServiceImpl : ExamService {

    @Autowired
    lateinit var examRepo: ExamRepo

    @Transactional(readOnly = false)
    @PreAuthorize(value = "hasRole('${AdminRole.superAdmin}') or hasRole('${AdminRole.deleteExam}')")
    override fun delete(id: Long): Boolean {
        return if (id > 0) {
            try {
                examRepo.delete(id)
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        } else {
            false
        }
    }

    override fun examList(page: Int, size: Int, title: String): Page<Exam>? {
        return examRepo.findAllByTitleContaining(title, PageRequest(page, size))
    }


    /**
     * 当实体有id时为更新，无id时为插入
     */
    @Transactional(readOnly = false)
    override fun save(exam: Exam?): Boolean {
        return if (exam != null) {
            examRepo.save(exam) != null
        } else {
            false
        }
    }

    /**
     * 返回考试次数
     * @param isProcessing 是否为进行中的考试次数
     * @return 考试的次数
     */
    override fun examsCount(isProcessing: Boolean): Long {
        return if (isProcessing) { //进行中的考试次数
            val exam = Exam()
            exam.status = 1
            //val exam = Exam(status = 1)
            examRepo.count(Example.of(exam))
        } else { //总的考试次数
            examRepo.count()
        }
    }


    override fun findOneById(id: Long): Exam {
        return examRepo.findOne(id)
    }

    override fun examList(): List<Exam> {
        return examRepo.findAll()
    }
//    override fun examList(page: Int,size:Int): Page<Exam>? {
//        return examRepo.findAll(PageRequest(page,size))
//    }
}