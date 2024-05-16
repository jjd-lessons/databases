package ru.itmo.databases.unit;

public class Service {
    private Dao dao;

    public Service(Dao dao) {
        this.dao = dao;
    }

    public String saveString(String string){
        try {
            if (dao.addString(string)) return "SUCCESS";
        } catch (TestException e) {
            return "FAIL";
        }
        return "FAIL";
    }
}
