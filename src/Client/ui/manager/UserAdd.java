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
		
		
		
		//����û����
		JPanel addPanel=new JPanel();
		addPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "����û�"));
		this.add(addPanel);
		
		
		
		addPanel.add(new JLabel("ID��"));
		text_ID=new JTextField(""+(Student.getNextID()),8);
		text_ID.setHorizontalAlignment(JTextField.CENTER);
		text_ID.setEditable(false);
		addPanel.add(text_ID);
		
		
		
		addPanel.add(new JLabel("�û��˺ţ�"));
		text_username=new JTextField(10);
		addPanel.add(text_username);
		
		
		
		addPanel.add(new JLabel("�û����룺"));
		text_password=new JTextField(10);
		addPanel.add(text_password);

		
		
		addPanel.add(new JLabel("ѧ����"));
		String undergraduates[]={"������","�о���","��ʦ"};
		combobox_undergraduate=new JComboBox(undergraduates);
		addPanel.add(combobox_undergraduate);

		
		
		button_add=new JButton("���");
		button_add.addActionListener(this);
		addPanel.add(button_add);

		
		
		
		
		
		//�û���Ϣ���
		JPanel userPanel=new JPanel();
	    //bookPanel.setLayout(new BorderLayout());
		userPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "�û���Ϣ"));	
		this.add(userPanel);
		
		
		
		String[] columnNames ={"ID","�û��˺�","�û�����","ѧ��"};
		mode = new DefaultTableModel(columnNames, 0);
		JTable userTable = new JTable(mode);
		userTable.setEnabled(false);
		userPanel.add(new JScrollPane(userTable));

		
		
		
		
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
		if(e.getActionCommand()=="���"){
			this.addUser();
		}	
	}
	
	
	
	
	
	//����û�
	public void addUser(){
		ID=Student.getNextID();
		username=text_username.getText();
		password=text_password.getText();
		undergraduate=false;
		if(username.equals("")||password.equals("")){
			Client.remind("������������Ϣ��");
			return;
		}
		for(int i=0;i<Library.library.users.size();i++){
			if(Library.library.users.get(i).getUsername().equals(username)){
				Client.remind("���û����Ѵ��ڣ����������룡");
				return;
			}
		}
		if(combobox_undergraduate.getSelectedItem()=="��ʦ"){
			Client.sendRequest();
			this.addTeacher();
			Client.returnInfo();
		}
		else{
			Client.sendRequest();
			this.addStudent();
			Client.returnInfo();
		}
		
		
		
		//ͬ���û���Ϣ���
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
				Object rowData[]={ID,username,password,"��ʦ"};
				mode.addRow(rowData);
			}
			else{
				Object rowData[]={ID,username,password,undergraduate?"������":"�о���"};
				mode.addRow(rowData);
			}
		}
		Client.remind("�û���ӳɹ���");
	}
	
	
	
	
	//���ѧ���û�
	public void addStudent(){
		if(combobox_undergraduate.getSelectedItem()=="������"){
			undergraduate=true;
		}
		Student s=new Student(username, password, undergraduate);
		text_ID.setText(""+(Student.getNextID()));
	}
	
	
	
	
	
	//��ӽ�ʦ�û�
	public void addTeacher(){
		Student t=new Teacher(username, password);
		text_ID.setText(""+(Student.getNextID()));
	}
	
	
}
