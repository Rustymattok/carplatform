package presistens;
import com.google.gson.Gson;
import models.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class DAOFilterItems implements StoreFilter{
    private static  final DAOFilterItems INSTANCE = new DAOFilterItems();
    private static final SessionFactory factory = new Configuration().configure().buildSessionFactory();
    private List<Item> listOfItems = new LinkedList<>();

    public DAOFilterItems() {
    }

    public static DAOFilterItems getINSTANCE() {
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
    /**
     * Method for selected items by Filter
     * @param filterItems
     */
    public static String requestByFilter(FilterItems filterItems) {
        String bodyLink = bodyUrl(filterItems);
        String oilLink = oilUrl(filterItems);
        String transmissionLink = transmissionUrl(filterItems);
        String result = "";
        if(bodyLink.equals("")){
            if(oilLink.equals("") && !transmissionLink.equals("")){
                result = "from Item where " + transmissionLink;
            }
            if(!oilLink.equals("") && transmissionLink.equals("")){
                result = "from Item where " + oilLink;
            }
            if(!oilLink.equals("") && !transmissionLink.equals("")){
                result = "from Item where " + oilLink + " and " +transmissionLink;
            }
        } else{
            if(oilLink.equals("") && transmissionLink.equals("")){
                result = "from Item where " + bodyLink;
            }
            if(oilLink.equals("") && !transmissionLink.equals("")){
                result = "from Item where " + bodyLink + " and " + transmissionLink;
            }
            if(!oilLink.equals("") && transmissionLink.equals("")){
                result = "from Item where " + bodyLink + " and "+ oilLink;
            }
            if(!oilLink.equals("") && !transmissionLink.equals("")){
                result = "from Item where " + bodyLink + " and "+ oilLink + " and " +transmissionLink;
            }
        }
        if ((bodyLink.equals("") && oilLink.equals("") && transmissionLink.equals(""))){
            result = "from Item";
        }
        return  result;
    }
    /**
     * Method for convert data from getData by filter.
     * @param filterItems - filters value.
     * @return json string.
     */
    @Override
    public String selectByFilter(FilterItems filterItems) {
        listOfItems = getData(filterItems);
        Session session = factory.openSession();
        List<ItemConvert> jsonList = new LinkedList<>();
        for (Item itemL : listOfItems) {
            Item item = session.load(Item.class, itemL.getId());
            ItemConvert itemAdd = new ItemConvert();
            Body body = item.getBody();
            Brand brand = item.getBrand();
            Model model = item.getModel();
            Oil oil = item.getOil();
            Transmission transmission = item.getTransmission();
            User user = item.getUser();
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
        session.close();
        return jsonString;
    }
    /**
     * Get all data from data base by filter.
     * @param filterItems -  filter values.
     * @return List of item.
     */
    public List<Item> getData(FilterItems filterItems) {
        Session session = factory.openSession();
            Query query = session.createQuery(requestByFilter(filterItems));
            if(query.list().size() != 0){
                listOfItems = query.list();
            }
            session.close();
        return listOfItems;
    }
    /**
     * Method for prepare filter reuest dure Body.
     * @param filterItems - which was choose.
     * @return string for req body
     */
    public static String bodyUrl(FilterItems filterItems){
        String bodyLink = "";
        if (filterItems.getBody() != null){
            bodyLink = "(body_id= " + selectBodyByNameId(filterItems.getBody().get(0));
            for (int i = 1; i < filterItems.getBody().size(); i++) {
                Integer id = selectBodyByNameId(filterItems.getBody().get(i));
                if(id != null){
                    bodyLink = bodyLink + " or body_id= " + id;
                }
            }
            bodyLink = bodyLink+")";
        }
        return bodyLink;
    }
    /**
     * Method for prepare filter reuest dure Oil.
     * @param filterItems - which was choose.
     * @return string for req oil
     */
    public static String oilUrl(FilterItems filterItems){
        String oilLink = "";
        if (filterItems.getOil() != null){
            oilLink = "(oil_id= " + selectOilByNameId(filterItems.getOil().get(0));
            for (int i = 1; i < filterItems.getOil().size(); i++) {
                Integer id = selectOilByNameId(filterItems.getOil().get(i));
                if (id != null){
                    oilLink = oilLink + " or oil_id= " + id;
                }
            }
            oilLink = oilLink + ")";
        }
        return oilLink;
    }
    /**
     * Method for prepare filter reuest dure Transmission.
     * @param filterItems - which was choose.
     * @return string for req transmission.
     */
    public static String transmissionUrl(FilterItems filterItems){
        String transmissionLink = "";
        if (filterItems.getTransmission() != null){
            transmissionLink = "(transmission_id= " + selectTransmissionByNameId(filterItems.getTransmission().get(0));
            for (int i = 1; i < filterItems.getTransmission().size(); i++) {
                Integer id = selectTransmissionByNameId(filterItems.getTransmission().get(i));
                if(id != null){
                    transmissionLink = transmissionLink + " or transmission_id= " + id;
                }
            }
            transmissionLink = transmissionLink + ")";
        }
        return transmissionLink;
    }
    /**
     * Select Body id by filter.
     * @param name - name body.
     * @return id dure name.
     */
    public static Integer selectBodyByNameId(String name){
        Body body = null;
        Integer id = null;
        Session session = factory.openSession();
        Query query = session.createQuery("from Body " + " where name = '" + name + "'");
        if(query.list().size() != 0){
            body = (Body) query.list().get(0);
        }
        if (body != null){
            id = body.getId();
        }
        session.close();
        return id;
    }
    /**
     * Select Oil id by filter.
     * @param name - name oil.
     * @return id dure name.
     */
    public static Integer selectOilByNameId(String name){
        Oil oil = null;
        Integer id = null;
        Session session = factory.openSession();
        Query query = session.createQuery("from Oil " + " where name = '" + name + "'");
        if(query.list().size() != 0){
            oil = (Oil) query.list().get(0);
        }
        if (oil != null){
            id = oil.getId();
        }
        session.close();
        return id;
    }
    /**
     * Select Transmission id by filter.
     * @param name - name transmission.
     * @return id dure name.
     */
    public static Integer selectTransmissionByNameId(String name){
        Transmission transmission = null;
        Integer id = null;
        Session session = factory.openSession();
        Query query = session.createQuery("from Transmission " + " where name = '" + name + "'");
        if(query.list().size() != 0){
            transmission = (Transmission) query.list().get(0);
        }
        if (transmission != null){
            id = transmission.getId();
        }
        session.close();
        return id;
    }

}
