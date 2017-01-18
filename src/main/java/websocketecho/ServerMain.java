package websocketecho;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.SimpleWebServer;
import udpdiscovery.DiscoveryThread;
import udpdiscovery.UDPDiscoveryServer;
import utility.FakeWriter;

public class ServerMain {

	public static void main(String[] args) throws IOException {
		SimpleWebServer ws= new SimpleWebServer("0.0.0.0", 8099, new File("../AUIVRBabylon/"), true);
		ws.start();
		InetSocketAddress listenAddress = new InetSocketAddress("0.0.0.0", 8091);
		UDPDiscoveryServer discoveryServer =new UDPDiscoveryServer(new UDPDiscoveryServer.Callbacks() {
			
			@Override
			public void onServerMessage(String message) {
				System.out.println("DS:" + message);
			}
			
			@Override
			public void onClientFound(InetAddress address) {
				System.out.println("DS:New Client found at address " + address.toString());
			}
		});
		FakeWriter fake = new FakeWriter();
		Server server = new Server(listenAddress,fake,fake);
		
		server.start();
		discoveryServer.run();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter command\n");
		String s = null;

		while (!(s = br.readLine()).isEmpty()) {
			if ("stop".equals(s)) {
				try {
					server.stop();
					//serverThread.stopThread();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.exit(0);
			}
		}

	}

}
