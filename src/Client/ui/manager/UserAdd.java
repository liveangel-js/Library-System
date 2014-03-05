package Client.ui.manager;



import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.*;

import Client.ui.Client;
import Common.Library;
import Common.Student;
import Common.Teacher;



public class UserAdd extends JPanel implements ActionListener  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int ID;
	private String username;
	private String password;
	private boolean undergraduate;
	private JTextField text_ID;
	private JTextField text_username;
	private JTextField text_password;
	private JComboBox combobox_undergraduate;
	private JButton button_add;
	private DefaultTableModel mode;
	ManagerPanel mp;
	
	
	
	public UserAdd(ManagerPanel mp){
		this.mp=mp;
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		
		
		//添加用户面板
		JPanel addPanel=new JPanel();
		addPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "添加用户"));
		this.add(addPanel);
		
		
		
		addPanel.add(new JLabel("ID："));
		text_ID=new JTextField(""+(Student.getNextID()),8);
		text_ID.setHorizontalAlignment(JTextField.CENTER);
		text_ID.setEditable(false);
		addPanel.add(text_ID);
		
		
		
		addPanel.add(new JLabel("用户账号："));
		text_username=new JTextField(10);
		addPanel.add(text_username);
		
		
		
		addPanel.add(new JLabel("用户密码："));
		text_password=new JTextField(10);
		addPanel.add(text_password);

		
		
		addPanel.add(new JLabel("学历："));
		String undergraduates[]={"本科生","研究生","教师"};
		combobox_undergraduate=new JComboBox(undergraduates);
		addPanel.add(combobox_undergraduate);

		
		
		button_add=new JButton("添加");
		button_add.addActionListener(this);
		addPanel.add(button_add);

		
		
		
		
		
		//用户信息面板
		JPanel userPanel=new JPanel();
	    //bookPanel.setLayout(new BorderLayout());
		userPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "用户信息"));	
		this.add(userPanel);
		
		
		
		String[] columnNames ={"ID","用户账号","用户密码","学历"};
		mode = new DefaultTableModel(columnNames, 0);
		JTable userTable = new JTable(mode);
		userTable.setEnabled(false);
		userPanel.add(new JScrollPane(userTable));

		
		
		
		
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
		if(e.getActionCommand()=="添加"){
			this.addUser();
		}	
	}
	
	
	
	
	
	//添加用户
	public void addUser(){
		ID=Student.getNextID();
		username=text_username.getText();
		password=text_password.getText();
		undergraduate=false;
		if(username.equals("")||password.equals("")){
			Client.remind("请输入完整信息！");
			return;
		}
		for(int i=0;i<Library.library.users.size();i++){
			if(Library.library.users.get(i).getUsername().equals(username)){
				Client.remind("该用户名已存在，请重新输入！");
				return;
			}
		}
		if(combobox_undergraduate.getSelectedItem()=="教师"){
			Client.sendRequest();
			this.addTeacher();
			Client.returnInfo();
		}
		else{
			Client.sendRequest();
			this.addStudent();
			Client.returnInfo();
		}
		
		
		
		//同步用户信息面板
		while(mode.getRowCount()!=0){
			mode.removeRow(0);
		}
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
		Client.remind("用户添加成功！");
	}
	
	
	
	
	//添加学生用户
	public void addStudent(){
		if(combobox_undergraduate.getSelectedItem()=="本科生"){
			undergraduate=true;
		}
		Student s=new Student(username, password, undergraduate);
		text_ID.setText(""+(Student.getNextID()));
	}
	
	
	
	
	
	//添加教师用户
	public void addTeacher(){
		Student t=new Teacher(username, password);
		text_ID.setText(""+(Student.getNextID()));
	}
	
	
}
