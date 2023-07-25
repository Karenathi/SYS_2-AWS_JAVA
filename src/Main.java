import java.io.*;
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

    public static void main(String[] args) throws NullPointerException {
        String USER = "postgres";
        String PORT = "5432";

        try {
            String[] env = new BufferedReader(new FileReader(System.getProperty("user.dir") + File.separator + "src" + File.separator + "env.csv")).readLine().split(" ");
            if (env.length != 2){
                System.out.println("Vous devez avoir exactement 2 parametres dans le fichier env.csv");
            } else {
                String DATABASE = env[0];
                String HOST = env[1];


                if (args.length == 0) {
                    System.out.println("Il manque des arguments pour l'execution");
                } else if (args.length >= 3) {
                    System.out.println("Il y a trop des arguments pour l'execution");
                } else {
                    switch (args[0]) {
                        case "createNewConnection" -> {
                            if (args.length != 2) {
                                System.out.println("Il manque un arguments pour l'execution (le nom de la connection)");
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
                            System.out.println("".repeat(20));
                            System.out.println("Vous avez le choix:");
                            System.out.println("    - showAllConnection : afficher ");
                            System.out.println("                    toutes les connections disponible ");
                            System.out.println("    - createNewConnection [nom de la nouvelle connection] : ajouter une nouvelle connection ");
                            System.out.println("                    elle aura une heure de creation et un id generer par defaut a l'instant ");
                        }
                    }
                }

            }
        } catch (Throwable e) {
            System.out.println("Vous devez créer un fichier env.csv et le placer dans le même dossier que le .jar");
            System.out.println("env.csv doit contenir le nom de la base de donnée et l'addresse de la machine qui contient la base de donnée séparer par un espace");
        }
    }
}
