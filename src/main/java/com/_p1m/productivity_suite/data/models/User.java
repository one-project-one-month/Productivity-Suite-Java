package com._p1m.productivity_suite.data.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean emailVerified;

    @Builder.Default
    @Column(nullable = false)
    private boolean status = true;

    @Column(nullable = false)
    private Integer gender;

    @Builder.Default
    @Column(nullable = false)
    private boolean loginFirstTime = true;

    private String dateFormat;
    
    @Builder.Default
    @Column(nullable = false, columnDefinition = "varchar(255) default 'MMK'")
    private String currencyCode="MMK";
    
    @Column(name = "set_amount", scale = 6, precision = 19, nullable = true)
    private BigDecimal setAmount;
    
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;
}
