package ru.itmo.databases.model;

import ru.itmo.databases.model.Author;

import java.time.OffsetDateTime;

public class Note {
    private long id;
    private String title;
    private String text;
    private OffsetDateTime createdAt;
    private Author author;
}
