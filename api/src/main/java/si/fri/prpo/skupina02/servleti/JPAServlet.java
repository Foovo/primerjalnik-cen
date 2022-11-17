package si.fri.prpo.skupina02.servleti;

import org.hibernate.Hibernate;
import si.fri.prpo.skupina02.storitve.KosaricaZrno;
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
    @Inject
    private KosaricaZrno kosaricaZrno;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var uporabniki = uporabnikZrno.getAll();

        var writer = resp.getWriter();
        for (var uporabnik : uporabniki) {
            writer.println(uporabnik);
            writer.println("Kosarice: ");
            var kosarica = kosaricaZrno.getByUporabnik(uporabnik);
            for(var k :  kosarica) {
                writer.println(k.getId());
            }
            writer.println();
        }
    }
}
