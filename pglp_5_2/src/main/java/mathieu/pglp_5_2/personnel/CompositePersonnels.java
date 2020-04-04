package mathieu.pglp_5_2.personnel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * pattern composite associé à l'interface InterfacePersonnels.
 */
public class CompositePersonnels
implements InterfacePersonnels, Iterable<InterfacePersonnels>,
Serializable {
    /**
     * serial number.
     */
    private static final long serialVersionUID = 5189666711357927869L;
    /**
     * liste des personnes.
     */
    private ArrayList<InterfacePersonnels> personnels;
    /**
     * identifiant pour identifier un composite.
     */
    private int id;
    /**
     * modifier l'identifiant.
     * @param newId nouvel identifiant
     */
    public void setId(int newId) {
        id = newId;
    }
    /**
     * pour attribuer un identifiant différent à chaque construction.
     */
    private static int idNext = 1;
    /**
     * constructeur de la classe.
     */
    public CompositePersonnels() {
        id = idNext++;
        personnels = new ArrayList<InterfacePersonnels>();
    }
    /**
     * implémente la méthode print() de l'interface InterfacePersonnels.
     */
    public void print() {
        System.out.print(toString());
    }
    /**
     * String représentant un CompositePersonnels.
     * @return représentation en String d'un CompositePersonnels
     */
    public String toString() {
        String s = "Id : " + id + "\n";
        for (InterfacePersonnels ip : personnels) {
            s += ip.toString();
        }
        return s;
    }
    /**
     * ajoute une personne à la liste
     * si celle-ci n'est pas déjà dans la liste.
     * @param ip la personne à ajouter
     * @return lui-même
     */
    public CompositePersonnels add(final InterfacePersonnels ip) {
        if (!personnels.contains(ip)) {
            personnels.add(ip);
        }
        return this;
    }
    /**
     * supprime une personne de la liste si celle-ci est dans la liste.
     * @param ip la personne à retirer de la liste
     * @return lui-même
     */
    public CompositePersonnels remove(final InterfacePersonnels ip) {
        System.out.println(personnels.remove(ip));
        return this;
    }
    /**
     * obtenir un itérateur su la liste des personnes de ce composite.
     * @return iterateur sur la liste des personnes de ce composite
     */
    public Iterator<InterfacePersonnels> iterator() {
        return personnels.iterator();
    }
    /**
     * getter sur l'identifiant de ce composite.
     * @return l'identifiant
     */
    public final int getId() {
        return id;
    }
    @SuppressWarnings("unchecked")
    /**
     * retourne la liste des InterfacePersonnels.
     * @return une copie de la liste
     */
    public ArrayList<InterfacePersonnels> getList(){
        return (ArrayList<InterfacePersonnels>) personnels.clone();
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
    public static CompositePersonnels deserialize(final String path) {
        ObjectInputStream reader = null;
        CompositePersonnels cp = null;
        try {
            FileInputStream file = new FileInputStream(path);
            reader = new ObjectInputStream(file);
            cp = (CompositePersonnels) reader.readObject();
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
        return cp;
    }
    /**
     * vide la liste.
     */
    public void reset() {
        personnels.clear();
    }
}
