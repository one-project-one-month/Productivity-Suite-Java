package com._p1m.productivity_suite.features.note_taking.controller;

import com._p1m.productivity_suite.config.request.RequestUtils;
import com._p1m.productivity_suite.config.response.dto.ApiResponse;
import com._p1m.productivity_suite.config.response.utils.ResponseUtils;
import com._p1m.productivity_suite.features.note_taking.dto.CreateNoteRequest;
import com._p1m.productivity_suite.features.note_taking.dto.NoteResponse;
import com._p1m.productivity_suite.features.note_taking.dto.UpdateNoteRequest;
import com._p1m.productivity_suite.features.note_taking.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Note Module", description = "Endpoints for note-taking management")
@RestController
@RequestMapping("/productivity-suite/api/v1/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @PostMapping
    @Operation(
            summary = "Create a new note",
            description = "Creates a new note for the authenticated user.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200", description = "Note created successfully",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    public ResponseEntity<ApiResponse> createNote(
            @Validated @RequestBody final CreateNoteRequest createNoteRequest,
            @RequestHeader("Authorization") final String authHeader,
            final HttpServletRequest request
    ) {
        final double requestStartTime = RequestUtils.extractRequestStartTime(request);

        this.noteService.createNote(createNoteRequest, authHeader);

        final ApiResponse response = ApiResponse.builder()
                .success(1)
                .code(200)
                .data(true)
                .message("Note created successfully")
                .build();
        return ResponseUtils.buildResponse(request, response, requestStartTime);
    }

    @GetMapping
    @Operation(
            summary = "Retrieve all notes by user",
            description = "Fetches all notes belonging to the authenticated user.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Notes retrieved successfully",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    public ResponseEntity<ApiResponse> retrieveAllNotes(
            @RequestHeader("Authorization") final String authHeader,
            final HttpServletRequest request
    ) {
        final double requestStartTime = RequestUtils.extractRequestStartTime(request);

        final List<NoteResponse> notes = this.noteService.retrieveAllByUser(authHeader);

        final ApiResponse response = ApiResponse.builder()
                .success(1)
                .code(200)
                .data(notes)
                .message("Notes retrieved successfully")
                .build();
        return ResponseUtils.buildResponse(request, response, requestStartTime);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Retrieve a specific note by ID",
            description = "Fetches the details of a note by its ID.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Note retrieved successfully",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    public ResponseEntity<ApiResponse> retrieveNoteById(
            @RequestHeader("Authorization") final String authHeader,
            @PathVariable final Long id,
            final HttpServletRequest request
    ) {
        final double requestStartTime = RequestUtils.extractRequestStartTime(request);

        final NoteResponse note = this.noteService.retrieveOne(authHeader, id);

        final ApiResponse response = ApiResponse.builder()
                .success(1)
                .code(200)
                .data(note)
                .message("Note retrieved successfully")
                .build();
        return ResponseUtils.buildResponse(request, response, requestStartTime);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update a note by ID",
            description = "Updates the title and/or body of an existing note.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Note updated successfully",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    public ResponseEntity<ApiResponse> updateNote(
            @RequestHeader("Authorization") final String authHeader,
            @PathVariable final Long id,
            @Validated @RequestBody final UpdateNoteRequest updateNoteRequest,
            final HttpServletRequest request
    ) {
        final double requestStartTime = RequestUtils.extractRequestStartTime(request);

        this.noteService.updateNote(updateNoteRequest, authHeader, id);

        final ApiResponse response = ApiResponse.builder()
                .success(1)
                .code(200)
                .data(true)
                .message("Note updated successfully")
                .build();
        return ResponseUtils.buildResponse(request, response, requestStartTime);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a note by ID",
            description = "Deletes the specified note.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Note deleted successfully",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    public ResponseEntity<ApiResponse> deleteNote(
            @RequestHeader("Authorization") final String authHeader,
            @PathVariable final Long id,
            final HttpServletRequest request
    ) {
        final double requestStartTime = RequestUtils.extractRequestStartTime(request);

        this.noteService.deleteNote(authHeader, id);

        final ApiResponse response = ApiResponse.builder()
                .success(1)
                .code(200)
                .data(true)
                .message("Note deleted successfully")
                .build();
        return ResponseUtils.buildResponse(request, response, requestStartTime);
    }
}
