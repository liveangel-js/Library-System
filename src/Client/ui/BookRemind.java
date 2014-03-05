package Client.ui;


import javax.swing.*;


public class BookRemind extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
	
	
	public BookRemind(){
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		this.add(new DeadlineRemind());
		this.add(new BookRequest());
	}

}
