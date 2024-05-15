package ru.itmo.databases.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Author {
    private int id;
    private String uniqueName;
    private LocalDate registeredAt;
    private boolean isActive = true;
    private List<Note> notes = new ArrayList<>();
}


