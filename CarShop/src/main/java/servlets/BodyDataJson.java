package servlets;

import logic.ValidateService;
import models.Body;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class BodyDataJson  extends HttpServlet {
    private final static ValidateService work = ValidateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json");
        String jsonString = work.getLogic().convertToJson(work.getLogic().getSelectAllElements(new Body()));
        /*
         * Only stream allowed to make Russian letters without changes .acchess
         * Put in stream encode UTF-8.
         * */
        PrintWriter out = new PrintWriter(new OutputStreamWriter(resp.getOutputStream(), StandardCharsets.UTF_8), true);
        out.write(jsonString);
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
