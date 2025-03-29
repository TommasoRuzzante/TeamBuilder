import java.util.*;
import java.io.*;
import java.text.NumberFormat;

public class Calcetto {
    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();
        NumberFormat format = NumberFormat.getInstance();
        
        try {
            // Use the builder pattern to create and configure the TeamBuilder
            TeamBuilder teamBuilder = new TeamBuilder.Builder()
                .inputFile("file.txt")
                .outputFile("squadre.txt")
                .build();
            
            // Build the teams
            teamBuilder.buildTeams();
            
            System.out.println("FINITO!");
        }
        catch(Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }

        // Display memory usage
        StringBuilder sb = new StringBuilder();
        long maxMemory = runtime.maxMemory();
        long allocatedMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();

        sb.append("free memory: " + format.format(freeMemory / 1024) + "\n");
        sb.append("allocated memory: " + format.format(allocatedMemory / 1024) + "\n");
        sb.append("max memory: " + format.format(maxMemory / 1024) + "\n");
        sb.append("total free memory: " + format.format((freeMemory + (maxMemory - allocatedMemory)) / 1024) + "\n");

        System.out.println(sb.toString());
    }
}