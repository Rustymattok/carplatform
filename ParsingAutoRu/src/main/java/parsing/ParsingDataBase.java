package parsing;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.logging.Logger;
/**
 * Class for parsing data models and brand from auto.ru.
 */
public class ParsingDataBase {
    private String url;
    private Document document;
    private static Logger LOGGER = Logger.getLogger("InfoLogging");
    private String filePath;

    public ParsingDataBase() {
        try {
            this.url = "https://auto.ru/catalog/cars/";
            this.document = Jsoup.connect(url).get();
            this.filePath = "ParsingAutoRu\\src\\main\\resources\\scripts\\data_models.sql";
        } catch (IOException e) {
            LOGGER.fine("not correct link");
        }
    }
    /**
     * Common method for formed file sql for migrate Data- https://auto.ru/catalog/cars/.
     * This parsing combine all links of Brand in Set.
     */
    public void createDataBaseModels() {
        HashSet<String> listBrandLinks = new HashSet<>();
        String className = "search-form-v2-list__text-item";
        Elements elements = document.getElementsByClass(className);
        int i = 0;
        for (Element element : elements) {
            String linkBrand = element.getElementsByAttribute("href").attr("href");
            if(!listBrandLinks.contains(linkBrand)){
                i++;
                String brandName = element.getElementsByAttribute("href").text();
                addBrand(brandName);
                addModels(getModels(linkBrand),i);
                listBrandLinks.add(linkBrand);
            }
        }
    }
    /**
     * Method for pars all Brands - more optimize.
     * @return - all brands.
     */
    public HashSet<String> getBrands(){
        String className = "search-form-v2-list__text-item";
        HashSet<String> brands = new HashSet<>();
        Elements elements = document.getElementsByClass(className);
        for (Element element : elements) {
            String brandName = element.getElementsByAttribute("href").text();
            if(!brands.contains(brandName)){
                brands.add(brandName);
            }
        }
        return brands;
    }
    /**
     * Method to take all models by Brand link.
     * @param link - brand link for all models.
     * @return - set of all models for current brand.
     */
    public HashSet<String> getModels(String link){
        HashSet<String> list = new HashSet<>();
        String className = "search-form-v2-list__text-item";
        try {
            document = Jsoup.connect(link).get();
            Elements elements = document.getElementsByClass(className);
            for (Element element : elements) {
                String model = element.getElementsByAttribute("href").text();
                if(!list.contains(model)) {
                    model = model.toLowerCase();
                    list.add(model);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
    /**
     * Method to insert to the file brand.
     * @param brand - value to insert.
     */
    private void addBrand(String brand){
        brand = brand.toLowerCase();
        if(brand.contains("_")||brand.contains(" ")||brand.contains("'")){
            brand = brand.replace("_","");
            brand = brand.replace(" ","");
            brand = brand.replace("'","");
        }
        if (brand.equals("lada(ваз)")){
            brand = "vaz";
        }
        if (brand.equals("уаз")){
            brand = "uaz";
        }
        String sqlReq = "INSERT INTO models_list (name) VALUES ('" + brand + "');" + "\n";
        saveToFile(sqlReq);
    }
    /**
     * Method for add models to sql file for migrate Data.
     * @param models - list of models in brand.
     * @param id - id of brand.
     */
     private void addModels(HashSet<String> models,Integer id){
        for (String model : models) {
            if(model.contains("_")||model.contains(" ")||model.contains("'")){
                while (model.contains("_")||model.contains(" ")||model.contains("'")){
                    model = model.replace("_","");
                    model = model.replace(" ","");
                    model = model.replace("'","");
                }
            }
             String sqlReq = new StringBuilder().append("INSERT INTO models (name,models_id) VALUES ('")
                    .append(model).append("',").append(id).append(");").append("\n").toString();
            saveToFile(sqlReq);
        }
    }
    /**
     * Method for saving data to sql file.
     * @param sqlReq - sql request include to file.
     */
    private void saveToFile(String sqlReq){
        //todo если файд существует, то сначала очиситить его -  реализовать
        try {
            FileWriter writer = new FileWriter(filePath, true);
            BufferedWriter bufferWriter = new BufferedWriter(writer);
            bufferWriter.write(sqlReq);
            bufferWriter.close();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

}
