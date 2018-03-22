package com.example.bootkotlin.utils

import com.example.bootkotlin.entity.master.Question
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileInputStream

/**
 * Excel 表格工具类
 */
class ExcelUtil {
    companion object {
        fun importQuestionsFromExcel(file: File): List<Question>? {
            return importQuestionsFromExcel(file.path)
        }

        fun importQuestionsFromExcel(pathname: String): List<Question>? {
            val file = File(pathname)
            if (!file.exists()) { //判断文件是否存在
                return null
            }
            val list = mutableListOf<Question>()
            try {
                val workBook = if (pathname.endsWith("xlsx")) {
                    XSSFWorkbook(file)
                } else {
                    HSSFWorkbook(FileInputStream(file))
                }
                val sheet = workBook.getSheetAt(0)
                val numberOfRows = sheet.physicalNumberOfRows
                println(numberOfRows)
                if (numberOfRows > 2) {
                    (1 until numberOfRows) //从第二行开始读取
                            .map { sheet.getRow(it) }
                            .forEach {
                                if (it != null) {
                                    val question = Question()
                                    var cell = it.getCell(0)
                                    cell?.let { question.title = cell.stringCellValue }
                                    cell = it.getCell(1)
                                    cell?.let { question.type = cell.numericCellValue.toInt() }
                                    if (question.type != 0) {
                                        cell = it.getCell(2)
                                        cell?.let { question.answer1 = cell.stringCellValue }
                                        cell = it.getCell(3)
                                        cell?.let { question.answer2 = cell.stringCellValue }
                                        cell = it.getCell(4)
                                        cell?.let { question.answer3 = cell.stringCellValue }
                                        cell = it.getCell(5)
                                        cell?.let { question.answer4 = cell.stringCellValue }
                                    }
                                    cell = it.getCell(6)
                                    cell?.let { question.rightAnswer = cell.stringCellValue }
                                    list.add(question)
                                }
                            }
                }
            } catch (e: Exception) {
                throw Exception("题目格式有错误:${e.message}")
            }
            return list
        }
    }
}
