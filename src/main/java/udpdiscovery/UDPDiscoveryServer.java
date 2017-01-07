package udpdiscovery;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPDiscoveryServer extends Thread {
	
	private Callbacks mCallbacks;
	
	public interface Callbacks{
		void onServerMessage(String message);
		void onClientFound(InetAddress address);
	}
	
	public UDPDiscoveryServer(Callbacks mCallbacks){
		this.mCallbacks=mCallbacks;
	}
	private DatagramSocket socket;
	private boolean stop = false;

	@Override
	public void run() {
		// Keep a socket open to listen to all the UDP trafic that is
		// destined for this port
		try {
			socket = new DatagramSocket(8091, InetAddress.getByName("0.0.0.0"));
			socket.setBroadcast(true);
		} catch (SocketException | UnknownHostException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		

		while (!stop) {
			mCallbacks.onServerMessage("I am Ready to receive broadcast packets!");

			// Receive a packet
			byte[] recvBuf = new byte[15000];
			DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
			try {
				socket.receive(packet);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// Packet received
			mCallbacks.onServerMessage("Discovery packet received from: " + packet.getAddress().getHostAddress());
			mCallbacks.onServerMessage("Packet received; data: " + new String(packet.getData()));

			// See if the packet holds the right command (message)
			String message = new String(packet.getData()).trim();
			if (message.equals("DISCOVER_AUISERVER_REQUEST")) {
				byte[] sendData = "DISCOVER_AUISERVER_REQUEST".getBytes();

				// Send a response
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, packet.getAddress(),
						packet.getPort());
				try {
					socket.send(sendPacket);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				mCallbacks.onServerMessage("Sent packet to: " + sendPacket.getAddress().getHostAddress());
				mCallbacks.onClientFound(sendPacket.getAddress());
			}
		}
	}
}
