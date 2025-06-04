package com._p1m.productivity_suite.data.models;

import com._p1m.productivity_suite.config.utils.PersistenceUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "income")
public class Income implements PersistenceUtils.Identifiable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount", scale = 6, precision = 19, nullable = true)
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Long createdAt;

    @Column(name = "updated_at", nullable = false)
    private Long updatedAt;

    @Override
    public Long getId() {
        return id;
    }

    @PrePersist
    protected void onCreate() {
        long now = System.currentTimeMillis();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = System.currentTimeMillis();
    }

    public Income(final BigDecimal amount, final Category category, final User user) {
        this.amount = amount;
        this.category = category;
        this.user = user;
    }
}
