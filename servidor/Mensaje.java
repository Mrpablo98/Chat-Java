package servidor;

import java.io.Serializable;

public class Mensaje implements Serializable{

	private String mensaje;
	private TipoMensaje tipomensaje;
	
	public Mensaje(String mensaje, TipoMensaje tipomensaje) {
		super();
		this.mensaje = mensaje;
		this.tipomensaje = tipomensaje;
	}


	public Object getMensaje() {
		return mensaje;
	}

	public TipoMensaje getTipomensaje() {
		return tipomensaje;
	}

	public enum TipoMensaje {
		NICKNUEVO,ACEPTADO,NOACEPTADO,LISTANICKS,CONECTADO,CHAT,DESCONECTADO;
	}
}
