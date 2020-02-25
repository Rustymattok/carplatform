package presistens;
import models.Item;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.util.function.Function;
/**
 * DAO for many to many.
 */
public class DAOItems implements Store {
    private static  final DAOItems INSTANCE = new DAOItems();
    private static final SessionFactory factory = new Configuration().configure().buildSessionFactory();

    private DAOItems() {
    }

    public static DAOItems getINSTANCE() {
        return INSTANCE;
    }
    /**
     * Function method for lambda.
     * @param command - in description, session and tx.
     * @param <T> -  void or Object.
     * @return - Object or null.
     */
    private <T> T tx(final Function<Session, T> command) {
        final Session session =  factory.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        }finally {
            session.close();
        }
    }

    @Override
    public void add(Item item) {
        this.tx(session -> {
            session.save(item);
            return null;
        });
    }
    /**
     * Remove from DATA car by ID.
     * @param id - removed by id.
     */
    @Override
    public void remove(Integer id) {
        this.tx(session -> {
            Item item = session.get(Item.class,id);
            session.remove(item);
            return null;
        });
    }

    @Override
    public void update(Integer id, Item item) {
        this.tx(session -> {
            //todo smth
            session.update(item);
            return null;
        });
    }
}
