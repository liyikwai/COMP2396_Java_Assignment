import java.io.IOException;
import java.net.ServerSocket;

/**
 * This is the server class.
 * 
 * @author liyikwai
 *
 */
public class TicTacToeServer {

	/**
	 * The main method is to create a server.
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		System.out.println("Server is Running...");
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				System.out.println("Server Stopped.");
			}
		}));

		try (var listener = new ServerSocket(58901)) {
			Server myServer = new Server(listener);
			myServer.start();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
