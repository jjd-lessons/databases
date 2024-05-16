package ru.itmo.databases.jpa.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;

@NamedNativeQueries({
        @NamedNativeQuery(name = "get_by_name", query = "SELECT * FROM tb_competition where title ILIKE ?")
})
@Data
@Entity
@Table(name = "tb_competition")
public class Competition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @NotBlank
    @Size(min = 10, max = 300)
    @Column(name = "title", // название в таблице
            unique = true, // UNIQUE INDEX
            nullable = false, // NOT NULL
            updatable = false,
            length = 300 // TEXT
    )
    private String title;

    @NotNull
    @NotBlank
    @Column(name = "label",
            columnDefinition = "jsonb", // "jsonb NOT NULL"
            nullable = false)
    @JdbcTypeCode(value = SqlTypes.JSON)
    private String label;

    @OneToMany(mappedBy = "competition")
    private List<Comment> comments = new ArrayList<>();
    // List / Set с обязательной инициализацией

    @ManyToMany
    @JoinTable(name = "tb_competition_students",
            joinColumns = @JoinColumn(name = "competition_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private List<Student> students = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "competition_id", insertable = false)
    private List<Prize> prizes = new ArrayList<>();


    public Competition(String title,
                       String label) {
        this.title = title;
        this.label = label;
    }

}

// многие ко многим
//@ManyToMany без дополнительного класса,
// если необходимо хранить только связь
// класс + @OneToMany и @ManyToOne,
// если необходимо хранить доп. информацию о связи

