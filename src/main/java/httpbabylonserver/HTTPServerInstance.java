package httpbabylonserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;

public class HTTPServerInstance {
	
	public void runServer() throws IOException{
		int port = 8091;
		HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
		System.out.println("server started at " + port);
		server.createContext("/", new RootHandler());
		server.setExecutor(null); // creates a default executor
	    server.start();
	}

}
