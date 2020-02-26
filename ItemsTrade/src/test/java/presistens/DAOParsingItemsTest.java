package presistens;
import models.*;
import org.junit.Assert;
import org.junit.Test;
import java.util.List;

public class DAOParsingItemsTest {
    /*
     * Only for simulation parser not for doc file. For Build you should hide this block of test.
     * */
    @Test
    public void addItemsLast() {
        DAOParsingItems daoParsingItems = DAOParsingItems.getINSTANCE();
        List<Item> list = daoParsingItems.getItem(daoParsingItems.getItemConvert("../ParsingAutoRu/src/main/resources/lastitems.json"));
    }
    /**
     * Get Body object- parsing json file.
     */
    @Test
    public void getBodyTest() {
        DAOParsingItems daoParsingItems = DAOParsingItems.getINSTANCE();
        Body body = daoParsingItems.getBody("седан");
        int id = body.getId();
        Assert.assertEquals(id,1);
    }
    /**
     * Get Brand object- parsing json file.
     */
    @Test
    public void getBrand() {
        DAOParsingItems daoParsingItems = DAOParsingItems.getINSTANCE();
        Brand brand = daoParsingItems.getBrand("lexus");
        int id = brand.getId();
        Assert.assertEquals(id,14);
    }
    /**
     * Get Oil object- parsing json file.
     */
    @Test
    public void getOil() {
        DAOParsingItems daoParsingItems = DAOParsingItems.getINSTANCE();
        Oil oil = daoParsingItems.getOil("дизель");
        int id = oil.getId();
        Assert.assertEquals(id,2);
    }
    /**
     * Get Transmission object- parsing json file.
     */
    @Test
    public void getTransmission() {
        DAOParsingItems daoParsingItems = DAOParsingItems.getINSTANCE();
        Transmission transmission = daoParsingItems.getTransmission("робот");
        int id = transmission.getId();
        Assert.assertEquals(id,2);
    }
    /**
     * Get User object- parsing json file.
     */
    @Test
    public void getUser() {
        DAOParsingItems daoParsingItems = DAOParsingItems.getINSTANCE();
        User user = daoParsingItems.getUser("admin");
        int id = user.getId();
        Assert.assertEquals(id,1);
    }
    /**
     * List Items - method work(by parsing json file)
     */
    @Test
    public void getItem() {
        DAOParsingItems daoParsingItems = DAOParsingItems.getINSTANCE();
        List<Item> list = daoParsingItems.getItem(daoParsingItems.getItemConvert("../ParsingAutoRu/src/main/resources/lastitems.json"));
    }
}