package cliente.udp;

import java.net.*;
import java.io.*;

/**
 * Define la clase ClienteUDP.
 * 
 * @author Aldonso Becerra Sánchez
 * @version 1.1
 */
public class ClienteUDP {
	// Guarda la IP del servidor.
	protected final String SERVER;
	// Guarda el puerto del servidor.
	protected final int PUERTO_SERVER;

	DatagramSocket socket;
	ClienteEscuchaUDP clienteEscUDP;
	ClienteEnviaUDP clienteEnvUDP;

	/**
	 * Define el constructor de la clase ServidorTCP.
	 * 
	 * @param servidor       Es la IP que tiene el servidor.
	 * @param puertoServidor Es puerto en el que funcionará el servidor.
	 */
	public ClienteUDP(String servidor, int puertoServidor) throws Exception {
		PUERTO_SERVER = puertoServidor;
		SERVER = servidor;

		socket = new DatagramSocket();

		clienteEscUDP = new ClienteEscuchaUDP(socket);
		clienteEnvUDP = new ClienteEnviaUDP(socket, SERVER, PUERTO_SERVER);
	}

	/**
	 * Define el método inicia.
	 */
	public void inicia() {
		clienteEnvUDP.start();
		clienteEscUDP.start();
	}

	/**
	 * Define el método send.
	 * 
	 * @param message mensaje a enviar.
	 */
	public void send(String message) {
		clienteEnvUDP.send(message);
	}

	/**
	 * Define el método send.
	 * 
	 * @param info   mensaje a enviar.
	 * @param puerto puerto al que se enviará el mensaje.
	 */
	public void send(byte[] info, int puerto) {
		clienteEnvUDP.send(info, puerto);
	}
}
