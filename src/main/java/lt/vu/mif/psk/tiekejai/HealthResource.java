package lt.vu.mif.psk.tiekejai;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/health")
public class HealthResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject health() {
        return Json.createObjectBuilder()
                .add("status", "ok")
                .build();
    }
}
