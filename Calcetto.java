import java.util.Scanner;
import java.io.*;
public class Calcetto {
    public static void main(String[] args) {
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
            System.out.println(n);  // togliere
            Scanner sc = new Scanner(t); 
            Roster roster = new Roster(n);
            System.out.println("Palle 1");  // togliere
            int conto = 0;  // togliere
            do {
                String name = sc.next();
                int rate = sc.nextInt();
                System.out.println("Palle 2");  // togliere
                Player player = new Player(rate, name);
                System.out.println("Palle 3");  // togliere
                roster.insert(player);
                conto++;  // togliere
                System.out.println("Palle 4 " + conto);  // togliere
            } while(sc.hasNextLine());
            
            // SQUADRE
            System.out.println("Palle 5 ");  // togliere
            print.print(roster.getTeams());
            
            sc.close();
            print.close();
            read.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }
        System.out.println("FINITO!");
    }
}