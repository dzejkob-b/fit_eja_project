package fit.bikeja.client.view;

import fit.bikeja.client.ui.PageBackLink;
import fit.bikeja.client.ui.PageComposer;
import fit.bikeja.client.ui.PageContent;
import fit.bikeja.client.ui.PageHeader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

abstract public class BaseHttpView extends HttpServlet {

    protected String backPath;

    public BaseHttpView(String backPath) {
        this.backPath = backPath;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html");

        PageComposer cp = new PageComposer();

        cp.addHead(new PageHeader("Nastala chyba!"));

        if (req.getAttribute("errorMsg") != null) {
            cp.addBody(new PageContent("<p class='error_msg'><strong>Chyba: </strong> " + req.getAttribute("errorMsg") + "</p>"));

        } else {
            cp.addBody(new PageContent("<p class='error_msg'><strong>Chyba!</strong></p>"));
        }

        cp.addBody(new PageBackLink(this.backPath));

        resp.getWriter().write(cp.toString());
    }

}
