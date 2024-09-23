package cliente.visual;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRootPane;

import com.github.sarxos.webcam.Webcam;

import cliente.controlador.ControladorChat;
import com.github.sarxos.webcam.WebcamException;
import java.io.IOException;
import utils.Zip;

public class VentanaCamara extends Thread {
	// Propiedades de la ventana
	private JFrame camara;
	private JLabel video;
	private boolean camara_activa;
	private int x, y;

	// Camara
	private BufferedImage bufferedImg;
	private ImageIcon drawImg;
	private Webcam webcam;

	// Microfono
	private TargetDataLine microphone;
	private AudioFormat format;
	private int dsize;
	private DataLine.Info info;

	private final ControladorChat controlador;

	public VentanaCamara(ControladorChat controlador) {
		this.controlador = controlador;
		this.camara_activa = true;
	}

	/**
	 * Inicializa los componentes de la ventana.
	 */
	public void initComp() {
		camara = new JFrame();
		camara.setTitle("Sala de Reunion");
		camara.setSize(640, 480);
		camara.setResizable(false);
		camara.setLocationRelativeTo(null);

		camara.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				x = e.getX();
				y = e.getY();
			}
		});

		camara.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent evt) {
				camara.setLocation(evt.getXOnScreen() - x, evt.getYOnScreen() - y);
			}
		});

		video = new JLabel();
		video.setSize(640, 480);
		video.setVisible(true);

		camara.add(video);

		camara.setUndecorated(true);
		camara.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		camara.setVisible(true);
	}

	/**
	 * Inicializa la camara y el microfono.
	 */
	public void run() {
		try {
			webcam = Webcam.getDefault();
			webcam.setViewSize(new Dimension(176, 144));
			webcam.open();

			format = new AudioFormat(24000.0f, 8, 2, true, false);
			microphone = AudioSystem.getTargetDataLine(format);
			info = new DataLine.Info(TargetDataLine.class, format);

			microphone = (TargetDataLine) AudioSystem.getLine(info);
			microphone.open(format);
			microphone.start();
		} catch (WebcamException | LineUnavailableException e) {
		}

		while (camara_activa) {
			try {
				// VIDEO
				bufferedImg = webcam.getImage();
				drawImg = new ImageIcon(bufferedImg);
				video.setIcon(drawImg);

				// Envio al servidor
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				ImageIO.write(bufferedImg, "jpg", bos);

				byte[] data = bos.toByteArray();
				byte[] compressed = Zip.compress(data);

				controlador.enviarImagen(compressed);

				// AUDIO
				byte[] dataAudio = new byte[1024 * 30];

				dsize = microphone.read(dataAudio, 0, dataAudio.length);

				compressed = Zip.compress(dataAudio);

				controlador.enviarAudio(compressed);

			} catch (IOException e) {
			}
		}

		webcam.close();
		microphone.close();
		camara.dispose();
	}

	/**
	 * Metodo para apagar la camara.
	 */
	public void apagar_camara() {
		this.camara_activa = false;
	}
}
