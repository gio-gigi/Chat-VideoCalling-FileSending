package servidor.tcp;

import java.net.*;

import servidor.tcp.ServidorEscuchaTCP;

/**
 * Define la clase ServidorTCP.
 * 
 * @author Aldonso Becerra Sánchez
 * @version 1.0
 */
public class ServidorTCP {
	// Guarda el puerto en el que estará funcionando el servidor.
	protected final int PUERTO_SERVER;
	protected ServerSocket socket;

	/**
	 * Define el constructor de la clase ServidorTCP.
	 * 
	 * @param puertoServidor Es puerto en el que funcionará el servidor.
	 */
	public ServidorTCP(int puertoServidor) throws Exception {
		this.PUERTO_SERVER = puertoServidor;

		socket = new ServerSocket(PUERTO_SERVER);
	}

	public void inicia() throws Exception {
		ServidorEscuchaTCP servidor = new ServidorEscuchaTCP(socket);

		servidor.start();
	}
}
