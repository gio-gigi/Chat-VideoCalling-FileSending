package servidor.udp;

import java.io.*; // Importar la libreria java.net
import java.net.*; // Importar la libreria java.io
import utils.Zip;


/**
 * Define la clase ServidorEscuchaUDP
 * @author Aldonso Becerra Sánchez
 * @version 1.0
 */
public class ServidorEscuchaUDP extends Thread {
	// Es el objeto que servirá para realizar la comunicación.
	protected DatagramSocket socket;
	// Guarda el puerto en el que estará funcionando el servidor.
	protected final int PUERTO_SERVER;

	protected int puertoCliente = 0;
	protected InetAddress addressCliente;
	protected byte[] mensaje2_bytes;
	protected final int MAX_BUFFER = 1024*6;
	// Guarda el paquete recibido del cliente.
	protected DatagramPacket paquete;
	protected byte[] mensaje_bytes;
	protected DatagramPacket envPaquete;

	protected VentanaCamara ventana_camara;

	/**
	 * Define el constructor de la clase ServidorUDP.
	 * @param puertoServidor Es puerto en el que funcionará el servidor.
	 */
	public ServidorEscuchaUDP(int puertoServidor, VentanaCamara ventanaCamara) throws Exception {
		PUERTO_SERVER = puertoServidor;
		socket = new DatagramSocket(puertoServidor); //Creamos el socket
		ventana_camara = ventanaCamara;
	}

	/**
	 * Método principal de la clase.
	 */
	public void run() {
		System.out.println("# Servidor UDP iniciado en el puerto: " + this.PUERTO_SERVER);
		
		try {
			String mensaje = "";
			String mensajeComp = "";
					   
			do { // Iniciamos el bucle
				// Recibimos el paquete
				mensaje_bytes=new byte[MAX_BUFFER];

				paquete = new DatagramPacket(mensaje_bytes,MAX_BUFFER);
				socket.receive(paquete);
				
				// Lo formateamos
				mensaje_bytes=new byte[paquete.getLength()];
				mensaje_bytes=paquete.getData();
				//mensaje = new String(mensaje_bytes,0,paquete.getLength()).trim();
				
				// Lo mostramos por pantalla
				//System.out.println("\n# Mensaje recibido \""+mensaje_bytes+"\" del cliente "+
						//paquete.getAddress()+":"+paquete.getPort());

				ventana_camara.flush(mensaje_bytes);

				socket.receive(paquete);

				mensaje_bytes=new byte[paquete.getLength()];
				mensaje_bytes=paquete.getData();

				ventana_camara.play(mensaje_bytes);
				
				//Obtenemos IP Y PUERTO
				puertoCliente = paquete.getPort();
				addressCliente = paquete.getAddress();

				//enviaMensaje("Tu mensaje llego correctamente al servidor");
			} while (true);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}

	private void send (String mensaje, String IP, int puerto) {
		//
	}

	private void enviaMensaje(String mensajeComp) throws Exception {
		mensaje2_bytes = new byte[mensajeComp.length()];
		mensaje2_bytes = mensajeComp.getBytes();
		
		//Preparamos el paquete que queremos enviar
		envPaquete = new DatagramPacket(mensaje2_bytes,mensaje2_bytes.length,addressCliente,puertoCliente);

		// realizamos el envio
		socket.send(envPaquete);
		System.out.println("\n# Mensaje saliente del servidor \""+ (new String(envPaquete.getData(),0,envPaquete.getLength()))+ "\" al cliente " + addressCliente + ": "+puertoCliente);
	}
}
