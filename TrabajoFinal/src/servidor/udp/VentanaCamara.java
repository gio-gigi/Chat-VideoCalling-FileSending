package servidor.udp;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;

import utils.Zip;

/**
 * Clase VentanaCamara que se encarga de mostrar la camara y el audio
 */
public class VentanaCamara {
	private JLabel video;
	private JFrame camara;
	private ImageIcon drawImg;
	private AudioFormat format;
	private DataLine.Info info;
	private SourceDataLine speakers;

	public VentanaCamara() throws LineUnavailableException {
		initComp();
	}

	public void initComp() throws LineUnavailableException {
		camara = new JFrame();
		camara.setSize(640, 480);
		camara.setTitle("Sala de Reunion");

		video = new JLabel();
		video.setSize(176, 144);
		video.setVisible(true);

		format = new AudioFormat(24000.0f, 8, 2, true, false);
		info = new DataLine.Info(SourceDataLine.class, format);

		speakers = (SourceDataLine) AudioSystem.getLine(info);
		speakers.open(format);
		speakers.start();

		camara.add(video);
		camara.setVisible(true);
	}

	/**
	 * Método que muestra la imagen.
	 * 
	 * @param imagen Es la imagen que se va a mostrar.
	 */
	public void flush(byte[] imagen) {
		try {
			imagen = Zip.decompress(imagen);
			ByteArrayInputStream bain = new ByteArrayInputStream(imagen);
			BufferedImage bIma = ImageIO.read(bain);
			drawImg = new ImageIcon(bIma);
			video.setIcon(drawImg);
			video.revalidate();
			video.repaint();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método que reproduce el audio.
	 * 
	 * @param audio Es el audio que se va a reproducir.
	 */
	public void play(byte[] audio) {
		int dsize = audio.length;
		speakers.write(audio, 0, dsize);
	}
}
