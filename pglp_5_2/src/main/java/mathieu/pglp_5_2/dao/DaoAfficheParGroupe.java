package mathieu.pglp_5_2.dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import mathieu.pglp_5_2.personnel.AfficheParGroupe;
import mathieu.pglp_5_2.personnel.InterfacePersonnels;

/**
 * Dao pour la classe AfficheParGroupe.
 */
public class DaoAfficheParGroupe
implements Dao<AfficheParGroupe>, Serializable {
    /**
     * serial number pour la serialization.
     */
    private static final long serialVersionUID = 7495792860593545943L;
    /**
     * ensemble des AfficheParGroupe du DAO.
     */
    private ArrayList<AfficheParGroupe> list;
    /**
     * constructeur de la classe.
     */
    public DaoAfficheParGroupe() {
        list = new ArrayList<AfficheParGroupe>();
    }
    /**
     * ajoute un AfficheParGroupe dans la liste du DAO.
     * @param object l'élément à ajouter
     */
    public void add(final AfficheParGroupe object) {
        list.add(object);
    }
    /**
     * obtenir un AfficheParGroupe du DAO.
     * @param id identifiant de l'élément à obtenir
     * @return null si aucun AfficheParGroupe existe avec l'id spécifié
     * et le AfficheParGroupe sinon
     */
    public AfficheParGroupe get(final int id) {
        for (AfficheParGroupe apg : list) {
            if (apg.getId() == id) {
                return apg;
            }
        }
        return null;
    }
    /**
     * obtenir une liste de tout les AfficheParGroupe du DAO.
     * @return une liste des AfficheParGroupe du DAO
     */
    @SuppressWarnings("unchecked")
    public ArrayList<AfficheParGroupe> getAll() {
        return (ArrayList<AfficheParGroupe>) list.clone();
    }
    /**
     * modifier un AfficheParGroupe.
     * la clé doit être identique à l'attribut.
     * la valeur doit être de même type que l'attribut.
     * l'identifiant ne peut être ajouté
     * à la liste des paramètres pour le modifier.
     * @param object l'élément à modifier
     * @param params les paramètres à modifier
     */
    @SuppressWarnings("unchecked")
    public void update(final AfficheParGroupe object,
            final Map<String, Object> params) {
        if (list.contains(object)) {
            ArrayList<InterfacePersonnels> remplace =
            (ArrayList<InterfacePersonnels>) params.get("file");
            if (params.containsKey("file")) {
                object.reset();
                for (InterfacePersonnels ip : remplace) {
                    object.add(ip);
                }
            }
        }
    }
    /**
     * supprime un AfficheParGroupe du DAO.
     * @param object élément à supprimer
     */
    public void remove(final AfficheParGroupe object) {
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
    public static DaoAfficheParGroupe deserialize(final String path) {
        ObjectInputStream reader = null;
        DaoAfficheParGroupe dapg = null;
        try {
            FileInputStream file = new FileInputStream(path);
            reader = new ObjectInputStream(file);
            dapg = (DaoAfficheParGroupe) reader.readObject();
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
        return dapg;
    }
}
