package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class servidor extends Thread{

	private ventanaServidor ventana;
	private int PUERTO;
	
	public servidor(ventanaServidor ventana) {
		this.ventana=ventana;
		PUERTO=Integer.valueOf(ventana.getCampoPuerto().getText());
	}
	
	public void run() {
		
		ServerSocket socketservidor=null;
		Compartida compartida=new Compartida(ventana.getAreaServidor());
		try {
			socketservidor=new ServerSocket(PUERTO);
			ventana.getAreaServidor().setVisible(true);
			ventana.getAreaServidor().append("SERVIDOR ARRANCADO EN EL PUERTO "+PUERTO+"\n");
			ventana.getCampoPuerto().setText("");
			ventana.getCampoPuerto().setEnabled(false);
			while(true) {
				Socket socketcliente=socketservidor.accept();
				Hilo hilo=new Hilo(socketcliente,compartida);
				hilo.start();
			}
		} catch (IOException e) {e.printStackTrace();}
		finally {
			try {
				socketservidor.close();
			} catch (IOException e) {e.printStackTrace();}
		}
		
	}
}
