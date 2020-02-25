package presistens;
import models.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

public class DAOItemsTest {
    /**
     * Test add ORM method.
     */
    @Test
    public void addTest() {
        DAOItems daoItems = DAOItems.getINSTANCE();
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        session.beginTransaction();
        Brand brand = session.get(Brand.class,1);
        Model model = session.get(Model.class,21);
        Body body = session.get(Body.class,1);
        Oil oil = session.get(Oil.class,1);
        Transmission transmission = session.get(Transmission.class,1);
        User user = session.get(User.class,1);
        user.setRole(session.get(Role.class,1));
        Integer price = 500000;
        String image = "ссылка для примера";
        String status = "sale";
        Item item = new Item();
        item.setBrand(brand);
        item.setModel(model);
        item.setBody(body);
        item.setUser(user);
        item.setPrice(price);
        item.setOil(oil);
        item.setTransmission(transmission);
        item.setImage(image);
        item.setStatus(status);
        daoItems.add(item);
        session.close();
        factory.close();
    }
    /**
     * Test remove ORM method.
     */
    @Test
    public void remove() {
        DAOItems daoItems = DAOItems.getINSTANCE();
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        session.beginTransaction();
        Item item = session.get(Item.class,1);
        daoItems.remove(item.getId());
        session.close();
        factory.close();
    }
}