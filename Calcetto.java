import java.util.Scanner;
import java.io.*;
public class Calcetto {
    public static void main(String[] args) {
        try(FileReader read= new FileReader("file.txt");
            PrintWriter print= new PrintWriter("squadre.txt");
            Scanner sc= new Scanner(read)) {
            
            System.out.println("inizio"); //TOGLIERE

            Scanner counter= new Scanner(read);
            int n= 0;
            while(true) {
                if(!counter.hasNextLine())
                    break;
                String t= counter.nextLine();
                n++;
            }
            counter.close();

            if(n%2 == 1)
                throw new Exception();
            System.out.println(n); //TOGLIERE
            
            // INSERIMENTO GIOCATORI
            Roster roster= new Roster(n);
            sc.reset();
            do {
                String name= sc.next();
                Double rate= sc.nextDouble();
                Player player= new Player(rate, name);
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
    }
}