package com.example.bootkotlin.controller

import com.example.bootkotlin.entity.ResultResponse
import com.example.bootkotlin.entity.master.AnswerOnly
import com.example.bootkotlin.entity.master.DecimalTest
import com.example.bootkotlin.entity.master.Question
import com.example.bootkotlin.repository.master.DecimalTestRepo
import com.example.bootkotlin.service.QuestionService
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.swagger.annotations.ApiOperation
import org.apache.tomcat.util.codec.binary.Base64
import org.hibernate.annotations.DynamicUpdate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import java.io.Serializable
import java.util.*
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec
import javax.persistence.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession
import javax.validation.Valid
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

fun generalKey(): SecretKey {
    val key = "JWT_SECRET"
    val encodeKey = Base64.decodeBase64(key)
    return SecretKeySpec(encodeKey, 0, encodeKey.size, "AES")
}

@RestControllerAdvice
@RestController
@RequestMapping("/test")
class HelloController {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var bookRepository: BookRepository

    @Autowired
    lateinit var decimalTestRepo: DecimalTestRepo

    @Autowired
    lateinit var questionService: QuestionService

    @ApiOperation(value = "获取用户", notes = "根据Id查找用户")
    @GetMapping("/hello")
    fun hello(): User = userRepository.findOne(3)

    @GetMapping("/question")
    fun question(): ResultResponse<Question> = ResultResponse(222, "查询成功", questionService.findOneById(7))

    @GetMapping("/answer")
    fun answer(): ResultResponse<AnswerOnly> = ResultResponse(222, "查询成功", questionService.findById(7))

    @GetMapping("de")
    fun de(): List<DecimalTest> = decimalTestRepo.findAll()

    @GetMapping("/book")
    fun book(): Book = bookRepository.findOne(6)

    @PostMapping("/save")
    fun save(name: String, phone: String, id: Int): User? = userRepository.save(User(name = name, phone = phone, id = 11))

    @ApiOperation("表单验证", notes = "验证表单中的字段是否正确", httpMethod = "POST")
    @PostMapping("/validate")
    fun validate(@Valid user: User?, error: BindingResult): ResultResponse<out Any> {
        return if (error.hasErrors()) {
            ResultResponse(-1, "共有：${error.errorCount}处错误",
                    error.allErrors?.map { it.defaultMessage }.toString())
        } else {
            ResultResponse(200, "验证成功", user)
        }
    }


    @PostMapping("/login")
    fun login(name: String, @RequestParam(name = "age", required = false, defaultValue = "21") age: Int, httpRequest: HttpServletRequest, session: HttpSession): String {
        val date = Date()
        //1 / 0

        /**
         *  public static final int MINUTE_TTL = 60*1000;  //一分钟
         *  public static final int HOURS_TTL = 60*60*1000;  //一小时
         *  public static final int DAY_TTL = 12*60*60*1000;  //一天
         */
        val expiration = date.time + 60 * 60 * 1000
        val token = Jwts.builder().setId("jwt").setIssuedAt(date).setExpiration(Date(expiration)).claim("name", name).claim("age", age).signWith(SignatureAlgorithm.HS256, generalKey()).compact()


        //val session = httpRequest.session
        session.setAttribute("token", token)

        return token
    }

    @GetMapping("/user")
    fun getUser() = User("刘岑溪2", "18529458802")
}

@Entity(name = "user")
@DynamicUpdate(true)
data class User(
        @get:Size(min = 6, max = 12, message = "名字长度必须在6到12")
        @get:NotNull(message = "名字不能为空")
        var name: String? = null,
        @get:Size(min = 11, max = 11, message = "手机号长度必须为11位")
        @get:NotNull(message = "手机号不能为空")
        var phone: String? = null,

        @OneToMany(cascade = [(CascadeType.ALL)], fetch = FetchType.EAGER, mappedBy = "user")
        @OrderBy(value = "id desc")
        @JsonIgnoreProperties("user")//不序列化books对象的user属性
        var books: List<Book>? = null,

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column
        var id: Int? = null) : Serializable

@Entity(name = "book")
@DynamicUpdate(true)
data class Book(var name: String? = null,
                @JoinColumn(name = "user_id")
                @ManyToOne(cascade = [(CascadeType.ALL)], fetch = FetchType.EAGER, optional = true)
                @JsonIgnoreProperties("books")   //不序列化User对象的books属性
                var user: User? = null,

                @Id @GeneratedValue(strategy = GenerationType.AUTO) @Column var id: Int? = null)

interface UserRepository : CrudRepository<User, Int> {
    fun findFirst2ByName(name: String): List<User>
}

@Transactional(readOnly = true)  //事务管理，设置为只读
interface BookRepository : CrudRepository<Book, Int> {
    @Transactional //将只读改为可改
    @Modifying
    @Query(value = "update book set name=:name where id=:id")
    fun updateNameById(@Param("name") name: String, @Param("id") id: Int?)

}

