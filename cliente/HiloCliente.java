package cliente;



import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JOptionPane;

import servidor.Mensaje;
import servidor.Mensaje.TipoMensaje;

public class HiloCliente extends Thread{

	private VentanaCliente v;
	private ObjectInputStream entrada;
	private cliente cliente;
	public HiloCliente(VentanaCliente v, ObjectInputStream entrada, cliente cliente) {
		
		this.v=v;
		this.entrada=entrada;
		this.cliente=cliente;
		
	}
	
	
	public void run() {
	
			
		while(true) {
			if(cliente.isDesconectado())break;
			try {
				Mensaje mens=(Mensaje) entrada.readObject();
				if(mens.getTipomensaje()==TipoMensaje.ACEPTADO) {
					Mensaje lista=(Mensaje) entrada.readObject();
					String listaNicks=(String) lista.getMensaje();
					v.getTextAreaNicks().setText(listaNicks);
				}else if(mens.getTipomensaje()==TipoMensaje.CHAT) {
					v.getTextAreaCliente().append((String) mens.getMensaje());
					v.getTextFieldbajo().setText("");
				}else if(mens.getTipomensaje()==TipoMensaje.DESCONECTADO) {
					if(cliente.isDesconectado()) {
					cliente.getEntrada().close();
					cliente.getSalida().close();
					cliente.getCliente().close();
					}else {
						Mensaje lista=(Mensaje) entrada.readObject();
						JOptionPane.showMessageDialog(null,mens.getMensaje());
						v.getTextAreaNicks().setText((String) lista.getMensaje());
					}
				}
			}catch ( IOException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
