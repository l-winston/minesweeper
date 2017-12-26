import java.awt.*;
import java.awt.image.*;
import java.io.IOException;
import java.util.*;

import javax.swing.*;

public class GUI {
	static final int X = 1000;
	static final int Y = 1000;

	static JFrame frame;
	static BufferedImage image;
	static Graphics2D g2d;
	static MyML ml = new MyML();

	static boolean running = true;
	
	static Tile[][] board = new Tile[10][10];

	public static void main(String[] args) throws IOException {
		initFrame();

		initBoard(board);

	}

	private static void initBoard(Tile[][] board) throws IOException {
		Random rand = new Random();
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				board[i][j] = new Tile(i, j, rand.nextDouble() > 0.8);
				board[i][j].show(g2d);
			}
		}
		assignValues();
	}

	private static void assignValues() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				board[i][j].assignValue(board);
			}
		}
	}

	private static void initFrame() {
		frame = new JFrame("minesweeper");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().addMouseListener(ml);

		image = new BufferedImage(X, Y, BufferedImage.TYPE_INT_RGB);

		g2d = image.createGraphics();
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, X, Y);
		g2d.setColor(Color.black);
		g2d.drawRect(0, 0, X, Y);

		frame.getContentPane().add(new JLabel(new ImageIcon(image)));
		frame.pack();
	}

	public static void click(int x, int y) throws IOException {
		board[y/(Y/board.length)][x/(X/board[0].length)].flip(g2d);	
	}

	public static void endGame() throws IOException {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if(board[i][j].isBomb){
					board[i][j].flip(g2d);
				}
			}
		}
		frame.repaint();
		g2d.dispose();
	}
}
