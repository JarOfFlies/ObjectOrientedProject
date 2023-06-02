package com.library.Logic.DB;

import java.sql.*;

public class DB {

    /**
     * Used to close connection to the database.
     * @param connection Connection to the database
     */
    public static void CloseConnection(Connection connection){

        System.out.println("Closing connection...");
        if(connection != null)
        {
            try
            {
                connection.close();
            }
            catch (SQLException ignore){
            }
        }
    }

    /**
     * Returns connection to the database.
     * @return Connection to the database.
     */
    public static Connection CreateConnection(){

        //TODO: CHANGE TO YOUR DATABASE CREDENTIALS
        String url = "jdbc:mysql://localhost:3306/libraryschema";
        String username = "Jon";
        String password = "AdminPassword123";

        Connection connection = null;

        try{
            System.out.println("Connecting to the MySQL database...");
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("MySQL database connected!");
        }
        catch (SQLException ignored){
        }

        return connection;
    }

    /**
     * Performs ExectureQuery using provided query string and returns resultset.
     * @param connection Connction to the database
     * @param query SQL query to be executed
     * @return ResultSet for query
     */
    public static ResultSet Fetch(Connection connection, String query){

        ResultSet results = null;

        try{
            System.out.println(query);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            results = preparedStatement.executeQuery();

        }
        catch (SQLException ignored){
        }

        return results;
    }

    /**
     * Performs ExecuteUpdate using provided query string.
     * @param connection Connection to the database
     * @param query SQL query to be executed
     */
    public static void Update(Connection connection, String query){

        try{
            System.out.println(query);
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        }
        catch (SQLException ignored){
        }
    }
}
