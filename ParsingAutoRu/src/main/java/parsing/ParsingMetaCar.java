package parsing;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import model.MetaData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
/**
 * Class for parsing auto ru by last Items.
 * This class support vaz and uaz - other Russian labels not supported(need update).
 */
//todo необхожимо поривести Image к одному формату. Один тип ссылается на облако яндекс, другой сылку на облако автору. Сделать облако автору кликабельным в разделе getImage.
public class ParsingMetaCar {
    private String url;
    private Document document;
    private List<String> jsonItemList  = new LinkedList<>();
    private static Logger LOGGER = Logger.getLogger("InfoLogging");

    public ParsingMetaCar() {
        try {
            this.url = "https://auto.ru/cars/all/?has_history=true#priceRange";
            this.document = Jsoup.connect(url).get();
        } catch (IOException e) {
            LOGGER.fine("not correct link");
        }
    }
    /**
     * This method parsing https://auto.ru/cars/all/?has_history=true#priceRange.
     * Take all positions for sales recently.
     * @return - list of json items with data.
     */
    public List<HashMap> jsonItemsList(){
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, String> dataParsing = new HashMap<String, String>();
        List<HashMap> convert = new LinkedList<HashMap>();
        Elements elementModelCarNumber = document.getElementsByTag("meta");
        String jsonCommonPars;
        HashMap convertedJsonItem;
        int j = 0;
        for (int i = 0; i < elementModelCarNumber.size(); i++) {
            if (i > 15) {
                String key = elementModelCarNumber.get(i).attr("itemprop");
                String value = elementModelCarNumber.get(i).attr("content");
                dataParsing.put(key, value);
                if ((i - 15) % 20 == 0 && i >= 35) {
                    try {
                        j++;
                        jsonCommonPars = mapper.writeValueAsString(dataParsing);
                        convertedJsonItem = getOptimazeJson(jsonCommonPars);
                        convert.add(convertedJsonItem);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return convert;
    }
   /**
     * Method to take url from Meta - personal for car items.
     * This url used for parsing alone item and take personal name model.
     * The reasone - not possible to take name model from meta(a lot of the same meta name).
     * @param json - base meta json
     * @return - url for alone sample.
     */
    public String getUrl(String json){
        String urlModel = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            MetaData data = mapper.readValue(json,MetaData.class);
            urlModel = data.getUrl();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return urlModel;
    }
    /**
     * Method to take full name vehicle by personal url item.
     * @param modelUrl - personal link.
     * @return - model name.
     */
    public String getNameModel(String modelUrl){
        String modelName = "";
        //todo sort link every time change - need to check.
        String sort = "CardOwner-module__vehicle-info-title";
        try {
            Document documentModel = Jsoup.connect(modelUrl).get();
            Elements element = documentModel.getElementsByClass(sort);
            modelName = element.text().toLowerCase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return modelName;
    }
    /**
     * Method to drop brand from fullname vehicle to include only model.
     * @param fullNameModel - full name with brand of vehicle.
     * @param json - parsed common json data.
     * @return - name of model only.
     */
    //todo убрать лишние слова после проблема в названии модели
    public String getModel(String fullNameModel,String json){
        String model = fullNameModel.replace(getBrand(json) + " ","");
        if(model.contains(" ")){
           model = model.split(" ")[0];
        }
        if(model.contains("alfa")){
            model = "alfasud";
        }
        return model;
    }
    /**
     * Method to take brand vehicle by meta url - 20 last items.
     * @param json - personal link.
     * @return - brand name.
     */
    public  String getBrand(String json){
        String brand = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            MetaData data = mapper.readValue(json,MetaData.class);
            brand = data.getBrand().toLowerCase();
            if(brand.contains("_")||brand.contains(" ")){
                brand = brand.replace("_","");
                brand = brand.replace(" ","");
            }
            if(brand.contains("mercedes")){
                brand = "mercedes-benz";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return brand;
    }
    /**
     * Method to take body vehicle by meta url - 20 last items.
     * @param json - personal link.
     * @return - body name.
     */
    public String getBody(String json){
        String body = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            MetaData data = mapper.readValue(json,MetaData.class);
            body = data.getBodyType().toLowerCase();
            if(body.contains("универсал")){
                body = "универсал";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return body;
    }
    /**
     * Method to take transmission vehicle by meta url - 20 last items.
     * @param json - personal link.
     * @return - transmission name.
     */
    public String getTransmission(String json){
        String transmission = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            MetaData data = mapper.readValue(json,MetaData.class);
            transmission = data.getVehicleTransmission().toLowerCase();
            if(transmission.contains("робот")){
                transmission = "робот";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return transmission;
    }
    /**
     * Method to take oil vehicle by meta url - 20 last items.
     * @param json - personal link.
     * @return - oil name.
     */
    public String getOil(String json){
        String oil = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            MetaData data = mapper.readValue(json,MetaData.class);
            oil = data.getFuelType().toLowerCase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return oil;
    }
    /**
     * Method to take image link vehicle by meta url - 20 last items.
     * @param json - personal link.
     * @return - image link.
     */
    public String getImage(String json){
        String imageLink = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            MetaData data = mapper.readValue(json,MetaData.class);
            imageLink = data.getImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageLink;
    }
    /**
     * Method to take price vehicle by meta url - 20 last items.
     *
     */
    public Double getPrice(String json){
        Double price = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            MetaData data = mapper.readValue(json,MetaData.class);
            price = Double.valueOf(data.getPrice());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return price;
    }
    /**
     * Method for optimize common parsed json to work with app Data.
     * @param json - parsed data
     * @return - optimize json HashMap.
     */
    public HashMap getOptimazeJson(String json){
        HashMap<String, String> optimizeJson = new HashMap<String, String>();
        optimizeJson.put("price",String.valueOf(getPrice(json)));
        optimizeJson.put("oil",getOil(json));
        optimizeJson.put("transmission",getTransmission(json));
        optimizeJson.put("image",getImage(json));
        optimizeJson.put("model",getModel(getNameModel(getUrl(json)),json));
        optimizeJson.put("brand",getBrand(json));
        optimizeJson.put("body",getBody(json));
        return optimizeJson;
    }

    /**
     * Method for saving data to json file.
     * @param list - json file save.
     */
    public void saveToFile(List<HashMap> list,String path){
        String json = new Gson().toJson(list);
        try {
            File file = new File(path);
            FileWriter writer = new FileWriter(file, true);
            BufferedWriter bufferWriter = new BufferedWriter(writer);
            bufferWriter.write(json);
            bufferWriter.close();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }
}
