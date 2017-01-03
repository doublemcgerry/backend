package websocketecho;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;

public class ServerMain {

	public static void main(String[] args) throws IOException {
		InetSocketAddress listenAddress = new InetSocketAddress("0.0.0.0", 8091);
		Server server = new Server(listenAddress);
		server.start();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter command\n");
		String s = null;

		while (!(s = br.readLine()).isEmpty()) {
			if ("stop".equals(s)) {
				try {
					server.stop();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.exit(0);
			}
		}

	}

}
