package com.example.bootkotlin.service.impl

import com.example.bootkotlin.service.DateService
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.util.*

@Service("dateService")
class DateServiceImpl : DateService {
    override fun format(timestamp: Long): String {
        val simpleDateFormat = SimpleDateFormat("yyyy-mm-dd hh:MM:ss")
        return simpleDateFormat.format(Date(timestamp))
    }
}