/**
 * This is the SharedGame class. It uses a 3X3 arrays to store the information
 * of chess board.
 * 
 * @author liyikwai
 *
 */
public class SharedGame {
	private final Object lock = new Object();
	private int n;
	private boolean over = false;
	private int[][] table;

	private int count;

	/**
	 * This is the constructor of the SharedGame class.
	 */
	public SharedGame() {
		this.n = 0;
		this.table = new int[][] { { 0, 0, 0 }, { 0, 0, 0 }, { 0, 0, 0 } };

	}

	/**
	 * The up() method is to count the number of steps,
	 */
	public void up() {
		synchronized (lock) {
			n++;
		}
	}

	/**
	 * The checkResult() method is to check the result of the game. There are 3
	 * results: Win; Lose; Draw.
	 * 
	 * @return
	 */
	public int checkResult() {
		synchronized (lock) {

			if (table[0][0] == table[0][1] && table[0][1] == table[0][2]) {
				int win = table[0][0];
				return win;
			} else if (table[1][0] == table[1][1] && table[1][1] == table[1][2]) {
				int win = table[1][0];
				return win;
			} else if (table[2][0] == table[2][1] && table[2][1] == table[2][2]) {
				int win = table[2][0];
				return win;
			} else if (table[0][0] == table[1][0] && table[1][0] == table[2][0]) {
				int win = table[0][0];
				return win;
			} else if (table[0][1] == table[1][1] && table[1][1] == table[2][1]) {
				int win = table[0][1];
				return win;
			} else if (table[0][2] == table[1][2] && table[1][2] == table[2][2]) {
				int win = table[0][2];
				return win;
			} else if (table[0][0] == table[1][1] && table[1][1] == table[2][2]) {
				int win = table[0][0];
				return win;
			} else if (table[0][2] == table[1][1] && table[1][1] == table[2][0]) {
				int win = table[0][2];
				return win;
			}

			return 0;
		}

	}

	/**
	 * The circle() method is to change the element in the array to 1 which stands
	 * for drawing circle.
	 * 
	 * @param r
	 * @param c
	 */
	public void circle(int r, int c) {
		synchronized (lock) {
			table[r][c] = 1;
		}
	}

	/**
	 * The cross() method is to change the element in the array to 2 which stands
	 * for drawing cross.
	 * 
	 * @param r
	 * @param c
	 */
	public void cross(int r, int c) {
		synchronized (lock) {
			table[r][c] = 2;
		}
	}

	/**
	 * This is the getter of the integer n.
	 * 
	 * @return n
	 */
	public int getNumber() {
		synchronized (lock) {
			return n;
		}
	}

	/**
	 * This is the getter of the 3X3 array.
	 * 
	 * @return int[][]
	 */
	public int[][] getTable() {
		synchronized (lock) {
			return table;
		}
	}

	/**
	 * The getValid() method check whether the chess board is occupied.
	 * 
	 * @param r
	 * @param c
	 * @return table[r][c]
	 */
	public int getValid(int r, int c) {
		synchronized (lock) {
			return table[r][c];
		}
	}

	/**
	 * The reset() method is to initialize the 3X3 array table as well as the
	 * integer n.
	 */
	public void reset() {
		synchronized (lock) {
			table = new int[][] { { 0, 0, 0 }, { 0, 0, 0 }, { 0, 0, 0 } };
			n = 0;
			return;
		}
	}
}
