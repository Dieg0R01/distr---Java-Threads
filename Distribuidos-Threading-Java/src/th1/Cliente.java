package th1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class Cliente {
	
	private static final String HOST = "localhost";  // Dirección del servidor
    private static final int PUERTO = 12345;        // Puerto del servidor
	
	public static void main(String[] args) {
		try (Socket socket = new Socket(HOST, PUERTO);
	         ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
	         ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
			
            Persona persona = new Persona("Pepe", 56);
            
            // Enviar peticion
            oos.writeObject("Hola, soy un cliente y te voy a enviar una persona");
            oos.writeObject(persona);

            // Recibir respuesta del servidor
            String respuesta = (String) ois.readObject();
            System.out.println(respuesta);

            // Actualizar la persona
            persona.setNombre("Carlos");
            persona.setEdad(35);

            // Enviar mensaje de actualización
            oos.writeObject("Actualizo la persona");
            // Enviar el objeto Persona actualizado
            oos.writeObject(persona);

            // Recibir la respuesta del servidor
            respuesta = (String) ois.readObject();
            System.out.println(respuesta);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
	}
}
