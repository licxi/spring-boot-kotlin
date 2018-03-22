package com.example.bootkotlin

import com.example.bootkotlin.entity.master.DecimalTest
import com.example.bootkotlin.entity.master.Exam
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*
import javax.validation.Validation
import javax.validation.Validator


class BaseTest {
    @Test
    fun repeatTest() {
        repeat(4) {
            println(it)
        }
    }

    @Test
    fun withTest() {
        val stringBuffer = StringBuffer()
        //
        with(stringBuffer) {
            append("a")
            append("b")
            append("c")
            print("")
            6//返回最后一行
        }.let {
            println(it)
        }
    }

    @Test
    fun applyTest() {
        val stringBuffer = StringBuffer()
        stringBuffer.apply {
            append("a")
            append("b")
            append("c")
            print(this) //方法当前的对象
        }.let {
                    println(it)
                }
    }

    @Test
    fun runTest() {
        val stringBuffer = StringBuffer()
        stringBuffer.run {
            append("a")
            append("b")
            append("c")
            print("a")
            Exam(2, "3", Date()) //返回最后一行
        }.let {
                    println(it)
                }
    }

    @Test
    fun alsoTest() {
        val stringBuffer = StringBuffer()
        stringBuffer.also {
            it.append("a")
            it.append("b")
            it.append("c")
            print("a")
            Exam(2, "3", Date()) //返回调用的对象
        }.let {
                    println(it)
                }
    }

    @Test
    fun takeIfTest() {
        23.takeIf {
            it > 21  //满足条件返回，不满足返回null
        }.let {
                    print(it)
                }

    }

    @Test
    fun takeUnlessTest() {
        23.takeUnless {
            it < 21  //不满足条件返回，满足返回null
        }.let {
                    print(it)
                }
    }

    @Test
    fun mapTest() {
        val a = (1..5).map { it * 2 }
        println(a)
        val b = (1..5).map { it * 2 }.filter { it > 4 } //条件过滤
        println(b)
        val c = (1..5).map { it * 2 }.firstOrNull { it > 4 } //返回大于4的第一个数
        println(c)
        val d = (1..5).map { it * 2 }.last { it < 9 } //从后往前找符合条件的
        println(d)
    }

    //如listof(),mutableof(),setof(),mutableSetof()来构造集合
    @Test
    fun collectionTest() {
        println("list")
        val a = listOf("a", "b") //不可变list
        println(a)
        val b = mutableListOf<String>()//可变list
        b.add("a")
        b.add("dd")
        b.add("edd")
        b.add("add277")
        b.add("add3")
        b.add("add3")
        println(b)

        val bb =  b.mapTo(mutableListOf()){
            it.length
        }

        println(bb)

        val bbb =  b.mapTo(mutableSetOf()){
            it.length
        }
        println(bbb.sorted())

        val c = setOf("a", "b", "a")
        println("set:")
        println(c)
        val d = mutableSetOf("a")
        d.add("b")
        d.add("a")
        println(d)

        val aaa:String by lazy { "2" }

        println(aaa)

        println("map")
        val e = mapOf<String, Any>("a" to "aa", "b" to "bb", "c" to "cc", Pair("d","dd"))
        println(e)

        println("map")
        val f = mutableMapOf<String, Any>("a" to "aa", "b" to "bb", "c" to "cc")
        println(f)


    }

    @Test
    fun licensePlateTooShort() {
        val validator: Validator = Validation.buildDefaultValidatorFactory().validator
        val user = DecimalTest()

        val constraintViolations = validator.validate(user)

        print(constraintViolations.size)
        assertEquals(2, constraintViolations.size)
        assertEquals(
                "不能为空",
                constraintViolations.iterator().next().message
        )
    }
}