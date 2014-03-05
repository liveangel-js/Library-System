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
		
		
		//�޸��û����
		JPanel modifyPanel=new JPanel();
		modifyPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "�޸��û�"));
		this.add(modifyPanel);
		
		
		modifyPanel.add(new JLabel("ID��"));
		combobox_ID=new JComboBox();
		combobox_ID.addItemListener(this);
		modifyPanel.add(combobox_ID);
		
		

		modifyPanel.add(new JLabel("�û��˺ţ�"));
		text_username=new JTextField(10);
		modifyPanel.add(text_username);

		
		

		modifyPanel.add(new JLabel("�û����룺"));
		text_password=new JTextField(10);
		modifyPanel.add(text_password);

		
		
		modifyPanel.add(new JLabel("ѧ����"));
		String undergraduates[]={"������","�о���","��ʦ"};
		combobox_undergraduate=new JComboBox(undergraduates);
		modifyPanel.add(combobox_undergraduate);

		
		
		button_modify=new JButton("�޸�");
		button_modify.addActionListener(this);
		modifyPanel.add(button_modify);

		
			
		
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
			combobox_ID.addItem(ID);
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
	
	
	
	//ͬ����ʾ�û���Ϣ
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
    					combobox_undergraduate.addItem("��ʦ");
    				}
    				else{
    					combobox_undergraduate.removeAllItems();
    					combobox_undergraduate.addItem("������");
    					combobox_undergraduate.addItem("�о���");
    					combobox_undergraduate.setSelectedItem(s.isUndergraduate()?"������":"�о���");
    				}
    				break;
    			}
    		}
    	}
	}
	
	
    
    
	
	//��ť����
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand()=="�޸�"){
			if(combobox_ID.getSelectedItem()!=null){
				Client.sendRequest();
				this.modifyUser();
				Client.returnInfo();
			}
		}	
	}
	
	
	
	
	
	//�޸��û�
	public void modifyUser(){
		ID=(Integer) combobox_ID.getSelectedItem();
		username=text_username.getText();
		password=text_password.getText();
		undergraduate=false;
		if(username.equals("")||password.equals("")){
			Client.remind("������������Ϣ��");
			return;
		}
		for(int i=0;i<Library.library.users.size();i++){
			if(Library.library.users.get(i).getUsername().equals(username)&&Library.library.users.get(i).getID()!=ID){
				Client.remind("���û����Ѵ��ڣ����������룡");
				return;
			}
		}
		if(combobox_undergraduate.getSelectedItem()=="������"){
			undergraduate=true;
		}
		for(int i=0;i<Library.library.users.size();i++){
			if(Library.library.users.get(i).getID()==ID){
				Library.library.users.get(i).setStudent(username, password, undergraduate);
			}
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
		Client.remind("�û��޸ĳɹ�");
	}
}
