/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientxatn;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author cep
 */
public class ClientXatN {
 public static final int PORT = 5000;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            
            String IP = Teclado.leerString("IP servidor:\n");

            
            String missatge = Teclado.leerString("Missatge:\n");

            Socket servidor = new Socket(IP, PORT);
            System.out.println("Connexió correcta");

            //While no sigui fi, seguir esperant
            while (!missatge.equals("FI")&&!missatge.equals("fi")&&!missatge.equals("Fi")) {
                enviarMissatge(servidor, missatge);

                String rebutServidor = rebreMissatge(servidor);
                System.out.println("Del servidor: " + rebutServidor);
                
                missatge = Teclado.leerString("Missatge:\n");
            }
            System.out.println("Finalitzat");
            servidor.close();

        } catch (Exception exc) {
            System.out.println(exc.toString());
        }

    }

    public static void enviarMissatge(Socket socket, String msg) {

        try {
            OutputStream os = socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(msg);
        } catch (Exception exc) {
            exc.toString();
        }

    }

    public static String rebreMissatge(Socket socket) {
        String missatge = null;
        try {
            InputStream is = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            missatge = (String) ois.readObject();
        } catch (Exception exc) {
            exc.toString();
        }
        return missatge;
    }
}
