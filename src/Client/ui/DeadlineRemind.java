package Client.ui;

import java.awt.BorderLayout;

import javax.swing.*;
import javax.swing.table.*;

import Common.Book;

public class DeadlineRemind extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int ID;
	private String name;
	private String author;
	private String borrow_time;
	private String return_time;
	private String string_remind;
	private DefaultTableModel mode;
	
	
	
	
	public DeadlineRemind(){
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "到期提醒"));	
		
		
		
		String[] columnNames ={"ID","书名","作者","借阅时间","应还时间","提醒"};
		mode = new DefaultTableModel(columnNames, 0);
		JTable bookTable = new JTable(mode);
		bookTable.setEnabled(false);
		this.add(new JScrollPane(bookTable));
		
		
		
		for(int i=0;i<Client.user.getBooks().size();i++){
			Book b=Client.user.getBooks().get(i);
			if(b.isNeedReminded()){
				ID=b.getID();
				name=b.getName();
				author=b.getAuthor();
				borrow_time=b.getBorrowTime();
				return_time=b.getReturnTime();
				if(b.isDeadLine()){
					string_remind="该书已到期，请尽快归还";
				}
				else{
					string_remind="该书快到期，请及时归还或者续借";
				}
				Object rowData[]={ID,name,author,borrow_time,return_time,string_remind};
				mode.addRow(rowData);
			}
		}
	}
	

}
