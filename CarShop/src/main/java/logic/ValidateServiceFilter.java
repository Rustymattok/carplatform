package logic;

import presistens.DAOFilterItems;
import presistens.DAOItems;
import presistens.Store;
import presistens.StoreFilter;

public class ValidateServiceFilter {
    private final StoreFilter logic = DAOFilterItems.getINSTANCE();
    private final static ValidateServiceFilter instance = new ValidateServiceFilter();

    private ValidateServiceFilter(){
    }
    public static ValidateServiceFilter getInstance(){
        return instance;
    }

    public StoreFilter getLogic() {
        return logic;
    }
}
