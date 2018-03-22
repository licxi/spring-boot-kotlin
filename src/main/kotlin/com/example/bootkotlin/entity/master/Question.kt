package com.example.bootkotlin.entity.master

import com.example.bootkotlin.config.AssignColumn
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import java.io.Serializable
import javax.persistence.*

@Entity(name = "question")
@DynamicUpdate
@DynamicInsert
data class Question(@Id
                    @GeneratedValue(strategy = GenerationType.AUTO)
                    @Column(nullable = false)
                    var id: Long? = null,
                    @Column(nullable = false)
                    var title: String? = null,
                    var type: Int? = null,
                    var answer1: String? = null,
                    var answer2: String? = null,
                    var answer3: String? = null,
                    var answer4: String? = null,
                    @Column(name = "right_answer", nullable = false)
                    var rightAnswer: String? = null,
                    @Column(name = "exam_id", nullable = false)
                    var examId: Long? = null
) : Serializable


interface AnswerOnly : AssignColumn {
    fun getId(): Long
    fun getAnswer1(): String
    fun getAnswer2(): String
    fun getAnswer3(): String
    fun getAnswer4(): String
}



