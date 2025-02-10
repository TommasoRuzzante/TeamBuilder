import java.util.Scanner;
public class calctest {
    public static void main(String[] args) {
        
        String temp= "aaa 5 \n bbb 2 \n ccc 1 \n ddd 4 \n eee 6 \n fff 16 \n ggg 7 \n hhh 9 \n iii 29 \n jjj 11";
        Scanner sc= new Scanner(temp);
        /* 
        System.out.println("inizio");

        Scanner counter= new Scanner(read);
        int n= 0;
        while(true) {
            if(!counter.hasNextLine())
                break;
            String t= counter.nextLine();
            n++;
        }
        counter.close();

        System.out.println(n); */
        
        // INSERIMENTO GIOCATORI
        Roster roster= new Roster(10); // aggiungere int n
        // sc.reset();
        do {
            String name= sc.next();
            Double rate= sc.nextDouble();
            Player player= new Player(rate, name);
            try {
                roster.insert(player);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while(sc.hasNextLine());
        
        // SQUADRE
        System.out.print(roster.getTeams());

        sc.close();
        System.out.println("FINITO!");
    }
}