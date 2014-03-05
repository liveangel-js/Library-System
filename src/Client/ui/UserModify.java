package Client.ui;



import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.*;

import Client.ui.manager.ManagerPanel;
import Common.Library;
import Common.Student;
import Common.Teacher;



public class UserModify extends JPanel implements ActionListener,ItemListener  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int ID;
	private String username;
	private String password;
	private boolean undergraduate;
	private JComboBox combobox_ID;
	private JTextField text_username;
	private JTextField text_password;
	private JComboBox combobox_undergraduate;
	private JButton button_modify;
	private DefaultTableModel mode;
	ManagerPanel mp;
	
	
	
	
	public UserModify(ManagerPanel mp){
		this.mp=mp;
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		
		//修改用户面板
		JPanel modifyPanel=new JPanel();
		modifyPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "修改用户"));
		this.add(modifyPanel);
		
		
		modifyPanel.add(new JLabel("ID："));
		combobox_ID=new JComboBox();
		combobox_ID.addItemListener(this);
		modifyPanel.add(combobox_ID);
		
		

		modifyPanel.add(new JLabel("用户账号："));
		text_username=new JTextField(10);
		modifyPanel.add(text_username);

		
		

		modifyPanel.add(new JLabel("用户密码："));
		text_password=new JTextField(10);
		modifyPanel.add(text_password);

		
		
		modifyPanel.add(new JLabel("学历："));
		String undergraduates[]={"本科生","研究生","教师"};
		combobox_undergraduate=new JComboBox(undergraduates);
		modifyPanel.add(combobox_undergraduate);

		
		
		button_modify=new JButton("修改");
		button_modify.addActionListener(this);
		modifyPanel.add(button_modify);

		
			
		
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
			combobox_ID.addItem(ID);
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
	
	
	
	//同步显示用户信息
    public void itemStateChanged(ItemEvent e) {
    	if(combobox_ID.getSelectedItem()!=null){     
    		ID=(Integer)combobox_ID.getSelectedItem();
    		for(int i=0;i<Library.library.users.size();i++){
    			if(ID==Library.library.users.get(i).getID()){
    				Student s=Library.library.users.get(i);
    				text_username.setText(s.getUsername());
    				text_password.setText(s.getPassword());
    				if(s instanceof Teacher){
    					combobox_undergraduate.removeAllItems();
    					combobox_undergraduate.addItem("教师");
    				}
    				else{
    					combobox_undergraduate.removeAllItems();
    					combobox_undergraduate.addItem("本科生");
    					combobox_undergraduate.addItem("研究生");
    					combobox_undergraduate.setSelectedItem(s.isUndergraduate()?"本科生":"研究生");
    				}
    				break;
    			}
    		}
    	}
	}
	
	
    
    
	
	//按钮监听
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand()=="修改"){
			if(combobox_ID.getSelectedItem()!=null){
				Client.sendRequest();
				this.modifyUser();
				Client.returnInfo();
			}
		}	
	}
	
	
	
	
	
	//修改用户
	public void modifyUser(){
		ID=(Integer) combobox_ID.getSelectedItem();
		username=text_username.getText();
		password=text_password.getText();
		undergraduate=false;
		if(username.equals("")||password.equals("")){
			Client.remind("请输入完整信息！");
			return;
		}
		for(int i=0;i<Library.library.users.size();i++){
			if(Library.library.users.get(i).getUsername().equals(username)&&Library.library.users.get(i).getID()!=ID){
				Client.remind("该用户名已存在，请重新输入！");
				return;
			}
		}
		if(combobox_undergraduate.getSelectedItem()=="本科生"){
			undergraduate=true;
		}
		for(int i=0;i<Library.library.users.size();i++){
			if(Library.library.users.get(i).getID()==ID){
				Library.library.users.get(i).setStudent(username, password, undergraduate);
			}
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
		Client.remind("用户修改成功");
	}
}
