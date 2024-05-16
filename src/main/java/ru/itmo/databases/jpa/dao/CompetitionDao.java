package ru.itmo.databases.jpa.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import ru.itmo.databases.jpa.model.Competition;

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

    public Competition findByTitle(String title) {
        String nativeSql = "SELECT * FROM tb_competition where title ILIKE ?";
        String jpql = "SELECT c FROM Competition c WHERE c.title ILIKE ?";

        TypedQuery<Competition> namedNativeQuery = entityManager
                .createNamedQuery("get_by_name", Competition.class);
        Competition competition01 = namedNativeQuery.getSingleResult();


        TypedQuery<Competition> jpqlQuery = entityManager
                .createQuery(jpql, Competition.class);
        Competition competition02 = jpqlQuery.getSingleResult();

        TypedQuery<Competition> nativeQuery = entityManager.createNamedQuery(nativeSql, Competition.class);
        Competition competition03 = nativeQuery.getSingleResult();

        return competition01;
    }
}
