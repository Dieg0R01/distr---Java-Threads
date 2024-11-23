package th2;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ContadorSecuencialBuff{
	private String archivo;
	private int lineasCont;
	
	public ContadorSecuencialBuff(String archivo) {
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
		ContadorSecuencialBuff file1 = new ContadorSecuencialBuff("documents/8tG3tzKO");
		ContadorSecuencialBuff file2 = new ContadorSecuencialBuff("documents/R4rxhJWW");
		ContadorSecuencialBuff file3 = new ContadorSecuencialBuff("documents/UzZPSJZB");		
		
		long init = System.currentTimeMillis();
		file1.run();
		file2.run();
		file3.run();
		
		System.out.println("He tardado " + (System.currentTimeMillis() - init) + " ms de manera secuencial con BufferedInputStream");
		
		System.out.println(file1.getLineasCont());
		System.out.println(file2.getLineasCont());
		System.out.println(file3.getLineasCont());
	}

}


