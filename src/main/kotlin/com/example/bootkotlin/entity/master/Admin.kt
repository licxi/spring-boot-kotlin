package com.example.bootkotlin.entity.master


import com.fasterxml.jackson.annotation.JsonFormat
import org.hibernate.annotations.DynamicUpdate
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*
import javax.persistence.*


@EntityListeners(AuditingEntityListener::class)
@Entity(name = "admin")
@DynamicUpdate(true)
data class Admin(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(nullable = false)
        var id: Long? = null,


        @Column(name = "name", length = 20, nullable = false, unique = true)
        var name: String? = null,

        @Column(name = "nickname", length = 20, nullable = false)
        var nickname: String? = null,

        @Column(name = "password", length = 60, nullable = false)
        var password: String? = null,

        @Column(name = "create_time", updatable = false)
        @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
        @CreatedDate //插入时自动添加时间（在实体类添加@EntityListeners(AuditingEntityListener::class，在启动类添加EnableJpaAuditing)）
        var createTime: Date? = null,

        @Column(name = "last_login_time")
        @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
        var lastLoginTime: Date? = null,

        @ManyToMany(cascade = [(CascadeType.MERGE)], fetch = FetchType.EAGER)
        @JoinTable(name = "admin_has_role", joinColumns = [JoinColumn(name = "admin_id", referencedColumnName = "id")], inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")])
        @OrderBy(value = "id asc")
        var roles: Set<AdminRole>? = null)