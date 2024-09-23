package servidor.tcp;

import java.io.*; // Importar la libreria java.net
import java.net.*; // Importar la libreria java.io

/**
 * Define la clase ServidorTCP
 * 
 * @author Aldonso Becerra Sánchez
 * @version 1.0
 */
public class ServidorEscuchaTCP extends Thread {
	// Es el objeto que servirá para realizar la comunicación.
	protected ServerSocket socket;
	protected DataInputStream in;
	protected Socket socket_cli;
	// Guarda el puerto en el que estará funcionando el servidor.

	/**
	 * Define el constructor de la clase ServidorEscuchaTCP
	 * 
	 * @param socketServidor Es puerto en el que funcionará el servidor.
	 */
	public ServidorEscuchaTCP(ServerSocket socketServidor) {
		socket = socketServidor; // Instanciamos un ServerSocket con la dirección del destino y el puerto que
									// vamos a utilizar para la comunicación.
	}

	/**
	 * Método principal de la clase.
	 */
	public void run() {
		System.out.println("# Servidor TCP iniciado en el puerto: " + socket.getLocalPort());

		try { // Declaramos un bloque try y catch para controlar la ejecución del subprograma
			do {
				System.out.println("[-]: Esperando conexion...");
				socket_cli = socket.accept(); // Creamos un socket_cli al que le pasamos el contenido del objeto socket
												// después de ejecutar la función accept que nos permitirá aceptar
												// conexiones de clientes.
				in = new DataInputStream(socket_cli.getInputStream()); // Declaramos e instanciamos el objeto
																		// DataInputStream que nos valdrá para recibir
																		// datos del cliente.
				System.out.println("[+] Conectado");

				String mensaje = "";
				mensaje = in.readUTF();

				if (mensaje.contains(">>>") && mensaje.split(">>>")[0].equalsIgnoreCase("File")) {
					InputStream input = socket_cli.getInputStream();
					DataOutputStream flujo = new DataOutputStream(socket_cli.getOutputStream());

					recibirArchivo(flujo, ".", socket_cli, mensaje.split(">>>")[1]);
				} else {
					System.out.println("[+] Mensaje recibido: " + mensaje);
				}

				socket_cli.close();
			} while (true);
		} catch (Exception e) { // Utilizamos el catch para capturar los errores que puedan surgir
			e.printStackTrace(); // Si existen errores los mostrará en la consola y después saldrá del programa.
		}
	}

	/**
	 * Define el método recibirArchivo.
	 * 
	 * @param cliente          Es el flujo de salida.
	 * @param direccionArchivo Es la dirección del archivo.
	 * @param sk               Es el socket.
	 * @param nombreArchivo    Es el nombre del archivo.
	 */
	public void recibirArchivo(DataOutputStream cliente, String direccionArchivo, Socket sk, String nombreArchivo) {
		try {
			BufferedWriter outReader = new BufferedWriter(new OutputStreamWriter(sk.getOutputStream()));
			FileOutputStream wr = new FileOutputStream(new File(".\\" + nombreArchivo));
			InputStream input = sk.getInputStream();
			byte[] buffer = new byte[sk.getReceiveBufferSize()];
			int bytesReceived = 0;
			System.out.println("[+] Inicia la descarga del archivo...");

			while ((bytesReceived = input.read(buffer)) > 0) {
				wr.write(buffer, 0, bytesReceived);
			}

			wr.flush();
			wr.close();
			outReader.flush();
			System.out.println("[+] Termino la descarga del archivo...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
