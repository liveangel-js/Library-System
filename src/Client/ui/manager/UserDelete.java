package Client.ui.manager;


import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.*;

import Client.ui.Client;
import Common.Library;
import Common.Student;
import Common.Teacher;



public class UserDelete extends JPanel implements ActionListener,ItemListener  {
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
	private JTextField text_undergraduate;
	private JButton button_delete;
	private DefaultTableModel mode;
	ManagerPanel mp;
	
	
	
	public UserDelete(ManagerPanel mp){
		this.mp=mp;
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		
		//删除用户面板
		JPanel deletePanel=new JPanel();
		deletePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "删除用户"));
		this.add(deletePanel);
			
		
		
		deletePanel.add(new JLabel("ID："));
		combobox_ID=new JComboBox();
		combobox_ID.addItemListener(this);
		deletePanel.add(combobox_ID);
		
		
		

		deletePanel.add(new JLabel("用户账号："));
		text_username=new JTextField(10);
		text_username.setEditable(false);
		deletePanel.add(text_username);
		
		

		deletePanel.add(new JLabel("用户密码："));
		text_password=new JTextField(10);
		text_password.setEditable(false);
		deletePanel.add(text_password);
		
		
		
		deletePanel.add(new JLabel("学历："));
		text_undergraduate=new JTextField(6);
		text_undergraduate.setHorizontalAlignment(JTextField.CENTER);
		text_undergraduate.setEditable(false);
		deletePanel.add(text_undergraduate);
		
		
		
		button_delete=new JButton("删除");
		button_delete.addActionListener(this);
		deletePanel.add(button_delete);

		
		
		
		
		
		//用户信息面板
		JPanel userPanel=new JPanel();
	    //userPanel.setLayout(new BorderLayout());
		userPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "用户信息"));	
		this.add(userPanel);
		
		
		
		String[] columnNames ={"ID","用户账号","用户密码","学历"};
		mode=new DefaultTableModel(columnNames, 0);
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
    	else{
			text_username.setText("");
			text_password.setText("");
			text_undergraduate.setText("");
    	}
	}
	
	
	
	
    
    
	//按钮监听
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand()=="删除"){
			if(combobox_ID.getSelectedItem()!=null){
				Client.sendRequest();
				this.deleteUser();
				Client.returnInfo();
			}
		}	
	}
	
    
    
    
    
    
	//删除用户
	public void deleteUser(){
		ID=(Integer)combobox_ID.getSelectedItem();
		for(int i=0;i<Library.library.users.size();i++){
            if(Library.library.users.get(i).getID()==ID){	
            	for(int j=0;j<Library.library.users.get(i).getBooks().size();j++){
            		Library.library.users.get(i).getBooks().get(j).setBorrowed(false);
            		Library.library.users.get(i).getBooks().get(j).setBorrower(null);
            	}
            	Library.library.users.remove(i);
				combobox_ID.removeItem(ID);
				break;
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
		Client.remind("用户删除成功！");
	}
}
