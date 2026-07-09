package util;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {
    private static final Dotenv dotenv = Dotenv.load(); // loads .env
    private static final String URL = String.format(
            "jdbc:postgresql://%s:%s/%s",
            dotenv.get("DB_HOST"),
            dotenv.get(
            "DB_PORT"),
            dotenv.get("DB_NAME")
    );
    private  static final String USER = dotenv.get("DB_USER");
    private static final String PASS = dotenv.get("DB_PASSWORD");

    // Private constructor: no instance, static methods only
    private Connexion() {}

    public static Connection ouvrir() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
