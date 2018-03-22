package com.example.bootkotlin.entity.master

import com.fasterxml.jackson.annotation.JsonFormat
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.validator.constraints.NotEmpty
import java.util.*
import javax.persistence.*
import javax.validation.constraints.Size


@Entity(name = "exam")
@DynamicUpdate
@DynamicInsert
data class Exam(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(nullable = false)
        var id: Long? = null,

        @Column(nullable = false)
        @get:NotEmpty(message = "考试标题不能为空")
        @get:Size(message = "考试标题字数必须在10到25")
        var title: String? = null,

        @Column(name = "start_time", nullable = false)
        @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")

        var startTime: Date? = null,

        @Column(name = "end_time", nullable = false)
        @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
        var endTime: Date? = null,

        /**
         * 0 未进行，1进行中
         */
        @Column(name = "status", nullable = false)
        var status: Short? = 0,

        @Column(length = 10, nullable = false)
        var author: String? = null,

        @Transient
        var joinExamNum: Int = 0
)