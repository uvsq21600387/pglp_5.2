package mathieu.pglp_5_2.dao;

public abstract class AbstractDaoFactory {
    public enum DaoType {
        CRUD,
        JDBC; 
    }
}
