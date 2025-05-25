package com._p1m.productivity_suite.features.timer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com._p1m.productivity_suite.data.models.Timer;

@Repository
public interface TimerRepository extends JpaRepository<Timer,Long>{
}
