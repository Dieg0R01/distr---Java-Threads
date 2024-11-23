package th2;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ContadorSecuencialData{
	private String archivo;
	private int lineasCont;
	
	public ContadorSecuencialData(String archivo) {
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
		ContadorSecuencialData file1 = new ContadorSecuencialData("documents/8tG3tzKO");
		ContadorSecuencialData file2 = new ContadorSecuencialData("documents/R4rxhJWW");
		ContadorSecuencialData file3 = new ContadorSecuencialData("documents/UzZPSJZB");		
		
		long init = System.currentTimeMillis();
		file1.run();
		file2.run();
		file3.run();
		
		System.out.println("He tardado " + (System.currentTimeMillis() - init) + " ms de manera secuencial con DataInputStream");
		
		System.out.println(file1.getLineasCont());
		System.out.println(file2.getLineasCont());
		System.out.println(file3.getLineasCont());
	}

}

