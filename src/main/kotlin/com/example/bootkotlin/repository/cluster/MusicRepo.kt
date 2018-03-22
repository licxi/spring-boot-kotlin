package com.example.bootkotlin.repository.cluster

import com.example.bootkotlin.entity.cluster.Music
import org.springframework.data.jpa.repository.JpaRepository

interface MusicRepo : JpaRepository<Music, Long>