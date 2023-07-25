import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;


public class Main {

    public static String query(String sql, String user, String database, String host, String port) throws IOException, InterruptedException {
        List<String> command = List.of(String.format("psql -U %s -d %s -h %s -p %s -c \"%s\"", user, database, host, port, sql).split(" "));
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        System.out.println(sql);

        Process process = processBuilder.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null){
            System.out.println(line);
        }

        int exitCode = process.waitFor();
        return ("Exit code: " + exitCode);
    }

    public static void main(String[] args) throws IOException, InterruptedException, NullPointerException {
        String USER = "postgres";
        String DATABASE = "javatest";
        String HOST = "localhost";
        String PORT = "5432";

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
                        String sql = String.format("INSERT INTO connection (first_name) VALUES ('%s');", args[1]);
                        query(sql, USER, DATABASE, HOST, PORT);
                    }
                }
                case "showAllConnection" -> {
                    String sql = "SELECT * FROM connection;";
                    query(sql, USER, DATABASE, HOST, PORT);
                }
                default -> {
                    System.out.println("Unknow command : " + args[0]);
                }
            }
        }
    }
}
