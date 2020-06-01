import javax.swing.SwingUtilities;

/**
 * This is the client class.
 * 
 * @author liyikwai
 *
 */
public class TicTacToeClient {

	/**
	 * The main method is to create a client.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				View view = new View(0);
				Controller controller = new Controller(view, 0);
				controller.start();
			}
		});
	}
}
