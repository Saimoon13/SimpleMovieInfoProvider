package edu.java.connect;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

public class Queries {

    private static final String propFileName = "queries.properties";
    private static Properties props;

    public static Properties getQueries() throws SQLException {
        InputStream is = Queries.class.getResourceAsStream("/edu/java/connect/"+propFileName);
        if (is == null){
            throw new SQLException("Unable to load property file: " + propFileName);
        }
        //singleton
        if(props == null){
            props = new Properties();
            try {
                props.load(is);
            } catch (IOException e) {
                throw new SQLException("Unable to load property file: " + propFileName + "\n" + e.getMessage());
            }
        }
        return props;
    }

    public static String getQuery(String query) throws SQLException{
        return getQueries().getProperty(query);
    }

}
