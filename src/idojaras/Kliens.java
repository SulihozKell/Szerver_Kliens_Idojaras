package idojaras;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Kliens {
    public static void main(String[] args) {
        try {
            Socket kapcsolat = new Socket("localhost", 8080);
            DataInputStream szervertol = new DataInputStream(kapcsolat.getInputStream());
            DataOutputStream szervernek = new DataOutputStream(kapcsolat.getOutputStream());

            Scanner sc = new Scanner(System.in);
            while (true) {
                int menu;
                do {
                    System.out.println("\nVálasszon a menüpontok közül!" +
                            "\n1.: Hány darab előrejelzés található a fájlban?" +
                            "\n2.: A mai napon hány megyében lesz napos az idő?" +
                            "\n3.: A holnapi napon hány fok lesz a legmelegebb?" +
                            "\n4.: A holnapi napon hány megyében lesz szeles vagy felhős az idő?" +
                            "\n5.: A mai napon mekkora lesz a legnagyobb hőmérséklet ingadozás és az melyik megyében lesz?");
                    menu = sc.nextInt();
                    szervernek.writeInt(menu);
                    szervernek.flush();
                    System.out.println(szervertol.readUTF());
                }
                while (menu != 6);
            }
        }
        catch (IOException ex) {
            System.out.println(ex);
        }
    }
}
