package com.crud_javatokotiln.entity

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "users")
@Schema(description = "사용자 엔티티")
class User(
    id: Long? = null,
    @Schema(description = "사용자 이름", example = "홍길동")
    var name: String = "",

    @Schema(description = "사용자 이메일", example = "hong@example.com")
    var email: String = ""
) : BaseEntity(id) {
    constructor(Name: String, Email: String) : this() {
        this.name = Name
        this.email = Email
    }
}