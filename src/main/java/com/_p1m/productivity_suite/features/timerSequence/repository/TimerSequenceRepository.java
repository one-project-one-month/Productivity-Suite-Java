package com._p1m.productivity_suite.features.timerSequence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com._p1m.productivity_suite.data.models.TimerSequence;

@Repository
public interface TimerSequenceRepository extends JpaRepository<TimerSequence, Long>{

}
