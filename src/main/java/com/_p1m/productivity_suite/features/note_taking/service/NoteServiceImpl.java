package com._p1m.productivity_suite.features.note_taking.service;

import com._p1m.productivity_suite.config.exceptions.UnauthorizedException;
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

import static com._p1m.productivity_suite.config.utils.AuthorizationUtils.checkUserAuthorization;
import static com._p1m.productivity_suite.config.utils.EntityServiceHelper.*;
import static com._p1m.productivity_suite.config.utils.PersistenceUtils.*;
import static com._p1m.productivity_suite.config.utils.RepositoryUtils.*;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService{
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final UserUtil userUtil;
    private final ModelMapper modelMapper;

    @Override
    public void createNote(final CreateNoteRequest createNoteRequest, final String authHeader) {
        final UserDto userDto = userUtil.getCurrentUserDto(authHeader);
        final User user = findByIdOrThrow(this.userRepository, userDto.getId(), "User");
        final Note note = Note.builder()
                .title(createNoteRequest.getTitle())
                .body(createNoteRequest.getBody())
                .user(user)
                .build();

        save(this.noteRepository, note, "Note");
    }

    @Override
    public List<NoteResponse> retrieveAllByUser(final String authHeader) {
        final UserDto userDto = this.userUtil.getCurrentUserDto(authHeader);
        final Sort sortByUpdatedAt = Sort.by(Sort.Direction.DESC, "updatedAt");
        final List<Note> notes = findAllByUserId(userDto.getId(), sortByUpdatedAt, this.noteRepository::findAllByUserId);
        return mapList(notes, NoteResponse.class, this.modelMapper);
    }

    @Override
    public NoteResponse retrieveOne(final String authHeader, final Long id) {
        final UserDto userDto = userUtil.getCurrentUserDto(authHeader);
        final Note note = findByIdOrThrow(this.noteRepository, id, "Note");

        checkUserAuthorization(userDto, note, (entity, user) -> entity.getUser().getId());

        return map(note, NoteResponse.class, this.modelMapper);
    }

    @Override
    public void updateNote(final UpdateNoteRequest updateNoteRequest, final String authHeader, final Long id) {
        final UserDto userDto = userUtil.getCurrentUserDto(authHeader);
        final Note note = findByIdOrThrow(this.noteRepository, id, "Note");

        checkUserAuthorization(userDto, note, (entity, user) -> entity.getUser().getId());

        note.setTitle(updateNoteRequest.getTitle());
        note.setBody(updateNoteRequest.getBody());
        save(this.noteRepository, note, "Note");
    }

    @Override
    public void deleteNote(final String authHeader, final Long id) {
        final UserDto userDto = userUtil.getCurrentUserDto(authHeader);
        final Note note = findByIdOrThrow(this.noteRepository, id, "Note");

        checkUserAuthorization(userDto, note, (entity, user) -> entity.getUser().getId());

        deleteById(this.noteRepository, id, "Note");
    }
}
