package pablo.sockets;

import javax.swing.*;

import java.awt.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.*;

public class Servidor {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		MarcoServidor mimarco = new MarcoServidor();

		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
}

class MarcoServidor extends JFrame implements Runnable {

	public MarcoServidor() {

		setBounds(1200, 300, 280, 350);

		JPanel milamina = new JPanel();

		milamina.setLayout(new BorderLayout());

		areatexto = new JTextArea();

		milamina.add(areatexto, BorderLayout.CENTER);

		add(milamina);

		setVisible(true);

		// hilo
		Thread mi_hilo = new Thread(this);
		mi_hilo.start();

	}

	private JTextArea areatexto;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		// System.out.println("estoy escuchando");

		/*******************************************************
		 * servidor ******************************************************
		 */

		try {
			// servidor
			ServerSocket servidor = new ServerSocket(9999);

			while (true) {
				// aceptar conexiones del exterior
				Socket mi_socket = servidor.accept();

				// flujo de ingresao de datos
				DataInputStream flujo_entrada = new DataInputStream(mi_socket.getInputStream());

				// almacenar lo que recibe el flojo de entrada
				String mensaje_flujo = flujo_entrada.readUTF();

				// ver el texto
				areatexto.append("\n" + mensaje_flujo);

				// cierre de la conexion
				mi_socket.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
