package websocketecho;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;

import udpdiscovery.DiscoveryThread;

public class ServerMain {

	public static void main(String[] args) throws IOException {
		InetSocketAddress listenAddress = new InetSocketAddress("0.0.0.0", 8091);
		DiscoveryThread serverThread = DiscoveryThread.getInstance();
		Server server = new Server(listenAddress);
		server.start();
		serverThread.run();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter command\n");
		String s = null;

		while (!(s = br.readLine()).isEmpty()) {
			if ("stop".equals(s)) {
				try {
					server.stop();
					serverThread.stopThread();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.exit(0);
			}
		}

	}

}
