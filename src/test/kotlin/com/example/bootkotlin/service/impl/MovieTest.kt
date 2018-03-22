package com.example.bootkotlin.service.impl

import com.example.bootkotlin.repository.cluster.MovieRepo
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class MovieTest {

    @Autowired
    lateinit var movieRepo: MovieRepo

    @Test
    fun getTest(){
        println(movieRepo.count())
    }
}