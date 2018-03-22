package com.example.bootkotlin

import com.example.bootkotlin.controller.Book
import com.example.bootkotlin.controller.BookRepository
import com.example.bootkotlin.controller.User
import com.example.bootkotlin.controller.UserRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import java.text.SimpleDateFormat
import java.util.*

@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest
class BootKotlinApplicationTests {
    private val logger: Logger = LoggerFactory.getLogger(BootKotlinApplicationTests::class.java)
    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var bookRepository: BookRepository

    @Test
    fun testSave() {
        val books = mutableListOf<Book>()
        books.add(Book("java"))
//		var user = userRepository.findOne(1)
//		user.books = books
//		user.name = "刘岑溪"
//		user.phone = "18529458802"
        userRepository.save(User(name = "刘先生", id = 1))
    }

    @Test
    fun testFind() {
        val user = userRepository.findOne(3)
        print(user)
    }

    @Test
    fun testFindFirst2ByName() {
        print(userRepository.findFirst2ByName("老王"))
    }

    @Test
    fun testBookSave() {
        print(bookRepository.save(Book(name = "spring2", id = 6)))
    }

    @Test
    fun testUpdateBook() {
        print(bookRepository.updateNameById("C++", id = 1))
    }

    @Test
    fun testFindOneBook() {
        val book = bookRepository.findOne(1)
        book.user?.books = null
        print(book)
    }

    @Test
    fun testDate() {
        val date = Date(1518341654455)

        val sdf = SimpleDateFormat("YYYY-MM-dd HH:mm:ss")
        println("1518341654455：" + sdf.format(date))
        println("1466759400：" + sdf.format(Date(15976322470)))
        println("现在时间：" + sdf.format(Date()) + "的时间戳为：" + Date().time)
    }
}
