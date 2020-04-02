package presistens;
import models.FilterItems;
/**
 * Interface for DAO Filter Items. Work with filtered data.
 */
public interface StoreFilter {
    String selectByFilter(FilterItems filterItems);
}
