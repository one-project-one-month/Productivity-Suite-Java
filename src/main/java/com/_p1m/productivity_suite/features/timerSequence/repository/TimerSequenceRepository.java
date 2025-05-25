package com._p1m.productivity_suite.features.timerSequence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com._p1m.productivity_suite.data.models.TimerSequence;

@Repository
public interface TimerSequenceRepository extends JpaRepository<TimerSequence, Long>{
	Optional<TimerSequence> findBySequenceId(Long sequenceId);
	
	List<TimerSequence> findAllBySequenceId(Long sequenceId);
	
	@Query("SELECT COALESCE(MAX(ts.step), -1) FROM TimerSequence ts WHERE ts.sequence.id = :sequenceId")
	int findMaxStepBySequenceId(@Param("sequenceId") Long sequenceId);
	
	@Query("SELECT ts.step FROM TimerSequence ts WHERE ts.timer.id = :timerId")
	Optional<Integer> findStepByTimerId(@Param("timerId") Long timerId);
	

    @Query("""
        SELECT ts FROM TimerSequence ts
        WHERE ts.sequence.id = :sequenceId
          AND ts.step = (
            SELECT MAX(sub.step) FROM TimerSequence sub
            WHERE sub.sequence.id = :sequenceId
          )
    """)
    Optional<TimerSequence> findTimerSequenceWithMaxStepBySequenceId(@Param("sequenceId") Long sequenceId);

}
