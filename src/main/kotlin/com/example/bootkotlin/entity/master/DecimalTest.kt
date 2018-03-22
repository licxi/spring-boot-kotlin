package com.example.bootkotlin.entity.master

import java.io.Serializable
import java.math.BigDecimal
import javax.persistence.*
import javax.validation.constraints.Max
import javax.validation.constraints.NotNull

@Entity(name = "decimal1")
data class DecimalTest(@Id
                       @GeneratedValue(strategy = GenerationType.AUTO)
                       @Column(nullable = false)
                       var id: Long? = null,
                       @Column(precision = 5, scale = 2)
                       @get:NotNull(message = "不能为空") //必须使用get开头
                       var money: BigDecimal? = null,
                       @get:NotNull
                       @Max(12)
                       var price: Double? = null
) : Serializable

interface DecimalOnlyWithIdAndMoney {  //用于获取部分字段
    fun getId(): Long
    fun getMoney(): BigDecimal
}

interface DecimalOnlyWithIdAndPrice {  //用于获取部分字段
    fun getId(): Long
    fun getPrice(): Double
}