package com._p1m.productivity_suite.features.note_taking.service;

import com._p1m.productivity_suite.config.utils.PersistenceUtils;
import com._p1m.productivity_suite.config.utils.RepositoryUtils;
import com._p1m.productivity_suite.data.models.Note;
import com._p1m.productivity_suite.data.models.User;
import com._p1m.productivity_suite.features.note_taking.dto.CreateNoteRequest;
import com._p1m.productivity_suite.features.note_taking.dto.NoteResponse;
import com._p1m.productivity_suite.features.note_taking.dto.UpdateNoteRequest;
import com._p1m.productivity_suite.features.note_taking.repository.NoteRepository;
import com._p1m.productivity_suite.features.users.dto.response.UserDto;
import com._p1m.productivity_suite.features.users.repository.UserRepository;
import com._p1m.productivity_suite.features.users.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService{
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final UserUtil userUtil;
    private final ModelMapper modelMapper;

    @Override
    public void createNote(final CreateNoteRequest createNoteRequest, final String authHeader) {
        final UserDto userDto = this.userUtil.getCurrentUserDto(authHeader);

        final User user = RepositoryUtils.findByIdOrThrow(this.userRepository, userDto.getId(), "User");

        final Note note = new Note(
                createNoteRequest.title(),
                createNoteRequest.body(),
                user
        );

        PersistenceUtils.save(this.noteRepository, note, "Note");
    }

    @Override
    public List<NoteResponse> retrieveAll(final String authHeader) {
        final UserDto userDto = this.userUtil.getCurrentUserDto(authHeader);
        final Sort sortByUpdatedAt = Sort.by(Sort.Direction.DESC, "updatedAt");
        final List<Note> notes = RepositoryUtils.findAllByUserId(userDto.getId(), sortByUpdatedAt, this.noteRepository::findAllByUserId);
        return notes.stream()
                .map(this::toNoteResponseWithType)
                .toList();
    }

    @Override
    public NoteResponse retrieveOne(final Long id) {
        final Note note = RepositoryUtils.findByIdOrThrow(this.noteRepository, id, "Note");

        return this.toNoteResponseWithType(note);
    }

    @Override
    public void updateNote(final UpdateNoteRequest updateNoteRequest, final Long id) {
        final Note note = RepositoryUtils.findByIdOrThrow(this.noteRepository, id, "Note");

        note.setTitle(updateNoteRequest.getTitle());
        note.setBody(updateNoteRequest.getBody());
        PersistenceUtils.save(this.noteRepository, note, "Note");
    }

    @Override
    public void deleteNote(final Long id) {
        RepositoryUtils.findByIdOrThrow(this.noteRepository, id, "Note");

        PersistenceUtils.deleteById(this.noteRepository, id, "Note");
    }

    private NoteResponse toNoteResponseWithType(final Note note) {
        return new NoteResponse(
                note.getId(),
                note.getTitle(),
                note.getBody(),
                note.getCreatedAt(),
                note.getUpdatedAt()
        );
    }
}
