package Graph;

/**
 * Projekt: RomUndKathargo
 * Author : Julian Heeger
 * Date : 13.05.14
 * Year : 2014
 */
public class Zug {
    private Seite seite;
    private int stadt;

    public Zug(Seite seite, int stadt) {
        this.seite = seite;
        this.stadt = stadt;
    }

    public Zug(String zug){
        String[] temp = zug.split(" ");
        if (temp.length > 1){
            try{
                this.seite = readSeite(temp[0]);
                this.stadt = Integer.parseInt(temp[1]);
            } catch (Exception ex){
                System.out.println(ex.getMessage());
            }

        }
    }

    private Seite readSeite(String x) throws Exception {
        Seite resu;
        if (x.equals("N")) {
            resu = Seite.Neutral;
        } else {
            if (x.equals("R")) {
                resu = Seite.Rom;
            } else {
                if (x.equals("C")) {
                    resu = Seite.Kathargo;
                } else {
                    throw new Exception("Keine geeigneten Besetzer gefunden");
                }
            }
        }
        return resu;
    }

    public Seite getSeite() {
        return seite;
    }

    public int getStadt() {
        return stadt;
    }

    @Override
    public String toString() {
        return "Zug{" +
                "seite=" + seite +
                ", stadt=" + stadt +
                '}';
    }
}
