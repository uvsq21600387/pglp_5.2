package mathieu.pglp_5_2.dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import mathieu.pglp_5_2.personnel.CompositePersonnels;
import mathieu.pglp_5_2.personnel.InterfacePersonnels;

/**
 * Dao pour la classe CompositePersonnels.
 */
public class DaoCompositePersonnels extends Dao<CompositePersonnels>
implements Serializable {
    /**
     * serial number pour la serialization.
     */
    private static final long serialVersionUID = 5587699980189412278L;
    /**
     * ensemble des CompositePersonnels du DAO.
     */
    private ArrayList<CompositePersonnels> list;
    /**
     * constructeur de la classe.
     */
    public DaoCompositePersonnels() {
        list = new ArrayList<CompositePersonnels>();
    }
    /**
     * ajoute un CompositePersonnels dans la liste du DAO.
     * @param object l'élément à ajouter
     */
    public void add(final CompositePersonnels object) {
        list.add(object);
    }
    /**
     * obtenir un CompositePersonnels du DAO.
     * @param id identifiant de l'élément à obtenir
     * @return null si aucun CompositePersonnels existe avec l'id spécifié
     * et le CompositePersonnels sinon
     */
    public CompositePersonnels get(final int id) {
        for (CompositePersonnels cp : list) {
            if (cp.getId() == id) {
                return cp;
            }
        }
        return null;
    }
    /**
     * obtenir une liste de tout les CompositePersonnels du DAO.
     * @return une liste des CompositePersonnels du DAO
     */
    @SuppressWarnings("unchecked")
    public ArrayList<CompositePersonnels> getAll() {
        return (ArrayList<CompositePersonnels>) list.clone();
    }
    /**
     * modifier un CompositePersonnels.
     * la clé doit être identique à l'attribut.
     * la valeur doit être de même type que l'attribut.
     * l'identifiant ne peut être ajouté
     * à la liste des paramètres pour le modifier.
     * @param object l'élément à modifier
     * @param params les paramètres à modifier
     */
    @SuppressWarnings("unchecked")
    public void update(final CompositePersonnels object,
            final Map<String, Object> params) {
        if (list.contains(object)) {
            if (params.containsKey("personnels")) {
                ArrayList<InterfacePersonnels> remplace =
                (ArrayList<InterfacePersonnels>)
                params.get("personnels");
                object.reset();
                for (InterfacePersonnels ip : remplace) {
                    object.add(ip);
                }
            }
        }
    }
    /**
     * supprime un CompositePersonnels du DAO.
     * @param object élément à supprimer
     */
    public void remove(final CompositePersonnels object) {
        list.remove(object);
    }
    /**
     * serialize vers le fichier voulu.
     * @param path nom du fichier vers lequel serializer
     */
    public void serialize(final String path) {
        ObjectOutputStream writer = null;
        try {
            FileOutputStream file = new FileOutputStream(path);
            writer = new ObjectOutputStream(file);
            writer.writeObject(this);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.err.println(
            "La serialization a échoué vers le fichier \""
            + path + "\"");
        }
        try {
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }
    /**
     * deserialize vers le fichier voulu.
     * @param path nom du fichier pour deserializer
     * @return l'instance de classe créé avec deserialization
     */
    public static DaoCompositePersonnels deserialize(final String path) {
        ObjectInputStream reader = null;
        DaoCompositePersonnels dcp = null;
        try {
            FileInputStream file = new FileInputStream(path);
            reader = new ObjectInputStream(file);
            dcp = (DaoCompositePersonnels) reader.readObject();
        } catch (IOException e) {
            System.err.println(
            "La deserialization a échoué depuis le fichier \""
            + path + "\"");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            if (reader != null) {
                reader.close();
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return dcp;
    }
}
