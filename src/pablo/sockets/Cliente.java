package pablo.sockets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.*;

public class Cliente {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		MarcoCliente mimarco = new MarcoCliente();

		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}

class MarcoCliente extends JFrame {

	public MarcoCliente() {

		setBounds(600, 300, 280, 350);

		LaminaMarcoCliente milamina = new LaminaMarcoCliente();

		add(milamina);

		setVisible(true);
	}

}

class LaminaMarcoCliente extends JPanel implements Runnable {

	private JTextField campo1, nick, ip;
	private JButton miboton;
	private JTextArea campoChat; // ver charla

	public LaminaMarcoCliente() {
		nick = new JTextField(5);
		add(nick);

		JLabel texto = new JLabel("-CHAT-");

		add(texto);

		ip = new JTextField(8);
		add(ip);

		campo1 = new JTextField(20);

		add(campo1);

		campoChat = new JTextArea(12, 20);

		add(campoChat);

		miboton = new JButton("Enviar");

		// antes de agregar el boton (add)
		EnviaTexto mi_evento = new EnviaTexto(); // evento
		miboton.addActionListener(mi_evento); // el boton escucha el evento

		add(miboton);

		Thread mi_hilo = new Thread(this);
		mi_hilo.start();

	}

	// clase interna, tiene el evento
	private class EnviaTexto implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

			// System.out.println(campo1.getText());

			/************************************************************
			 * SOCKET
			 ************************************************************/
			try {
				Socket mi_socket = new Socket("192.168.1.60", 9999);

				PaqueteEnvio datos = new PaqueteEnvio();
				datos.setNick(nick.getText());
				datos.setIp(ip.getText());
				datos.setMensaje(campo1.getText());

				// flujo de datos
				ObjectOutputStream paquete_datos = new ObjectOutputStream(mi_socket.getOutputStream());

				// agregar datos
				paquete_datos.writeObject(datos);

				// cerrar soccket
				mi_socket.close();

//				// flujo de datos
//				DataOutputStream flujo_salida = new DataOutputStream(mi_socket.getOutputStream());
//
//				// pasar datos por el flujo
//				flujo_salida.writeUTF(campo1.getText());
//				flujo_salida.close();

			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				// e1.printStackTrace();
				System.out.println(e1.getMessage());
			}
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			ServerSocket servidor_cliente = new ServerSocket(9090);

			Socket cliente;
			PaqueteEnvio paqueteRecibido;

			while (true) {
				// acepta las entradas del exterior
				cliente = servidor_cliente.accept();

				ObjectInputStream flujo_entrada = new ObjectInputStream(cliente.getInputStream());

				// reconstruccion del los datos recibidos
				paqueteRecibido = (PaqueteEnvio) flujo_entrada.readObject();

				campoChat.append("\n " + paqueteRecibido.getNick() + ": " + paqueteRecibido.getMensaje());
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}
}

// serializable, para poder enviarse como bites por la red
class PaqueteEnvio implements Serializable {
	private String nick;
	private String ip;
	private String mensaje;

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

}