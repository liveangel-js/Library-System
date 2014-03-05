package Client.ui;



import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.*;

import Common.Book;
import Common.Library;
import Common.Teacher;




public class BookBorrow extends JPanel implements ActionListener,ItemListener  {
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
	private JTextField text_rare;
	private JTextField text_borrowed;
	private JButton button_borrow;
	private JButton button_request;
	private DefaultTableModel mode;
	
	
	
	public BookBorrow(){
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		
		
		//借阅图书面板
		JPanel borrowPanel=new JPanel();
		borrowPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "借阅图书"));
		this.add(borrowPanel);
		
		
		
		borrowPanel.add(new JLabel("ID："));
		combobox_ID=new JComboBox();
		combobox_ID.addItemListener(this);
		borrowPanel.add(combobox_ID);

	
		
		borrowPanel.add(new JLabel("书名："));
		text_name=new JTextField(10);
		text_name.setEditable(false);
		borrowPanel.add(text_name);

		
		
		borrowPanel.add(new JLabel("作者："));
		text_author=new JTextField(10);
		text_author.setEditable(false);
		borrowPanel.add(text_author);
		
		
		
		borrowPanel.add(new JLabel("珍本："));
		text_rare=new JTextField(3);
		text_rare.setHorizontalAlignment(JTextField.CENTER);
		text_rare.setEditable(false);
		borrowPanel.add(text_rare);
		
		
		
		borrowPanel.add(new JLabel("借阅情况："));
		text_borrowed=new JTextField(5);
		text_borrowed.setHorizontalAlignment(JTextField.CENTER);
		text_borrowed.setEditable(false);
		borrowPanel.add(text_borrowed);
		
		
		
		button_borrow=new JButton("借阅");
		button_borrow.addActionListener(this);
		borrowPanel.add(button_borrow);

		
		if(Client.user instanceof Teacher){
			button_request=new JButton("要求提前归还");
			button_request.addActionListener(this);
			borrowPanel.add(button_request);
		}

		
		
		
		
		
		//图书信息面板
		JPanel bookPanel=new JPanel();
		//bookPanel.setLayout(new BorderLayout());
		bookPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "图书信息"));	
		this.add(bookPanel);
		
		
		
		String[] columnNames ={"ID","书名","作者","珍本","借阅情况"};
		mode=new DefaultTableModel(columnNames, 0);
		JTable bookTable = new JTable(mode);
		bookTable.setEnabled(false);
		bookPanel.add(new JScrollPane(bookTable));

		
		
		
		
		//载入图书信息
		for(int i=0;i<Library.library.books.size();i++){
			Book b=Library.library.books.get(i);
			ID=b.getID();
			combobox_ID.addItem(ID);
			name=b.getName();
			author=b.getAuthor();
			rare=b.isRare();
			borrowed=b.isBorrowed();
			Object rowData[]={ID,name,author,rare?"是":"否",b.isBorrowed()?"已借出":"未借出"};
			mode.addRow(rowData);
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
    				text_rare.setText(b.isRare()?"是":"否");
    				text_borrowed.setText(b.isBorrowed()?"已借出":"未借出");
    				break;
    			}
    		}	
    	}
	}
	
	
    
    
    
	
	//按钮监听
	public void actionPerformed(ActionEvent e) {
		if(combobox_ID.getSelectedItem()!=null){
			if(e.getActionCommand()=="借阅"){
				Client.sendRequest();
				this.borrowBook();
				Client.returnInfo();
				return;
			}
			if(e.getActionCommand()=="要求提前归还"){
				Client.sendRequest();
				this.requestReturnInAdvance();
				Client.returnInfo();
				return;
			}

		}	
	}
	
	

	
	
	
	
	//借阅图书
	public void borrowBook(){
		ID=(Integer) combobox_ID.getSelectedItem();
		for(int i=0;i<Library.library.books.size();i++){
			if(Library.library.books.get(i).getID()==ID){
				Book b=Library.library.books.get(i);
				if(b.isBorrowed()){
					Client.remind("你要借阅的书已经被他人借阅！");
					return;
				}
				else{
					if(b.isRare()&&Client.user.isUndergraduate()){
						Client.remind("你要借阅的书为珍本，你无权限借阅此书！");
						return;
					}
					else{
						b.borrowedBy(Client.user);
						Client.user.borrow(b);
					}
				}
			}
		}
		
		
		
		
		//同步借阅图书面板
		text_borrowed.setText("已借出");
		
	
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
		Client.remind("图书借阅成功！");
	}	
	
	
	
	public void requestReturnInAdvance(){
		ID=(Integer) combobox_ID.getSelectedItem();
		for(int i=0;i<Library.library.books.size();i++){
			if(Library.library.books.get(i).getID()==ID){
				Book b=Library.library.books.get(i);
				if(b.isBorrowed()){
					if(b.getBorrower() instanceof Teacher){
						Client.remind("该图书被教师所借，不能要求提前归还");
					}
					else{
						if(b.isRequest()){
							Client.remind("该书已经被另一名教师预约");
						}
						else{
							Client.user.getRequestBooks().add(b);
							b.getBorrower().getRequestBooks().add(b);
							b.setRequest(true);
							b.setRequester(Client.user);
							Client.remind("请求已发送");
						}
					}
				}
				else{
					Client.remind("该图书未借出，你可以直接借阅");
				}
			}
		}
	}
	
}
