import java.util.Scanner;
import java.io.*;
public class Calcetto {
    public static void main(String[] args) {
        try(FileReader read = new FileReader("file.txt");
            PrintWriter print = new PrintWriter("squadre.txt");
            Scanner sc = new Scanner(read);) {
            
            int n = 10;
            /*while(true) {
                if(!sc.hasNextLine())
                    break;
                String t = sc.nextLine();
                n++;
            }*/

            if(n%2 == 1) {
                sc.close();
                throw new Exception();
            }
            
            // INSERIMENTO GIOCATORI
            Roster roster = new Roster(n);
            System.out.println("Palle 1");  // togliere
            int conto = 0;  // togliere

            do {
                String name = sc.next();
                Double rate = sc.nextDouble();
                System.out.println("Palle 2");  // togliere
                Player player = new Player(rate, name);
                System.out.println("Palle 3");  // togliere
                roster.insert(player);
                conto++;  // togliere
                System.out.println("Palle 4 " + conto);  // togliere
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
    }
}