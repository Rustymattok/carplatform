package presistens;
import com.google.gson.Gson;
import models.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Logger;
/**
 * DAO for many to many.
 */
public class DAOItems implements Store {
    private static  final DAOItems INSTANCE = new DAOItems();
    private static final SessionFactory factory = new Configuration().configure().buildSessionFactory();
    private List<Item> listOfItems = new LinkedList<>();

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
    @Override
    /**
     * Method for convert Data - > Json items .
     * @param bodyList - items.
     * @return - String json.
     */
    public String getItemsJsonFormat(){
        listOfItems = getData();
        List<ItemConvert> jsonList = new LinkedList<>();
        for (Item item : listOfItems) {
            ItemConvert itemAdd = new ItemConvert();
            Body body = getBody(item.getBody().getId());
            Brand brand = getBrand(item.getBrand().getId());
            Model model = getModel(item.getModel().getId());
            Oil oil = getOil(item.getOil().getId());
            Transmission transmission = getTransmission(item.getTransmission().getId());
            User user = getUser(item.getUser().getId());
            String status = item.getStatus();
            String image = item.getImage();
            Integer price = item.getPrice();
            itemAdd.setBrand(brand.getName());
            itemAdd.setBody(body.getName());
            itemAdd.setModel(model.getName());
            itemAdd.setImage(image);
            itemAdd.setOil(oil.getName());
            itemAdd.setTransmission(transmission.getName());
            itemAdd.setPrice(price);
            itemAdd.setStatus(status);
            itemAdd.setUser(user.getName());
            jsonList.add(itemAdd);
        }
        String jsonString ="";
        Gson gson = new Gson();
        jsonString = gson.toJson(jsonList);
        return jsonString;
    }
    @Override
    /**
     * HQL - request for all data.
     * new User() - all users.
     * new Body() - all body type.
     * new Oil() - all oil type.
     * new Transmission - all transmission type.
     * new Brand() - all brand type.
     * new Model() - all model type.
     * @return bodyList  - bodyList of choose object.
     */
    public List<Object> getSelectAllElements(Object obj){
        List<Object> bodyList = new LinkedList<>();
        Session session = factory.openSession();
        String nameClass = obj.getClass().getSimpleName();
        try {
            Query query = session.createQuery("from " + nameClass);
            if(query.list().size() != 0){
                bodyList = query.list();
            }
            session.close();
        }catch (Exception exception){
            bodyList = null;
            Logger.getLogger("class Object:").info("not correct data");
        }
        return bodyList;
    }
    /**
     * @param list - bodyList of choosed obj.
     * @return json  - String json(id and name from Database).
     */
    @Override
    public <T> String convertToJson(List<T> list){
        String json = "";
        Gson gson = new Gson();
        List<PatternJson> jsonList = new LinkedList<PatternJson>();
        try {
            for (T tx: list) {
                Method methodName = tx.getClass().getMethod("getName");
                Method methodId = tx.getClass().getMethod("getId");
                String name = (String)methodName.invoke(tx);
                Integer id = (Integer)methodId.invoke(tx);
                jsonList.add(new PatternJson(id,name));
            }
            json= gson.toJson(jsonList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }
    /**
     * Get items from Data.
     * @return - bodyList of Items.
     */
    public List<Item> getData() {
        this.tx(session -> {
            Query query = session.createQuery("from Item");
            listOfItems = query.list();
            return null;
        });
        return listOfItems;
    }
    /**
     * HQL - request for Body by id.
     * @param id - body id.
     * @return body object.
     */
    public Body getBody(Integer id){
        Body body = null;
        Session session = factory.openSession();
        Query query = session.createQuery("from Body where id =" + id);
        if(query.list().size() != 0){
            body = (Body) query.list().get(0);
        }
        session.close();
        return body;
    }
    /**
     * HQL - request for Brand by id.
     * @param id - brand type.
     * @return brand object.
     */
    public Brand getBrand(Integer id){
        Brand brand = null;
        Session session = factory.openSession();
        Query query = session.createQuery("from Brand where id =" + id);
        if(query.list().size() != 0){
            brand = (Brand) query.list().get(0);
        }
        session.close();
        return brand;
    }
    /**
     * HQL - request for Model by id.
     * @param id - brand type.
     * @return model object.
     */
    public Model getModel(Integer id){
        Model model = null;
        Session session = factory.openSession();
        Query query = session.createQuery("from Model where id =" + id);
        if(query.list().size() != 0){
            model = (Model) query.list().get(0);
        }
        session.close();
        return model;
    }
    /**
     * HQL - request for Oil by id.
     * @param id - oil type.
     * @return oil object.
     */
    public Oil getOil(Integer id){
        Oil oil = null;
        Session session = factory.openSession();
        Query query = session.createQuery("from Oil where id =" + id);
        if(query.list().size() != 0){
            oil = (Oil) query.list().get(0);
        }
        session.close();
        return oil;
    }
    /**
     * HQL - request for Transmission by id.
     * @param id - transmission type.
     * @return transmission object.
     */
    public Transmission getTransmission(Integer id){
        Transmission transmission = null;
        Session session = factory.openSession();
        Query query = session.createQuery("from Transmission where id =" + id);
        if(query.list().size() != 0){
            transmission = (Transmission) query.list().get(0);
        }
        session.close();
        return transmission;
    }
    /**
     * HQL - request for User by id.
     * @param id - User type.
     * @return user object.
     */
    public User getUser(Integer id){
        User user = null;
        Session session = factory.openSession();
        Query query = session.createQuery("from User where id =" + id);
        if(query.list().size() != 0){
            user = (User) query.list().get(0);
        }
        session.close();
        return user;
    }
}
