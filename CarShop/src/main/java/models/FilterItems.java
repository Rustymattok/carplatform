package models;
import java.util.List;
/**
 * Class for convert data JSON - > Object.
 */
public class FilterItems {
    private List<String> body;
    private List<String> oil;
    private List<String> transmission;

    public List<String> getBody() {
        return body;
    }

    public void setBody(List<String> body) {
        this.body = body;
    }

    public List<String> getOil() {
        return oil;
    }

    public void setOil(List<String> oil) {
        this.oil = oil;
    }

    public List<String> getTransmission() {
        return transmission;
    }

    public void setTransmission(List<String> transmission) {
        this.transmission = transmission;
    }
}
