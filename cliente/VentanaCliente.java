package cliente;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import servidor.Mensaje.TipoMensaje;
import servidor.Compartida;
import servidor.Mensaje;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.BevelBorder;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class VentanaCliente extends JFrame {

	private JPanel contentPane;
	private JTextField servidorText;
	private JTextField PuertoText;
	private JTextField NickText;
	private JButton btnConectar;
	private JTextArea textAreaNicks;
	private JTextArea textAreaCliente;
	private JLabel ConectadosLabel;
	private JTextField textFieldbajo;
	private JLabel ServidorLabel;
	private JLabel PuertoLabel;
	private JLabel LabelNick;
	private JButton btnEnviar;
	private JScrollPane scrollPane1;
	private JScrollPane scrollPane2;
	private VentanaCliente v=this;
	boolean conectado=false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaCliente frame = new VentanaCliente();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaCliente() {
		
		cliente cliente=new cliente(v);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1155, 690);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(35, 35, 35));
		contentPane.setBorder(new MatteBorder(2, 1, 1, 1, (Color) new Color(0, 0, 0)));
		this.setTitle("Cliente chat: Conexion");
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] {186, 131, 46, 122, 28, 363, 131, 0};
		gbl_contentPane.rowHeights = new int[]{23, 0, 122, 370, 41, 46, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		btnEnviar = new JButton("Enviar");
		btnEnviar.setBackground(new Color(131, 137, 169));
		btnEnviar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cliente.enviar(v);
			}
		});
		btnEnviar.setVisible(false);
		
		textFieldbajo = new JTextField();
		textFieldbajo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==10) {
					cliente.enviar(v);
				}
			}
		});
		textFieldbajo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldbajo.setCaretColor(new Color(255, 255, 255));
		textFieldbajo.setSelectionColor(new Color(128, 0, 255));
		textFieldbajo.setForeground(new Color(255, 255, 255));
		textFieldbajo.setBackground(new Color(128, 128, 128));
		textFieldbajo.setVisible(false);
		
		ConectadosLabel = new JLabel("Conectados:");
		ConectadosLabel.setForeground(new Color(0, 255, 0));
		ConectadosLabel.setFont(new Font("Yu Gothic", Font.BOLD, 15));
		ConectadosLabel.setVisible(false);
		
		scrollPane1 = new JScrollPane();
		scrollPane1.setVisible(false);
		
		ServidorLabel = new JLabel("Servidor:");
		ServidorLabel.setForeground(new Color(192, 192, 192));
		ServidorLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_ServidorLabel = new GridBagConstraints();
		gbc_ServidorLabel.anchor = GridBagConstraints.EAST;
		gbc_ServidorLabel.insets = new Insets(0, 0, 5, 5);
		gbc_ServidorLabel.gridx = 0;
		gbc_ServidorLabel.gridy = 1;
		contentPane.add(ServidorLabel, gbc_ServidorLabel);
		
		servidorText = new JTextField();
		servidorText.setCaretColor(new Color(255, 255, 255));
		servidorText.setForeground(new Color(255, 255, 255));
		servidorText.setBackground(new Color(128, 128, 128));
		GridBagConstraints gbc_servidorText = new GridBagConstraints();
		gbc_servidorText.fill = GridBagConstraints.HORIZONTAL;
		gbc_servidorText.insets = new Insets(0, 0, 5, 5);
		gbc_servidorText.gridx = 1;
		gbc_servidorText.gridy = 1;
		contentPane.add(servidorText, gbc_servidorText);
		servidorText.setColumns(10);
		
		PuertoLabel = new JLabel("Puerto:");
		PuertoLabel.setForeground(new Color(192, 192, 192));
		PuertoLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_PuertoLabel = new GridBagConstraints();
		gbc_PuertoLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_PuertoLabel.insets = new Insets(0, 0, 5, 5);
		gbc_PuertoLabel.gridx = 2;
		gbc_PuertoLabel.gridy = 1;
		contentPane.add(PuertoLabel, gbc_PuertoLabel);
		
		PuertoText = new JTextField();
		PuertoText.setCaretColor(new Color(255, 255, 255));
		PuertoText.setForeground(new Color(255, 255, 255));
		PuertoText.setBackground(new Color(128, 128, 128));
		GridBagConstraints gbc_PuertoText = new GridBagConstraints();
		gbc_PuertoText.fill = GridBagConstraints.HORIZONTAL;
		gbc_PuertoText.insets = new Insets(0, 0, 5, 5);
		gbc_PuertoText.gridx = 3;
		gbc_PuertoText.gridy = 1;
		contentPane.add(PuertoText, gbc_PuertoText);
		PuertoText.setColumns(10);
		
		LabelNick = new JLabel("Nick:");
		LabelNick.setForeground(new Color(192, 192, 192));
		LabelNick.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_LabelNick = new GridBagConstraints();
		gbc_LabelNick.fill = GridBagConstraints.HORIZONTAL;
		gbc_LabelNick.insets = new Insets(0, 0, 5, 5);
		gbc_LabelNick.gridx = 4;
		gbc_LabelNick.gridy = 1;
		contentPane.add(LabelNick, gbc_LabelNick);
		NickText = new JTextField();
		NickText.setCaretColor(new Color(255, 255, 255));
		NickText.setForeground(new Color(255, 255, 255));
		NickText.setBackground(new Color(128, 128, 128));
		GridBagConstraints gbc_NickText = new GridBagConstraints();
		gbc_NickText.weightx = 1.0;
		gbc_NickText.weighty = 1.0;
		gbc_NickText.anchor = GridBagConstraints.WEST;
		gbc_NickText.insets = new Insets(0, 0, 5, 5);
		gbc_NickText.gridx = 5;
		gbc_NickText.gridy = 1;
		contentPane.add(NickText, gbc_NickText);
		NickText.setColumns(10);
		
		btnConectar = new JButton("Conectar");
		btnConectar.setBackground(new Color(172, 170, 210));
		btnConectar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cliente.conectar(v);
				v.setTitle("Cliente Chat Conectado: " + cliente.getNick());
				HiloCliente hilo1=new HiloCliente(v, cliente.getEntrada(), cliente);
				hilo1.start();
				}
			
		});
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if(!cliente.isDesconectado()) {
					try {
						cliente.setDesconestado(true);
						Mensaje desc=new Mensaje("Se desconecto el cliente con nick: " + cliente.getNick() + "\n", TipoMensaje.DESCONECTADO);
						cliente.getSalida().writeObject(desc);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					v.conectado=false;
				}
			}
		});
		GridBagConstraints gbc_btnConectar = new GridBagConstraints();
		gbc_btnConectar.anchor = GridBagConstraints.NORTH;
		gbc_btnConectar.insets = new Insets(0, 0, 5, 0);
		gbc_btnConectar.gridx = 6;
		gbc_btnConectar.gridy = 1;
		contentPane.add(btnConectar, gbc_btnConectar);
		scrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		GridBagConstraints gbc_scrollPane1 = new GridBagConstraints();
		gbc_scrollPane1.weighty = 1.0;
		gbc_scrollPane1.weightx = 1.0;
		gbc_scrollPane1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane1.insets = new Insets(50, 0, 5, 50);
		gbc_scrollPane1.gridheight = 2;
		gbc_scrollPane1.gridwidth = 6;
		gbc_scrollPane1.gridx = 0;
		gbc_scrollPane1.gridy = 2;
		contentPane.add(scrollPane1, gbc_scrollPane1);
		
		textAreaCliente = new JTextArea();
		textAreaCliente.setFont(new Font("Monospaced", Font.PLAIN, 15));
		textAreaCliente.setBackground(new Color(128, 128, 128));
		textAreaCliente.setSelectionColor(new Color(128, 0, 255));
		textAreaCliente.setForeground(new Color(255, 255, 255));
		textAreaCliente.setEditable(false);
		scrollPane1.setViewportView(textAreaCliente);
		textAreaCliente.setVisible(false);
		GridBagConstraints gbc_ConectadosLabel = new GridBagConstraints();
		gbc_ConectadosLabel.anchor = GridBagConstraints.SOUTH;
		gbc_ConectadosLabel.insets = new Insets(0, 0, 5, 0);
		gbc_ConectadosLabel.gridx = 6;
		gbc_ConectadosLabel.gridy = 2;
		contentPane.add(ConectadosLabel, gbc_ConectadosLabel);
		
		scrollPane2 = new JScrollPane();
		scrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane2.setVisible(false);
		GridBagConstraints gbc_scrollPane2 = new GridBagConstraints();
		gbc_scrollPane2.fill = GridBagConstraints.BOTH;
		gbc_scrollPane2.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane2.gridx = 6;
		gbc_scrollPane2.gridy = 3;
		contentPane.add(scrollPane2, gbc_scrollPane2);
		
		textAreaNicks = new JTextArea();
		textAreaNicks.setEditable(false);
		textAreaNicks.setBackground(Color.DARK_GRAY);
		textAreaNicks.setForeground(new Color(0, 255, 0));
		textAreaNicks.setSelectionColor(new Color(128, 0, 255));
		scrollPane2.setViewportView(textAreaNicks);
		textAreaNicks.setVisible(false);
		GridBagConstraints gbc_textFieldbajo = new GridBagConstraints();
		gbc_textFieldbajo.weightx = 1.0;
		gbc_textFieldbajo.fill = GridBagConstraints.BOTH;
		gbc_textFieldbajo.insets = new Insets(0, 0, 10, 5);
		gbc_textFieldbajo.gridwidth = 6;
		gbc_textFieldbajo.gridx = 0;
		gbc_textFieldbajo.gridy = 5;
		contentPane.add(textFieldbajo, gbc_textFieldbajo);
		textFieldbajo.setColumns(10);
		GridBagConstraints gbc_btnEnviar = new GridBagConstraints();
		gbc_btnEnviar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnEnviar.gridx = 6;
		gbc_btnEnviar.gridy = 5;
		contentPane.add(btnEnviar, gbc_btnEnviar);
	}
	

	public JPanel getContentPane() {
		return contentPane;
	}


	public JTextField getServidorText() {
		return servidorText;
	}



	public JTextField getPuertoText() {
		return PuertoText;
	}


	public JTextField getNickText() {
		return NickText;
	}

	public JButton getBtnConectar() {
		return btnConectar;
	}


	public JTextArea getTextAreaNicks() {
		return textAreaNicks;
	}


	public JTextArea getTextAreaCliente() {
		return textAreaCliente;
	}

	public JLabel getConectadosLabel() {
		return ConectadosLabel;
	}



	public JTextField getTextFieldbajo() {
		return textFieldbajo;
	}

	
	public JLabel getServidorLabel() {
		return ServidorLabel;
	}


	public JLabel getPuertoLabel() {
		return PuertoLabel;
	}

	
	public JLabel getLabelNick() {
		return LabelNick;
	}


	public JButton getBtnEnviar() {
		return btnEnviar;
	}


	public JScrollPane getScrollPane1() {
		return scrollPane1;
	}


	public JScrollPane getScrollPane2() {
		return scrollPane2;
	}

}
