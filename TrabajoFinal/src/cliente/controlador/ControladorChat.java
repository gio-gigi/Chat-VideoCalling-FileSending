package cliente.controlador;

import java.io.File;

import cliente.tcp.ClienteTCP;
import cliente.udp.ClienteUDP;

/**
 * Clase ControladorChat que se encarga de enviar mensajes, archivos, imagenes y
 * audios
 */
public class ControladorChat {
	private final ClienteTCP conexionTCP;
	private final ClienteUDP conexionUDP;

	public ControladorChat(ClienteTCP conexionTCP, ClienteUDP conexionUDP) {
		this.conexionTCP = conexionTCP;
		this.conexionUDP = conexionUDP;
	}

	/**
	 * Metodo que envia un mensaje
	 * 
	 * @param mensaje mensaje a enviar
	 * @return true si se envio el mensaje, false en caso contrario
	 */

	public boolean enviarMensaje(String mensaje) {
		if (!mensaje.isEmpty()) {
			try {
				this.conexionTCP.send(mensaje);

				return true;
			} catch (Exception e) {
				System.err.println("[!]" + e.getMessage());

				return false;
			}
		}

		return false;
	}

	/**
	 * Metodo que envia un archivo
	 * 
	 * @param archivo archivo a enviar
	 * @return true si se envio el archivo, false en caso contrario
	 */
	public boolean enviarArchivo(File archivo) {
		this.conexionTCP.send(archivo);

		return true;
	}

	/**
	 * Metodo que envia una imagen
	 * 
	 * @param imagen imagen a enviar
	 * @return true si se envio la imagen, false en caso contrario
	 */
	public boolean enviarImagen(byte[] imagen) {
		this.conexionUDP.send(imagen, 50000);
		return true;
	}

	/**
	 * Metodo que envia un audio
	 * 
	 * @param audio audio a enviar
	 * @return true si se envio el audio, false en caso contrario
	 */
	public boolean enviarAudio(byte[] audio) {
		this.conexionUDP.send(audio, 60001);
		return true;
	}

}
