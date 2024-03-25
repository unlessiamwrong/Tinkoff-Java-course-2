package edu.java.scrapper.liquibase;

import edu.java.scrapper.AbstractIntegrationTest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class LiquibaseTest extends AbstractIntegrationTest {

    @Test
    void whenUse_Insertion_AddRowToDB() throws SQLException {
        //Arrange
        Connection connection =
            DriverManager.getConnection(POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword());
        String query = "INSERT INTO users (chat_id) VALUES (1)";
        Statement statement = connection.createStatement();

        //Act
        int rowsInserted = statement.executeUpdate(query);

        //Assert
        assertThat(rowsInserted).isEqualTo(1);
    }
}

