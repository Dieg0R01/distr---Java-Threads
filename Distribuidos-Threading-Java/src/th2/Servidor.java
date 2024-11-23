package th2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Servidor {
    public static void main(String[] args) {
        int totalLineas = 0;

        try (ServerSocket servidor = new ServerSocket(666)) {
            System.out.println("Servidor escuchando en el puerto 666");

            while (true) {
                try (Socket cliente = servidor.accept();
                     BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream(), StandardCharsets.UTF_8));
                     PrintWriter salida = new PrintWriter(new BufferedWriter(new OutputStreamWriter(cliente.getOutputStream(), StandardCharsets.UTF_8)), true)) {

                    System.out.println("Cliente conectado: " + cliente.getInetAddress());
                    boolean continuar = true;

                    while (continuar) {
                        salida.println("""
                                ==================================
                                        MENÚ PRINCIPAL
                                ==================================
                                1. Contar líneas de un archivo
                                2. Desconectarse
                                ==================================
                                Por favor, elija una opción:
                                """);

                        int opcion = Integer.parseInt(entrada.readLine());
                        switch (opcion) {
                            case 1 -> {
                                salida.println("Ingrese una ruta absoluta para el fichero:");
                                int lineasArchivo = 0;

                                String linea;
                                while (!(linea = entrada.readLine()).equals("EOF")) {
                                    lineasArchivo++;
                                }

                                totalLineas += lineasArchivo;
                                salida.println("Archivo procesado. Líneas contadas: " + lineasArchivo);
                            }
                            case 2 -> {
                                salida.println("Total de líneas procesadas por el servidor: " + totalLineas);
                                continuar = false;
                            }
                            default -> salida.println("Opción inválida. Intente nuevamente.");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
