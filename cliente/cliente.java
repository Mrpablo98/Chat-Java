package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;


import servidor.Mensaje;
import servidor.Mensaje.TipoMensaje;

public class cliente {

	private ObjectInputStream entrada;
	private ObjectOutputStream salida;
	private Socket cliente;
	private String Nick;
	private boolean desconectado;
	private String ip;
	public cliente(VentanaCliente v) {
		Nick="";
		desconectado=false;
		ip="";
		}
	
	public void conectar(VentanaCliente v) {
	
		try {
			String nick=v.getNickText().getText();
			Nick=nick;
			
			
			
				if(v.conectado==false) {
					cliente=new Socket(v.getServidorText().getText(), Integer.parseInt(v.getPuertoText().getText()));
					String ip=String.valueOf(cliente.getInetAddress());
					this.setIp(ip);
					Mensaje conectado=new Mensaje("Se conecto el cliente:" + ip +" con nick: " + nick + "\n", TipoMensaje.CONECTADO);
					salida=new ObjectOutputStream(cliente.getOutputStream());
					entrada=new ObjectInputStream(cliente.getInputStream());
					v.getServidorLabel().setVisible(false);
					v.getServidorText().setVisible(false);
					v.getPuertoLabel().setVisible(false);
					v.getPuertoText().setVisible(false);
					v.getLabelNick().setVisible(false);
					v.getNickText().setVisible(false);
					v.getTextAreaCliente().setVisible(true);
					v.getConectadosLabel().setVisible(true);
					v.getBtnEnviar().setVisible(true);
					v.getTextFieldbajo().setVisible(true);
					v.getTextAreaNicks().setVisible(true);
					v.getScrollPane1().setVisible(true);
					v.getScrollPane2().setVisible(true);
					v.getBtnConectar().setText("Desconecatar");
					v.conectado=true;
					
					salida.writeObject(conectado);
					salida.flush();
					Mensaje Nick=new Mensaje(nick, TipoMensaje.NICKNUEVO);
					salida.writeObject(Nick);
					salida.flush();
					
					try {
						Mensaje compNick=(Mensaje) entrada.readObject();
						if(compNick.getTipomensaje()==TipoMensaje.ACEPTADO) {
							Mensaje lista=(Mensaje) entrada.readObject();
							String listaNicks=(String) lista.getMensaje();
							v.getTextAreaNicks().setText(listaNicks);
						}else {
							JOptionPane.showMessageDialog(null,"ERROR: Nick Existente");
							v.getBtnConectar().setText("Conectar");
							v.getServidorLabel().setVisible(true);
							v.getServidorText().setVisible(true);
							v.getPuertoLabel().setVisible(true);
							v.getPuertoText().setVisible(true);
							v.getLabelNick().setVisible(true);
							v.getNickText().setVisible(true);
							v.getTextAreaCliente().setVisible(false);
							v.getConectadosLabel().setVisible(false);
							v.getBtnEnviar().setVisible(false);
							v.getTextFieldbajo().setVisible(false);
							v.getTextAreaNicks().setVisible(false);
							v.getScrollPane1().setVisible(false);
							v.getScrollPane2().setVisible(false);
							v.conectado=false;
						}
					}catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
							
					
				}else {
					desconectado=true;
					Mensaje desc=new Mensaje("Se desconecto el cliente:" + nick + "\n", TipoMensaje.DESCONECTADO);
					salida.writeObject(desc);
					v.getBtnConectar().setText("Conectar");
					v.getServidorLabel().setVisible(true);
					v.getServidorText().setVisible(true);
					v.getPuertoLabel().setVisible(true);
					v.getPuertoText().setVisible(true);
					v.getLabelNick().setVisible(true);
					v.getNickText().setVisible(true);
					v.getTextAreaCliente().setVisible(false);
					v.getConectadosLabel().setVisible(false);
					v.getBtnEnviar().setVisible(false);
					v.getTextFieldbajo().setVisible(false);
					v.getTextAreaNicks().setVisible(false);
					v.getScrollPane1().setVisible(false);
					v.getScrollPane2().setVisible(false);
					v.conectado=false;
				}
			}  catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
	}
	
	public synchronized void enviar(VentanaCliente v) {
		if(!v.getTextFieldbajo().getText().equals("")) {
			Mensaje mens=new Mensaje(Nick + ": " +v.getTextFieldbajo().getText() + "\n\n", TipoMensaje.CHAT);
			try {
				salida.writeObject(mens);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	public ObjectInputStream getEntrada() {
		return entrada;
	}

	

	public ObjectOutputStream getSalida() {
		return salida;
	}


	public Socket getCliente() {
		return cliente;
	}


	public String getNick() {
		return Nick;
	}

	

	public boolean isDesconectado() {
		return desconectado;
	}
	public void setDesconestado(boolean b) {
		this.desconectado=b;
	}
	public void setIp(String ip) {
		this.ip=ip;
	}
	public String getIp() {
		return this.ip;
	}
}
