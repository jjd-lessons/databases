package ru.itmo.databases.jpa;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_prizes")
public class Prize {

    @Id
    // AUTO - JPA провайдер решает, как генерировать уникальные ID для сущности
    // SEQUENCE - используется последовательность - объект БД для генерации уникальных значений, реализуется средствами БД
    // TABLE - эмуляция последовательность в отдельной таблице, реализуется средствами ORM
    // IDENTITY - используется встроенный в БД тип данных для генерации значения первичного ключа
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 20)
    @Column(nullable = false)
    private String title;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "place")
    private PlaceNumber placeNumber;

    @Positive
    @Column(name = "competition_id")
    private int competitionId;

    public enum PlaceNumber{
        FIRST, SECOND, THIRD;
    }
}
