package th1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Servidor {
    private static final int PUERTO = 12345;  // Puerto donde escucha el servidor

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PUERTO);
        ExecutorService pool = Executors.newCachedThreadPool();
        System.out.println("Servidor esperando conexiones...");

        while (true) {
            Socket socketCliente = serverSocket.accept();
            pool.submit(new ClienteHandler(socketCliente));
        }
    }
    
}
class ClienteHandler implements Runnable {
    private Socket socketCliente;

    public ClienteHandler(Socket socketCliente) {
        this.socketCliente = socketCliente;
    }

    @Override
    public void run() {
        try (ObjectInputStream ois = new ObjectInputStream(socketCliente.getInputStream());
             ObjectOutputStream oos = new ObjectOutputStream(socketCliente.getOutputStream())) {

            // Recibir el mensaje inicial
            String mensaje = (String) ois.readObject();
            System.out.println(mensaje);

            // Recibir el objeto Persona
            Persona persona = (Persona) ois.readObject();
            System.out.println("Servidor: He recibido el objeto Persona con nombre " + persona.getNombre() + " y edad " + persona.getEdad());

            // Enviar respuesta al cliente
            oos.writeObject("He recibido el objeto Persona con nombre " + persona.getNombre() + " y edad " + persona.getEdad());

            // Recibir otro mensaje con el objeto Persona actualizado
            mensaje = (String) ois.readObject();
            System.out.println(mensaje);

            // Recibir el objeto Persona actualizado
            persona = (Persona) ois.readObject();
            System.out.println("Servidor: He recibido el objeto Persona con nombre " + persona.getNombre() + " y edad " + persona.getEdad());

            // Enviar la respuesta final
            oos.writeObject("He recibido el objeto Persona con nombre " + persona.getNombre() + " y edad " + persona.getEdad());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                socketCliente.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
