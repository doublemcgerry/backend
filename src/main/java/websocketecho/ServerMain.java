package websocketecho;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;

import udpdiscovery.DiscoveryThread;
import utility.FakeWriter;

public class ServerMain {

	public static void main(String[] args) throws IOException {
		InetSocketAddress listenAddress = new InetSocketAddress("0.0.0.0", 8091);
		DiscoveryThread serverThread = DiscoveryThread.getInstance();
		FakeWriter fake = new FakeWriter();
		Server server = new Server(listenAddress,fake,fake);
		serverThread.setWriter(fake);
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
