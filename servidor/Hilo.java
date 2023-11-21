package servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import servidor.Mensaje.TipoMensaje;

public class Hilo extends Thread {

	private Socket socket;
	private Compartida compartida;
	private String nick;
	
	public Hilo(Socket socket, Compartida compartida) {
		super();
		this.socket = socket;
		this.compartida = compartida;
		
	}
	
	public void run() {
		try {
			ObjectInputStream entrada=new ObjectInputStream(socket.getInputStream());
			ObjectOutputStream salida=new ObjectOutputStream(socket.getOutputStream());
			boolean fin=false;
			while(!fin) {
				Mensaje conect=  (Mensaje) entrada.readObject();
				compartida.getAservidor().append((String) conect.getMensaje());;
				Mensaje mensajeinicial=(Mensaje) entrada.readObject();
				nick=(String) mensajeinicial.getMensaje();
				if (compartida.comprobarnick(nick, salida)) {
					// Nick valido, cliente conectado
					Mensaje mensaje=new Mensaje( "Nick correcto",TipoMensaje.ACEPTADO);
					compartida.enviaratodos(mensaje);
					salida.flush();
					compartida.enviarnicks();
					fin=true;
					
				}
				else {
					Mensaje mensaje=new Mensaje( "Nick incorrecto",TipoMensaje.NOACEPTADO);
					salida.writeObject(mensaje);
					salida.flush();
					fin=true;
				}
				
			}
			fin=false;
			while(!fin) {
				Mensaje mens=(Mensaje) entrada.readObject();
				if(mens.getTipomensaje()==TipoMensaje.CHAT) {
				compartida.enviaratodos(mens);
				}else if(mens.getTipomensaje()==TipoMensaje.DESCONECTADO){
					compartida.enviaratodos(mens);
					compartida.getAservidor().append((String) mens.getMensaje());
					for(int i=0;i<compartida.getListanicks().size();i++) {
						if(nick.equals(compartida.getListanicks().get(i))) {
							compartida.getListanicks().remove(i);
							compartida.getListastreamssalida().remove(i);
							
						}
					}
					compartida.enviarnicks();
					fin=true;
					break;
				}
				
			}
			entrada.close();
			salida.close();
		} catch (Exception e) {e.printStackTrace();}
		finally {
			try {
				socket.close();
			} catch (Exception e) {e.printStackTrace();}
		}
	}
	
	
}
