import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JPanel;

/**
 * This is the controller class. Its functionalities are to set up buttons in
 * view of GUI and receive response from the server.
 * 
 * @author liyikwai 3035566015
 *
 */
public class Controller extends JPanel {

	private View view;
	private Socket socket;
	private Scanner in;
	private PrintWriter out;
	private int index;
	private ActionListener submitListener;
	private ActionListener exitListener;
	private boolean set = false;

	/**
	 * This is the constructor of the Controller class.
	 * 
	 * @param view
	 * @param index
	 */
	public Controller(View view, int index) {
		this.view = view;
		this.index = index;
	}

	/**
	 * This start() method is to connect to the server via socket.
	 */
	public void start() {
		try {
			this.socket = new Socket("127.0.0.1", 58901);
			this.in = new Scanner(socket.getInputStream());
			this.out = new PrintWriter(socket.getOutputStream(), true);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Creates a new Thread for reading server messages
		Thread handler = new ClinetHandler(socket);
		handler.start();
	}

	/**
	 * This is the ClientHandler class. Its functionalities are to adjust the view
	 * of GUI according to the response from the server
	 * 
	 * @author liyikwai
	 *
	 */
	class ClinetHandler extends Thread {
		private Socket socket;

		/**
		 * This is the constructor of the ClientHandler class.
		 * 
		 * @param socket
		 */
		public ClinetHandler(Socket socket) {
			this.socket = socket;
		}

		/**
		 * This run() method is to read information from the server.
		 */
		@Override
		public void run() {
			try {
				readFromServer();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		/**
		 * The readFromServer() method is to receive messages from the server and then
		 * give response accordingly.
		 * 
		 * @throws Exception
		 */
		public void readFromServer() throws Exception {
			try {
				while (in.hasNextLine()) {

					var command = in.nextLine();
					System.out.println("Client Received: " + command);
					out.flush();
					if (command.charAt(0) == 'O') {
						int r = command.charAt(1) - '0';
						int c = command.charAt(2) - '0';
						view.DrawCircle(r, c);
					}
					if (command.charAt(0) == 'X') {
						int r = command.charAt(1) - '0';
						int c = command.charAt(2) - '0';
						view.DrawCross(r, c);
					}

					char r = (char) (index + '0');

					if (command.charAt(0) == 'F' && r == '1') {
						view.getLabel().setText("Valid move, wait for your opponent.");
					}

					if (command.charAt(0) == 'F' && r == '2') {
						view.getLabel().setText("Your opponent has moved, now is your turn.");
					}

					if (command.charAt(0) == 'S' && r == '1') {
						view.getLabel().setText("Your opponent has moved, now is your turn.");
					}

					if (command.charAt(0) == 'S' && r == '2') {
						view.getLabel().setText("Valid move, wait for your opponent.");
					}

					if (command.charAt(0) == '1' && r == '1') {
						view.win();
					}

					if (command.charAt(0) == '1' && r == '2') {
						view.lose();
					}
					if (command.charAt(0) == '2' && r == '1') {
						view.lose();
					}

					if (command.charAt(0) == '2' && r == '2') {
						view.win();
					}

					if (command.charAt(0) == '9') {
						view.draw();
					}
					if (command.charAt(0) == 'L') {
						view.left();
					}

					if (command.charAt(0) == 'E' && r != command.charAt(1)) {
						view.left();
					}

					if (command.startsWith("P1") && set == false) {
						setIndex(1);
					}

					if (command.startsWith("P2") && set == false) {
						setIndex(2);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				socket.close();
			}
		}

	}

	/**
	 * The setIndex() method is to set the index of the controller so that the
	 * server can distinguish each client.
	 * 
	 * @param i
	 */
	public void setIndex(int i) {
		index = i;
		set = true;
		setButton(i);
	}

	/**
	 * The setButton() method is to set the buttons for the view of GUI. If the
	 * index is 1, the buttons are responsible for drawing X. If the index is 2, the
	 * buttons are responsible for drawing O.
	 * 
	 * @param i
	 */
	public void setButton(int i) {
		if (index == 1) {
			MouseAdapter X00 = new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					if (view.getStart() == true) {
						out.println("X00");
						System.out.println("Client Sent: " + "X00");
						repaint();
					}

				}
			};

			MouseAdapter X01 = new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					if (view.getStart() == true) {
						out.println("X01");
						System.out.println("Client Sent: " + "X01");
						repaint();
					}

				}
			};

			MouseAdapter X02 = new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					if (view.getStart() == true) {
						out.println("X02");
						System.out.println("Client Sent: " + "X02");
						repaint();
					}

				}
			};
			MouseAdapter X10 = new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					if (view.getStart() == true) {
						out.println("X10");
						System.out.println("Client Sent: " + "X10");
						repaint();
					}

				}
			};
			MouseAdapter X11 = new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					if (view.getStart() == true) {
						out.println("X11");
						System.out.println("Client Sent: " + "X11");
						repaint();
					}

				}
			};
			MouseAdapter X12 = new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					if (view.getStart() == true) {
						out.println("X12");
						System.out.println("Client Sent: " + "X12");
						repaint();
					}

				}
			};
			MouseAdapter X20 = new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					if (view.getStart() == true) {
						out.println("X20");
						System.out.println("Client Sent: " + "X20");
						repaint();
					}

				}
			};
			MouseAdapter X21 = new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					if (view.getStart() == true) {
						out.println("X21");
						System.out.println("Client Sent: " + "X21");
						repaint();
					}

				}
			};
			MouseAdapter X22 = new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					if (view.getStart() == true) {
						out.println("X22");
						System.out.println("Client Sent: " + "X22");
						repaint();
					}

				}
			};
			view.getDrawPanel(0, 0).addMouseListener(X00);
			view.getDrawPanel(0, 1).addMouseListener(X01);
			view.getDrawPanel(0, 2).addMouseListener(X02);
			view.getDrawPanel(1, 0).addMouseListener(X10);
			view.getDrawPanel(1, 1).addMouseListener(X11);
			view.getDrawPanel(1, 2).addMouseListener(X12);
			view.getDrawPanel(2, 0).addMouseListener(X20);
			view.getDrawPanel(2, 1).addMouseListener(X21);
			view.getDrawPanel(2, 2).addMouseListener(X22);

			submitListener = new ActionListener() {
				public void actionPerformed(ActionEvent actionEvent) {
					out.println("submit");
					System.out.println("Client Sent: Submit");
				}
			};
			view.getSubmit().addActionListener(submitListener);

			exitListener = new ActionListener() {
				public void actionPerformed(ActionEvent actionEvent) {
					view.getFrame().dispose();
					out.println("E" + index);
					System.out.println("Client Sent: exit");
				}
			};
			view.getExit().addActionListener(exitListener);
		}

		else if (index == 2) {
			MouseAdapter O00 = new MouseAdapter() {

				@Override
				public void mousePressed(MouseEvent e) {
					if (view.getStart() == true) {
						out.println("O00");
						System.out.println("Client Sent: " + "O00");
						repaint();
					}

				}
			};

			MouseAdapter O01 = new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					if (view.getStart() == true) {
						out.println("O01");
						System.out.println("Client Sent: " + "O01");
						repaint();
					}

				}
			};

			MouseAdapter O02 = new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					if (view.getStart() == true) {
						out.println("O02");
						System.out.println("Client Sent: " + "O02");
						repaint();
					}

				}
			};
			MouseAdapter O10 = new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					if (view.getStart() == true) {
						out.println("O10");
						System.out.println("Client Sent: " + "O10");
						repaint();
					}

				}
			};
			MouseAdapter O11 = new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					if (view.getStart() == true) {
						out.println("O11");
						System.out.println("Client Sent: " + "O11");
						repaint();
					}

				}
			};
			MouseAdapter O12 = new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					if (view.getStart() == true) {
						out.println("O12");
						System.out.println("Client Sent: " + "O12");
						repaint();
					}

				}
			};
			MouseAdapter O20 = new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					if (view.getStart() == true) {
						out.println("O20");
						System.out.println("Client Sent: " + "O20");
						repaint();
					}

				}
			};
			MouseAdapter O21 = new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					if (view.getStart() == true) {
						out.println("O21");
						System.out.println("Client Sent: " + "O21");
						repaint();
					}

				}
			};
			MouseAdapter O22 = new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					if (view.getStart() == true) {
						out.println("O22");
						System.out.println("Client Sent: " + "O22");
						repaint();
					}

				}
			};
			view.getDrawPanel(0, 0).addMouseListener(O00);
			view.getDrawPanel(0, 1).addMouseListener(O01);
			view.getDrawPanel(0, 2).addMouseListener(O02);
			view.getDrawPanel(1, 0).addMouseListener(O10);
			view.getDrawPanel(1, 1).addMouseListener(O11);
			view.getDrawPanel(1, 2).addMouseListener(O12);
			view.getDrawPanel(2, 0).addMouseListener(O20);
			view.getDrawPanel(2, 1).addMouseListener(O21);
			view.getDrawPanel(2, 2).addMouseListener(O22);

			submitListener = new ActionListener() {
				public void actionPerformed(ActionEvent actionEvent) {
					out.println("submit");
					System.out.println("Client Sent: Submit");
				}
			};
			view.getSubmit().addActionListener(submitListener);

			exitListener = new ActionListener() {
				public void actionPerformed(ActionEvent actionEvent) {
					view.getFrame().dispose();
					out.println("E" + index);
					System.out.println("Client Sent: exit");
				}
			};
			view.getExit().addActionListener(exitListener);
		}
	}
}
