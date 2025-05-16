package com._p1m.productivity_suite.features.sequence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com._p1m.productivity_suite.data.models.Sequence;

@Repository
public interface SequenceRepository extends JpaRepository<Sequence, Long>{
	List<Sequence> findByUserEmail(String email);
}
