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



public class BookModify extends JPanel implements ActionListener,ItemListener  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int ID;
	private String name;
	private String author;
	private boolean rare;
	private boolean borrowed;
	private JComboBox combobox_ID;
	private JTextField text_name;
	private JTextField text_author;
	private JComboBox combobox_rare;
	private JButton button_modify;
	private DefaultTableModel mode;
	ManagerPanel mp;
	
	
	
	public BookModify(ManagerPanel mp){
		this.mp=mp;
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		
		
		//修改图书面板
		JPanel modifyPanel=new JPanel();
		modifyPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "修改图书"));
		this.add(modifyPanel);
		
		

		modifyPanel.add(new JLabel("ID："));
		combobox_ID=new JComboBox();
		combobox_ID.addItemListener(this);
		modifyPanel.add(combobox_ID);
		
		

		modifyPanel.add(new JLabel("书名："));
		text_name=new JTextField(10);
		modifyPanel.add(text_name);
		
		
		
		modifyPanel.add(new JLabel("作者："));
		text_author=new JTextField(10);
		modifyPanel.add(text_author);

		
		
		modifyPanel.add(new JLabel("珍本："));
		String string_rare[]={"是","否"};
		combobox_rare=new JComboBox(string_rare);
		modifyPanel.add(combobox_rare);
		
		
		
		button_modify=new JButton("修改");
		button_modify.addActionListener(this);
		modifyPanel.add(button_modify);

		
				
		
		
		JPanel bookPanel=new JPanel();
		//bookPanel.setLayout(new BorderLayout());
		bookPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "图书信息"));	
		this.add(bookPanel);
		
		
		String[] columnNames ={"ISBN","书名","作者","出版社","类别"};
		mode = new DefaultTableModel(columnNames, 0);
		JTable bookTable = new JTable(mode);
		bookTable.setEnabled(false);
		bookPanel.add(new JScrollPane(bookTable));

		
		update();

		
		
		
		
		//载入图书信息
//		for(int i=0;i<Library.library.books.size();i++){
//			Book b=Library.library.books.get(i);
//			ID=b.getID();
//			combobox_ID.addItem(ID);
//			name=b.getName();
//			author=b.getAuthor();
//			rare=b.isRare();
//			borrowed=b.isBorrowed();
//			Object rowData[]={ID,name,author,rare?"是":"否",borrowed?"已借出":"未借出"};
//			mode.addRow(rowData);
//		}	
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
		if(e.getActionCommand()=="修改"){
			if(combobox_ID.getSelectedItem()!=null){
				Client.sendRequest();
				this.modifyBook();
				Client.returnInfo();
			}
		}	
	}
	
	
	
	
	
	//同步显示书籍信息
    public void itemStateChanged(ItemEvent e) {
    	if(combobox_ID.getSelectedItem()!=null){
    		ID=(Integer)combobox_ID.getSelectedItem();
    		for(int i=0;i<Library.library.books.size();i++){
    			if(ID==Library.library.books.get(i).getID()){
    				Book b=Library.library.books.get(i);
    				text_name.setText(b.getName());
    				text_author.setText(b.getAuthor());
    				combobox_rare.setSelectedItem(b.isRare()?"是":"否");
    				break;
    			}
    		}
    	}
	}
	
	
    
    
	
	//添加图书
	public void modifyBook(){
		ID=(Integer) combobox_ID.getSelectedItem();
		for(int i=0;i<Library.library.books.size();i++){
            if(Library.library.books.get(i).getID()==ID){	
        		name=text_name.getText();
        		author=text_author.getText();
        		rare=false;
        		borrowed=false;
        		if(combobox_rare.getSelectedItem()=="是"){
        			rare=true;
        		}
        		if(name.equals("")||author.equals("")){
        			Client.remind("请输入完整信息！");
        		}
        		else{
        			Library.library.books.get(i).setBook(name,author,rare);
        		}		
				break;
			}
		}
		
		
		
		
		
		//同步图书信息面板
		while(mode.getRowCount()!=0){
			mode.removeRow(0);
		}
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
		Client.remind("图书修改成功！");
	}

}
