package Client.ui;




import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.*;

import Client.ui.manager.ManagerPanel;
import Common.Book;
import Common.Library;




public class BookSearch extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int ID;
	private String name;
	private String author;
	private boolean rare;
	private boolean borrowed;
	private boolean search_exist;
	private JComboBox combobox_way;
	private JTextField text_book;
	private JButton button_search;
	private DefaultTableModel mode;
	UserPanel up;
	ManagerPanel mp;


	
	
	public BookSearch(UserPanel up){
		this();
		this.up=up;
	}
	
	
	public BookSearch(ManagerPanel mp){
		this();
		this.mp=mp;
	}
	
	
	
	
	public BookSearch(){
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		
		
		//查询面板
		JPanel searchPanel=new JPanel();
		searchPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "查询条件"));
		this.add(searchPanel);
		
		
		searchPanel.add(new JLabel("查询方式："));
		Object way[]={"按书名","按作者"};
		combobox_way=new JComboBox(way);
		searchPanel.add(combobox_way);

		
		text_book=new JTextField(20);
		searchPanel.add(text_book);
		
		
		button_search=new JButton("查询");
		button_search.addActionListener(this);
		searchPanel.add(button_search);
	
		
		
		//查询结果面板
		JPanel resultPanel=new JPanel();
		resultPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "查询结果"));
		this.add(resultPanel);
		
		
		
		String[] columnNames ={"ID","书名","作者","珍本","借阅情况"};
		mode = new DefaultTableModel(columnNames, 0);
		JTable bookTable = new JTable(mode);
		bookTable.setEnabled(false);
		resultPanel.add(new JScrollPane(bookTable));
		
		
		
		//载入图书信息
		for(int i=0;i<Library.library.books.size();i++){
			Book b=Library.library.books.get(i);
			ID=b.getID();
			name=b.getName();
			author=b.getAuthor();
			rare=b.isRare();
			borrowed=b.isBorrowed();
			Object rowData[]={ID,name,author,rare?"是":"否",borrowed?"已借出":"未借出"};
			mode.addRow(rowData);
		}
	}
	
	
	
	
	
	 //按钮监听
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand()=="查询"){
			//清空之前内容
			while(mode.getRowCount()!=0){
				mode.removeRow(0);
			}
			if(combobox_way.getSelectedItem()=="按书名"){
				Client.sendRequest();
				this.searchByName();
				Client.returnInfo();
			}
			else{
				Client.sendRequest();
				this.searchByAuthor();
				Client.returnInfo();
			}	
			if(!search_exist){
				Client.remind("你要查询呢的图书不存在！");	
			}
		}
	}
	
	
	
	//按书名查找
	public void searchByName(){		
		search_exist=false;
		name=text_book.getText();
		for(int i=0;i<Library.library.books.size();i++){
			if(Library.library.books.get(i).getName().equals(name)){
				Book b=Library.library.books.get(i);
				ID=b.getID();
				name=b.getName();
				author=b.getAuthor();
				rare=b.isRare();
				borrowed=b.isBorrowed();
				Object rowData[]={ID,name,author,rare?"是":"否",borrowed?"已借出":"未借出"};
				mode.addRow(rowData);
				search_exist=true;
			}
		}	
	}
	
	
	
	
	//按作者查找
	public void searchByAuthor(){		
		search_exist=false;
		author=text_book.getText();
		for(int i=0;i<Library.library.books.size();i++){
			if(Library.library.books.get(i).getAuthor().equals(author)){
				Book b=Library.library.books.get(i);
				ID=b.getID();
				name=b.getName();
				author=b.getAuthor();
				rare=b.isRare();
				borrowed=b.isBorrowed();
				Object rowData[]={ID,name,author,rare?"是":"否",borrowed?"已借出":"未借出"};
				mode.addRow(rowData);
				search_exist=true;
			}
		}
	}
}
