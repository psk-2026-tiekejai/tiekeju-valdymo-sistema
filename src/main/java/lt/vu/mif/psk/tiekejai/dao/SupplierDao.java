package lt.vu.mif.psk.tiekejai.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lt.vu.mif.psk.tiekejai.domain.Supplier;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class SupplierDao {

    @PersistenceContext(unitName = "tiekejaiPU")
    private EntityManager entityManager;

    public SupplierDao() {
    }

    SupplierDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Supplier> findAll() {
        return entityManager
                .createQuery("select s from Supplier s order by s.name", Supplier.class)
                .getResultList();
    }

    public Optional<Supplier> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Supplier.class, id));
    }

    public Optional<Supplier> findByRegistrationCode(String code) {
        return entityManager
                .createQuery(
                        "select s from Supplier s where s.registrationCode = :registrationCode",
                        Supplier.class
                )
                .setParameter("registrationCode", code)
                .getResultStream()
                .findFirst();
    }

    public Supplier save(Supplier supplier) {
        if (supplier.getId() == null) {
            entityManager.persist(supplier);
            return supplier;
        }

        return entityManager.merge(supplier);
    }

    public void delete(Long id) {
        findById(id).ifPresent(entityManager::remove);
    }
}
