package mathieu.pglp_5_2.personnel;

/**
 * interface pour le pattern composite associé à CompositePersonnels.
 */
public interface InterfacePersonnels {
    /**
     * pour s'afficher.
     */
    void print();
    /**
     * représentation en chaine de caractère.
     * @return toString d'un InterfacePersonnels
     */
    String toString();
    /**
     * récupérer l'identifiant.
     * @return son identifiant
     */
    int getId();
}
