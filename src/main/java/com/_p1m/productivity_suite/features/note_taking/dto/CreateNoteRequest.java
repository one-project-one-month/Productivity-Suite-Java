package com._p1m.productivity_suite.features.note_taking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateNoteRequest {
    private String title;
    private String body;
}
