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
		
		
		
		//��ѯ���
		JPanel searchPanel=new JPanel();
		searchPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "��ѯ����"));
		this.add(searchPanel);
		
		
		searchPanel.add(new JLabel("�û��˺�:"));
		text_username=new JTextField(14);
		searchPanel.add(text_username);
		

		
		button_search=new JButton("��ѯ");
		button_search.addActionListener(this);
		searchPanel.add(button_search);
		


		
		
		//��ѯ������
		JPanel resultPanel=new JPanel();
		resultPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "��ѯ���"));
		this.add(resultPanel);
		
		
		
		String[] columnNames ={"ID","�û��˺�","�û�����","ѧ��"};
		mode = new DefaultTableModel(columnNames, 0);
		JTable userTable = new JTable(mode);
		userTable.setEnabled(false);
		resultPanel.add(new JScrollPane(userTable));
		

		
		
		
		//�����û���Ϣ
		for(int i=0;i<Library.library.users.size();i++){
			Student s=Library.library.users.get(i);
			ID=s.getID();
			username=s.getUsername();
			password=s.getPassword();
			undergraduate=s.isUndergraduate();
			if(s instanceof Teacher){
				Object rowData[]={ID,username,password,"��ʦ"};
				mode.addRow(rowData);
			}
			else{
				Object rowData[]={ID,username,password,undergraduate?"������":"�о���"};
				mode.addRow(rowData);
			}
		}
	}
	
	
	
	
	
	
	//��ť����
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand()=="��ѯ"){
			Client.sendRequest();
			this.searchUser();
			Client.returnInfo();
		}
	}
	
	
	
	
	
	public void searchUser(){
		//���֮ǰ����
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
					Object rowData[]={ID,username,password,"��ʦ"};
					mode.addRow(rowData);
				}
				else{
					Object rowData[]={ID,username,password,undergraduate?"������":"�о���"};
					mode.addRow(rowData);
				}
				search_exist=true;
			}
		}
		if(!search_exist){
			Client.remind("��Ҫ��ѯ���û������ڣ�");	
		}	
	}

}
