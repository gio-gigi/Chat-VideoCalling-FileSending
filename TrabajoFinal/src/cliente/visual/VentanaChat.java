package cliente.visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Toolkit;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import cliente.controlador.ControladorChat;

public class VentanaChat extends JFrame {
	// Propiedades de la ventana
	private final Toolkit window = Toolkit.getDefaultToolkit();
	private final Dimension SCREEN_SIZE = window.getScreenSize();
	protected final int HEIGHT = SCREEN_SIZE.height;
	protected final int WIDTH = SCREEN_SIZE.width;

	// Elementos de la ventana
	private JPanel MessagePanel, FormPanel;
	private JTextArea areaMensajes;
	private JTextField areaEscritura;
	private JButton btnFile;
	private JButton btnWebCam;
	private JButton btnSend;

	// Guarda el nombre de usuario de la aplicación.
	private final String USUARIO;

	private VentanaCamara camara;
	private ControladorChat controlador;

	public VentanaChat(String usuario, ControladorChat controlador) {
		this.USUARIO = usuario;
		this.controlador = controlador;

		this.camara = new VentanaCamara(controlador);

		this.initComp();
	}

	private void initComp() {

		// Configuraciones de la ventana

		this.setTitle("Chat - Usuario: " + USUARIO); // Titulo de la ventana.
		this.setIconImage(window.getImage("resources/favicon.png")); // Icono de la ventana.
		this.setLocationRelativeTo(null); // Ubicación de la ventana.
		this.setLayout(null); // Layout de la ventana.
		this.setSize((int) (WIDTH * 0.4), (int) (HEIGHT * 0.6));// Tamaño de la ventana.
		this.setResizable(false); // La ventana no puede cambiar de tamaño.

		// Seccion de mensajes:

		MessagePanel = new JPanel(new BorderLayout());
		MessagePanel.setBounds(0, 0, (int) (WIDTH * 0.4), (int) (HEIGHT * 0.5));
		MessagePanel.setBackground(new Color(242, 134, 204));// Color

		areaMensajes = new JTextArea();
		areaMensajes.setText("Bienvenido " + USUARIO + "\n");
		areaMensajes.setEditable(false);
		areaMensajes.setBackground(new Color(242, 134, 204));
		areaMensajes.setForeground(Color.WHITE);

		JScrollPane scroll = new JScrollPane(areaMensajes);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setBackground(new Color(242, 134, 204));// color rosa
		scroll.setBorder(BorderFactory.createCompoundBorder(areaMensajes.getBorder(),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));

		MessagePanel.add(scroll);

		// Sección para escribir y mandar mensajes:

		FormPanel = new JPanel();

		FormPanel.setBounds(0, (int) (HEIGHT * 0.50), (int) (WIDTH * 0.4), (int) (HEIGHT * 0.05));
		FormPanel.setBackground(new Color(242, 134, 204));

		areaEscritura = new JTextField((int) (WIDTH * 0.015));
		areaEscritura.setBackground(new Color(242, 134, 204));
		areaEscritura.setForeground(Color.WHITE);
		areaEscritura.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					enviarMensaje();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// Do nothing
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// Do nothing
			}
		});

		Icon send_icon = new ImageIcon("resources/send.png");
		btnSend = new JButton("Enviar", send_icon);

		btnSend.setBackground(new Color(242, 134, 204));
		btnSend.setForeground(Color.WHITE);

		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enviarMensaje();
			}
		});
		// Botones para enviar archivos
		Icon file_icon = new ImageIcon("resources/file.png");
		btnFile = new JButton(file_icon);

		btnFile.setBackground(new Color(242, 134, 204));
		btnFile.setForeground(Color.WHITE);

		btnFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirArchivo();
			}
		});
		// Boton para abrir la camara
		Icon webcam_icon = new ImageIcon("resources/webcam.png");
		btnWebCam = new JButton(webcam_icon);

		btnWebCam.setBackground(new Color(242, 134, 204));
		btnWebCam.setForeground(Color.WHITE);
		btnWebCam.setSize(10, 10);

		btnWebCam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirCamara();
			}
		});
		// Se agregan los elementos al panel
		FormPanel.add(btnFile);
		FormPanel.add(btnWebCam);
		FormPanel.add(areaEscritura);
		FormPanel.add(btnSend);

		this.add(MessagePanel);
		this.add(FormPanel);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setVisible(true);
	}

	/**
	 * Método que envía un mensaje.
	 */
	public void enviarMensaje() {
		String mensaje = areaEscritura.getText().trim();
		if (controlador.enviarMensaje(mensaje)) {
			this.agregarMensaje(mensaje);
			this.areaEscritura.setText("");
		}
	}

	/**
	 * Método que agrega un mensaje al área de mensajes.
	 * 
	 * @param mensaje Es el mensaje que se va a agregar.
	 */
	public void agregarMensaje(String mensaje) {
		this.areaMensajes.append(mensaje + "\n");
	}

	/**
	 * Método que abre un archivo.
	 */
	private void abrirArchivo() {
		JFileChooser chooser = new JFileChooser();
		int selection = chooser.showOpenDialog(this);
		if (selection == JFileChooser.APPROVE_OPTION) {
			String archivo = chooser.getSelectedFile().getAbsolutePath();

			this.controlador.enviarArchivo(chooser.getSelectedFile());
		}
	}

	/**
	 * Método que abre la cámara.
	 */
	private void abrirCamara() {
		if (this.camara.isAlive()) {
			// La camara ya está corriendo, entonces se detiene el proceso.
			this.camara.apagar_camara();
		} else {
			// La camara no está corriendo, se inicia el proceso.
			this.camara = new VentanaCamara(controlador);
			this.camara.start();
			this.camara.initComp();
		}
	}
}
