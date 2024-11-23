package th1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Servidor {
    private static final int PUERTO = 12345;  // Puerto donde escucha el servidor

    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
			ExecutorService pool = Executors.newCachedThreadPool();
			System.out.println("Servidor esperando conexiones...");

			while (true) {
			    Socket socketCliente = serverSocket.accept();
			    pool.submit(new ClienteHandler(socketCliente));
			}
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

            //Thread.sleep(2000);
            
            // Enviar respuesta al cliente
            oos.writeObject("He recibido el objeto Persona con nombre " + persona.getNombre() + " y edad " + persona.getEdad());
            oos.flush();
            
            // Recibir otro mensaje con el objeto Persona actualizado
            mensaje = (String) ois.readObject();
            System.out.println(mensaje);

            // Recibir el objeto Persona actualizado
            persona = (Persona) ois.readObject();
            System.out.println("Servidor: He recibido el objeto Persona con nombre " + persona.getNombre() + " y edad " + persona.getEdad());
            
            //Thread.sleep(2000);

            // Enviar la respuesta final
            oos.writeObject("He recibido el objeto Persona con nombre " + persona.getNombre() + " y edad " + persona.getEdad());
            oos.flush();
            
        } catch (IOException | ClassNotFoundException /*| InterruptedException*/ e) {
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
