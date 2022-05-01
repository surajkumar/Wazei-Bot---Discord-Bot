package surajkumar.discord.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Map;

public class Hibernate {
    private final static SessionFactory sessionFactory =
            new Configuration()
                    .configure()
                    .buildSessionFactory();

    public static List<?> queryDatabase(String statement, Map<String, Object> params) {
        var session = getSession();
        try {
            var query = session.createQuery(statement);
            if (params != null) {
                params.forEach(query::setParameter);
            }
            return query.list();
        } finally {
            session.getTransaction().commit();
        }
    }

    public static void save(Persistable obj) {
        getSession().save(obj);
    }

    public static void saveAndCommit(Persistable obj) {
        var session = getSession();
        session.save(obj);
        commit(session);
    }

    public static void update(Persistable obj) {
        var session = getSession();
        session.update(obj);
    }

    public static void rollback() {
        var session = getSession();
        session.getTransaction().rollback();
    }

    public static void delete(Persistable obj) {
        var session = getSession();
        session.delete(obj);
        commit(session);
    }

    public static void commit(Session session) {
        session.getTransaction().commit();
    }

    public static void commit() {
        getSession().getTransaction().commit();
    }

    private static Session getSession() {
        var session =
                sessionFactory.isClosed()
                        ? sessionFactory.openSession()
                        : sessionFactory.getCurrentSession();
        if(!session.getTransaction().isActive()) {
            session.beginTransaction();
        }
        return session;
    }

    public static boolean isReady() {
        return (sessionFactory.isClosed()
                ? sessionFactory.openSession()
                : sessionFactory.getCurrentSession()).isOpen();
    }
}