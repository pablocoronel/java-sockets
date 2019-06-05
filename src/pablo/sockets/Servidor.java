package pablo.sockets;

import javax.swing.*;

import java.awt.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
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

			// paquete recibido por la red
			String nick, ip, mensaje;

			PaqueteEnvio paquete_recibido;

			while (true) {
				// aceptar conexiones del exterior
				Socket mi_socket = servidor.accept();

				ObjectInputStream paquete_datos = new ObjectInputStream(mi_socket.getInputStream());
				paquete_recibido = (PaqueteEnvio) paquete_datos.readObject();

				nick = paquete_recibido.getNick();
				ip = paquete_recibido.getIp();
				mensaje = paquete_recibido.getMensaje();

//				// flujo de ingresao de datos
//				DataInputStream flujo_entrada = new DataInputStream(mi_socket.getInputStream());
//
//				// almacenar lo que recibe el flojo de entrada
//				String mensaje_flujo = flujo_entrada.readUTF();
//
//				// ver el texto
//				areatexto.append("\n" + mensaje_flujo);

				areatexto.append("\n" + nick + ": " + mensaje + " para" + ip);

				// socket por el cual el server le envia los datos al cliente destinatario
				Socket enviarDestinatario = new Socket(ip, 9090);

				ObjectOutputStream paqueteReenvio = new ObjectOutputStream(enviarDestinatario.getOutputStream());
				paqueteReenvio.writeObject(paquete_recibido);

				paqueteReenvio.close(); // cierre del flujo de datos

				enviarDestinatario.close(); // cierre de socket de reenvio al destinatario

				// cierre de la conexion
				mi_socket.close();
			}
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
