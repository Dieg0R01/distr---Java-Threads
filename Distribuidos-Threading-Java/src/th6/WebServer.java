package th6;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class WebServer {
    private static final int PUERTO = 12345;  // Puerto donde escucha el servidor
    static final String RUTA_FICHERO = "C:\\Users\\diego\\git\\distr---Java-Threads\\Distribuidos-Threading-Java\\documents\\hola.txt"; // Ruta del archivo que se enviará

    /*
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            System.out.println("Servidor esperando conexiones...");

            // Aceptar una única conexión de cliente
            try (Socket clienteSocket = serverSocket.accept();
                 FileInputStream fis = new FileInputStream(RUTA_FICHERO);
                 BufferedInputStream bis = new BufferedInputStream(fis);
                 OutputStream os = clienteSocket.getOutputStream()) {

                System.out.println("Cliente conectado, enviando archivo...");

                byte[] buffer = new byte[1024];
                int bytesLeidos;
                while ((bytesLeidos = bis.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesLeidos);
                }

                System.out.println("Archivo enviado correctamente.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */
    public static void main(String[] args) {
        ExecutorService pool = Executors.newCachedThreadPool();  // Pool de hilos
        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            System.out.println("Servidor esperando conexiones...");

            while (true) {
                // Aceptar cliente
                Socket clienteSocket = serverSocket.accept();
                // Enviar el archivo al cliente en un hilo
                //pool.submit(new ClienteHandler(clienteSocket));
                
                // Crear un Callable para enviar el archivo al cliente
                Callable<Void> tarea = new AtenderPeticion(clienteSocket);
                // Ejecutar la tarea y obtener un Future para manejar el resultado
                Future<Void> future = pool.submit(tarea);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            pool.shutdown();
        }
    }
}

class AtenderPeticion implements Callable<Void> {
    private Socket socket;

    public AtenderPeticion(Socket socket) {
        this.socket = socket;
    }

	@Override
	public Void call() {
		try (FileInputStream fis = new FileInputStream(WebServer.RUTA_FICHERO);
             BufferedInputStream bis = new BufferedInputStream(fis);
             OutputStream os = socket.getOutputStream()) {

            System.out.println("Atendiendo cliente: " + socket.getInetAddress());

            byte[] buffer = new byte[1024];
            int bytesLeidos;
            while ((bytesLeidos = bis.read(buffer)) != -1) {
                os.write(buffer, 0, bytesLeidos);
            }

            System.out.println("Archivo enviado a " + socket.getInetAddress());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
            	socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		return null;
	}
}
