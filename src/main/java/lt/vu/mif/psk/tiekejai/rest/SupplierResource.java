package lt.vu.mif.psk.tiekejai.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lt.vu.mif.psk.tiekejai.domain.Supplier;
import lt.vu.mif.psk.tiekejai.service.SupplierService;

import java.net.URI;
import java.util.List;

@Path("/suppliers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SupplierResource {

    @Inject
    private SupplierService supplierService;

    @GET
    public List<Supplier> getAll() {
        return supplierService.getAll();
    }

    @GET
    @Path("/{id}")
    public Supplier getById(@PathParam("id") Long id) {
        return supplierService.getById(id)
                .orElseThrow(NotFoundException::new);
    }

    @POST
    public Response create(Supplier supplier, @Context UriInfo uriInfo) {
        Supplier created = supplierService.create(supplier);
        URI location = uriInfo.getAbsolutePathBuilder()
                .path(created.getId().toString())
                .build();

        return Response.created(location)
                .entity(created)
                .build();
    }

    @PUT
    @Path("/{id}")
    public Supplier update(@PathParam("id") Long id, Supplier supplier) {
        Supplier existing = supplierService.getById(id)
                .orElseThrow(NotFoundException::new);

        existing.setName(supplier.getName());
        existing.setRegistrationCode(supplier.getRegistrationCode());
        existing.setEmail(supplier.getEmail());
        existing.setPhone(supplier.getPhone());

        return supplierService.update(existing);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        supplierService.delete(id);
        return Response.noContent().build();
    }
}
