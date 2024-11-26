// programma per creare 2 squadre

public class Roster{
    
    // array e costruttore
    private Player[] v;
    private int size;
    public Roster(int n) {
        v = new Player[n];
        size = 0;
    }

    // ordinamento
    private void insertionAlg(Player p) {
        if(size == 0)
            v[0] = p;
        else if(p.compareTo(v[0]) <= 0) {
            Player tmp = new Player[v.length];
            System.arraycopy(v, 1, tmp, 0, v.length - 1); // copia da v a tmp traslato di 1
            v = tmp;
            v[0] = p;
        }
        else if(p.compareTo(v[size]) > 0)
            v[size + 1] = p
        else {
            for(int i = 1; i < size; i++) {
                if(p.compareTo(v[i]) <= 0) {
                    Player tmp = new Player[v.length];
                    //System.arraycopy(v, , tmp, i, v.length); // copia da v a tmp traslato di 1
                    v = tmp;
                    v[0] = p;
                }
            }
        }
    }

    // metodi
    public void insert(Player p) {
        insertionAlg(p);
        size++;
    }

    private Player[] team1() {
        Player[] T1= new Player[size/2];
        for(int i = 0, k = 0; i < T1.length; i++, k++)
            T1[i] = v[i+k];
        return T1;
    }

    private Player[] team2() {
        Player[] T2= new Player[size/2];
        for(int i = 0, k = 1; i < T2.length; i++, k++)
            T2[i] = v[i+k];
        return T2;
    }

    public String getTeams() {
        Player[] T1 = team1();
        Player[] T2 = team2();
        String str = "Squadra 1: \n";
        for(int i = 0; i < T1.length; i++)
            str = str + T1[i].getName() + "\n";
        str += "Squadra 2: \n";
        for(int i = 0; i < T2.length; i++)
            str= str + T2[i].getName() + "\n";
        return str;
    }

}