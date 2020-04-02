package presistens;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class DAOItemsTest {
    private DAOItems daoItems = DAOItems.getINSTANCE();
    private String text = "{\"image\":\"https://avatars.mds.yandex.net/get-autoru-vos/1938005/d5053df7d2752490bfdd51578dddf59d/320x240\",\"oil\":\"бензин\",\"transmission\":\"автоматическая\",\"price\":1500000,\"model\":\"qx50\",\"body\":\"внедорожник 5 дв.\",\"brand\":\"infiniti\",\"status\":\"sale\",\"user\":\"admin\"}";

    @Test
    public void getData() {
    }

    @Test
    public void getBodyTest() {
        Body body = daoItems.getBody(1);
        Assert.assertEquals(body.getName(),"седан");
    }

    @Test
    public void getBrandTest() {
        Brand brand = daoItems.getBrand(1);
        Assert.assertEquals(brand.getName(),"audi");
}

    @Test
    public void getModelTest() {
        Model model = daoItems.getModel(1);
        Assert.assertEquals(model.getName(),"tt");
        Assert.assertEquals(model.getBrand().getId(),(Integer) 1);
    }

    @Test
    public void getOilTEst() {
        Oil oil = daoItems.getOil(1);
        Assert.assertEquals(oil.getName(),"бензин");
    }

    @Test
    public void getTransmissiontTest() {
        Transmission transmission = daoItems.getTransmission(1);
        Assert.assertEquals(transmission.getName(),"автоматическая");
    }

    @Test
    public void getUserTest() {
        User user = daoItems.getUser(1);
        Assert.assertEquals(user.getName(),"admin");
    }

    @Test
    public void convertToJson() {
        daoItems.convertToJson(daoItems.getSelectAllElements(new Body()));
    }


}