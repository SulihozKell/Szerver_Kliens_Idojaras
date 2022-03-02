package idojaras;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Szerver {
    public static void main(String[] args) {
        ExecutorService exe = Executors.newCachedThreadPool();
        System.out.println("Szerver indul...");
        try {
            ServerSocket socket = new ServerSocket(8080);
            while (true) {
                Socket kapcsolat = socket.accept();

                Ugyfelkiszolgalo u = new Ugyfelkiszolgalo(kapcsolat);
                exe.submit(u);
            }
        }
        catch (IOException ex) {
            System.out.println(ex);
        }
    }
}
