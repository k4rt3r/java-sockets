/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorxatn;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author cep
 */
public class ServidorXatN {

	  public static final int PORT = 5000;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            
            ServerSocket server = new ServerSocket(PORT);
            System.out.println("Connectat, esperant...");
            
            Socket client = server.accept();
            System.out.println(client.getInetAddress().getHostAddress());

            String missatge = rebreMissatge(client);

            System.out.println("Del client: " + missatge);
            
            //While no sigui fi, seguir esperant
            while (!missatge.equals("FI")&&!missatge.equals("fi")&&!missatge.equals("Fi")) {
                System.out.println("Missatge:");
                String enviar = new Scanner(System.in).nextLine();

                enviarMissatge(client, enviar);

                missatge = rebreMissatge(client);
                System.out.println("Del client: " + missatge);
            }
            System.out.println("Connexió acabada");

            client.close();

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
