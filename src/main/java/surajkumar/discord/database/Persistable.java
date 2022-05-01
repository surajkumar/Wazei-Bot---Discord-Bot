package surajkumar.discord.database;

import java.util.logging.Logger;

public abstract class Persistable {
    private static final Logger L = Logger.getLogger(Persistable.class.getName());

    public boolean save() {
        try {
            Hibernate.save(this);
            return true;
        } catch (Exception e) {
            Hibernate.rollback();
            L.severe(e.getMessage());
            return false;
        }
    }

    public boolean update() {
        try {
            Hibernate.update(this);
            return true;
        } catch (Exception e) {
            Hibernate.rollback();
            L.severe(e.getMessage());
            return false;
        }
    }

    public void commit() {
        Hibernate.commit();
    }

    public void delete() {
        Hibernate.delete(this);
    }
}
