package presistens;
import models.FilterItems;
import org.junit.Assert;
import org.junit.Test;
import servlets.ActiveFilter;

import javax.transaction.Transactional;

@Transactional
public class DAOFilterItemsTest {
    private DAOFilterItems daoFilterItems = DAOFilterItems.getINSTANCE();

    @Test
    public void BodyUrl() {
        String text = "body=[\"хэтчбек 3 дв.\",\"хэтчбек 5 дв.\",\"хэтчбек 6 дв.\",\"хэтчбек 7 дв.\"]&oil=[\"хэтчбек 3 дв.\",\"хэтчбек 5 дв.\"]&transmission=[]";
        ActiveFilter  activeFilter = new ActiveFilter();
        FilterItems itemSearch = activeFilter.jsonToObject(text);
        Assert.assertEquals(DAOFilterItems.bodyUrl(itemSearch),"(body_id= 3 or body_id= 4)");
    }

    @Test
    public void oilUrl() {
        String text = "body=[\"хэтчбек 3 дв.\",\"хэтчбек 5 дв.\",\"хэтчбек 6 дв.\",\"хэтчбек 7 дв.\"]&oil=[\"дизель\",\"хэтчбек 5 дв.\"]&transmission=[]";
        ActiveFilter  activeFilter = new ActiveFilter();
        FilterItems itemSearch = activeFilter.jsonToObject(text);
        Assert.assertEquals(DAOFilterItems.oilUrl(itemSearch),"(oil_id= 2)");
    }

    @Test
    public void transmissionUrl() {
        String text = "body=[\"хэтчбек 3 дв.\",\"хэтчбек 5 дв.\",\"хэтчбек 6 дв.\",\"хэтчбек 7 дв.\"]&oil=[\"хэтчбек 3 дв.\",\"хэтчбек 5 дв.\"]&transmission=[\"хэт.\",\"робот\"]";
        ActiveFilter  activeFilter = new ActiveFilter();
        FilterItems itemSearch = activeFilter.jsonToObject(text);
        Assert.assertEquals(DAOFilterItems.transmissionUrl(itemSearch),"(transmission_id= null or transmission_id= 2)");
    }

    @Test
    public  void requestByFilterTest(){
        String text = "body=[\"седан\"]&oil=[\"седан\"]&transmission=[\"седан\"]";
        ActiveFilter  activeFilter = new ActiveFilter();
        FilterItems itemSearch = activeFilter.jsonToObject(text);
        Assert.assertEquals(DAOFilterItems.requestByFilter(itemSearch),"from Item where (body_id= 1) and (oil_id= null) and (transmission_id= null)");
    }

    @Test
    public void selectBodyByNameId() {
       Integer id = DAOFilterItems.selectBodyByNameId("хэтчбек 3 дв.");
       Assert.assertEquals(id,(Integer) 3);
    }

    @Test
    public void selectOilByNameId() {
        Integer id = DAOFilterItems.selectOilByNameId("бензин");
        Assert.assertEquals(id,(Integer) 1);
    }

    @Test
    public void oilUrl1() {
        String text = "body=[\"хэтчбек 3 дв.\",\"хэтчбек 5 дв.\",\"хэтчбек 6 дв.\",\"хэтчбек 7 дв.\"]&oil=[\"бензиУн\",\"бензин\"]&transmission=[]";
        ActiveFilter  activeFilter = new ActiveFilter();
        FilterItems itemSearch = activeFilter.jsonToObject(text);
        Assert.assertEquals(DAOFilterItems.oilUrl(itemSearch),"(oil_id= null or oil_id= 1)");
    }

    @Test
    public void selectTransmissionByNameId() {
        Integer id = DAOFilterItems.selectTransmissionByNameId("робот");
        Assert.assertEquals(id,(Integer) 2);
    }


    @Test
    public void selectByFilterTest() {
        String text = "body=[\"хэтчбек 5 дв.\"]&oil=[\"бензин\"]&transmission=[]";
        ActiveFilter  activeFilter = new ActiveFilter();
        FilterItems itemSearch = activeFilter.jsonToObject(text);
        daoFilterItems.selectByFilter(itemSearch);
    }

    @Test
    public void getDataTest() {
        String text = "body=[\"хэтчбек 3 дв.\",\"хэтчбек 5 дв.\",\"хэтчбек 6 дв.\",\"хэтчбек 7 дв.\"]&oil=[\"бензин\"]&transmission=[\"хэт.\",\"робот\"]";
        ActiveFilter  activeFilter = new ActiveFilter();
        FilterItems itemSearch = activeFilter.jsonToObject(text);
        daoFilterItems.getData(itemSearch);
    }
}