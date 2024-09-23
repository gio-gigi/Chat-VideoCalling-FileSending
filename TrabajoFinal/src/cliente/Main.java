package cliente;

import cliente.controlador.ControladorChat;
import cliente.tcp.ClienteTCP;
import cliente.udp.ClienteUDP;
import cliente.visual.VentanaChat;


public class Main {
	public static void main(String args[]) throws Exception {
		String usuario = System.getProperty("user.name"); // Se obtiene el usuario que usará la aplicación.
		ClienteTCP conexionTPC = new ClienteTCP("127.0.0.1", 60000); // Se inicia la conexión TCP.
		ClienteUDP conexionUDP = new ClienteUDP("127.0.0.1", 50000); // Se inicia la conexión UDP.

		ControladorChat app = new ControladorChat(conexionTPC, conexionUDP); // Se inicia el controlador del programa.

		VentanaChat chat = new VentanaChat(usuario, app); // Se inicializa la ventana.
	}
}
