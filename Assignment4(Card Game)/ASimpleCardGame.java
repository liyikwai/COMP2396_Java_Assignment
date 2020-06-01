import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
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
 * COMP2396A-Assignment4
 * 
 * @author Li Yik Wai 3035566015
 */

/**
 * 
 * This is a class of ASimpleCardGame
 *
 */
public class ASimpleCardGame {
	double input = 0;
	int bet = 0;
	int budget = 100;
	int replaceTimes = 0;

	JLabel dealerCard1 = new JLabel();
	JLabel dealerCard2 = new JLabel();
	JLabel dealerCard3 = new JLabel();
	JLabel playerCard1 = new JLabel();
	JLabel playerCard2 = new JLabel();
	JLabel playerCard3 = new JLabel();

	JButton replaceCard1 = new JButton("Replace Card 1");
	JButton replaceCard2 = new JButton("Replace Card 2");
	JButton replaceCard3 = new JButton("Replace Card 3");

	JLabel showingBet = new JLabel("Bet: $");

	JTextField inputBet = new JTextField(10);

	JButton start = new JButton("Start");
	JButton result = new JButton("Result");

	JLabel displayMessage = new JLabel("Please place your bet!");
	JLabel displayRemain = new JLabel("Amount of money you have: $" + budget);

	ImageIcon d1 = new ImageIcon("card_back.gif");
	ImageIcon d2 = new ImageIcon("card_back.gif");
	ImageIcon d3 = new ImageIcon("card_back.gif");
	ImageIcon p1 = new ImageIcon("card_back.gif");
	ImageIcon p2 = new ImageIcon("card_back.gif");
	ImageIcon p3 = new ImageIcon("card_back.gif");

	ArrayList<Integer> ranks = new ArrayList<Integer>();
	ArrayList<Integer> numbers = new ArrayList<Integer>();
	ArrayList<String> cards = new ArrayList<String>();

