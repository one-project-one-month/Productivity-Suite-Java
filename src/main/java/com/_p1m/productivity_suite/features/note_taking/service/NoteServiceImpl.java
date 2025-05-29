package com._p1m.productivity_suite.features.note_taking.service;

import com._p1m.productivity_suite.config.exceptions.EntityNotFoundException;
import com._p1m.productivity_suite.config.utils.PersistenceUtils;
import com._p1m.productivity_suite.config.utils.RepositoryUtils;
import com._p1m.productivity_suite.data.models.Category;
import com._p1m.productivity_suite.data.models.Note;
import com._p1m.productivity_suite.data.models.User;
import com._p1m.productivity_suite.features.categories.repository.CategoryRepository;
import com._p1m.productivity_suite.features.note_taking.dto.NoteCategoryData;
import com._p1m.productivity_suite.features.note_taking.dto.NoteRequest;
import com._p1m.productivity_suite.features.note_taking.dto.NoteResponse;
import com._p1m.productivity_suite.features.note_taking.dto.NoteRetrieveOneData;
import com._p1m.productivity_suite.features.note_taking.repository.NoteRepository;
import com._p1m.productivity_suite.features.note_taking.repository.jdbc.NoteJdbcRepository;
import com._p1m.productivity_suite.features.users.dto.response.UserDto;
import com._p1m.productivity_suite.features.users.repository.UserRepository;
import com._p1m.productivity_suite.features.users.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService{
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final UserUtil userUtil;
    private final CategoryRepository categoryRepository;
    private final NoteJdbcRepository noteJdbcRepository;

    @Override
    public void createNote(final NoteRequest createNoteRequest, final String authHeader) {
        final UserDto userDto = this.userUtil.getCurrentUserDto(authHeader);

        final User user = RepositoryUtils.findByIdOrThrow(this.userRepository, userDto.getId(), "User");

        final Note note = new Note(
                createNoteRequest.title(),
                createNoteRequest.body(),
                user,
                createNoteRequest.color(),
                RepositoryUtils.findByIdOrThrow(this.categoryRepository, createNoteRequest.categoryId(), "Category")
        );

        PersistenceUtils.save(this.noteRepository, note, "Note");
    }

//    @Override
//    public List<NoteResponse> retrieveAll(final String authHeader) {
//        final UserDto userDto = this.userUtil.getCurrentUserDto(authHeader);
//        final Sort sortByUpdatedAt = Sort.by(Sort.Direction.DESC, "updatedAt");
//        final List<Note> notes = RepositoryUtils.findAllByUserId(userDto.getId(), sortByUpdatedAt, this.noteRepository::findAllByUserId);
//        return notes.stream()
//                .map(this::toNoteResponseWithType)
//                .toList();
//    }

    @Override
    public List<NoteCategoryData> retrieveAll(final String authHeader) {

        final UserDto userDto = this.userUtil.getCurrentUserDto(authHeader);
        final User user = RepositoryUtils.findByIdOrThrow(this.userRepository, userDto.getId(), "User");

        return this.noteJdbcRepository.findAllByUserId(user.getId());
    }

    @Override
    public NoteResponse retrieveAllByCategoryId(final String authHeader, final Long categoryId) {
        final Category category = RepositoryUtils.findByIdOrThrow(this.categoryRepository, categoryId, "Category");
        return new NoteResponse(
                category.getId(),
                category.getName(),
                this.noteJdbcRepository.findAllByCategoryId(category.getId())
        );
    }

//    @Override
//    public NoteResponse retrieveOne(final Long id) {
//        final Note note = RepositoryUtils.findByIdOrThrow(this.noteRepository, id, "Note");
//
//        return this.toNoteResponseWithType(note);
//    }

    @Override
    public NoteRetrieveOneData retrieveOne(final Long id) {
        final Note note = RepositoryUtils.findByIdOrThrow(this.noteRepository, id, "Note");

        return this.noteJdbcRepository.findById(note.getId());
    }

    @Override
    public void updateNote(final NoteRequest updateNoteRequest, final Long id) {
        final Note note = RepositoryUtils.findByIdOrThrow(this.noteRepository, id, "Note");

        note.setTitle(updateNoteRequest.title());
        note.setBody(updateNoteRequest.body());
        note.setColor(updateNoteRequest.color());
        note.setCategory(RepositoryUtils.findByIdOrThrow(this.categoryRepository, updateNoteRequest.categoryId(), "Category"));
        PersistenceUtils.save(this.noteRepository, note, "Note");
    }

    @Override
    public void deleteNote(final Long id) {
        RepositoryUtils.findByIdOrThrow(this.noteRepository, id, "Note");

        PersistenceUtils.deleteById(this.noteRepository, id, "Note");
    }

    @Override
    public boolean togglePinStatus(final Long id) {
        final Note note = RepositoryUtils.findByIdOrThrow(this.noteRepository, id, "Note");
        note.setPinned(!note.isPinned());
        PersistenceUtils.save(this.noteRepository, note, "Note");
        return note.isPinned();
    }

    @Override
    public boolean togglePinStatusBulk(final List<Long> noteIds) {
        Optional.ofNullable(noteIds)
                .filter(ids -> !ids.isEmpty())
                .orElseThrow(() -> new IllegalArgumentException("Note IDs list must not be empty."));

        final Long currentUserId = this.userUtil.getCurrentUserInternal().getId();

        final List<Note> notes = this.noteRepository.findAllById(noteIds);

        if (notes.size() != noteIds.size()) {
            throw new EntityNotFoundException("Some notes not found for given IDs.");
        }

        notes.stream()
                .filter(note -> !note.getUser().getId().equals(currentUserId))
                .findFirst()
                .ifPresent(note -> {
                    throw new SecurityException("Note with ID: " + note.getId() + " does not belong to the current user.");
                });

        notes.forEach(note -> note.setPinned(!note.isPinned()));

        PersistenceUtils.saveAll(this.noteRepository, notes, "Note");

        return notes.stream().allMatch(Note::isPinned);
    }
}
