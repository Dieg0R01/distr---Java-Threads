package th6;

import java.io.*;
import java.net.Socket;

public class WebClient {
    private String host;
    private int puerto;

    public WebClient(String host, int puerto) {
        this.host = host;
        this.puerto = puerto;
    }

    public void descargaSinGuardar() {
        try (Socket socket = new Socket(host, puerto);
             InputStream is = socket.getInputStream()) {

            System.out.println("Conectado al servidor, comenzando a recibir el archivo...");

            byte[] buffer = new byte[1024];
            int bytesLeidos;
            while ((bytesLeidos = is.read(buffer)) != -1) {
                // No guardar el archivo, solo mostrar los datos recibidos
                System.out.println("Recibidos " + bytesLeidos + " bytes.");
            }

            System.out.println("Archivo recibido.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        WebClient cliente = new WebClient("localhost", 12345);
        cliente.descargaSinGuardar();
    }
}

