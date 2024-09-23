package servidor.udp;

import java.io.*; // Importar la libreria java.net
import java.net.*; // Importar la libreria java.io
import utils.Zip;


public class ServidorEscuchaAudioUDP extends Thread {
	protected DatagramSocket socket;
	protected final int PUERTO_SERVER;

	protected int puertoCliente = 0;
	protected InetAddress addressCliente;
	protected byte[] mensaje2_bytes;
	protected final int MAX_BUFFER = 1024*30;

	// Guarda el paquete recibido del cliente.
	protected DatagramPacket paquete;
	protected byte[] mensaje_bytes;
	protected DatagramPacket envPaquete;

	protected VentanaCamara ventana_camara;

	/**
	 * Define el constructor de la clase ServidorUDP.
	 * @param puertoServidor Es puerto en el que funcionará el servidor.
	 */
	public ServidorEscuchaAudioUDP(int puertoServidor, VentanaCamara ventana) throws Exception {
		PUERTO_SERVER = puertoServidor;
		socket = new DatagramSocket(puertoServidor); //Creamos el socket
		ventana_camara = ventana;
	}

	/**
	 * Método principal de la clase.
	 */
	public void run() {
		System.out.println("# Servidor UDP iniciado en el puerto: " + this.PUERTO_SERVER);
		long startTime, endTime, totalTime;
		int bytesReceived = 0;
		try {
			//String mensaje = "";
			//String mensajeComp = "";
					   
			do { // Iniciamos el bucle
				// Recibimos el paquete
				mensaje_bytes=new byte[MAX_BUFFER];

				paquete = new DatagramPacket(mensaje_bytes,MAX_BUFFER);
				socket.receive(paquete);

				startTime = System.currentTimeMillis();  // Start time
				// Lo formateamos
				mensaje_bytes = new byte[paquete.getLength()];
				mensaje_bytes = paquete.getData();
                                
				mensaje_bytes = Zip.decompress(mensaje_bytes);

				ventana_camara.play(mensaje_bytes);
				
				//Obtenemos IP Y PUERTO
				puertoCliente = paquete.getPort();
				addressCliente = paquete.getAddress();

				endTime = System.currentTimeMillis();  // End time
				totalTime = endTime - startTime;
				double transferRate = (bytesReceived * 8) / (totalTime / 1000.0);  // bps

				System.out.println("Bytes recibidos: " + bytesReceived);
				System.out.println("Tiempo total: " + totalTime + " ms");
				System.out.println("Tasa de trasferencia: " + transferRate + " bps");
			} while (true);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}
}

