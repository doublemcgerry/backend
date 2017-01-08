package gui.frames;

import java.awt.Button;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;

import gui.buttons.SimpleButton;
import gui.listener.MyListener;
import interfaces.LobbyLoggerInterface;
import interfaces.LobbyManagerInterface;
import interfaces.MainServerLoggerInterface;
import udpdiscovery.DiscoveryThread;
import websocketecho.Server;

/**
 * Schermata iniziale di gioco. Visualizza un'immagine di sfondo sulla sinistra e due bottoni sulla destra che permettono
 * di iniziare la partita o di leggere le istruzioni di gioco. C'è inoltre una canzone di sottofondo che parte alla
 * visualizzazione della schermata.
 * 
 * @author Andrea
 *
 */
public class PrimaSchermata extends JFrame implements MainServerLoggerInterface,LobbyManagerInterface{
	
	private static final long serialVersionUID = 1L;
	private JPanel pannello;
	private final int ALTEZZA= 600;
	private final int LARGHEZZA=1000;
	private SimpleButton startServerButton;
	private SimpleButton stopServerButton;
	private Border bordoBottone= BorderFactory.createLineBorder(Color.DARK_GRAY, 2);
	private JList list;
	private JTextArea mainServerTextField;
	private JTextArea udpServerTextField;
	
	private InetSocketAddress listenAddress;
	private DiscoveryThread serverThread;
	private Server server;
	
	
	/**
	 * Costruttore. Chiama i metodi che caricano le immagini e posizionano gli elementi sul frame e fa partire la canzone
	 * di sottofondo. 
	 */
	public PrimaSchermata(){
		inizialize();
		inizializeGUI(new MyListener(this));
	}
	
	/**
	 * Carica l'immagine di sfondo e l'icona del frame
	 */
	public void inizialize(){
		listenAddress = new InetSocketAddress("0.0.0.0", 8091);
		serverThread = DiscoveryThread.getInstance();
		serverThread.setWriter(this);
		server = new Server(listenAddress,this,this);
	}
	
	/**
	 * Posiziona e inizializza i bottoni presenti nel frame e i pannelli che li contengono. Crea un bottone per leggere
	 * le istruzioni e uno per iniziare la partita e gli associa i rispettivi listeners. Poi modifica il puntatore del
	 * mouse.
	 * 
	 * @param ascoltatore il listener da associare ai bottoni
	 */
	public void inizializeGUI(MyListener ascoltatore){
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Sever Manager for VR" );
		setResizable(false);
		pannello= new JPanel(null);
		setContentPane(pannello);
		setBackground(Color.BLACK);
		setLayout(null);
		setSize(LARGHEZZA, ALTEZZA);
		startServerButton= new SimpleButton("startServer", "Start");
		stopServerButton= new SimpleButton("stopServer", "Stop");
		startServerButton.setBounds(785, 190, 200, 40);
		stopServerButton.setBounds(835, 300, 100, 40);
		startServerButton.setVisible(true);
		stopServerButton.setVisible(true);
		startServerButton.addMouseListener(ascoltatore);
		stopServerButton.addMouseListener(ascoltatore);
		startServerButton.setBorder(bordoBottone);
		stopServerButton.setBorder(bordoBottone);
		startServerButton.setBackground(Color.BLACK);
		stopServerButton.setBackground(Color.BLACK);
		startServerButton.setForeground(Color.WHITE);
		stopServerButton.setForeground(Color.WHITE);
		pannello.add(stopServerButton);
		pannello.add(startServerButton);
		createListPanel();
		createMainServerTextPain();
		createServerTextPain();
	}
	
	public void startServer(){
		server.run();
		serverThread.run();
	}
	
	public void stopServer(){
		try {
			server.stop();
			serverThread.stopThread();
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void createListPanel(){
		DefaultListModel objectList = new DefaultListModel();
		list = new JList(objectList); //data has type Object[]
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list.setVisibleRowCount(-1);
		list.setVisible(true);
		
		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(250, 80));
		listScroller.setVisible(true);
		listScroller.setBounds(20, 20, 700, 200);
		
		this.add(listScroller);
	}
	
	@Override
	public void logMainServerActivity(String text){
		mainServerTextField.append("\n"+text);
	}
	
	@Override
	public void logDiscoveryServerActivity(String text){
		udpServerTextField.append("\n"+text);
	}
	
	private void createMainServerTextPain(){
		mainServerTextField = new JTextArea("Main Server Text Area");
		mainServerTextField.setVisible(true);
		mainServerTextField.setEditable(false);
		
		JScrollPane listScroller = new JScrollPane(mainServerTextField);
		listScroller.setVisible(true);
		listScroller.setBounds(20, 300, 700, 110);
		this.add(listScroller);
	}
	
	private void createServerTextPain(){
		udpServerTextField = new JTextArea();
		udpServerTextField.setVisible(true);
		udpServerTextField.setEditable(false);
		
		JScrollPane listScroller = new JScrollPane(udpServerTextField);
		listScroller.setVisible(true);
		listScroller.setBounds(20, 420, 700, 110);
		this.add(listScroller);
	}

	@Override
	public LobbyLoggerInterface createNewLobby(String lobbyToAdd) {
		DefaultListModel<String> model = (DefaultListModel<String>) this.list.getModel();
		model.addElement(lobbyToAdd);
		list.setModel(model);
		//TODO da sistemare
		return null;
	}
}
