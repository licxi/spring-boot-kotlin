package com.example.bootkotlin.entity.cluster

import javax.persistence.*

@Entity(name = "movie")
data class Movie(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(nullable = false)
        var id: Long? = null,
        @Column(nullable = false)
        var name: String? = null,
        @Column(nullable = false)
        var remark: String? = null
)