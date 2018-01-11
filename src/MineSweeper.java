import java.awt.*;
import java.awt.image.*;
import java.io.IOException;
import java.util.*;

import javax.swing.*;

public class MineSweeper extends JFrame{
	// frame width and height
	final int X = 750;
	final int Y = 750;

	// probability that a tile will be a bomb (decided during tile creation)
	final double bombRate = 0.1;

	BufferedImage image;
	Graphics2D g2d;
	MyML ml = new MyML(this);

	boolean running = true;

	// size of board is decided here
	Tile[][] board = new Tile[25][25];

	public static void main(String[] args) throws IOException {
		MineSweeper ms = new MineSweeper();
	}
	
	public MineSweeper() throws IOException{
		// frame initialization
		initFrame();

		// board initialization
		initBoard(board);
	}

	// randomly generate bombs
	private void initBoard(Tile[][] board) throws IOException {
		Random rand = new Random();
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				board[i][j] = new Tile(i, j, rand.nextDouble() > (1 - bombRate), this);
				board[i][j].show(g2d);
			}
		}

		assignValues();
	}

	// assign each tile the number of nearby bombs
	private void assignValues() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				board[i][j].assignValue(board);
			}
		}
	}

	// set up frame
	private void initFrame() {
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().addMouseListener(ml);

		image = new BufferedImage(X, Y, BufferedImage.TYPE_INT_RGB);

		g2d = image.createGraphics();

		getContentPane().add(new JLabel(new ImageIcon(image)));
		pack();
	}

	// reveal tile when clicked
	public void click(int x, int y) throws IOException {
		board[y / (Y / board.length)][x / (X / board[0].length)].flip(g2d);
	}

	// when you click on a bomb reveal the rest of them and end the game
	public void endGame() throws IOException {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j].isBomb) {
					board[i][j].flip(g2d);
				}
			}
		}
		repaint();
		g2d.dispose();
	}

	// flag a tile
	public void flag(int x, int y) throws IOException {
		board[y / (Y / board.length)][x / (X / board[0].length)].flag(g2d);
	}
}
