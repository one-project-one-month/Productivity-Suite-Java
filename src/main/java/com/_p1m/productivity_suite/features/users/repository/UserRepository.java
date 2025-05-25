package com._p1m.productivity_suite.features.users.repository;

import com._p1m.productivity_suite.data.models.Currency;
import com._p1m.productivity_suite.data.models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String identifier);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.dateFormat = :dateFormat, u.currency = :currency WHERE u.id = :userId")
    void updateDateFormatAndCurrencyById(@Param("userId") Long userId,
                                         @Param("dateFormat") String dateFormat,
                                         @Param("currency") Currency currency);
}
