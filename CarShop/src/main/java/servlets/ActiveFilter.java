package servlets;
import com.fasterxml.jackson.databind.ObjectMapper;
import logic.ValidateServiceFilter;
import models.FilterItems;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class ActiveFilter extends HttpServlet {
    private final static ValidateServiceFilter work = ValidateServiceFilter.getInstance();
    private String jsonFilterItems = "[{\"id\":null,\"name\":\"null\"}]";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json");
        /*
         * Only stream allowed to make Russian letters without changes .acchess
         * Put in stream encode UTF-8.
         * */
        PrintWriter out = new PrintWriter(new OutputStreamWriter(resp.getOutputStream(), StandardCharsets.UTF_8), true);
        out.write(jsonFilterItems);
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json");
        BufferedReader reader = req.getReader();
        String jsonString = reader.readLine();
        String orderData = URLDecoder.decode( jsonString, "UTF-8" );
        FilterItems filterItems = jsonToObject(orderData);
        jsonFilterItems = selectFilterItems(filterItems);
        //todo тестовый вариант.
    }
    /**
     * Method for covert json text to Object.
     * @param json - POST servlet data.
     * @return - object.
     */
    public FilterItems jsonToObject(String json){
        json = json.replace("body=","{ \"body\" : ");
        json = json.replace("&oil=",", \"oil\" : ");
        json = json.replace("&transmission=",", \"transmission\" :");
        json = json + "}";
        json = json.replace("[]","null");
        ObjectMapper mapper = new ObjectMapper();
        FilterItems itemSearch  = null;
        try {
            itemSearch = mapper.readValue(json, FilterItems.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return itemSearch;
    }

    public String selectFilterItems(FilterItems filterItems){
       return work.getLogic().selectByFilter(filterItems);
    }
}
