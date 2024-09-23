package cliente.udp;

import java.net.*;
import java.io.*;

/**
 * Define la clase ClienteEnviaUDP.
 * 
 * @author Aldonso Becerra Sánchez
 * @version 1.0
 */
public class ClienteEnviaUDP extends Thread {
	protected BufferedReader in;
	// Definimos el sockets, número de bytes del buffer, y mensaje.
	protected final int MAX_BUFFER = 1024 * 6;
	protected final int PUERTO_SERVER;
	protected DatagramSocket socket;
	protected InetAddress address;
	protected DatagramPacket paquete;
	protected final String SERVER;

	public ClienteEnviaUDP(DatagramSocket nuevoSocket, String servidor, int puertoServidor) {
		socket = nuevoSocket;
		SERVER = servidor;
		PUERTO_SERVER = puertoServidor;
	}

	public void run() {
		in = new BufferedReader(new InputStreamReader(System.in));

		byte[] mensaje_bytes;
		String mensaje = "";
		mensaje_bytes = mensaje.getBytes();

		String cadenaMensaje = "";

		byte[] RecogerServidor_bytes;

		try {
			address = InetAddress.getByName(SERVER);
			do {
				mensaje = in.readLine();
				mensaje_bytes = new byte[mensaje.length()];
				mensaje_bytes = mensaje.getBytes();
				paquete = new DatagramPacket(mensaje_bytes, mensaje.length(), address, PUERTO_SERVER);
				socket.send(paquete);

				String mensajeMandado = new String(paquete.getData(), 0, paquete.getLength()).trim();
				System.out.println("Mensaje \"" + mensajeMandado + "\" enviado a " + paquete.getAddress() + "#"
						+ paquete.getPort());
			} while (!mensaje.startsWith("fin"));
		} catch (Exception e) {
			System.err.println("Exception " + e.getMessage());
			System.exit(1);
		}
	}

	/**
	 * Define el método send
	 * 
	 * @param mensaje Es el mensaje que se va a enviar.
	 */
	public void send(String mensaje) {
		byte[] mensaje_bytes;

		try {
			address = InetAddress.getByName(SERVER);

			mensaje_bytes = new byte[mensaje.length()];
			mensaje_bytes = mensaje.getBytes();
			paquete = new DatagramPacket(mensaje_bytes, mensaje.length(), address, PUERTO_SERVER);
			socket.send(paquete);

			String mensajeMandado = new String(paquete.getData(), 0, paquete.getLength()).trim();
			System.out.println("[+] Mensaje \"" + mensajeMandado + "\" enviado a " + paquete.getAddress() + "#"
					+ paquete.getPort());
		} catch (IOException e) {
			System.err.println("[!] " + e.getMessage());
			System.exit(1);
		}
	}

	/**
	 * Define el método send
	 * 
	 * @param info   Es el mensaje que se va a enviar.
	 * @param puerto Es el puerto al que se va a enviar el mensaje.
	 */
	public void send(byte[] info, int puerto) {
		try {
			address = InetAddress.getByName(SERVER);

			// Medir el tiempo de inicio
			long startTime = System.currentTimeMillis();

			paquete = new DatagramPacket(info, info.length, address, puerto);
			socket.send(paquete);

			// Medir el tiempo de finalización
			long endTime = System.currentTimeMillis();
			long totalTime = endTime - startTime;

			// Calcular la tasa de transferencia en bits por segundo (bps)
			double transferRate = (info.length * 8.0) / (totalTime / 1000.0); // bps

			System.out.println("[+] Bytes enviados: " + info.length);
			System.out.println("[+] Tiempo total: " + totalTime + " ms");
			System.out.println("[+] Tasa de transferencia: " + transferRate + " bps");

		} catch (IOException e) {
			System.err.println("[!] " + e.getMessage());
			System.exit(1);
		}
	}
}