	/**
	 * This is the Main methods of the program. It creates a ASimpleCardGame object
	 * and invokes its Go methods to start a new game.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ASimpleCardGame g = new ASimpleCardGame();
		g.go();
	}

	/**
	 * This method sets up the frame of the card game and then runs the game with
	 * many functionalities.
	 */
	public void go() {
		dealerCard1.setIcon(d1);
		dealerCard2.setIcon(d2);
		dealerCard3.setIcon(d3);
		playerCard1.setIcon(p1);
		playerCard2.setIcon(p2);
		playerCard3.setIcon(p3);

		JPanel MainPanel = new JPanel();
		JPanel DealerPanel = new JPanel();
		JPanel PlayerPanel = new JPanel();
		JPanel RpCardBtnPanel = new JPanel();
		JPanel ButtonPanel = new JPanel();
		JPanel InfoPanel = new JPanel();

		DealerPanel.add(dealerCard1);
		DealerPanel.add(dealerCard2);
		DealerPanel.add(dealerCard3);

		PlayerPanel.add(playerCard1);
		PlayerPanel.add(playerCard2);
		PlayerPanel.add(playerCard3);

		RpCardBtnPanel.add(replaceCard1);
		RpCardBtnPanel.add(replaceCard2);
		RpCardBtnPanel.add(replaceCard3);

		ButtonPanel.add(showingBet);
		ButtonPanel.add(inputBet);
		ButtonPanel.add(start);
		ButtonPanel.add(result);

		InfoPanel.add(displayMessage);
		InfoPanel.add(displayRemain);

		MainPanel.setLayout(new GridLayout(5, 1));
		MainPanel.add(DealerPanel);
		MainPanel.add(PlayerPanel);
		MainPanel.add(RpCardBtnPanel);
		MainPanel.add(ButtonPanel);
		MainPanel.add(InfoPanel);

		DealerPanel.setBackground(Color.green);
		PlayerPanel.setBackground(Color.green);
		RpCardBtnPanel.setBackground(Color.green);

		JMenuBar menuBar = new JMenuBar();
		JMenu control = new JMenu("Control");
		JMenu help = new JMenu("Help");
		JMenuItem exit = new JMenuItem("Exit");
		JMenuItem rule = new JMenuItem("Rule");

		control.add(exit);
		help.add(rule);
		menuBar.add(control);
		menuBar.add(help);

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(MainPanel);
		frame.setTitle("A Simple Card Game");
		frame.setSize(400, 700);
		frame.setVisible(true);
		frame.setJMenuBar(menuBar);

		setButtons();

		/**
		 * This is a action listener of the Start button. After clicking, it checks
		 * whether the bet input is a positive integer or not. If the user input is
		 * valid, it creates a set of 6 cards and shows the 3 player cards. Then, it
		 * will be disabled while the other buttons will be enabled. Otherwise, it
		 * creates a warning pop-up message window.
		 */
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dealerCard1.setIcon(d1);
				dealerCard2.setIcon(d2);
				dealerCard3.setIcon(d3);
				if (inputBet.getText().isBlank() == false) {
					input = Double.parseDouble(inputBet.getText());
					bet = (int) input;
					if (bet > budget) {
						input = 0;
						bet = 0;
						displayMessage.setText("Please place your bet!");
						playerCard1.setIcon(p1);
						playerCard2.setIcon(p2);
						playerCard3.setIcon(p3);
						JOptionPane.showMessageDialog(frame, "WARNING: You only have $" + budget);
					} else if (input <= 0 || bet != input) {
						input = 0;
						bet = 0;
						displayMessage.setText("Please place your bet!");
						playerCard1.setIcon(p1);
						playerCard2.setIcon(p2);
						playerCard3.setIcon(p3);
						JOptionPane.showMessageDialog(frame, "WARNING: The bet you place must be a positive integer!");
					} else {
						cards = setCards();
						playerCard1.setIcon(new ImageIcon(cards.get(0)));
						playerCard2.setIcon(new ImageIcon(cards.get(1)));
						playerCard3.setIcon(new ImageIcon(cards.get(2)));
						start.setEnabled(false);
						result.setEnabled(true);
						replaceCard1.setEnabled(true);
						replaceCard2.setEnabled(true);
						replaceCard3.setEnabled(true);
						displayMessage.setText("Your current bet is: $" + bet);
					}
				}
			}
		});

		/**
		 * This is a action listener of the Replace Card 1 button. After clicking, it
		 * replaces a random card to the Cards set and updates the number of the card in
		 * the Numbers set. After that, the button will be disabled. If the replacing
		 * times is equal to 2, all buttons will be disabled.
		 */
		replaceCard1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				replaceTimes++;
				while (true) {
					int newRank = randomRank();
					int newNumber = randomNumber();
					String newCard = randomCard(newRank, newNumber);
					if (cards.contains(newCard) == false) {
						ranks.set(0, newRank);
						numbers.set(0, newNumber);
						cards.set(0, newCard);
						playerCard1.setIcon(new ImageIcon(newCard));
						break;
					}
				}
				replaceCard1.setEnabled(false);
				if (replaceTimes == 2) {
					replaceCard2.setEnabled(false);
					replaceCard3.setEnabled(false);
				}
			}
		});

		/**
		 * This is a action listener of the Replace Card 2 button. After clicking, it
		 * replaces a random card to the Cards set and updates the number of the card in
		 * the Numbers set. After that, the button will be disabled. If the replacing
		 * times is equal to 2, all buttons will be disabled.
		 */
		replaceCard2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				replaceTimes++;
				while (true) {
					int newRank = randomRank();
					int newNumber = randomNumber();
					String newCard = randomCard(newRank, newNumber);
					if (cards.contains(newCard) == false) {
						ranks.set(1, newRank);
						numbers.set(1, newNumber);
						cards.set(1, newCard);
						playerCard2.setIcon(new ImageIcon(newCard));
						break;
					}
				}
				replaceCard2.setEnabled(false);
				if (replaceTimes == 2) {
					replaceCard1.setEnabled(false);
					replaceCard3.setEnabled(false);
				}
			}
		});

		/**
		 * This is a action listener of the Replace Card 3 button. After clicking, it
		 * replaces a random card to the Cards set and updates the number of the card in
		 * the Numbers set. After that, the button will be disabled. If the replacing
		 * times is equal to 2, all buttons will be disabled.
		 */
		replaceCard3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				replaceTimes++;
				while (true) {
					int newRank = randomRank();
					int newNumber = randomNumber();
					String newCard = randomCard(newRank, newNumber);
					if (cards.contains(newCard) == false) {
						ranks.set(2, newRank);
						numbers.set(2, newNumber);
						cards.set(2, newCard);
						playerCard3.setIcon(new ImageIcon(newCard));
						break;
					}
				}
				replaceCard3.setEnabled(false);
				if (replaceTimes == 2) {
					replaceCard1.setEnabled(false);
					replaceCard2.setEnabled(false);
				}
			}
		});

		/**
		 * This is a action listener of the Result button. It shows the 3 dealer cards
		 * and invokes the checkResult() function to determine whether the player win or
		 * not. Then, it will create the corresponding pop-up message window and
		 * calculate the remaining budget. If the budget is equal to 0, all the buttons
		 * will be disabled and game over.
		 */
		result.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dealerCard1.setIcon(new ImageIcon(cards.get(3)));
				dealerCard2.setIcon(new ImageIcon(cards.get(4)));
				dealerCard3.setIcon(new ImageIcon(cards.get(5)));
				if (checkResult() == false) {
					budget -= bet;
					setButtons();
					JOptionPane.showMessageDialog(frame, "Sorry! The Dealer wins this round!");
				} else {
					budget += bet;
					setButtons();
					JOptionPane.showMessageDialog(frame, "Congrauations! You win this round!");
				}
				displayMessage.setText("Your current bet is: $" + bet);
				displayRemain.setText("Amount of money you have: $" + budget);
				if (budget == 0) {
					displayMessage.setText("You have no money!");
					displayRemain.setText("Please start a new game!");
					JOptionPane.showMessageDialog(frame,
							"Game Over!\nYou have no more money!\nPlease start a new game!");
					start.setEnabled(false);
					result.setEnabled(false);
					replaceCard1.setEnabled(false);
					replaceCard2.setEnabled(false);
					replaceCard3.setEnabled(false);
				}
				ranks.clear();
				numbers.clear();
				cards.clear();
			}
		});

		/**
		 * This is a action listener of Rule menu item. After clicking, it will create a
		 * pop-up message window to introduce the rules of the card game.
		 */
		rule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, "J, Q, K are regarded as special cards\n"
						+ "Rule 1: The one with more special cards wins.\n"
						+ "Rule 2: If both have the same number of special cards, add the face values of the other card(s) and take the remainder after dividing the sum by 10. The one with a bigger remainder wins. (Note: Ace = 1).\n"
						+ "Rule 3: The dealer wins if both rule 1 and rule 2 cannot distinguish the winner.");
			}
		});

		/**
		 * This is a action listener of the Exit menu item. After clicking, it will
		 * close the game window.
		 */
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
	}

	/**
	 * This method picks a random integer between 1 and 4 which indicates the rank
	 * of the card.
	 * 
	 * @return a random integer between 1 and 4
	 */
	public int randomRank() {
		Random rank = new Random();
		int r = rank.nextInt((4 - 1) + 1) + 1;
		return r;
	}

	/**
	 * This method picks a random integer between 1 and 13 which indicates the
	 * number of the card.
	 * 
	 * @return a random integer between 1 and 13
	 */
	public int randomNumber() {
		Random number = new Random();
		int n = number.nextInt((13 - 1) + 1) + 1;
		return n;
	}

	/**
	 * This method uses the random integer of rank and the random integer of number
	 * to generate a String which indicates name of the random card with
	 * corresponding format.
	 * 
	 * @param r a random integer of the rank of the card.
	 * @param n a random integer of the number of the card.
	 * @return c a String representing the name of the image of the card.
	 */
	public String randomCard(int r, int n) {
		String c = "card_" + r + n + ".gif";
		return c;
	}

	/**
	 * This methods creates 3 array lists with the length of 6. Each array list
	 * stores the ranks, numbers, and the names of image respectively. It will
	 * return the cards array list when its length reaches 6.
	 * 
	 * @return cards a String array list containing the names of the 6 cards.
	 */
	public ArrayList<String> setCards() {
		while (cards.size() < 6) {
			int rank = randomRank();
			int number = randomNumber();
			String card = randomCard(rank, number);
			if (cards.contains(card) == false) {
				ranks.add(rank);
				numbers.add(number);
				cards.add(card);
			}
		}
		return cards;
	}

	/**
	 * This methods checks the result of each round based on the 3 rules of the card
	 * games and return the corresponding boolean value after checking.
	 * 
	 * @return true if the player wins, false if the dealer wins.
	 */
	public boolean checkResult() {
		int dealerSP = 0;
		int dealerValue = 0;
		int playerSP = 0;
		int playerValue = 0;
		for (int i = 0; i < 3; i++) {
			if (numbers.get(i) > 10) {
				playerSP++;
			} else {
				playerValue += numbers.get(i);
			}
		}

		for (int i = 3; i < cards.size(); i++) {
			if (numbers.get(i) > 10) {
				dealerSP++;
			} else {
				dealerValue += numbers.get(i);
			}
		}
		dealerValue %= 10;
		playerValue %= 10;
		if (dealerSP > playerSP) {
			return false;
		} else if (dealerSP < playerSP) {
			return true;
		} else if (dealerValue > playerValue) {
			return false;
		} else if (dealerValue < playerValue) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method enables the Start button and disables the other buttons before
	 * the game starts. It will also initialize the replacing card times.
	 */
	public void setButtons() {
		start.setEnabled(true);
		result.setEnabled(false);
		replaceCard1.setEnabled(false);
		replaceCard2.setEnabled(false);
		replaceCard3.setEnabled(false);
		replaceTimes = 0;
	}
}
