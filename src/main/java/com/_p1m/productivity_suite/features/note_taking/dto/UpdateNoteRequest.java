package com._p1m.productivity_suite.features.note_taking.dto;

import lombok.Data;

@Data
public class UpdateNoteRequest {
    private String title;
    private String body;
}
