package com._p1m.productivity_suite.features.note_taking.service;

import com._p1m.productivity_suite.config.utils.PersistenceUtils;
import com._p1m.productivity_suite.config.utils.RepositoryUtils;
import com._p1m.productivity_suite.data.models.Category;
import com._p1m.productivity_suite.data.models.Note;
import com._p1m.productivity_suite.data.models.User;
import com._p1m.productivity_suite.features.categories.repository.CategoryRepository;
import com._p1m.productivity_suite.features.note_taking.dto.NoteRequest;
import com._p1m.productivity_suite.features.note_taking.dto.NoteResponse;
import com._p1m.productivity_suite.features.note_taking.repository.NoteRepository;
import com._p1m.productivity_suite.features.users.dto.response.UserDto;
import com._p1m.productivity_suite.features.users.repository.UserRepository;
import com._p1m.productivity_suite.features.users.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService{
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final UserUtil userUtil;
    private final CategoryRepository categoryRepository;

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
    public NoteResponse retrieveAllByCategoryId(final String authHeader, final Long categoryId) {
        final Category category = RepositoryUtils.findByIdOrThrow(this.categoryRepository, categoryId, "Category");
        return new NoteResponse(
                category.getId(),
                category.getName(),
                null
        );
    }

    @Override
    public NoteResponse retrieveOne(final Long id) {
        final Note note = RepositoryUtils.findByIdOrThrow(this.noteRepository, id, "Note");

//        return this.toNoteResponseWithType(note);
        return null;
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
}
