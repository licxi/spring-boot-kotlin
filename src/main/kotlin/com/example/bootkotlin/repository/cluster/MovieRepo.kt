package com.example.bootkotlin.repository.cluster

import com.example.bootkotlin.entity.cluster.Movie
import org.springframework.data.repository.CrudRepository

interface MovieRepo : CrudRepository<Movie, Long>