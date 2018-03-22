package com.example.bootkotlin

import com.example.bootkotlin.entity.master.DecimalOnlyWithIdAndMoney
import com.example.bootkotlin.entity.master.DecimalOnlyWithIdAndPrice
import com.example.bootkotlin.entity.master.DecimalTest
import com.example.bootkotlin.repository.master.DecimalTestRepo
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.math.BigDecimal


@RunWith(SpringRunner::class)
@SpringBootTest
class DecimalTestRepoTest {

    @Autowired
    lateinit var decimalTestRepo: DecimalTestRepo

    @Test
    fun findMoneyTest() {
        val dec = decimalTestRepo.findMoneyById(1, DecimalOnlyWithIdAndMoney::class.java)
        println(dec)
        println(decimalTestRepo.findOne(1))
        val deci = decimalTestRepo.findMoneyById(1, DecimalOnlyWithIdAndPrice::class.java)
        println(deci)
    }

    @Test
    fun saveTest() {
        val dec = DecimalTest(money = BigDecimal("23.2"), price = 23.2)
        decimalTestRepo.save(dec)
    }

    @Test
    fun getTest() {
        val decimalTest = decimalTestRepo.getOne(1)
        println(decimalTest)
        println(12.234 + 12.234)
        println(BigDecimal("12.234") + BigDecimal("12.234"))
        println(BigDecimal(12.234) + BigDecimal(12.234))
        //Assert.assertEquals(24.2,decimalTest.money?.add(BigDecimal("1")))
        //Assert.assertEquals(24.2, decimalTest.price!! +1)
    }
}