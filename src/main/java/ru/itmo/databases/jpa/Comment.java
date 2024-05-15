package ru.itmo.databases.jpa;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_comments")
public class Comment {
    @Id
    private long id;

    @NotNull
    @NotBlank
    @Size(min = 20, max = 800)
    @Column(nullable = false, length = 800)
    private String text;

    @NotNull
    @Past
    @Column(name = "created_at",
            nullable = false,
            updatable = false)
    private final LocalDateTime createdAt;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "competition_id", nullable = false)
    private Competition competition;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    public Comment() {
        createdAt = LocalDateTime.now();
    }
}
