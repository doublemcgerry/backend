package gui.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import gui.buttons.SimpleButton;
import gui.frames.PrimaSchermata;


public class MyListener implements MouseListener{
	
	private PrimaSchermata frame;
	
	/**
	 * Il costruttore associa la {@link PrimaSchermata} in ingresso alla propria
	 * 
	 * @param frame la prima schermata
	 */
	public MyListener (PrimaSchermata frame){
		this.frame=frame;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		SimpleButton bottone= (SimpleButton)e.getSource();
		
		if(bottone.getType().equals("startServer")){
			frame.startServer();
		}
		if(bottone.getType().equals("stopServer")){
			frame.stopServer();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {	
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
