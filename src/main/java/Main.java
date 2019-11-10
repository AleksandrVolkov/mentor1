//package com.vertex.academy.databases;

import sun.security.provider.certpath.Vertex;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class Main {

    //  Database credentials
    static final String DB_URL = "jdbc:postgresql://127.0.0.1:2020/vertex";
    static final String USER = "postgres";
    static final String PASS = "1234";


    public static void main(String[] argv) {
        TestDatabase();
//        System.out.println("Testing connection to PostgreSQL JDBC");
//
//        try {
//            Class.forName("org.postgresql.Driver");
//        } catch (ClassNotFoundException e) {
//            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
//            e.printStackTrace();
//            return;
//        }
//
//        System.out.println("PostgreSQL JDBC Driver successfully connected");
//        Connection connection = null;
//
//        try {
//            connection = DriverManager
//                    .getConnection(DB_URL, USER, PASS);
//
//        } catch (SQLException e) {
//            System.out.println("Connection Failed");
//            e.printStackTrace();
//            return;
//        }
//
//        if (connection != null) {
//            System.out.println("You successfully connected to database now");
//        } else {
//            System.out.println("Failed to make connection to database");
//        }
    }

    public static void TestDatabase() {

        Connection c;
        Statement stmt;

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:2020/testbase", "postgres", "1234");
            c.setAutoCommit(false);
            System.out.println("-- Opened database successfully");
            String sql;

            //-------------- CREATE TABLE ---------------
            stmt = c.createStatement();
            sql = "CREATE TABLE COMPANY " +
                    "(ID INT PRIMARY KEY     NOT NULL," +
                    " NAME           TEXT    NOT NULL, " +
                    " AGE            INT     NOT NULL, " +
                    " ADDRESS        VARCHAR(50), " +
                    " SALARY         REAL)";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            System.out.println("-- Table created successfully");

            //--------------- INSERT ROWS ---------------
            stmt = c.createStatement();
            sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) VALUES (1, 'Paul', 32, 'California', 20000.00 );";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) VALUES (2, 'Allen', 25, 'Texas', 15000.00 );";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) VALUES (3, 'Teddy', 23, 'Norway', 20000.00 );";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) VALUES (4, 'Mark', 25, 'Rich-Mond ', 65000.00 );";
            stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
            System.out.println("-- Records created successfully");


            //-------------- UPDATE DATA ------------------
            stmt = c.createStatement();
            sql = "UPDATE COMPANY set SALARY = 25000.00 where ID=1;";
            stmt.executeUpdate(sql);
            c.commit();
            stmt.close();

            System.out.println("-- Operation UPDATE done successfully");


            //--------------- SELECT DATA ------------------
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM COMPANY;");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String address = rs.getString("address");
                float salary = rs.getFloat("salary");
                System.out.println(String.format("ID=%s NAME=%s AGE=%s ADDRESS=%s SALARY=%s", id, name, age, address, salary));
            }
            rs.close();
            stmt.close();
            c.commit();
            System.out.println("-- Operation SELECT done successfully");


            //-------------- DELETE DATA ----------------------
            stmt = c.createStatement();
            sql = "DELETE from COMPANY where ID=2;";
            stmt.executeUpdate(sql);
            c.commit();
            stmt.close();
            System.out.println("-- Operation DELETE done successfully");


            c.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("-- All Operations done successfully");

    }
}