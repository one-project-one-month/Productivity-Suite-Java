package com._p1m.productivity_suite.features.note_taking.service;

import com._p1m.productivity_suite.data.models.Note;
import com._p1m.productivity_suite.data.models.User;
import com._p1m.productivity_suite.features.note_taking.dto.CreateNoteRequest;
import com._p1m.productivity_suite.features.note_taking.dto.NoteResponse;
import com._p1m.productivity_suite.features.note_taking.dto.UpdateNoteRequest;
import com._p1m.productivity_suite.features.note_taking.repository.NoteRepository;
import com._p1m.productivity_suite.features.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static com._p1m.productivity_suite.config.utils.EntityServiceHelper.*;
import static com._p1m.productivity_suite.config.utils.PersistenceUtils.*;
import static com._p1m.productivity_suite.config.utils.RepositoryUtils.*;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService{
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public void createNote(final CreateNoteRequest createNoteRequest) {
        final Long userId = createNoteRequest.getUserId();
        final User user = findByIdOrThrow(this.userRepository, userId, "User");
        final Note note = Note.builder()
                .title(createNoteRequest.getTitle())
                .body(createNoteRequest.getBody())
                .user(user)
                .build();
        save(this.noteRepository, note, "Note");
    }

    @Override
    public List<NoteResponse> retrieveAllByUser(final Long userId) {
        final Sort sortByUpdatedAt = Sort.by(Sort.Direction.DESC, "updatedAt");
        final List<Note> notes = this.noteRepository.findAllByUserId(userId, sortByUpdatedAt);
        return mapList(notes, NoteResponse.class, this.modelMapper);
    }

    @Override
    public NoteResponse retrieveOne(final Long id, final Long userId) {
        final Note note = findByIdOrThrow(this.noteRepository, id, "Note");
        return map(note, NoteResponse.class, this.modelMapper);
    }

    @Override
    public void updateNote(final Long id, final UpdateNoteRequest updateNoteRequest) {
        final Note note = findByIdOrThrow(this.noteRepository, id, "Note");
        note.setTitle(updateNoteRequest.getTitle());
        note.setBody(updateNoteRequest.getBody());
        save(this.noteRepository, note, "Note");
    }

    @Override
    public void deleteNote(final Long id) {
        deleteById(this.noteRepository, id, "Note");
    }
}
