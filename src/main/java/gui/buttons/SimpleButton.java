package gui.buttons;

import javax.swing.JButton;

public class SimpleButton extends JButton {
	
	private static final long serialVersionUID = 1L;
	private final String type;
	
	public SimpleButton(String type, String text){
		super(text);
		this.type=type;
	}

	/**
	 * Getter
	 * 
	 * @return the tipo
	 */
	public String getType() {
		return type;
	}
}
