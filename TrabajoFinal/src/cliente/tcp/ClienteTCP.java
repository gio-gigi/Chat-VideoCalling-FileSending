package cliente.tcp;

import java.io.File;

/**
 * Define la clase ClienteTCP.
 * 
 * @author Aldonso Becerra Sánchez
 * @version 1.0
 */
public class ClienteTCP {
	// Guarda la IP del servidor.
	protected final String SERVER;
	// Guarda el puerto del servidor.
	protected final int PUERTO_SERVER;

	ClienteEnviaTCP clienteTCP;

	/**
	 * Define el constructor de la clase ServidorTCP.
	 * 
	 * @param servidor       Es la IP que tiene el servidor.
	 * @param puertoServidor Es puerto en el que funcionará el servidor.
	 */
	public ClienteTCP(String servidor, int puertoServidor) throws Exception {
		SERVER = servidor;
		PUERTO_SERVER = puertoServidor;
		clienteTCP = new ClienteEnviaTCP(SERVER, PUERTO_SERVER);
	}

	/**
	 * Define el método inicia.
	 */
	public void inicia() throws Exception {
		clienteTCP.start();
	}

	/**
	 * Define el método send.
	 * 
	 * @param message mensaje a enviar.
	 */
	public void send(String message) {
		clienteTCP.send(message);
	}

	/**
	 * Define el método send.
	 * 
	 * @param archivo archivo a enviar.
	 */
	public void send(File archivo) {
		clienteTCP.send(archivo);
	}
}
