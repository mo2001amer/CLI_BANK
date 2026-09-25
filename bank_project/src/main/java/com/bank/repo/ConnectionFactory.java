package com.bank.repo;

import java.io.FileReader;
import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.Properties;

public class ConnectionFactory {

    private static final ConnectionFactory
            connectionFactory =
            new ConnectionFactory();


    private final Properties props =
            new Properties();


    private ConnectionFactory() {

        try (
            FileReader reader =
                    new FileReader(
                            "src/main/resources/"
                            + "db.properties"
                    )
        ) {

            props.load(reader);

        } catch (IOException e) {

          
            throw new IllegalStateException("Could not load database configuration",e
);
        }
    }


    public static ConnectionFactory
            getConnectionFactory() {

        return connectionFactory;
    }


    public Connection getConnection() {

        try {

            return DriverManager.getConnection(
                    props.getProperty(
                            "DB_URL"
                    ),
                    props.getProperty(
                            "DB_USER"
                    ),
                    props.getProperty(
                            "DB_PASSWORD"
                    )
            );

        } catch (SQLException e) {

            throw new IllegalStateException(
                    "Could not connect to the database",
                    e
            );
        }
    }
}