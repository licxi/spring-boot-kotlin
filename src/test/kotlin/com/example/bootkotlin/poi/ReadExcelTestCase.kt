package com.example.bootkotlin.poi

import com.example.bootkotlin.entity.master.Question
import com.example.bootkotlin.utils.ExcelUtil
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.junit.Test
import java.io.File


class ReadExcelTestCase {

    @Test
    fun readExcel2() {
        val list = ExcelUtil.importQuestionsFromExcel("C:\\Users\\sam-02\\Desktop\\questions.xlsx")
        list?.forEach { it.examId = 2 }
        println(list)
        println(list?.size)
    }


    @Test
    fun readExcel() {
        val file = File("C:\\Users\\sam-02\\Desktop\\questions.xlsx")
        val workBook = XSSFWorkbook(file)
        val sheet = workBook.getSheetAt(0)
        val numberOfRows = sheet.physicalNumberOfRows
        val list = mutableListOf<Question>()
        //println(numberOfRows)
        if (numberOfRows > 2) {
            (1 until numberOfRows)
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
//                        if(cell==null){
//                            println("空的")
//                        }else{
//                            println(cell.stringCellValue)
//                        }
                        }
                    }
        }
        println(list)
    }
}