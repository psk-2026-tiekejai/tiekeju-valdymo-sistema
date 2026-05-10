package lt.vu.mif.psk.tiekejai.web;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lt.vu.mif.psk.tiekejai.domain.Supplier;
import lt.vu.mif.psk.tiekejai.service.SupplierService;

import java.io.Serializable;
import java.util.List;

@Named("supplierListBean")
@ViewScoped
public class SupplierListBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private SupplierService supplierService;

    private List<Supplier> suppliers;

    @PostConstruct
    public void init() {
        suppliers = supplierService.getAll();
    }

    public List<Supplier> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<Supplier> suppliers) {
        this.suppliers = suppliers;
    }
}
