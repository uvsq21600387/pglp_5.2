package mathieu.pglp_5_2.dao;

import mathieu.pglp_5_2.personnel.CompositePersonnels;
import mathieu.pglp_5_2.personnel.Personnel;

/**
 * pattern factory.
 */
public final class DaoFactory extends AbstractDaoFactory {
    /**
     * fabrique Dao pour Personnel.
     * @param deserialize charger une sauvegarde
     * @return DaoPersonnel déserializé ou vide si param = null
     */
    public static Dao<Personnel> getDaoPersonnel(final String deserialize) {
        if (deserialize == null) {
            return new DaoPersonnel();
        } else {
            return DaoPersonnel.deserialize(deserialize);
        }
    }
    /**
     * fabrique Dao pour CompositePersonnel.
     * @param deserialize charger une sauvegarde
     * @return DaoCompositePersonnel déserializé ou vide si param = null
     */
    public static Dao<CompositePersonnels>
    getDaoCompositePersonnels(final String deserialize) {
        if (deserialize == null) {
            return new DaoCompositePersonnels();
        } else {
            return DaoCompositePersonnels.deserialize(deserialize);
        }
    }
}
