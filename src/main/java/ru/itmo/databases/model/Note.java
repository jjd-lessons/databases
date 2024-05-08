package ru.itmo.databases.model;

import lombok.Getter;
import lombok.Setter;
import ru.itmo.databases.model.Author;

import java.time.OffsetDateTime;

@Getter
@Setter
public class Note {
    private long id;
    private String title;
    private String text;
    private OffsetDateTime createdAt;
    private Author author;
}
