package Client.ui;




import java.awt.*;
import javax.swing.*;



public class UserPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int WIDTH=Client.WIDTH;
	public static final int HEIGHT=Client.HEIGHT;
	Client c;
	UserMenu menu;
	
	
	BookSearch bs;
	BookBorrow bb;
	BookRenew br;
	BookReturn breturn;
	BorrowInfo bi;
	BookRemind r;
	
	RecommondPanel rp;
	BorrowHistory bh;

	
	
	
	public UserPanel  (Client c){
		this.c=c;
		this.setLayout(new BorderLayout());
		menu=new UserMenu(this);
		this.add(menu,BorderLayout.WEST);
		this.add(new BookRemind());
	}
}
