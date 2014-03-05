package Client.ui.manager;




import java.awt.*;
import javax.swing.*;

import Client.ui.BookInfo;
import Client.ui.BookSearch;
import Client.ui.Client;
import Client.ui.UserInfo;
import Client.ui.UserModify;
import Client.ui.UserSearch;



public class ManagerPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int WIDTH=Client.WIDTH;
	public static final int HEIGHT=Client.HEIGHT;
	Client c;
	ManagerMenu menu;
	
	BookSearch bs;
	UserSearch us;
	
	BookInfo bi;
	UserInfo ui;
	
	BookAdd ba;
	BookModify bm;
	BookDelete bd;
	
	UserAdd ua;
	UserDelete ud;
	UserModify um;
	
	PublisherPanel pp;
	RecommandPanel rp;
	BookConditionPanel bp;
	
	
	
	public ManagerPanel  (Client c){	
		this.c=c;
		this.setLayout(new BorderLayout());
		menu=new ManagerMenu(this);
		this.add(menu,BorderLayout.WEST);
		this.add(new BookSearch(this));
	}
}
