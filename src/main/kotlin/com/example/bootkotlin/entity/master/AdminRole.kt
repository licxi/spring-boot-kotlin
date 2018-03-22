package com.example.bootkotlin.entity.master

import java.io.Serializable
import javax.persistence.*

@Entity(name = "admin_role")
data class AdminRole(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Int? = null,
        @Column(nullable = false)
        var name: String = "NO",
        @Column(name = "role_desc")
        var roleDescribe: String? = null
) : Serializable