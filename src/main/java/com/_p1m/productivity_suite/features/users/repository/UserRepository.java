package com._p1m.productivity_suite.features.users.repository;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com._p1m.productivity_suite.data.models.User;

import jakarta.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String identifier);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.dateFormat = :dateFormat, u.currencyCode = :currencyCode WHERE u.id = :userId")
    void updateDateFormatAndCurrencyCode(@Param("userId") Long userId,
                                         @Param("dateFormat") String dateFormat,
                                         @Param("currencyCode") String currencyCode);
    
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.setAmount = :setAmount WHERE u.id = :userId")
    void updateSetAmountById(@Param("userId") Long userId,
                             @Param("setAmount") BigDecimal setAmount);
}
