package lt.vu.mif.psk.tiekejai.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lt.vu.mif.psk.tiekejai.dao.SupplierDao;
import lt.vu.mif.psk.tiekejai.domain.Supplier;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class SupplierService {

    @Inject
    private SupplierDao supplierDao;

    public List<Supplier> getAll() {
        return supplierDao.findAll();
    }

    public Optional<Supplier> getById(Long id) {
        return supplierDao.findById(id);
    }

    @Transactional
    public Supplier create(Supplier supplier) {
        return supplierDao.save(supplier);
    }

    @Transactional
    public Supplier update(Supplier supplier) {
        return supplierDao.save(supplier);
    }

    @Transactional
    public void delete(Long id) {
        supplierDao.delete(id);
    }
}
