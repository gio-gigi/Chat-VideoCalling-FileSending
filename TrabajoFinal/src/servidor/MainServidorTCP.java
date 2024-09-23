package servidor;

import servidor.tcp.ServidorTCP;

/**
 * Define la clase MainServidorTCP.
 * 
 * Clase principal que inicia el servidor.
 * 
 * @version 1.0
 */
public class MainServidorTCP {
    public static void main(String args[]) throws Exception {
        ServidorTCP servidorTCP = new ServidorTCP(60000);

        servidorTCP.inicia();
    }
}
