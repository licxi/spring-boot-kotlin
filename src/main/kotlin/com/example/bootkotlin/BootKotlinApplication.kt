package com.example.bootkotlin

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2


@EnableJpaAuditing
@SpringBootApplication
@EntityScan("com.example.bootkotlin.entity.cluster","com.example.bootkotlin.entity.master")
class BootKotlinApplication {

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder(10)
    }

//    @Bean
//    fun init(userRepository: UserRepository) = CommandLineRunner {
//        //println(userRepository.findAll())  //在这里可以初始化一些数据
//    }
}

@Configuration
@EnableSwagger2
class Swagger2 {
    @Bean
    fun createRestApi(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.bootkotlin.controller"))
                .paths(PathSelectors.any())
                .build()
    }

    private fun apiInfo(): ApiInfo {
        return ApiInfoBuilder()
                .title("Spring Boot中使用Swagger2构建RESTful APIs")
                .description("这是测试")
                .termsOfServiceUrl("https://www.baidu.com")
                .contact(Contact("liu", "https://www.baidu.com", "123121212313@qq.com"))
                .version("1.0")
                .build()
    }

}


/**
 * java -jar *.jar 命令行运行jar
 */
fun main(args: Array<String>) {
    SpringApplication.run(BootKotlinApplication::class.java, *args)
}
