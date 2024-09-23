package cliente.tcp;

import java.io.*; // Importar la libreria java.io
import java.net.*; // Importar la libreria java.net
import java.io.FileInputStream;

/**
 * Define la clase ClienteEnviaTCP
 * 
 * @author Aldonso Becerra Sánchez
 * @version 1.1
 */
public class ClienteEnviaTCP extends Thread {
	protected BufferedReader in;
	// Guarda el objeto para realizar la comunicación
	protected Socket socket;
	// Guarda el puerto del servidor.
	protected final int PUERTO_SERVER;
	// Guarda la IP del servidor.
	protected final String SERVER;
	protected DataOutputStream out;

	/**
	 * Define el constructor de la clase ServidorTCP
	 * 
	 * @param servidor       Es la IP que tiene el servidor.
	 * @param puertoServidor Es puerto que está utilizando el servidor.
	 */
	public ClienteEnviaTCP(String servidor, int puertoServidor) throws Exception {
		this.PUERTO_SERVER = puertoServidor;
		this.SERVER = servidor;

		in = new BufferedReader(new InputStreamReader(System.in)); // Creamos una instancia BuffererReader en la que
																	// guardamos los datos introducido por el usuario.
	}

	/**
	 * Define el método run
	 */
	public void run() {
		String mensaje;

		try { // Declaramos un bloque try y catch para controlar la ejecución del subprograma.
			do { // Creamos un bucle do while en el que enviamos al servidor el mensaje los datos
					// que hemos obtenido despues de ejecutar la función "readLine" en la instancia
					// "in".
				mensaje = in.readLine();
				out.writeUTF(mensaje); // Enviamos el mensaje codificado en UTF
			} while (!mensaje.startsWith("fin")); // Mientras el mensaje no encuentre la cadena fin, seguiremos
													// ejecutando
		} catch (IOException e) { // Utilizamos el catch para capturar los errores que puedan surgir
			System.err.println(e.getMessage()); // Si existen errores los mostrará en la consola y después saldrá del
												// programa.
			System.exit(1);
		}
	}

	/**
	 * Define el método send
	 * 
	 * @param message Es el mensaje que se va a enviar.
	 */
	public void send(String message) {
		try {
			socket = new Socket(SERVER, PUERTO_SERVER); // Instanciamos un socket con la dirección del destino y el
														// puerto que vamos a utilizar para la comunicación
			out = new DataOutputStream(socket.getOutputStream());
			out.writeUTF(message); // Enviamos el mensaje codificado en UTF
			out.close();
			socket.close();
		} catch (IOException e) {
			System.err.println("[!] " + e.getMessage()); // Si existen errores los mostrará en la consola y después
															// saldrá del programa.
		}
	}

	/**
	 * Define el método send
	 * 
	 * @param archivo Es el archivo que se va a enviar.
	 */
	public void send(File archivo) {
		try {
			socket = new Socket(SERVER, PUERTO_SERVER); // Instanciamos un socket con la dirección del destino y el
														// puerto que vamos a utilizar para la comunicación
			out = new DataOutputStream(socket.getOutputStream());
			out.writeUTF("File>>>" + archivo.getName());
			out.flush();

			FileInputStream file = new FileInputStream(archivo);

			byte[] buffer = new byte[socket.getSendBufferSize()];
			int bytesRead = 0;
			int total = 0;

			while ((bytesRead = file.read(buffer)) > 0) {
				out.write(buffer, 0, bytesRead);
				// Get the number of bytes in the file
				long sizeInBytes = archivo.length();
				// transform in MB
				long sizeInMB = sizeInBytes / (1024 * 1024);

				total += bytesRead;
				System.out.print("\r[+] Transfiriendo " + archivo.getName() + ": " + (total / (1024 * 1024)) + "MB /"
						+ sizeInMB + "MB");
			}

			file.close();
			out.close();
			socket.close();
			System.out.println("\n[+] Finalizo la transferencia del archivo :)");
		} catch (IOException e) {
			System.err.println("[!] " + e.getMessage()); // Si existen errores los mostrará en la consola y después
															// saldrá del programa.
			System.exit(1);
		}
	}
}
