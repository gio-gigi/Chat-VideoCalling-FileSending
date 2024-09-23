package servidor.udp;

import servidor.udp.ServidorEscuchaUDP;
import servidor.udp.ServidorEscuchaAudioUDP;
import servidor.udp.VentanaCamara;

/**
 * Define la clase ServidorUDP.
 * @author Aldonso Becerra Sánchez
 * @version 1.0
 */
public class ServidorUDP{
	/** Guarda el puerto en el que estará funcionando el servidor. */
	public final int PUERTO_SERVER;

	/**
	 * Define el constructor de la clase ServidorUDP.
	 * @param puertoServidor Es puerto en el que funcionará el servidor.
	 */
	public ServidorUDP (int puertoServidor) {
		PUERTO_SERVER = puertoServidor;
	}

	public void inicia () throws Exception {
		VentanaCamara ventanaCamara = new VentanaCamara();
		ServidorEscuchaUDP servidorUDP=new ServidorEscuchaUDP(PUERTO_SERVER, ventanaCamara);
		ServidorEscuchaAudioUDP servidorAudio = new ServidorEscuchaAudioUDP(60001, ventanaCamara);
		
		servidorUDP.start();
		servidorAudio.start();
	}
}

