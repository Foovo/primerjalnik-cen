package si.fri.prpo.skupina02.servleti;

import org.hibernate.Hibernate;
import si.fri.prpo.skupina02.storitve.UporabnikZrno;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import javax.transaction.Transactional;
import java.io.IOException;

@WebServlet("/servlet")
public class JPAServlet extends HttpServlet {
    @Inject
    private UporabnikZrno uporabnikZrno;

    @Override
    @Transactional
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var uporabniki = uporabnikZrno.getAll();

        var writer = resp.getWriter();
        for (var uporabnik : uporabniki) {
            writer.println(uporabnik);
            Hibernate.initialize(uporabnik.getKosarice());
            writer.println(uporabnik.getKosarice());
        }
    }
}
