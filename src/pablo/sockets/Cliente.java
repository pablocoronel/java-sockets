package pablo.sockets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
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

class LaminaMarcoCliente extends JPanel {

	private JTextField campo1;
	private JButton miboton;

	public LaminaMarcoCliente() {

		JLabel texto = new JLabel("CLIENTE");

		add(texto);

		campo1 = new JTextField(20);

		add(campo1);

		miboton = new JButton("Enviar");

		// antes de agregar el boton (add)
		EnviaTexto mi_evento = new EnviaTexto(); // evento
		miboton.addActionListener(mi_evento); // el boton escucha el evento

		add(miboton);

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

				// flujo de datos
				DataOutputStream flujo_salida = new DataOutputStream(mi_socket.getOutputStream());

				// pasar datos por el flujo
				flujo_salida.writeUTF(campo1.getText());
				flujo_salida.close();

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
}