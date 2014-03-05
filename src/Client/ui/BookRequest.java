package Client.ui;

import java.awt.BorderLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Common.Book;
import Common.Teacher;



public class BookRequest extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int ID;
	private String name;
	private String author;
	private String string_remind;
	private DefaultTableModel mode;
	
	
	
	public BookRequest(){
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "ͼ������"));
		
		
		
		
		String[] columnNames ={"ID","����","����","����"};
		mode = new DefaultTableModel(columnNames, 0);
		JTable bookTable = new JTable(mode);
		bookTable.setEnabled(false);
		this.add(new JScrollPane(bookTable));
		
		
		
		for(int i=0;i<Client.user.getRequestBooks().size();i++){
			Book b=Client.user.getRequestBooks().get(i);
			ID=b.getID();
			name=b.getName();
			author=b.getAuthor();
			if(Client.user instanceof Teacher){
				if(b.isRequest()){
					string_remind="�������δ��ɣ������ĵȴ�";
				}
				else{
					string_remind="�����������ɣ�������鼮";
					Client.user.getRequestBooks().remove(b);
				}
			}
			else{
				string_remind="����Ϊ��ʦ�����뾡��黹";
			}
			Object rowData[]={ID,name,author,string_remind};
			mode.addRow(rowData);
		}	
	}
}
