package mathieu.pglp_5_2.dao;

import java.sql.Connection;
import java.util.Map;

/**
 * Pattern DAO.
 * @param <T> type pour le DAO
 */
public abstract class Dao<T> {
    /**
     * variable de connection à la base de données.
     */
    protected Connection connect;
    /**
     * constructeur de la classe.
     */
    public Dao(Connection connection) {
        connect = connection;
    }
    /**
     * ajoute un élément au DAO.
     * @param object l'élément à ajouter
     */
    public abstract T create(T object);
    /**
     * obtenir un élément par son identifiant.
     * @param id identifiant de l'élément à obtenir
     * @return l'élément souhaité
     */
    public abstract T find(int id);
    /**
     * modifie un élément du DAO.
     * @param object l'élément à modifier
     * @param params les paramètres à modifier
     */
    public abstract T update(T object);
    /**
     * supprime un élément du DAO.
     * @param object élément à supprimer
     */
    public abstract void delete(T object);
}
