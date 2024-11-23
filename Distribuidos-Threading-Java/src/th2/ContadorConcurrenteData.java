package th2;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ContadorConcurrenteData extends Thread{
	private String archivo;
	private int lineasCont;
	
	public ContadorConcurrenteData(String archivo) {
		super();
		this.archivo = archivo;
	}
	
	public int getLineasCont() {
		return lineasCont;
	}
	
	public void run() {
		try(DataInputStream entrada = new DataInputStream(new FileInputStream(archivo))){
			while ((entrada.readLine()) != null) {
				lineasCont++;
        	}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		ContadorConcurrenteData file1 = new ContadorConcurrenteData("documents/8tG3tzKO");
		ContadorConcurrenteData file2 = new ContadorConcurrenteData("documents/R4rxhJWW");
		ContadorConcurrenteData file3 = new ContadorConcurrenteData("documents/UzZPSJZB");		
		
		long init = System.currentTimeMillis();
		file1.start();
		file2.start();
		file3.start();
		
		try {
            file1.join();
            file2.join();
            file3.join();
            System.out.println("He tardado " + (System.currentTimeMillis() - init) + " ms de manera concurrente con DataInputStream");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
		
		System.out.println(file1.getLineasCont());
		System.out.println(file2.getLineasCont());
		System.out.println(file3.getLineasCont());
	}

}
