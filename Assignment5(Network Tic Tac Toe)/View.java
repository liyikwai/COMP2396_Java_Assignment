import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * This is the View class. Its functionality is to provide a GUI interface.
 * 
 * @author liyikwai
 *
 */
public class View {

	private JFrame frame;

	private JLabel info;

	private JButton submit;
	private JMenuItem exit;
	private DrawPanel[][] DrawPanels;
	private int index;
	private boolean start = false;

	/**
	 * This is the constructor of the View class.
	 * 
	 * @param index
	 */
	public View(int index) {
		this.index = index;
		setTable();
	}

	/**
	 * The setTable() method is to set a chess board GUI.
	 */
	private void setTable() {
		frame = new JFrame();
		JTextField inputName = new JTextField(10);
		info = new JLabel("Enter your player name...");
		submit = new JButton("Submit");
		JPanel GamePanel = new JPanel();
		JMenuBar menuBar = new JMenuBar();
		JMenu control = new JMenu("Control");
		JMenu help = new JMenu("Help");
		exit = new JMenuItem("Exit");
		JMenuItem instruction = new JMenuItem("Instruction");

		control.add(exit);
		help.add(instruction);
		menuBar.add(control);
		menuBar.add(help);

		DrawPanels = new DrawPanel[3][3];

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				DrawPanels[i][j] = new DrawPanel();
				DrawPanels[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
			}
		}

		GamePanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 3;
		GamePanel.add(info, c);
		c.ipadx = 100;
		c.ipady = 100;
		c.gridy = 1;
		c.gridx = 0;
		c.gridwidth = 1;
		GamePanel.add(DrawPanels[0][0], c);
		c.gridx = 1;
		GamePanel.add(DrawPanels[0][1], c);
		c.gridx = 2;
		GamePanel.add(DrawPanels[0][2], c);
		c.gridy = 2;
		c.gridx = 0;
		GamePanel.add(DrawPanels[1][0], c);
		c.gridx = 1;
		GamePanel.add(DrawPanels[1][1], c);
		c.gridx = 2;
		GamePanel.add(DrawPanels[1][2], c);
		c.gridy = 3;
		c.gridx = 0;
		GamePanel.add(DrawPanels[2][0], c);
		c.gridx = 1;
		GamePanel.add(DrawPanels[2][1], c);
		c.gridx = 2;
		GamePanel.add(DrawPanels[2][2], c);
		c.ipadx = 0;
		c.ipady = 0;
		c.insets = new Insets(5, 5, 5, 5);
		c.gridy = 4;
		c.gridx = 0;
		c.gridwidth = 2;
		GamePanel.add(inputName, c);
		c.gridx = 2;
		c.gridwidth = 1;
		GamePanel.add(submit, c);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.add(info);
		frame.getContentPane().add(GamePanel);
		frame.setJMenuBar(menuBar);
		frame.setTitle("Tic Tac Toe");
		frame.pack();
		frame.setSize(350, 450);
		frame.setVisible(true);

		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (inputName.getText().isBlank() == false) {
					String name = inputName.getText();
					frame.setTitle("Tic Tac Toe-Player:" + name);
					info.setText("WELCOME " + name);
					start = true;
					submit.setEnabled(false);
				}
			}
		});

		instruction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, "Some information about the game:\n"
						+ "Criteria for a valid move: \n" + "-The move is not occupied by any mark.\n"
						+ "-The move is made in the player's turn. \n" + "-The move is made within the 3 x 3 board. \n"
						+ "-The game would continue and switch among the opposite player until it reaches either one of the following conditions: \n"
						+ "Player 1 wins.\n" + "Player 2 wins.\n" + "Draw.");
			}
		});

		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
	}

	/**
	 * This is the DrawPanel class. Its functionalities are to create a JPanel that
	 * can be drawn a circle or a cross.
	 * 
	 * @author liyikwai
	 *
	 */
	public class DrawPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private boolean circle = false;
		private boolean cross = false;

		/**
		 * This is the constructor of the DrawPanel class.
		 */
		public DrawPanel() {
			setBackground(Color.WHITE);
			addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					repaint();
				}
			});
		}

		/**
		 * The paintComponent() method is to draw a circle or a cross.
		 */
		@Override
		public void paintComponent(Graphics g) {
			int thickness = 30;
			super.paintComponent(g);
			this.setBackground(Color.WHITE);
			Graphics2D g2 = (Graphics2D) g;
			if (circle) {
				g2.setColor(Color.RED);
				g2.setStroke(new BasicStroke(3.0f));
				g2.drawOval(35, 35, 40, 40);
				g2.setColor(Color.WHITE);
				g2.drawOval(35 + thickness, 35 + thickness, 20 - 2 * thickness, 20 - 2 * thickness);
			}
			if (cross) {
				g2.setColor(Color.GREEN);
				g2.setStroke(new BasicStroke(3.0f));
				g2.drawLine(35, 35, 80, 80);
				g2.drawLine(35, 80, 80, 35);
			}
		}

		/**
		 * The setCircle() method is to alter the boolean variable circle to true and
		 * then repaint.
		 */
		public void setCircle() {
			this.circle = true;
			repaint();
		}

		/**
		 * The setCircle() method is to alter the boolean variable cross to true and
		 * then repaint.
		 */
		public void setCross() {
			this.cross = true;
			repaint();
		}

	}

	/**
	 * The getStart() method is to return the value of start.
	 * 
	 * @return start
	 */
	public boolean getStart() {
		return start;
	}

	/**
	 * The getSubmit() method is to return the submit button.
	 * 
	 * @return submit
	 */
	public JButton getSubmit() {
		return submit;
	}

	/**
	 * The getExit() method is to return the exit menu item.
	 * 
	 * @return exit
	 */
	public JMenuItem getExit() {
		return exit;
	}

	/**
	 * The getLabel() method is to return the info label.
	 * 
	 * @return info
	 */
	public JLabel getLabel() {
		return info;
	}

	/**
	 * The getFrame() method is to return the frame.
	 * 
	 * @return frame
	 */
	public JFrame getFrame() {
		return frame;
	}

	/**
	 * The getDrawPanel() method is to return the DrawPanels.
	 * 
	 * @return DrawPanels[i][j];
	 */
	public DrawPanel getDrawPanel(int i, int j) {
		return DrawPanels[i][j];
	}

	/**
	 * The DrawCircle() method is to draw circle.
	 * 
	 */
	public void DrawCircle(int r, int c) {
		DrawPanels[r][c].setCircle();
	}

	/**
	 * The DrawCross() method is to draw cross.
	 * 
	 */
	public void DrawCross(int r, int c) {
		DrawPanels[r][c].setCross();
	}

	/**
	 * The win() method is to pop up the win message.
	 * 
	 */
	public void win() {
		JOptionPane.showMessageDialog(frame, "Congratulations. You Win.");
	}

	/**
	 * The lose() method is to pop up the lose message.
	 * 
	 */
	public void lose() {
		JOptionPane.showMessageDialog(frame, "You lose.");
	}

	/**
	 * The draw() method is to pop up the draw message.
	 * 
	 */
	public void draw() {
		JOptionPane.showMessageDialog(frame, "Draw.");
	}

	/**
	 * The left() method is to pop up the left message.
	 * 
	 */
	public void left() {
		JOptionPane.showMessageDialog(frame, "Game Ends. One of the players left.");
	}
}
