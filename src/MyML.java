import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class MyML implements MouseListener{
	public MineSweeper m;
	public MyML(MineSweeper m){
		this.m = m;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		try {
			if (e.getModifiers() == MouseEvent.BUTTON3_MASK) 
		    {
		        m.flag(e.getX(), e.getY());
		        System.out.println("flag");
		    }else{
				m.click(e.getX(), e.getY());
		    }
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {		
	}
}