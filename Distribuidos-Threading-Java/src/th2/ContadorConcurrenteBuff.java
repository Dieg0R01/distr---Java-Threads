package th2;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ContadorConcurrenteBuff extends Thread{
	private String archivo;
	private int lineasCont;
	
	public ContadorConcurrenteBuff(String archivo) {
		super();
		this.archivo = archivo;
	}
	
	public int getLineasCont() {
		return lineasCont;
	}
	
	public void run() {
		try(BufferedInputStream entrada = new BufferedInputStream(new FileInputStream(archivo))){
			while ((entrada.read()) != -1) {
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
		ContadorConcurrenteBuff file1 = new ContadorConcurrenteBuff("documents/8tG3tzKO");
		ContadorConcurrenteBuff file2 = new ContadorConcurrenteBuff("documents/R4rxhJWW");
		ContadorConcurrenteBuff file3 = new ContadorConcurrenteBuff("documents/UzZPSJZB");		
		
		long init = System.currentTimeMillis();
		file1.start();
		file2.start();
		file3.start();
		
		try {
            file1.join();
            file2.join();
            file3.join();
            System.out.println("He tardado " + (System.currentTimeMillis() - init) + " ms de manera concurrente con BufferedReader");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
		
		System.out.println(file1.getLineasCont());
		System.out.println(file2.getLineasCont());
		System.out.println(file3.getLineasCont());
	}

}

