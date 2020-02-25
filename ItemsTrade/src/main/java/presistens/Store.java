package presistens;

import models.Item;

public interface Store {

    void add(Item item);

    void update(Integer id,Item item);

    void remove(Integer id);
}
