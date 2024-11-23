package th2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 666);
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
             PrintWriter salida = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8)), true)) {
            
            Scanner scanner = new Scanner(System.in);
            String respuesta;
            while (true) {
            	String menu;
				while(menu = entrada.readLine() != null) {
            		System.out.println(menu)); // Mostrar menú del servidor
            	}
                int opcion = scanner.nextInt();
                salida.println(opcion);

                if (opcion == 1) {
                    System.out.println(entrada.readLine()); // Solicitar archivo
                    scanner.nextLine(); // Consumir salto de línea
                    String rutaArchivo = scanner.nextLine();

                    try (BufferedReader archivo = new BufferedReader(new FileReader(rutaArchivo))) {
                        String linea;
                        while ((linea = archivo.readLine()) != null) {
                            salida.println(linea);
                        }
                    } catch (IOException e) {
                        System.out.println("Error al leer el archivo: " + e.getMessage());
                    }
                    salida.println("EOF"); // Señal para finalizar el archivo
                } else if (opcion == 2) {
                    respuesta = entrada.readLine();
                    System.out.println("Número total de líneas procesadas: " + respuesta);
                    break;
                } else {
                    System.out.println("Opción inválida.");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
