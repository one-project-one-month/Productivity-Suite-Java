package com._p1m.productivity_suite.features.note_taking.controller;

import com._p1m.productivity_suite.config.annotations.AuthorizationCheck;
import com._p1m.productivity_suite.config.request.RequestUtils;
import com._p1m.productivity_suite.config.response.dto.ApiResponse;
import com._p1m.productivity_suite.config.response.utils.ResponseUtils;
import com._p1m.productivity_suite.features.note_taking.dto.NoteCategoryData;
import com._p1m.productivity_suite.features.note_taking.dto.NoteRequest;
import com._p1m.productivity_suite.features.note_taking.dto.NoteResponse;
import com._p1m.productivity_suite.features.note_taking.dto.NoteRetrieveOneData;
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

    @PostMapping("/react")
    @Operation(
            summary = "Create a new note for React",
            description = "Creates a new note for the authenticated user.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Note created successfully",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    public ResponseEntity<ApiResponse> createNoteForReact(
            @Validated @RequestBody final NoteRequest createNoteRequest,
            @RequestHeader(value = "Authorization") final String authHeader,
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

    @PostMapping("/flutter")
    @Operation(
            summary = "Create a new note for Flutter",
            description = "Creates a new note for the authenticated user.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Note created successfully",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    public ResponseEntity<ApiResponse> createNoteForFlutter(
            @Validated @RequestBody final List<NoteRequest> createNoteRequestList,
            @RequestHeader(value = "Authorization") final String authHeader,
            final HttpServletRequest request
    ) {
        final double requestStartTime = RequestUtils.extractRequestStartTime(request);

        for (final NoteRequest createNoteRequest : createNoteRequestList) {
            this.noteService.createNote(createNoteRequest, authHeader);
        }

        final ApiResponse response = ApiResponse.builder()
                .success(1)
                .code(200)
                .data(true)
                .message("Note created successfully")
                .build();
        return ResponseUtils.buildResponse(request, response, requestStartTime);
    }

//    @GetMapping
//    @Operation(
//            summary = "Retrieve all notes",
//            description = "Fetches a list of all notes.",
//            responses = {
//                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Notes retrieved successfully",
//                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
//            }
//    )
//    public ResponseEntity<ApiResponse> retrieveAllNotes(
//            @RequestHeader(value = "Authorization") final String authHeader,
//            final HttpServletRequest request
//    ) {
//        final double requestStartTime = RequestUtils.extractRequestStartTime(request);
//
//        final List<NoteResponse> notes = this.noteService.retrieveAll(authHeader);
//
//        final ApiResponse response = ApiResponse.builder()
//                .success(1)
//                .code(200)
//                .data(notes)
//                .message("Notes retrieved successfully")
//                .build();
//        return ResponseUtils.buildResponse(request, response, requestStartTime);
//    }

    @GetMapping
    @Operation(
            summary = "Retrieve all notes by User",
            description = "Fetches a list of all notes by User.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Notes retrieved successfully",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    public ResponseEntity<ApiResponse> retrieveAllNotes(
            @RequestHeader(value = "Authorization") final String authHeader,
            final HttpServletRequest request
    ) {
        final double requestStartTime = RequestUtils.extractRequestStartTime(request);

        final List<NoteCategoryData> notes = this.noteService.retrieveAll(authHeader);

        final ApiResponse response = ApiResponse.builder()
                .success(1)
                .code(200)
                .data(notes)
                .message("Notes retrieved successfully")
                .build();
        return ResponseUtils.buildResponse(request, response, requestStartTime);
    }

    @AuthorizationCheck(resource = "CATEGORY", idParam = "categoryId")
    @GetMapping("/by-category")
    @Operation(
            summary = "Retrieve all notes by Category ID",
            description = "Fetches a list of all notes by Category ID.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Notes retrieved successfully",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    public ResponseEntity<ApiResponse> retrieveAllNotesByCategory(
            @RequestHeader(value = "Authorization") final String authHeader,
            @RequestParam final Long categoryId,
            final HttpServletRequest request
    ) {
        final double requestStartTime = RequestUtils.extractRequestStartTime(request);

        final NoteResponse notes = this.noteService.retrieveAllByCategoryId(authHeader, categoryId);

        final ApiResponse response = ApiResponse.builder()
                .success(1)
                .code(200)
                .data(notes)
                .message("Notes retrieved successfully")
                .build();
        return ResponseUtils.buildResponse(request, response, requestStartTime);
    }

//    @AuthorizationCheck(resource = "NOTE", idParam = "id")
//    @GetMapping("/{id}")
//    @Operation(
//            summary = "Retrieve a specific note by ID",
//            description = "Fetches the details of a specific note by its ID.",
//            responses = {
//                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Note retrieved successfully",
//                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
//            }
//    )
//    public ResponseEntity<ApiResponse> retrieveNoteById(
//            @PathVariable final Long id,
//            final HttpServletRequest request
//    ) {
//        final double requestStartTime = RequestUtils.extractRequestStartTime(request);
//
//        final NoteResponse note = this.noteService.retrieveOne(id);
//
//        final ApiResponse response = ApiResponse.builder()
//                .success(1)
//                .code(200)
//                .data(note)
//                .message("Note retrieved successfully")
//                .build();
//        return ResponseUtils.buildResponse(request, response, requestStartTime);
//    }

    @AuthorizationCheck(resource = "NOTE", idParam = "id")
    @GetMapping("/{id}")
    @Operation(
            summary = "Retrieve a specific note by ID",
            description = "Fetches the details of a specific note by its ID.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Note retrieved successfully",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    public ResponseEntity<ApiResponse> retrieveNoteById(
            @PathVariable final Long id,
            final HttpServletRequest request
    ) {
        final double requestStartTime = RequestUtils.extractRequestStartTime(request);

        final NoteRetrieveOneData note = this.noteService.retrieveOne(id);

        final ApiResponse response = ApiResponse.builder()
                .success(1)
                .code(200)
                .data(note)
                .message("Note retrieved successfully")
                .build();
        return ResponseUtils.buildResponse(request, response, requestStartTime);
    }

    @AuthorizationCheck(resource = "NOTE", idParam = "id")
    @PutMapping("/{id}")
    @Operation(
            summary = "Update a note",
            description = "Updates the title and/or body of an existing note.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Note updated successfully",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    public ResponseEntity<ApiResponse> updateNote(
            @PathVariable final Long id,
            @Validated @RequestBody final NoteRequest updateNoteRequest,
            final HttpServletRequest request
    ) {
        final double requestStartTime = RequestUtils.extractRequestStartTime(request);

        this.noteService.updateNote(updateNoteRequest, id);

        final ApiResponse response = ApiResponse.builder()
                .success(1)
                .code(200)
                .data(true)
                .message("Note updated successfully")
                .build();
        return ResponseUtils.buildResponse(request, response, requestStartTime);
    }

    @AuthorizationCheck(resource = "NOTE", idParam = "id")
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
            @PathVariable final Long id,
            final HttpServletRequest request
    ) {
        final double requestStartTime = RequestUtils.extractRequestStartTime(request);

        this.noteService.deleteNote(id);

        final ApiResponse response = ApiResponse.builder()
                .success(1)
                .code(200)
                .data(true)
                .message("Note deleted successfully")
                .build();
        return ResponseUtils.buildResponse(request, response, requestStartTime);
    }

    @AuthorizationCheck(resource = "NOTE", idParam = "id")
    @PatchMapping("/{id}/pin")
    @Operation(
            summary = "Toggle pinned status of a single note",
            description = "Toggles the pin/unpin status of a note. No request body or params needed.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Note pinned/unpinned successfully",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    public ResponseEntity<ApiResponse> togglePinSingleNote(
            @PathVariable final Long id,
            final HttpServletRequest request
    ) {
        final double requestStartTime = RequestUtils.extractRequestStartTime(request);

        final boolean pinnedNow = this.noteService.togglePinStatus(id);

        final ApiResponse response = ApiResponse.builder()
                .success(1)
                .code(200)
                .data(true)
                .message(pinnedNow ? "Note pinned successfully" : "Note unpinned successfully")
                .build();

        return ResponseUtils.buildResponse(request, response, requestStartTime);
    }

    @PutMapping("/pin")
    @Operation(
            summary = "Toggle pinned status for multiple notes",
            description = "Pins or unpins multiple notes by their IDs.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Notes pinned/unpinned successfully",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    public ResponseEntity<ApiResponse> togglePinBulkNotes(
            @RequestBody final List<Long> noteIds,
            final HttpServletRequest request
    ) {
        final double requestStartTime = RequestUtils.extractRequestStartTime(request);

        final boolean allPinned = this.noteService.togglePinStatusBulk(noteIds);

        final ApiResponse response = ApiResponse.builder()
                .success(1)
                .code(200)
                .data(true)
                .message(allPinned ? "Notes pinned successfully" : "Notes unpinned successfully")
                .build();

        return ResponseUtils.buildResponse(request, response, requestStartTime);
    }

    @AuthorizationCheck(resource = "CATEGORY", idParam = "categoryId")
    @DeleteMapping("/by-category")
    @Operation(
            summary = "Delete all notes by Category ID",
            description = "Deletes all notes associated with the specified Category ID.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Notes deleted successfully",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    public ResponseEntity<ApiResponse> deleteNotesByCategoryId(
            @RequestHeader(value = "Authorization") final String authHeader,
            @RequestParam final Long categoryId,
            final HttpServletRequest request
    ) {
        final double requestStartTime = RequestUtils.extractRequestStartTime(request);

        this.noteService.deleteAllByCategoryId(authHeader, categoryId);

        final ApiResponse response = ApiResponse.builder()
                .success(1)
                .code(200)
                .data(true)
                .message("All notes under the category deleted successfully")
                .build();

        return ResponseUtils.buildResponse(request, response, requestStartTime);
    }

}
