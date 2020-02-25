package parsing;
import org.junit.Assert;
import org.junit.Test;

public class ParsingDataBaseTest {
    /**
     * Test correct parsing - https://auto.ru/catalog/cars/.
     * Common size of brands name(not links) should be 263.
     */
    @Test
    public void dataBrandCreateTest() {
        ParsingDataBase parsingDataBase = new ParsingDataBase();
        int size = parsingDataBase.getBrands().size();
        Assert.assertEquals(size,263);
    }
    /**
     * Test correct parsing - https://auto.ru/catalog/cars/ by links for model.
     * Common size of models name(not links) should be 50 for this sample.
     */
    @Test
    public void getModelsTest() {
        ParsingDataBase parsingDataBase = new ParsingDataBase();
        int size = parsingDataBase.getModels("https://auto.ru/catalog/cars/peugeot/").size();
        Assert.assertEquals(size,50);
    }

}