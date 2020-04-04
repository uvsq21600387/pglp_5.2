package mathieu.pglp_5_2.dao;

import java.util.ArrayList;
import java.util.Map;

/**
 * Pattern DAO.
 * @param <T> type pour le DAO
 */
public interface Dao<T> {
    /**
     * ajoute un élément au DAO.
     * @param object l'élément à ajouter
     */
    void add(T object);
    /**
     * obtenir un élément par son identifiant.
     * @param id identifiant de l'élément à obtenir
     * @return l'élément souhaité
     */
    T get(int id);
    /**
     * obtenir la liste des éléments du DAO.
     * @return la liste des éléments du DAO
     */
    ArrayList<T> getAll();
    /**
     * modifie un élément du DAO.
     * @param object l'élément à modifier
     * @param params les paramètres à modifier
     */
    void update(T object, Map<String, Object> params);
    /**
     * supprime un élément du DAO.
     * @param object élément à supprimer
     */
    void remove(T object);
}
