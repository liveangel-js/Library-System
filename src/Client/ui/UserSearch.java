package Client.ui;




import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.*;

import Client.ui.manager.ManagerPanel;
import Common.Library;
import Common.Student;
import Common.Teacher;



public class UserSearch extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int ID;
	private String username;
	private String password;
	private boolean undergraduate;
	private boolean search_exist;
	private JTextField text_username;
	private JButton button_search;
	private DefaultTableModel mode;
	ManagerPanel mp;


	
	
	public UserSearch(ManagerPanel mp){
		this.mp=mp;
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		
		
		//查询面板
		JPanel searchPanel=new JPanel();
		searchPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "查询条件"));
		this.add(searchPanel);
		
		
		searchPanel.add(new JLabel("用户账号:"));
		text_username=new JTextField(14);
		searchPanel.add(text_username);
		

		
		button_search=new JButton("查询");
		button_search.addActionListener(this);
		searchPanel.add(button_search);
		


		
		
		//查询结果面板
		JPanel resultPanel=new JPanel();
		resultPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "查询结果"));
		this.add(resultPanel);
		
		
		
		String[] columnNames ={"ID","用户账号","用户密码","学历"};
		mode = new DefaultTableModel(columnNames, 0);
		JTable userTable = new JTable(mode);
		userTable.setEnabled(false);
		resultPanel.add(new JScrollPane(userTable));
		

		
		
		
		//导入用户信息
		for(int i=0;i<Library.library.users.size();i++){
			Student s=Library.library.users.get(i);
			ID=s.getID();
			username=s.getUsername();
			password=s.getPassword();
			undergraduate=s.isUndergraduate();
			if(s instanceof Teacher){
				Object rowData[]={ID,username,password,"教师"};
				mode.addRow(rowData);
			}
			else{
				Object rowData[]={ID,username,password,undergraduate?"本科生":"研究生"};
				mode.addRow(rowData);
			}
		}
	}
	
	
	
	
	
	
	//按钮监听
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand()=="查询"){
			Client.sendRequest();
			this.searchUser();
			Client.returnInfo();
		}
	}
	
	
	
	
	
	public void searchUser(){
		//清空之前内容
		while(mode.getRowCount()!=0){
			mode.removeRow(0);
		}
		
		
		search_exist=false;
		username=text_username.getText();
		for(int i=0;i<Library.library.users.size();i++){
			if(Library.library.users.get(i).getUsername().equals(username)){
				Student s=Library.library.users.get(i);
				ID=s.getID();
				username=s.getUsername();
				password=s.getPassword();
				undergraduate=s.isUndergraduate();
				if(s instanceof Teacher){
					Object rowData[]={ID,username,password,"教师"};
					mode.addRow(rowData);
				}
				else{
					Object rowData[]={ID,username,password,undergraduate?"本科生":"研究生"};
					mode.addRow(rowData);
				}
				search_exist=true;
			}
		}
		if(!search_exist){
			Client.remind("你要查询的用户不存在！");	
		}	
	}

}
