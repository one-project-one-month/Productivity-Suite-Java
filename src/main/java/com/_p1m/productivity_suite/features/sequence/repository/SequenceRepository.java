package com._p1m.productivity_suite.features.sequence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com._p1m.productivity_suite.data.models.Sequence;

@Repository
public interface SequenceRepository extends JpaRepository<Sequence, Long>{
	List<Sequence> findByUserEmail(String email);
	
	@Query("SELECT s FROM Sequence s WHERE s.user.email = :email AND s.status = false")
	Optional<Sequence> findStatusSequencesByUserEmail(@Param("email") String email);
	
	@Query("SELECT s.status FROM Sequence s WHERE s.id = :id")
	Optional<Boolean> findStatusById(@Param("id") Long id);
}
