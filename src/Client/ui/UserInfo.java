package Client.ui;


import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.*;
import javax.swing.table.*;

import Client.ui.manager.ManagerPanel;
import Common.Book;
import Common.Library;
import Common.Student;
import Common.Teacher;


public class UserInfo extends JPanel implements ActionListener,ItemListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int ID;
	private int bookID;
	private String name;
	private String author;
	private boolean rare;
	private boolean renewed;
	private String borrow_time;
	private String return_time;
	private JComboBox combobox_ID;
	private JTextField text_username;
	private JTextField text_password;
	private JTextField text_undergraduate;
	private JButton button_search;
	private DefaultTableModel mode;
	ManagerPanel mp;
	
	
	
	public UserInfo(ManagerPanel mp){
		this.mp=mp;
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		
		//查询用户面板
		JPanel searchPanel=new JPanel();
		searchPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "用户信息"));
		this.add(searchPanel);
			
		
		
		searchPanel.add(new JLabel("ID："));
		combobox_ID=new JComboBox();
		combobox_ID.addItemListener(this);
		searchPanel.add(combobox_ID);
		
		
		

		searchPanel.add(new JLabel("用户账号："));
		text_username=new JTextField(10);
		text_username.setEditable(false);
		searchPanel.add(text_username);
		
		

		searchPanel.add(new JLabel("用户密码："));
		text_password=new JTextField(10);
		text_password.setEditable(false);
		searchPanel.add(text_password);
		
		
		
		searchPanel.add(new JLabel("学历："));
		text_undergraduate=new JTextField(6);
		text_undergraduate.setHorizontalAlignment(JTextField.CENTER);
		text_undergraduate.setEditable(false);
		searchPanel.add(text_undergraduate);
		
		
		
		button_search=new JButton("查询");
		button_search.addActionListener(this);
		searchPanel.add(button_search);

		
		
		
		
		
		//用户借阅信息面板
		JPanel userInfoPanel=new JPanel();
		userInfoPanel.setLayout(new BorderLayout());
		userInfoPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "借阅信息"));	
		this.add(userInfoPanel);
		
		
		
		String[] columnNames ={"ID","书名","作者","珍本","借阅情况","续借情况","借阅时间","应还时间"};
		mode=new DefaultTableModel(columnNames, 0);
		JTable userTable = new JTable(mode);
		userTable.setEnabled(false);
		userInfoPanel.add(new JScrollPane(userTable));
		
		
		
		//导入用户信息
		for(int i=0;i<Library.library.users.size();i++){
			Student s=Library.library.users.get(i);
			ID=s.getID();
			combobox_ID.addItem(ID);
		}
	}
	
	
	
	
	
	
	
	//同步显示用户信息
    public void itemStateChanged(ItemEvent e) {
    	if(combobox_ID.getSelectedItem()!=null){
    		ID=(Integer)combobox_ID.getSelectedItem();
    		for(int i=0;i<Library.library.users.size();i++){
    			if(Library.library.users.get(i).getID()==ID){
    				Student s=Library.library.users.get(i);
    				text_username.setText(s.getUsername());
    				text_password.setText(s.getPassword());
    				if(s instanceof Teacher){
    					text_undergraduate.setText("教师");
    				}
    				else{
    					text_undergraduate.setText(s.isUndergraduate()?"本科生":"研究生");
    				}
    				break;
    			}
    		}
    	}
	}
    
    
    
    
    
    
    
	//按钮监听
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand()=="查询"){
			if(combobox_ID.getSelectedItem()!=null){
				search();
			}	
		}	
	}
	
	
	
	
	
	public void search(){
		//清空图书信息面板
		while(mode.getRowCount()!=0){
			mode.removeRow(0);
		}
		ID=(Integer)combobox_ID.getSelectedItem();
		for(int i=0;i<Library.library.users.size();i++){
			if(Library.library.users.get(i).getID()==ID){
				Student s=Library.library.users.get(i);
				if(s.getBooks().size()==0){
					Client.remind("该用户未借阅任何图书！");
					return;
				}
				for(int j=0;j<s.getBooks().size();j++){
					Book b=s.getBooks().get(j);
					bookID=b.getID();
					name=b.getName();
					author=b.getAuthor();
					rare=b.isRare();
					renewed=b.isRenewed();
    				borrow_time=b.getBorrowTime();
    				return_time=b.getReturnTime();
					Object rowData[]={bookID,name,author,rare?"是":"否","已借出",renewed?"已续借":"未续借",borrow_time,return_time};
					mode.addRow(rowData);
				}
			    break;
			 }	
		 }	
	}
}
