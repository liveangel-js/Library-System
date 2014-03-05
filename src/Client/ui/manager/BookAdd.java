package Client.ui.manager;



import javax.swing.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.*;

import Client.ui.Client;
import Common.Book;
import Common.Library;
import Mysql.Mysql;



public class BookAdd extends JPanel implements ActionListener  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int ID;
	private String name;
	private String author;
	private boolean rare;
	private boolean borrowed;
	private JTextField text_ISBN;
	private JTextField text_name;
	private JTextField text_author;
	private JTextField text_publisher;
	private JTextField text_type;
	private JComboBox combobox_rare;
	private JButton button_add;
	private DefaultTableModel mode;
	ManagerPanel mp;
	
	
	
	public BookAdd(ManagerPanel mp){
		this.mp=mp;
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		
		
		
		//添加图书面板
		JPanel addPanel=new JPanel();
		addPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "添加图书"));
		this.add(addPanel);
		
		
		
		addPanel.add(new JLabel("ID："));
		text_ISBN=new JTextField(13);
		text_ISBN.setHorizontalAlignment(JTextField.CENTER);
		text_ISBN.setEditable(true);
		addPanel.add(text_ISBN);

		
		
		addPanel.add(new JLabel("书名："));
		text_name=new JTextField(10);
		addPanel.add(text_name);

		

		addPanel.add(new JLabel("作者："));
		text_author=new JTextField(10);
		addPanel.add(text_author);
		
		
		
//		addPanel.add(new JLabel("珍本："));
		String string_rare[]={"是","否"};
		combobox_rare=new JComboBox(string_rare);
//		addPanel.add(combobox_rare);
		
		addPanel.add(new JLabel("出版社"));
		text_publisher = new JTextField(10);
		addPanel.add(text_publisher);
		addPanel.add(new JLabel("类型"));
		text_type = new JTextField(10);
		addPanel.add(text_type);
		

		button_add=new JButton("添加");
		button_add.addActionListener(this);
		addPanel.add(button_add);

		
		
		
		
		
		//图书信息面板
		JPanel bookPanel=new JPanel();
		//bookPanel.setLayout(new BorderLayout());
		bookPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "图书信息"));	
		this.add(bookPanel);
		
		
		String[] columnNames ={"ISBN","书名","作者","出版社","类别"};
		mode = new DefaultTableModel(columnNames, 0);
		JTable bookTable = new JTable(mode);
		bookTable.setEnabled(false);
		bookPanel.add(new JScrollPane(bookTable));	
		//载入图书信息
//		for(int i=0;i<Library.library.books.size();i++){
//			Book b=Library.library.books.get(i);
//			ID=b.getID();
//			name=b.getName();
//			author=b.getAuthor();
//			rare=b.isRare();
//			borrowed=b.isBorrowed();
//			Object rowData[]={ID,name,author,rare?"是":"否",borrowed?"已借出":"未借出"};
//			mode.addRow(rowData);
//		}
		update();
	}
	
	public void update(){
		while(mode.getRowCount()!=0){
			mode.removeRow(0);
		}
		Mysql mysql = Mysql.getInstance();
		ResultSet rs = mysql.query("select * from booklist order by publisher ");
		try {
			while(rs.next()){
				Object rowData[]={rs.getString("isbn"),rs.getString("name"),rs.getString("author"),rs.getString("publisher"),rs.getString("booktype")};
				mode.addRow(rowData);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	//按钮监听
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand()=="添加"){
			Client.sendRequest();
			this.addBook();
			Client.returnInfo();
		}	
	}
	
	
	
	
	
	//添加图书
	public void addBook(){
		ID=Book.getNextID();
		
		
//		ResultSet rs = Mysql.getInstance().query("select * from booklist");
//		try {
//			while(rs.next()){
//				ID=Book.getNextID();
//				name=rs.getString("name");
//				author=rs.getString("author");
//				Book b=new Book(name,author,false);
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return ;
		
		
		name=text_name.getText();
		author=text_author.getText();
		rare=false;
		borrowed=false;
		combobox_rare.setSelectedItem("否");
		if(combobox_rare.getSelectedItem()=="是"){
			rare=true;
		}
		if(name.equals("")||author.equals("")){
			Client.remind("请输入完整信息！");
			return;
		}
		else{
			Book b=new Book(name,author,rare);
//			text_ISBN.setText(""+(Book.getNextID()));
		}
		
		
		
		
		
		
		String [] cc = {"艺术","哲学","社会科学总论","政法","军事","经济","文学","工业技术","自然科学总论","航空航天","交通运输","综合性图书","历史","生物科学","天文学"};
		int temp =(int)(Math.random()*cc.length);
		if(temp==cc.length) temp--;

		text_type.setText(cc[temp]);
		
		
		Mysql mysql = Mysql.getInstance();
		Book a = new Book(text_name.getText(), text_author.getText(), text_publisher.getText(), text_ISBN.getText(), text_type.getText());
		mysql.addBook(a);
		update();
		text_ISBN.setText("");
		
		
	}
}
