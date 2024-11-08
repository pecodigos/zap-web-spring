package com.pecodigos.zapweb.users.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pecodigos.zapweb.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Length(min = 5, max = 120)
    @Column(length = 120, nullable = false)
    private String name;

    @Length(min = 7, max = 30)
    @Column(length = 30, nullable = false)
    private String username;

    @Email
    @Length(min = 10, max = 120)
    @Column(length = 120, nullable = false)
    private String email;

    @Length(min = 9, max = 120)
    @Column(length = 120, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private Role role;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
