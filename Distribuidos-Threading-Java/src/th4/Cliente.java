package th4;

import java.io.*;
import java.net.*;

public class Cliente {
    public static void main(String[] args) {
        String host = "localhost";
        int puerto = 12345;

        try (Socket socket = new Socket(host, puerto);
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in))
        ) {
            System.out.println("Conectado al servidor. Introduce tu mensaje inicial:");
            String mensajeInicial = teclado.readLine();
            salida.println(mensajeInicial);

            // Recibe el ID asignado por el servidor
            String respuesta = entrada.readLine();
            System.out.println("Servidor: " + respuesta);

            while (true) {
                System.out.println("Introduce una solicitud (ID de cliente o SALIR):");
                String solicitud = teclado.readLine();
                salida.println(solicitud);

                if (solicitud.equalsIgnoreCase("SALIR")) {
                    System.out.println("Conexión cerrada.");
                    break;
                }

                String respuestaServidor = entrada.readLine();
                System.out.println("Servidor: " + respuestaServidor);
            }
        } catch (IOException e) {
            System.err.println("Error en el cliente: " + e.getMessage());
        }
    }
}

