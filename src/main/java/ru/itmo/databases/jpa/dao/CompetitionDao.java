package ru.itmo.databases.jpa.dao;

import jakarta.persistence.EntityManager;
import ru.itmo.databases.jpa.Competition;

public class CompetitionDao
        extends Dao<Competition, Integer> {


    public CompetitionDao(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Integer insert(Competition competition) {
        entityManager.getTransaction().begin();
        entityManager.persist(competition);
        entityManager.getTransaction().commit();
        return competition.getId();
    }

    @Override
    public void update(Competition competition) {
        entityManager.getTransaction().begin();
        entityManager.merge(competition);
        entityManager.getTransaction().commit();
    }

    @Override
    public Competition deleteById(Integer integer) {
        Competition competition = entityManager.find(Competition.class, integer);
        entityManager.getTransaction().begin();
        entityManager.remove(competition);
        entityManager.getTransaction().commit();
        return competition;
    }

    @Override
    public Competition findById(Integer integer) {
        return entityManager.find(Competition.class, integer);
    }
}
