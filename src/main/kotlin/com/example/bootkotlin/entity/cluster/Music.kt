package com.example.bootkotlin.entity.cluster

import java.io.Serializable
import javax.persistence.*

@Entity(name = "music")
data class Music(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(nullable = false)
        var id: Long? = null,
        @Column(name = "name", length = 20, nullable = false, unique = true)
        var name: String? = null
):Serializable