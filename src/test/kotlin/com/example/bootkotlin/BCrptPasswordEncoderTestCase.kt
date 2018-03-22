package com.example.bootkotlin

import junit.framework.Assert.assertTrue
import org.junit.Test
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder


class BCrptPasswordEncoderTestCase {

    @Test
    fun test() {
        val encoder = BCryptPasswordEncoder(16)
        val result = encoder.encode("myPassword")
        println(result)
        println(encoder.matches("myPassword", result))
        assertTrue(encoder.matches("myPassword", result))
        //$2a$16$yme/4fZb6oCjWY8fzVJN0.f2s5WAlFKOA2yMpmTF73S8oYj50jlE6
    }
}