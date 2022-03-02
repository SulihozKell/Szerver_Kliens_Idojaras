package idojaras;

import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.stream.Collectors;

public class Ugyfelkiszolgalo implements Runnable {
    private Socket kapcsolat;
    private HashMap<String, Idojaras> elorejelzes;

    public Ugyfelkiszolgalo(Socket kapcsolat) {
        this.elorejelzes = new HashMap<>();
        Beolvas();
        this.kapcsolat = kapcsolat;
    }

    @Override
    public void run() {
        try {
            DataInputStream ugyfeltol = new DataInputStream(kapcsolat.getInputStream());
            DataOutputStream ugyfelnek = new DataOutputStream(kapcsolat.getOutputStream());
            int menu;
            Beolvas();
            do {
                menu = ugyfeltol.readInt();
                switch (menu) {
                    case 1:
                        if (elorejelzes.size() == 0) {
                            Beolvas();
                        }
                        //ugyfelnek.writeUTF(elorejelzes.entrySet().stream().count() + ". darab előrejelzés található!");
                        ugyfelnek.writeUTF(elorejelzes.size() + ". darab előrejelzés található!");
                        ugyfelnek.flush();
                        break;
                    case 2:
                        ugyfelnek.writeUTF(String.format("A mai napon %d megyében lesz napos az idő.",
                                elorejelzes.entrySet().stream()
                                .filter(e -> Objects.equals(e.getValue().getMai().getSzovegesElorejelzes(), "Sunny"))
                                .count()));
                        ugyfelnek.flush();
                        break;
                    case 3:
                        Optional<Integer> holnapLegmelegebb = elorejelzes.values().stream()
                                .map(idojaras -> idojaras.getHolnapi().getMax())
                                .max(Comparator.naturalOrder());
                        ugyfelnek.writeUTF(String.format("A holnapi napon %s fok lesz a legmelegebb.", holnapLegmelegebb.get()));
                        ugyfelnek.flush();
                        break;
                    case 4:
                        ugyfelnek.writeUTF(String.format("A holnapi napon %d megyében lesz szeles vagy felhős az idő.",
                                elorejelzes.entrySet().stream()
                                .filter(e -> Objects.equals(e.getValue().getHolnapi().getSzovegesElorejelzes(), "Cloudy")
                                || Objects.equals(e.getValue().getHolnapi().getSzovegesElorejelzes(), "Windy"))
                                .count()));
                        ugyfelnek.flush();
                        break;
                    case 5:
                        Optional<Integer> maiLegHoIng = elorejelzes.values().stream()
                                .map(idojaras -> idojaras.getMai().getMax() - idojaras.getMai().getMin())
                                .max(Comparator.naturalOrder());
                        List<Idojaras> maiLegHoIngMegye = elorejelzes.values().stream()
                                .filter(e -> Objects.equals(e.getMai().getMax() - e.getMai().getMin(), maiLegHoIng.get()))
                                .collect(Collectors.toList());
                        ugyfelnek.writeUTF(String.format("A mai legnagyobb hőmérséklet ingadozás az %s fok lesz %s-ben.",
                                maiLegHoIng.get(), maiLegHoIngMegye.get(0).getMegye()));
                        ugyfelnek.flush();
                        break;
                }
            }
            while (menu != -1);
        }
        catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public void Beolvas() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("weather.txt"));
            br.readLine();
            String sor = br.readLine();
            while (sor != null) {
                Idojaras i = new Idojaras(sor);
                String megye = i.getMegye();
                elorejelzes.put(megye, i);
                sor = br.readLine();
            }
            for (Map.Entry<String, Idojaras> entry : elorejelzes.entrySet()){
                System.out.println(entry.getValue());
            }
        }
        catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
        catch (IOException ex) {
            System.out.println(ex);
        }
    }
}
