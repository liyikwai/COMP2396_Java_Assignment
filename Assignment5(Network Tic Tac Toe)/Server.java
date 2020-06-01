import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.Executors;

/**
 * This is the Server class. Its functionalities are to receive requests from
 * the clients and give responses.
 * 
 * @author liyikwai
 *
 */
public class Server {
	private ServerSocket serverSocket;
	private SharedGame game;
	// The set of all the print writers for all the clients, used for broadcast.
	private Set<PrintWriter> writers = new HashSet<>();
	private int valid = 1;
	private boolean start = false;
	private boolean end = false;
	private boolean left = false;
	private boolean reset = false;
	private int sub;
	private int num;

	/**
	 * This is the constructor of the Server class.
	 * 
	 * @param serverSocket
	 */
	public Server(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
		this.game = new SharedGame();
	}

	/**
	 * The start() method is to connect each client via socket.
	 */
	public void start() {
		var pool = Executors.newFixedThreadPool(200);
		int clientCount = 0;
		while (true) {
			try {
				Socket scoket = serverSocket.accept();
				pool.execute(new Handler(scoket));
				System.out.println("Connected to client " + clientCount++);
				num++;
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * The is the Handler class. Its functionalities are to process the requests
	 * from the client and then send response to them.
	 * 
	 * @author liyikwai
	 *
	 */
	public class Handler implements Runnable {
		private Socket socket;
		private Scanner input;
		private PrintWriter output;

		/**
		 * This is the constructor of the Handler class.
		 * 
		 * @param socket
		 */
		public Handler(Socket socket) {
			this.socket = socket;
		}

		/**
		 * The run() method is to analyze the requests sent by clients and give them
		 * responses respectively.
		 */
		@Override
		public void run() {
			System.out.println("Connected: " + socket);
			try {
				input = new Scanner(socket.getInputStream());
				output = new PrintWriter(socket.getOutputStream(), true);

				// add this client to the broadcast list
				writers.add(output);
				reset = false;
				for (PrintWriter writer : writers) {
					if (num == 1) {
						writer.println("P1");
					}
					if (num == 2) {
						writer.println("P2");
					}
				}
				while (input.hasNextLine()) {
					var command = input.nextLine();
					System.out.println("Server Received: " + command);
					if (command.startsWith("submit")) {
						sub++;
						if (num == 2 && sub == 2) {
							start = true;
							game.reset();
						}
					}

					if (num == 2 && end == false) {
						if (command.startsWith("E")) {
							start = false;
							left = true;
							num--;
							for (PrintWriter writer : writers) {
								writer.println(command);
							}
							start = false;
							end = false;
							valid = 1;
							sub = 0;
							num = 0;
							game.reset();
						}

					}

					if (start == true && end == false && writers.size() == 2) {
						if (command.startsWith("X") && valid == 1) {
							char row = command.charAt(1);
							char column = command.charAt(2);
							int r = row - '0';
							int c = column - '0';
							if (game.getValid(r, c) == 0) {
								game.circle(r, c);
								game.up();
								valid++;
								for (PrintWriter writer : writers) {
									int result = game.checkResult();
									System.out.println("Result: " + result);
									if (result == 1 || result == 2) {
										end = true;
									}
									writer.println('F');
									writer.println(command);
									writer.println(result);
									if (game.getNumber() == 9 && result == 0) {
										writer.println(game.getNumber());
										end = true;
									}
								}
								if (end == true) {
									reset();
								}
							}

						} else if (command.startsWith("O") && valid == 2) {
							char row = command.charAt(1);
							char column = command.charAt(2);
							int r = row - '0';
							int c = column - '0';
							if (game.getValid(r, c) == 0) {
								game.cross(r, c);
								game.up();
								valid--;
								for (PrintWriter writer : writers) {
									int result = game.checkResult();
									System.out.println("Result: " + result);
									if (result == 1 || result == 2) {
										end = true;
									}
									writer.println('S');
									writer.println(command);
									writer.println(result);
									if (game.getNumber() == 9 && result == 0) {
										writer.println(game.getNumber());
										end = true;
									}
								}
								if (end == true) {
									reset();
								}
							}
						}

						// broadcast updated number to all clients
						System.out.println("Server Broadcasted: " + Arrays.deepToString(game.getTable()));
					}
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			} finally {
				// client disconnected
				if (output != null) {
					writers.remove(output);
					if (end == false && left == false && reset == false) {
						for (PrintWriter writer : writers) {
							writer.println("L");
						}
					}
				}
				reset();
			}
		}

		/**
		 * The reset() method is to initialize the variables when the game ends or one
		 * of the client left.
		 */
		public void reset() {
			start = false;
			end = false;
			left = false;
			reset = true;
			valid = 1;
			sub = 0;
			num = 0;
			game.reset();
		}
	}

}
