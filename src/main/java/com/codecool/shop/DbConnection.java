package com.codecool.shop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class DbConnection {

    private static final Logger logger = LoggerFactory.getLogger (DbConnection.class);

    public Connection getConnection() throws IOException, SQLException {

        BufferedReader br = new BufferedReader(new FileReader("src/main/resources/connection.properties"));
        final String DATABASE = "jdbc:postgresql://" + br.readLine() + "/" + br.readLine();
        final String DB_USER = br.readLine();
        final String DB_PASSWORD = br.readLine();
        br.close();

        return DriverManager.getConnection(
                DATABASE,
                DB_USER,
                DB_PASSWORD);
    }

    public void executeQuery(String query) {
        try (Connection connection = getConnection();
             Statement statement =connection.createStatement();
        ){
            statement.execute(query);
            logger.info("DB query '{}' executed.", query);
        }
        catch (SQLException e) {
            logger.error ("The database connection could not be achieved.");
            e.printStackTrace();
        }
        catch (IOException e) {
            logger.error ("The database connection file has not been found.");
            e.printStackTrace();
        }

    }
}