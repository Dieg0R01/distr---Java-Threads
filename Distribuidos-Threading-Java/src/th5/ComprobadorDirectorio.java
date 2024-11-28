package th5;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class ComprobadorDirectorio {

    private static final String DIRECTORIO_RUTA = "C:\\Users\\diego\\Desktop"; // Cambiar por la ruta real del directorio
    private static long ultimaFechaModificacion = 0; // Almacena la última fecha de modificación conocida

    public static void main(String[] args) {
        File directorio = new File(DIRECTORIO_RUTA);

        if (!directorio.exists() || !directorio.isDirectory()) {
            System.out.println("El directorio especificado no existe o no es un directorio válido.");
            return;
        }

        // Listar contenido inicial
        listarContenidoDirectorio(directorio);
        ultimaFechaModificacion = directorio.lastModified();

        // Configurar tarea periódica
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
        	// scheduleAtFixedRate vs schedule
        	// El intervalo de tiempo se mantiene fijo entre ejecuciones, 
        	// es decir, se garantiza que la tarea se ejecutará a intervalos fijos, 
        	// independientemente de cuánto tiempo tarde en completarse la tarea anterior.
            @Override
            public void run() {
                verificarCambios(directorio);
            }
        },0, 10_000); // Cada 10 segundos

        // Mantener el programa en ejecución
        System.out.println("Comprobador de directorio en ejecución. Presiona Ctrl+C para salir.");
        try {
            Thread.sleep(Long.MAX_VALUE); // Evitar que el programa finalice
        } catch (InterruptedException e) {
            System.out.println("Programa interrumpido.");
        }
    }

    private static void listarContenidoDirectorio(File directorio) {
        System.out.println("Contenido del directorio " + directorio.getAbsolutePath() + ":");
        File[] archivos = directorio.listFiles();
        if (archivos != null) {
            for (File archivo : archivos) {
                System.out.println((archivo.isDirectory() ? "[DIR] " : "[FILE] ") + archivo.getName());
            }
        } else {
            System.out.println("No se pudo acceder al contenido del directorio.");
        }
    }

    private static void verificarCambios(File directorio) {
        long fechaModificacionActual = directorio.lastModified();

        // Verificar si la fecha de modificación cambió
        if (fechaModificacionActual != ultimaFechaModificacion) {
            System.out.println("\nSe detectaron cambios en el directorio. Actualizando listado:");
            listarContenidoDirectorio(directorio);
            ultimaFechaModificacion = fechaModificacionActual;
        } else {
            System.out.println("\nNo se detectaron cambios en el directorio.");
        }
    }
}
