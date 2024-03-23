package edu.java.scrapper.liquibase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import edu.java.scrapper.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class LiquibaseTest extends AbstractIntegrationTest {

    @Test
    void whenUse_Insertion_AddRowToDB() throws SQLException {
        //Arrange
        Connection connection =
            DriverManager.getConnection(POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword());
        String query = "INSERT INTO users (id) VALUES (1)";
        Statement statement = connection.createStatement();

        //Act
        int rowsInserted = statement.executeUpdate(query);

        //Assert
        assertThat(rowsInserted).isEqualTo(1);

        //Clear DB
        statement.executeUpdate("DELETE FROM users");

    }
}

