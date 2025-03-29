import java.io.*;
import java.util.*;

public class TeamBuilder {
    private Roster roster;
    private String inputFilePath;
    private String outputFilePath;
    
    private TeamBuilder(Builder builder) {
        this.inputFilePath = builder.inputFilePath;
        this.outputFilePath = builder.outputFilePath;
    }
    
    public static class Builder {
        private String inputFilePath = "file.txt";
        private String outputFilePath = "squadre.txt";
        
        public Builder inputFile(String path) {
            this.inputFilePath = path;
            return this;
        }
        
        public Builder outputFile(String path) {
            this.outputFilePath = path;
            return this;
        }
        
        public TeamBuilder build() {
            return new TeamBuilder(this);
        }
    }
    
    public void buildTeams() throws IOException {
        // Count lines to determine roster size
        int lineCount = countLines(inputFilePath);
        roster = new Roster(lineCount);
        
        // Read players and build teams
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
             PrintWriter writer = new PrintWriter(outputFilePath)) {
            
            String line;
            while ((line = reader.readLine()) != null) {
                Scanner scanner = new Scanner(line);
                if (scanner.hasNext()) {
                    String name = scanner.next();
                    int rating = scanner.nextInt();
                    
                    Player player = new Player.Builder()
                        .name(name)
                        .rating(rating)
                        .build();
                    
                    try {
                        roster.insert(player);
                    } catch (Exception e) {
                        System.err.println("Error inserting player: " + e.getMessage());
                    }
                    
                    scanner.close();
                }
            }
            
            // Write teams to output file
            writer.print(roster.getTeams());
        }
    }
    
    private int countLines(String filePath) throws IOException {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            while (reader.readLine() != null) {
                count++;
            }
        }
        return count;
    }
}