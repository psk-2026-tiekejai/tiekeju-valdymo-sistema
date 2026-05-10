package lt.vu.mif.psk.tiekejai.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import lt.vu.mif.psk.tiekejai.domain.Supplier;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
class SupplierDaoTest {

    @Container
    static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("tiekejai_db")
            .withUsername("tiekejai")
            .withPassword("tiekejai_dev");

    private static EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;
    private SupplierDao supplierDao;

    @BeforeAll
    static void setUpDatabase() throws Exception {
        runLiquibase();

        entityManagerFactory = Persistence.createEntityManagerFactory(
                "tiekejaiTestPU",
                Map.of(
                        "jakarta.persistence.jdbc.driver", "org.postgresql.Driver",
                        "jakarta.persistence.jdbc.url", POSTGRES.getJdbcUrl(),
                        "jakarta.persistence.jdbc.user", POSTGRES.getUsername(),
                        "jakarta.persistence.jdbc.password", POSTGRES.getPassword()
                )
        );
    }

    @AfterAll
    static void closeEntityManagerFactory() {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }

    @BeforeEach
    void createEntityManager() {
        entityManager = entityManagerFactory.createEntityManager();
        supplierDao = new SupplierDao(entityManager);
    }

    @AfterEach
    void closeEntityManager() {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
    }

    @Test
    void savesAndReadsSupplier() {
        Supplier supplier = new Supplier(
                "Acme Supplies",
                "ACME-001",
                "info@acme.test",
                "+37060000000"
        );

        entityManager.getTransaction().begin();
        supplierDao.save(supplier);
        entityManager.getTransaction().commit();

        assertNotNull(supplier.getId());
        assertNotNull(supplier.getCreatedAt());

        entityManager.clear();

        Optional<Supplier> found = supplierDao.findByRegistrationCode("ACME-001");

        assertTrue(found.isPresent());
        assertEquals("Acme Supplies", found.get().getName());
        assertEquals("info@acme.test", found.get().getEmail());
        assertEquals("+37060000000", found.get().getPhone());
    }

    private static void runLiquibase() throws Exception {
        try (Connection connection = DriverManager.getConnection(
                POSTGRES.getJdbcUrl(),
                POSTGRES.getUsername(),
                POSTGRES.getPassword()
        )) {
            Database database = DatabaseFactory.getInstance()
                    .findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new Liquibase(
                    "db/changelog/db.changelog-master.xml",
                    new ClassLoaderResourceAccessor(Thread.currentThread().getContextClassLoader()),
                    database
            );
            liquibase.update(new Contexts(), new LabelExpression());
        }
    }
}
