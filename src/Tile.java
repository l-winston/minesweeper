import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tile {
	int i, j, value, x, y;
	boolean isBomb, revealed;

	Tile(int i, int j, boolean isBomb) {
		this.i = i;
		this.j = j;
		this.isBomb = isBomb;
		this.revealed = false;
		this.x = GUI.X / GUI.board[0].length;
		this.y = GUI.Y / GUI.board.length;
	}

	public void show(Graphics2D g2d) throws IOException {
		BufferedImage img = ImageIO.read(new File("_.png"));
		g2d.drawImage(img, x * j, y * i, x * j + x, y * i + y, 0, 0, 220, 220, Color.black, GUI.frame);
		GUI.frame.repaint();
	}

	public void flip(Graphics2D g2d) throws IOException {
		revealed = true;
		BufferedImage img = null;
		if (isBomb) {
			img = ImageIO.read(new File("bomb.png"));
			g2d.drawImage(img, x * j, y * i, x * j + x, y * i + y, 0, 0, 220, 220, Color.black, GUI.frame);
			if(GUI.running){
				GUI.running = false;
				GUI.endGame();
			}
			return;
		} else if (value == 0) {
			img = ImageIO.read(new File("clicked.png"));

			try {
				if (!GUI.board[i + 1][j].isBomb && !GUI.board[i + 1][j].revealed)
					GUI.board[i + 1][j].flip(g2d);
			} catch (ArrayIndexOutOfBoundsException e) {
			}

			try {
				if (!GUI.board[i - 1][j].isBomb && !GUI.board[i - 1][j].revealed)
					GUI.board[i - 1][j].flip(g2d);
			} catch (ArrayIndexOutOfBoundsException e) {
			}

			try {
				if (!GUI.board[i][j + 1].isBomb && !GUI.board[i][j + 1].revealed)
					GUI.board[i][j + 1].flip(g2d);
			} catch (ArrayIndexOutOfBoundsException e) {
			}

			try {
				if (!GUI.board[i][j - 1].isBomb && !GUI.board[i][j - 1].revealed)
					GUI.board[i][j - 1].flip(g2d);
			} catch (ArrayIndexOutOfBoundsException e) {
			}
		} else if (value == 1)
			img = ImageIO.read(new File("1.png"));
		else if (value == 2)
			img = ImageIO.read(new File("2.png"));
		else if (value == 3)
			img = ImageIO.read(new File("3.png"));
		else if (value == 4)
			img = ImageIO.read(new File("4.png"));
		else if (value == 5)
			img = ImageIO.read(new File("5.png"));
		g2d.drawImage(img, x * j, y * i, x * j + x, y * i + y, 0, 0, 210, 210, Color.black, GUI.frame);
		GUI.frame.repaint();
	}

	public void assignValue(Tile[][] board) {
		if (isBomb)
			return;
		int count = 0;

		try {
			if (board[i][j + 1].isBomb)
				count++;
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			if (board[i - 1][j].isBomb)
				count++;
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			if (board[i + 1][j].isBomb)
				count++;
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			if (board[i - 1][j + 1].isBomb)
				count++;
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			if (board[i + 1][j + 1].isBomb)
				count++;
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			if (board[i - 1][j - 1].isBomb)
				count++;
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			if (board[i + 1][j - 1].isBomb)
				count++;
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			if (board[i][j - 1].isBomb)
				count++;
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		value = count;
	}
}
