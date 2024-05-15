package ru.itmo.databases.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import ru.itmo.databases.jpa.dao.CompetitionDao;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence
                .createEntityManagerFactory("competitions");
        EntityManager manager = factory.createEntityManager();

        Competition competition = new Competition(
                "Соревнование",
                "{\"text\": \"№1\", \"color\": \"white\"}"
        );

        CompetitionDao competitionDao = new CompetitionDao(manager);

        int insertedId = competitionDao.insert(competition);
        System.out.println("insertedId: " + insertedId);

        Competition fromDB = competitionDao.findById(insertedId);
        System.out.println("fromDB: " + fromDB.getTitle());

        fromDB.setTitle("Соревнование №1");

        competitionDao.update(competition);

        manager.close();
        factory.close();
    }
}
