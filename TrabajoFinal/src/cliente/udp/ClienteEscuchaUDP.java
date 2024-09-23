package cliente.udp;

import java.net.*;
import java.io.*;
 
/**
 * Define la clase ClienteEscuchaUDP.
 * @author Aldonso Becerra Sánchez
 * @version 1.0
 */
public class ClienteEscuchaUDP extends Thread {
	protected BufferedReader in;
	//Definimos el sockets, número de bytes del buffer, y mensaje.
	protected final int MAX_BUFFER = 256;
	protected final int PUERTO_CLIENTE;
	protected DatagramSocket socket;
	protected InetAddress address;
	protected DatagramPacket servPaquete;

	public ClienteEscuchaUDP (DatagramSocket socketNuevo) {
		socket = socketNuevo;
		PUERTO_CLIENTE = socket.getLocalPort();
	}

	public void run() {
		byte[] mensaje_bytes;
		String mensaje = "";
		mensaje_bytes = mensaje.getBytes();
		
		String cadenaMensaje = "";

		byte[] recogerServidor_bytes;

		try {
			do {
				recogerServidor_bytes = new byte[MAX_BUFFER];

				servPaquete = new DatagramPacket(recogerServidor_bytes,MAX_BUFFER);
				socket.receive(servPaquete); // Esperamos a recibir un paquete.

				cadenaMensaje = new String(recogerServidor_bytes).trim(); //Convertimos el mensaje recibido en un string.

				
				System.out.println("Mensaje recibido \""+cadenaMensaje +"\" de "+ servPaquete.getAddress()+"#"+servPaquete.getPort()); // Imprimimos el paquete recibido.
			} while (!cadenaMensaje.startsWith("fin"));
		}
		catch (Exception e) {
			e.printStackTrace();
			System.err.println("Excepcion C: " + e.getMessage());
			System.exit(1);
		}
	}
}

