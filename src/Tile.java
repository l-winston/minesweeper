import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tile {
	int i, j, value, x, y;
	boolean isBomb, revealed, flagged;
	MineSweeper m;

	Tile(int i, int j, boolean isBomb, MineSweeper m) {
		this.m = m;
		this.i = i;
		this.j = j;
		this.isBomb = isBomb;
		this.revealed = false;
		this.flagged = false;
		this.x = m.X / m.board[0].length;
		this.y = m.Y / m.board.length;
	}

	// draw blank image on tile
	public void show(Graphics2D g2d) throws IOException {
		BufferedImage img = ImageIO.read(new File("_.png"));
		g2d.drawImage(img, x * j, y * i, x * j + x, y * i + y, 0, 0, 220, 220, Color.black, m);
		m.repaint();
	}

	public void flip(Graphics2D g2d) throws IOException {
		revealed = true;
		BufferedImage img = null;
		if (isBomb) {
			img = ImageIO.read(new File("bomb.png"));
			g2d.drawImage(img, x * j, y * i, x * j + x, y * i + y, 0, 0, 220, 220, Color.black, m);
			if (m.running) {
				m.running = false;
				m.endGame();
			}
			return;
		} else if (value == 0) {
			// expand if there are no adjacent bombs
			img = ImageIO.read(new File("clicked.png"));

			try {
				if (!m.board[i + 1][j].isBomb && !m.board[i + 1][j].revealed)
					m.board[i + 1][j].flip(g2d);
			} catch (ArrayIndexOutOfBoundsException e) {
			}

			try {
				if (!m.board[i - 1][j].isBomb && !m.board[i - 1][j].revealed)
					m.board[i - 1][j].flip(g2d);
			} catch (ArrayIndexOutOfBoundsException e) {
			}

			try {
				if (!m.board[i][j + 1].isBomb && !m.board[i][j + 1].revealed)
					m.board[i][j + 1].flip(g2d);
			} catch (ArrayIndexOutOfBoundsException e) {
			}

			try {
				if (!m.board[i][j - 1].isBomb && !m.board[i][j - 1].revealed)
					m.board[i][j - 1].flip(g2d);
			} catch (ArrayIndexOutOfBoundsException e) {
			}
		} else{
			img = ImageIO.read(new File(value + ".png"));
		}

		g2d.drawImage(img, x * j, y * i, x * j + x, y * i + y, 0, 0, 210, 210, Color.black, m);
		m.repaint();
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

	public void flag(Graphics2D g2d) throws IOException {
		if (revealed)
			return;
		BufferedImage img = null;

		if (flagged)
			img = ImageIO.read(new File("_.png"));
		else
			img = ImageIO.read(new File("flag.png"));

		g2d.drawImage(img, x * j, y * i, x * j + x, y * i + y, 0, 0, 210, 210, Color.black, m);
		m.repaint();
		flagged ^= true;
	}
}
