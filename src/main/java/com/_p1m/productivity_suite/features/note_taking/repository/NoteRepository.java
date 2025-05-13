package com._p1m.productivity_suite.features.note_taking.repository;

import com._p1m.productivity_suite.data.models.Note;
import com._p1m.productivity_suite.data.models.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findAllByUserId(Long userId, Sort sort);
    boolean existsByTitleAndUser(String title, User user);
}
