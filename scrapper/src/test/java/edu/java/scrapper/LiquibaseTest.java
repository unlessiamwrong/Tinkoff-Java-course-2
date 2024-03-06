package edu.java.scrapper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class LiquibaseTest extends IntegrationTest {

    @Test
    void whenUse_Insertion_AddRowToDataBase() throws SQLException {
        //Arrange
        Connection connection =
            DriverManager.getConnection(POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword());
        String query = "INSERT INTO users (name) VALUES ('V')";
        Statement statement = connection.createStatement();

        //Act
        int rowsInserted = statement.executeUpdate(query);

        //Assert
        assertThat(rowsInserted).isEqualTo(1);

    }
}

