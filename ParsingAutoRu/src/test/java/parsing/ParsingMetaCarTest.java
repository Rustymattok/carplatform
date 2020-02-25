package parsing;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

public class ParsingMetaCarTest {
    private ParsingMetaCar parsingMetaCar = new ParsingMetaCar();
    private String testImageLink = "/_crpd/YEK17Z324/c29d2bIlrsH/OYFtrZS33RfhgvlGtLXdvSpzutTdsIa3wEgr1RtBp6wfv_hN7HQTcIn5_0dpYa_T4wdiawOeo8wY3BtNS4dqw2iXv2rBZAkHt9PogGG1jO8s8uqBCGKTINQZKBpUTf4snjYyzy8u0j5OLG_TOzGxjrUU-2fDlbrVjpwFFQJWg#DSD";
    private String test = "{\"bodyType\":\"хэтчбек 5 дв.\"," +
            "\"image\":\"/_crpd/YEK17Z324/c29d2bIlrsH/OYFtrZS33RfhgvlGtLXdvSpzutTdsIa3wEgr1RtBp6wfv_hN7HQTcIn5_0dpYa_T4wdiawOeo8wY3BtNS4dqw2iXv2rBZAkHt9PogGG1jO8s8uqBCGKTINQZKBpUTf4snjYyzy8u0j5OLG_TOzGxjrUU-2fDlbrVjpwFFQJWg#DSD\"," +
            "\"color\":\"красный\"," +
            "\"vehicleConfiguration\":\"ALLROAD_5_DOORS AUTOMATIC 2.0\"," +
            "\"numberOfDoors\":\"5\"," +
            "\"engineDisplacement\":\"2.0 LTR\"," +
            "\"availability\":\"http://schema.org/InStock\"," +
            "\"enginePower\":\"175 N12\"," +
            "\"url\":\"https://auto.ru/cars/used/sale/ssang_yong/actyon/1094502846-2ad1b470/\"," +
            "\"priceCurrency\":\"RUB\"," +
            "\"productionDate\":\"2011\"," +
            "\"fuelType\":\"дизель\"," +
            "\"price\":\"380000\"," +
            "\"modelDate\":\"2010\"," +
            "\"name\":\"2.0 AT\"," +
            "\"brand\":\"SSANGYONG\"," +
            "\"vehicleTransmission\":\"автоматическая\"}\n";
    /*
     * Only for simulation parser not for doc file. For Build you should hide this block of test.
     * */
    @Test
    public void getJsonItemListTest() {
//        List<HashMap> list = parsingMetaCar.jsonItemsList();
//        parsingMetaCar.saveToFile(list,"../ParsingAutoRu/src/main/resources/lastitems.json");
    }

    @Test
    public void getUrlTest() {
        String json = "{\"bodyType\":\"хэтчбек 5 дв.\",\"image\":\"https://avatars.mds.yandex.net/get-autoru-vos/1686981/265866a5d5e94cc145f84849fbfb8763/320x240\",\"color\":\"серый\",\"vehicleConfiguration\":\"HATCHBACK_5_DOORS AUTOMATIC 1.6\",\"numberOfDoors\":\"5\",\"engineDisplacement\":\"1.6 LTR\",\"availability\":\"http://schema.org/InStock\",\"enginePower\":\"123 N12\",\"url\":\"https://auto.ru/cars/used/sale/hyundai/solaris/1096012894-365ae28b/\",\"priceCurrency\":\"RUB\",\"productionDate\":\"2015\",\"fuelType\":\"бензин\",\"price\":\"580000\",\"modelDate\":\"2014\",\"name\":\"1.6 AT\",\"brand\":\"HYUNDAI\",\"vehicleTransmission\":\"автоматическая\"}\n";
        String url = "https://auto.ru/cars/used/sale/hyundai/solaris/1096012894-365ae28b/";
        Assert.assertEquals(parsingMetaCar.getUrl(json),url);

    }

    @Test
    public void getNameModelTest() {
        String name = "hyundai solaris i рестайлинг";
        String url = "https://auto.ru/cars/used/sale/hyundai/solaris/1096012894-365ae28b/";
        Assert.assertEquals(parsingMetaCar.getNameModel(url),name);
    }

    @Test
    public void getModel() {
        String fullName = "ssangyong actyon ii";
        Assert.assertEquals(parsingMetaCar.getModel(fullName,test),"actyon");
    }

    @Test
    public void getBrandTest() {
        String result =parsingMetaCar.getBrand(test);
        Assert.assertTrue(result.contains("ssangyong"));
    }

    @Test
    public void getBodyTest() {
        String result =parsingMetaCar.getBody(test);
        Assert.assertEquals("хэтчбек 5 дв.".toLowerCase(),result);
    }

    @Test
    public void getTransmissionTest() {
        String result =parsingMetaCar.getTransmission(test);
        Assert.assertTrue(result.contains("автоматическая".toLowerCase()));
    }

    @Test
    public void getOilTest() {
        String result =parsingMetaCar.getOil(test);
        Assert.assertTrue(result.contains("дизель".toLowerCase()));
    }

    @Test
    public void getImageTest() {
        String result = parsingMetaCar.getImage(test);
        Assert.assertEquals(result,testImageLink);
    }

    @Test
    public void getPriceTest() {
        Double result =parsingMetaCar.getPrice(test);
        Assert.assertEquals(result,Double.valueOf(380000));
    }

    @Test
    public void getOptimazeJsonTest() {
        String optimize = "{image=/_crpd/YEK17Z324/c29d2bIlrsH/OYFtrZS33RfhgvlGtLXdvSpzutTdsIa3wEgr1RtBp6wfv_hN7HQTcIn5_0dpYa_T4wdiawOeo8wY3BtNS4dqw2iXv2rBZAkHt9PogGG1jO8s8uqBCGKTINQZKBpUTf4snjYyzy8u0j5OLG_TOzGxjrUU-2fDlbrVjpwFFQJWg#DSD, oil=дизель, transmission=автоматическая, price=380000.0, model=actyon, body=хэтчбек 5 дв., brand=ssangyong}";
        Assert.assertEquals(parsingMetaCar.getOptimazeJson(test).toString(),optimize);
    }

}