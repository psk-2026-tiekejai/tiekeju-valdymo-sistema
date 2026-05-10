package lt.vu.mif.psk.tiekejai;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Startup
@Singleton
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class LiquibaseStartup {

    private static final String CHANGELOG_FILE = "db/changelog/db.changelog-master.xml";

    @Resource(lookup = "java:app/jdbc/tiekejaiDS")
    private DataSource dataSource;

    @PostConstruct
    public void runMigrations() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        try (Connection connection = dataSource.getConnection()) {
            Database database = DatabaseFactory.getInstance()
                    .findCorrectDatabaseImplementation(new JdbcConnection(connection));

            Liquibase liquibase = new Liquibase(
                    CHANGELOG_FILE,
                    new ClassLoaderResourceAccessor(classLoader),
                    database
            );
            liquibase.update(new Contexts(), new LabelExpression());
        } catch (SQLException | LiquibaseException exception) {
            throw new IllegalStateException("Failed to run Liquibase migrations", exception);
        }
    }
}
