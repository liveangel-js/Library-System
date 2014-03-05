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



public class BookDelete extends JPanel implements ActionListener,ItemListener  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int ID;
	private String name;
	private String author;
	private boolean rare;
	private boolean borrowed;
	private JTextField combobox_ID;
	private JTextField text_name;
	private JTextField text_author;
	private JTextField text_rare;
	private JButton button_delete;
	private DefaultTableModel mode;
	ManagerPanel mp;
	
	
	
	public BookDelete(ManagerPanel mp){
		this.mp=mp;
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		
		//删除图书面板
		JPanel deletePanel=new JPanel();
		deletePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "删除图书"));
		this.add(deletePanel);
		
		
		
		deletePanel.add(new JLabel("ISBN："));
		combobox_ID=new JTextField(20);
//		combobox_ID.addItemListener(this);
		deletePanel.add(combobox_ID);

	
		
		deletePanel.add(new JLabel("书名："));
		text_name=new JTextField(10);
		text_name.setEditable(false);
		deletePanel.add(text_name);

		
		
		deletePanel.add(new JLabel("作者："));
		text_author=new JTextField(10);
		text_author.setEditable(false);
		deletePanel.add(text_author);
		
		
		
		deletePanel.add(new JLabel("珍本："));
		text_rare=new JTextField(3);
		text_rare.setHorizontalAlignment(JTextField.CENTER);
		text_rare.setEditable(false);
		deletePanel.add(text_rare);
		
		
		
		button_delete=new JButton("删除");
		button_delete.addActionListener(this);
		deletePanel.add(button_delete);

		
		
		
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
	
	
	
	
	
//	//同步显示书籍信息
//    public void itemStateChanged(ItemEvent e) {
//    	if(combobox_ID.getSelectedItem()!=null){
//    		ID=(Integer)combobox_ID.getSelectedItem();
//    		for(int i=0;i<Library.library.books.size();i++){
//    			if(ID==Library.library.books.get(i).getID()){
//    				Book b=Library.library.books.get(i);
//    				text_name.setText(b.getName());
//    				text_author.setText(b.getAuthor());
//    				text_rare.setText(b.isRare()?"是":"否");
//    				break;
//    			}
//    		}	
//    	}
//    	else{
//    		text_name.setText("");
//			text_author.setText("");
//			text_rare.setText("");
//    	}
//	}
//	
//	
    
    
    
	
	//按钮监听
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand()=="删除"){
//			if(combobox_ID.getSelectedItem()!=null){
//				Client.sendRequest();
//				this.deleteBook();
//				Client.returnInfo();
//			}
			
			this.deleteBook();
			update();
		}	
	}
	
	

	
	
	//删除图书
	public void deleteBook(){
		int temp = Mysql.getInstance().update("delete from booklist where isbn=\""+combobox_ID.getText()+"\"");
		if(temp ==-1){
			JOptionPane.showMessageDialog(null, "失败","", JOptionPane.INFORMATION_MESSAGE);
		}
		
//		ID=(Integer) combobox_ID.getSelectedItem();
//		for(int i=0;i<Library.library.books.size();i++){
//            if(Library.library.books.get(i).getID()==ID){	
//            	if(Library.library.books.get(i).isBorrowed()){
//            		Library.library.books.get(i).getBorrower().getBooks().remove(Library.library.books.get(i));
//            	}	
//            	Library.library.books.remove(i);
//				combobox_ID.removeItem(ID);
//				break;
//			}
//		}
//		
//		
//		
//		
//		//同步图书信息面板
//		while(mode.getRowCount()!=0){
//			mode.removeRow(0);
//		}
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
//		Client.remind("图书删除成功！");
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		
	}	
	
}
