package com.example.bootkotlin.security

import com.example.bootkotlin.handler.AccessDeniedHandlerImpl
import com.example.bootkotlin.handler.LoginSuccessHandler
import com.example.bootkotlin.role.AdminRole
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.sql.DataSource


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //注解的方式实现权限管理
class SecurityConfiguration : WebSecurityConfigurerAdapter() {

    @Qualifier("dataSourceCluster")
    @Autowired
    lateinit var dataSource: DataSource

    @Autowired
    lateinit var bCryptPasswordEncoder: BCryptPasswordEncoder


    @Autowired
    @Qualifier("adminDetailService")
    lateinit var userDetailsService: UserDetailsService

    @Autowired
    @Throws(Exception::class)
    fun configureGlobalSecurity(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService<UserDetailsService>(userDetailsService)
        auth.authenticationProvider(authenticationProvider())
    }

    @Bean
    fun authenticationProvider(): DaoAuthenticationProvider {
        val authenticationProvider = DaoAuthenticationProvider()
        authenticationProvider.setUserDetailsService(userDetailsService)
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder)
        return authenticationProvider
    }

    @Throws(Exception::class)
    override fun configure(web: WebSecurity?) {
        web!!.ignoring().antMatchers("/test/**","/druid/**", "/admin/css/**", "/admin/js/**", "/admin/image/**", "/admin/Access_Denied")
        //可以仿照上面一句忽略静态资源
    }

    override fun configure(http: HttpSecurity?) {

        http?.authorizeRequests()

                ?.antMatchers("/admin")?.authenticated()
                ?.antMatchers("/admin/login_success","/test/**")?.permitAll()
                ?.antMatchers("/admin/**")
                ?.access("hasRole('${AdminRole.superAdmin}') or hasRole('${AdminRole.Admin}')")
                ?.and()
                ?.formLogin()?.loginPage("/admin/login")?.permitAll()
                ?.successHandler(LoginSuccessHandler())
                ?.failureUrl("/admin/login_failure")
                ?.usernameParameter("name")?.passwordParameter("password")
                ?.and()?.exceptionHandling()
                ?.accessDeniedHandler(AccessDeniedHandlerImpl())
    }
}
