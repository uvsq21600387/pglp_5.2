package mathieu.pglp_5_2.personnel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * classe représentant un personnel
 * implémente InterfacePersonnels en tant que leaf.
 */
public final class Personnel implements InterfacePersonnels, Serializable {
    /**
     * serial number.
     */
    private static final long serialVersionUID = 8028856539374241727L;
    /**
     * identifiant personnel.
     */
    private int id;
    /**
     * obtenir l'id du personnel.
     * @return l'identifiant
     */
    public int getId() {
        return id;
    }
    /**
     * modifier l'identifiant.
     * @param newId nouvel identifiant
     */
    public void setId(final int newId) {
        id = newId;
    }
    /**
     * nom de la personne.
     */
    private final String nom;
    /**
     * récupérer le nom.
     * @return le nom
     */
    public String getNom() {
        return nom;
    }
    /**
     * prenom de la personne.
     */
    private final String prenom;
    /**
     * récupérer le prenom.
     * @return le prénom
     */
    public String getPrenom() {
        return prenom;
    }
    /**
     * date de naissance de la personne.
     */
    private final java.time.LocalDate dateNaissance;
    /**
     * récupérer la date de naissance.
     * @return récupérer la date de naissance
     */
    public java.time.LocalDate getDateNaissance() {
        return dateNaissance;
    }
    /**
     * liste des numéros de téléphone de la personne.
     */
    private final ArrayList<String> numeroTelephone;
    /**
     * donne la liste des numéros de téléphone du personnel.
     * @return une copie de la liste des numéros de téléphone
     */
    @SuppressWarnings("unchecked")
    public ArrayList<String> getNumeroTelephone() {
        return (ArrayList<String>) numeroTelephone.clone();
    }
    /**
     * Pattern builder pour la classe personnel.
     */
    public static class Builder {
        /**
         * définir un id différent au personnel.
         */
        private static int idNext = 1;
        /**
         * identifiant personnel.
         */
        private final int id;
        /**
         * nom de la personne.
         */
        private final String nom;
        /**
         * prenom de la personne.
         */
        private final String prenom;
        /**
         * date de naissance de la personne.
         */
        private final java.time.LocalDate dateNaissance;
        /**
         * liste des numéros de téléphone de la personne.
         */
        private final ArrayList<String> numeroTelephone;
        /**
         * constructeur pour la classe Builder.
         * @param nomP nom de la personne
         * @param prenomP prenom de la personne
         * @param dateNaissanceP date de naissance de la personne
         * @param numeroTelephoneP les numéros de téléphone
         * de la personne
         */
        public Builder(final String nomP, final String prenomP,
            final java.time.LocalDate dateNaissanceP,
            final ArrayList<String> numeroTelephoneP) {
            this.nom = nomP;
            this.prenom = prenomP;
            this.dateNaissance = dateNaissanceP;
            Collections.sort(numeroTelephoneP);
            this.numeroTelephone = numeroTelephoneP;
            this.id = idNext++;
        }
        /**
         * construit une variable de type Personnel avec le builder.
         * @return la variable Personnel construit à partir du builder
         */
        public Personnel build() {
            return new Personnel(this);
        }
    }
    /**
     * construit le personnel avec le builder.
     * @param builder le builder pour créer un personnel
     */
    private Personnel(final Builder builder) {
        nom = builder.nom;
        prenom = builder.prenom;
        dateNaissance = builder.dateNaissance;
        numeroTelephone = builder.numeroTelephone;
        id = builder.id;
    }
    /**
     * implémentation de la méthode print() de l'interface.
     */
    public void print() {
        System.out.print(toString());
    }
    /**
     * String représentant un personnel.
     * @return représentation en String d'un personnel
     */
    public String toString() {
        String s = prenom + " " + nom
                + ", naissance : " + dateNaissance
                + ", numéros de téléphone : ";
        for (String i : numeroTelephone) {
            s += i + "  ";
        }
        return s + "\n";
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
    public static Personnel deserialize(final String path) {
        ObjectInputStream reader = null;
        Personnel p = null;
        try {
            FileInputStream file = new FileInputStream(path);
            reader = new ObjectInputStream(file);
            p = (Personnel) reader.readObject();
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
        return p;
    }
}
