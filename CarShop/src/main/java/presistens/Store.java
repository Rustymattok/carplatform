package presistens;
import models.Item;
import java.util.List;
/**
 * Interface for DAO Items. Work with full data base.
 */
public interface Store {

    void add(Item item);

    void update(Integer id, Item item);

    void remove(Integer id);

    String getItemsJsonFormat();

    List<Object> getSelectAllElements(Object obj);

    <T>String convertToJson(List<T> list);
}
