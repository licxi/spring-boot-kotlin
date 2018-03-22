package com.example.bootkotlin.role

class AdminRole {
    companion object {

        /**
         * 超级管理员
         */
        const val superAdmin = "SUPER_ADMIN"

        /**
         * 管理员
         */
        const val Admin = "ADMIN"

        /**
         * 添加管理员权限
         */
        const val addAdmin = "ADD_ADMIN"

        /**
         * 添加考试权限
         */
        const val addExam = "ADD_EXAM"

        /**
         * 删除考试权限
         */
        const val deleteExam = "DELETE_EXAM"

        /**
         * 上传文件权限
         */
        const val uploadFile = "UPLOAD_FILE"
    }
}