package th4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Servidor {
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
	private static final Object lock = new Object();
    private static HashMap<Integer, String> clientes = new HashMap<Integer, String>();
    private static int idCounter;
	private Socket socket;
	
    public ClienteHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter salida = new PrintWriter(socket.getOutputStream(), true)) {
            // Recibe el primer mensaje del cliente y asigna un ID único
            String mensajeInicial = entrada.readLine();
            int clienteId;
            synchronized (lock) {
                clienteId = idCounter++;
            }
            clientes.put(clienteId, mensajeInicial);

            // Envía el ID al cliente
            salida.println("Tu ID es " + clienteId);

            while (!Thread.currentThread().isInterrupted()) { // Manejo elegante de interrupción
                String solicitud = entrada.readLine();

                if (solicitud == null || solicitud.equalsIgnoreCase("SALIR")) {
                    salida.println("Conexión terminada.");
                    break;
                }

                try {
                    int idSolicitado = Integer.parseInt(solicitud);
                    String mensaje = clientes.getOrDefault(idSolicitado, "DESCONOCIDO");
                    salida.println(mensaje);
                } catch (NumberFormatException e) {
                    salida.println("ERROR");
                }
            }
        } catch (IOException e) {
            System.err.println("Error con el cliente: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Error al cerrar el socket: " + e.getMessage());
            }
        }
    }
}

