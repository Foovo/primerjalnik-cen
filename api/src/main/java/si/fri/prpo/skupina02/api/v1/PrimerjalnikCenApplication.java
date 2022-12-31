package si.fri.prpo.skupina02.api.v1;

import com.kumuluz.ee.cors.annotations.CrossOrigin;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("v1")
@OpenAPIDefinition(
        info = @Info(
                title = "Primerjalnik Cen",
                version = "v1.0.0",
                license = @License(name="MIT")),
        servers = @Server(url = "http://localhost:8080/"))
@CrossOrigin(supportedMethods = "GET, POST, PUT, DELETE")
public class PrimerjalnikCenApplication extends Application {

}
