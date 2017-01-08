package udpdiscovery;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

import gui.frames.PrimaSchermata;
import interfaces.MainServerLoggerInterface;

public class DiscoveryThread implements Runnable {

	private DatagramSocket socket;
	private boolean stop=false;
	private MainServerLoggerInterface writer;

	@Override
	public void run() {
		try {
			// Keep a socket open to listen to all the UDP trafic that is
			// destined for this port
			socket = new DatagramSocket(8091, InetAddress.getByName("0.0.0.0"));
			socket.setBroadcast(true);

			while (!stop) {
				writer.logDiscoveryServerActivity("I am Ready to receive broadcast packets!");

				// Receive a packet
				byte[] recvBuf = new byte[15000];
				DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
				socket.receive(packet);

				// Packet received
				writer.logDiscoveryServerActivity("Discovery packet received from: "+ packet.getAddress().getHostAddress());
				writer.logDiscoveryServerActivity("Packet received; data: " + new String(packet.getData()));

				// See if the packet holds the right command (message)
				String message = new String(packet.getData()).trim();
				if (message.equals("DISCOVER_AUISERVER_REQUEST")) {
					byte[] sendData = "DISCOVER_AUISERVER_REQUEST".getBytes();

					// Send a response
					DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, packet.getAddress(),
							packet.getPort());
					socket.send(sendPacket);
					
					writer.logDiscoveryServerActivity("Sent packet to: " + sendPacket.getAddress().getHostAddress());
				}
			}
		} catch (IOException ex) {
			Logger.getLogger(DiscoveryThread.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public void setWriter(MainServerLoggerInterface frame){
		this.writer=frame;
	}
	
	public void stopThread(){
		writer.logDiscoveryServerActivity("I am shutting down");
		this.stop=true;
		this.socket.close();
	}

	public static DiscoveryThread getInstance() {
		return DiscoveryThreadHolder.INSTANCE;
	}

	private static class DiscoveryThreadHolder {
		
		private static final DiscoveryThread INSTANCE = new DiscoveryThread();
	}

}
