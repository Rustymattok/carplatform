package presistens;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import models.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
/**
 * Class response for parsing json file add data to table Items.
 * This is for sample.
 */
public class DAOParsingItems {
    private List<Item> items = new LinkedList<>();
    private static  final DAOParsingItems INSTANCE = new DAOParsingItems();
    private static final SessionFactory factory = new Configuration().configure().buildSessionFactory();

    public DAOParsingItems() {
    }

    public static DAOParsingItems getINSTANCE() {
        return INSTANCE;
    }
    /**
     * Connect with Data by ORM and put all Items value.
     * @param list - items.
     */
    public void addItemsLast(List<Item> list){
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        for (Item item : list) {
          session.save(item);
        }
        tx.commit();
        session.close();
        factory.close();
    }
    /**
     * Data from json file.
     * @return - list of json object ItemConvert.
     */
    public List<ItemConvert> getItemConvert(String filePath) {
        List<ItemConvert> itemConvert = new LinkedList<>();
        try {
            File file =new File(filePath);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String jsonLine = reader.readLine();
            Gson gson = new Gson();
            Type founderListType = new TypeToken<ArrayList<ItemConvert>>(){}.getType();
            itemConvert = gson.fromJson(jsonLine, founderListType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemConvert;
    }
    /**
     * Convert Items Stirng to object(using hibernate connection with DataBase).
     * @param itemConverts - json list object for convert.
     * @return - list of items.
     */
    public List<Item> getItem(List<ItemConvert> itemConverts ) {
        for (ItemConvert itemConvert : itemConverts) {
            Item item = new Item();
            itemConvert.setUser("admin");
            itemConvert.setStatus("sale");
            Body body = getBody(itemConvert.getBody());
            Brand brand = getBrand(itemConvert.getBrand());
            Model model = getModel(itemConvert.getModel());
            Oil oil = getOil(itemConvert.getOil());
            Transmission transmission = getTransmission(itemConvert.getTransmission());
            User user = getUser(itemConvert.getUser());
            if(body != null && brand != null && model != null && oil != null && transmission != null && user != null){
                item.setBody(body);
                item.setBrand(brand);
                item.setModel(model);
                item.setOil(oil);
                item.setTransmission(transmission);
                item.setStatus(itemConvert.getStatus());
                Integer price = Double.valueOf(itemConvert.getPrice()).intValue();
                item.setPrice(price);
                item.setImage(itemConvert.getImage());
                item.setUser(user);
                items.add(item);
            }
        }
        return items;
    }
    /**
     * HQL - request for Body by name
     * @param bodyName - body type.
     * @return body object.
     */
    public Body getBody(String bodyName){
        Body body = null;
        Session session = factory.openSession();
        Query query = session.createQuery("from Body where name ="+"'"+bodyName+"'");
        if(query.list().size() != 0){
             body = (Body) query.list().get(0);
        }
        session.close();
        return body;
    }
    /**
     * HQL - request for Brand by name
     * @param brandName - brand type.
     * @return brand object.
     */
    public Brand getBrand(String brandName){
        Brand brand = null;
        Session session = factory.openSession();
        Query query = session.createQuery("from Brand where name ="+"'"+brandName+"'");
        if(query.list().size() != 0){
            brand = (Brand) query.list().get(0);
        }
        session.close();
        return brand;
    }
    /**
     * HQL - request for Model by name
     * @param modelName - brand type.
     * @return model object.
     */
    public Model getModel(String modelName){
        Model model = null;
        Session session = factory.openSession();
        Query query = session.createQuery("from Model where name ="+"'"+modelName+"'");
        if(query.list().size() != 0){
            model = (Model) query.list().get(0);
        }
        session.close();
        return model;
    }
    /**
     * HQL - request for Oil by name
     * @param oilName - oil type.
     * @return oil object.
     */
    public Oil getOil(String oilName){
        Oil oil = null;
        Session session = factory.openSession();
        Query query = session.createQuery("from Oil where name ="+"'"+oilName+"'");
        if(query.list().size() != 0){
            oil = (Oil) query.list().get(0);
        }
        session.close();
        return oil;
    }
    /**
     * HQL - request for Transmission by name
     * @param transmissionName - transmission type.
     * @return transmission object.
     */
    public Transmission getTransmission(String transmissionName){
        Transmission transmission = null;
        Session session = factory.openSession();
        Query query = session.createQuery("from Transmission where name ="+"'"+transmissionName+"'");
        if(query.list().size() != 0){
            transmission = (Transmission) query.list().get(0);
        }
        session.close();
        return transmission;
    }
    /**
     * HQL - request for User by name
     * @param userName - User type.
     * @return user object.
     */
    public User getUser(String userName){
        User user = null;
        Session session = factory.openSession();
        Query query = session.createQuery("from User where name ="+"'"+userName+"'");
        if(query.list().size() != 0){
            user = (User) query.list().get(0);
        }
        session.close();
        return user;
    }

    public void setItem(List<Item> items) {
        this.items = items;
    }
}
