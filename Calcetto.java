import java.util.*;
import java.io.*;
import java.text.NumberFormat;

public class Calcetto {
    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();
        NumberFormat format = NumberFormat.getInstance();
        
        try(FileReader read = new FileReader("file.txt");
            PrintWriter print = new PrintWriter("squadre.txt");
            Scanner lineCounter = new Scanner(read);) {
            
            int n = 0;
            String t = "";
            while(true) {
                t += lineCounter.nextLine();
                n++;
                if(lineCounter.hasNextLine())
                    t += "\n";
                else
                    break;
            }
            
            // INSERIMENTO GIOCATORI
            Scanner sc = new Scanner(t); 
            Roster roster = new Roster(n);
            do {
                String name = sc.next();
                int rate = sc.nextInt();
                Player player = new Player(rate, name);
                roster.insert(player);
            } while(sc.hasNextLine());
            
            // SQUADRE
            print.print(roster.getTeams());
            
            sc.close();
            print.close();
            read.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }

        System.out.println("FINITO!");

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