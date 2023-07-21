import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Scanner;


public class Main {

    public static void query(String sql, String user, String database, String host, String port, String password) throws IOException, InterruptedException {
        String command = String.format("psql -U %s -d %s -h %s -p %s -c \"%s\"", user, database, host, port, sql);
        ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", command);
        processBuilder.environment().put("PGPASSWORD", password);

        Process process = processBuilder.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null){
            System.out.println(line);
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException, NullPointerException {
        String USER = "postgres";
        String PASSWORD = "password";
        String DATABASE = "javatest";
        String HOST = "localhost";
        String PORT = "5432";

        Scanner in = new Scanner(System.in);
        System.out.println("Changer le mot de passe (password) ?");
        String tmp = in.nextLine();
        if (!tmp.equals("")) { PASSWORD = tmp;}

        if (args.length == 0) {
            System.out.println("Il manque des arguments pour l'execution");
        } else if (args.length >= 3) {
            System.out.println("Il manque des arguments pour l'execution");
        } else {
            switch (args[0]) {
                case "createNewConnection" -> {
                    if (args.length != 2) {
                        System.out.println("Il manque des arguments pour l'execution");
                    } else {
                        String sql = String.format("INSERT INTO connection(first_name) VALUES (%s)", args[1]);
                        query(sql, USER, DATABASE, HOST, PORT, PASSWORD);
                    }
                }
                case "showAllConnection" -> {
                    String sql = "SELECT * FROM connection;";
                    query(sql, USER, DATABASE, HOST, PORT, PASSWORD);
                }
                default -> {
                    System.out.println("Unknow command : " + args[0]);
                }
            }
        }
    }
}