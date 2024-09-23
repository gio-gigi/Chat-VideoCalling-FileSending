package servidor;

import servidor.udp.ServidorUDP;

/**
 * Define la clase MainServidorUDP.
 * 
 * Clase principal que inicia el servidor.
 * 
 * @version 1.0
 */
public class MainServidorUDP {
    public static void main(String args[]) throws Exception {
        ServidorUDP servidorUDP = new ServidorUDP(50000);

        servidorUDP.inicia();
    }
}
