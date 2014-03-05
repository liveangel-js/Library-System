package Client.ui;



import java.awt.*;

import javax.swing.*;

import Client.ui.manager.ManagerPanel;
import Common.Library;
import Common.Manager;
import Common.Student;

import java.awt.event.*;



public class Login extends JDialog implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int WIDTH=250;
	public static final int HEIGHT=280;
	public static final int X=Client.X+Client.WIDTH/2-WIDTH/2;
	public static final int Y=Client.Y+Client.HEIGHT/2-HEIGHT/2;
	
	
	private JPanel loginPanel;
	private JPanel settingPanel;
	
	
	
	private JTextField text_username;
	private JPasswordField text_password;
	private JComboBox combobox_userkind; 
	private JButton button_login;	
	private JButton button_set;
	private JButton button_exit;
	private static TextArea text_info;
	private String username;
	private String password;
	
	
	
	private JTextField text_IP;
	private JTextField text_port;
	private JButton button_modify;	
	private JButton button_return;
	private JButton button_recover;
    
	Client c;

	

	
	public Login(Client c){	
		super(c,"ͼ���2.5��½����",true);
		this.setLocation(X,Y);
		this.setSize(WIDTH, HEIGHT);
		this.setResizable(false);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setLayout(new FlowLayout());
		this.c=c;
		
		this.initializeLoginPanel();
		this.initializeSettingPanel();
		this.login();
	}
	
	

	
	
	//��ť����
    public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand()=="��¼"){
			c.connectServer();
			Client.sendRequest();
			System.out.println("�ȴ���������");
			username=text_username.getText(); 
			password=String.valueOf(text_password.getPassword());
			if(combobox_userkind.getSelectedItem()=="��ͨ�û�           "){		
				this.userLogin();
			}	
			if(combobox_userkind.getSelectedItem()=="����Ա"){
				this.managerLogin();
			}
			Client.returnInfo();
			return;
		}
		if(e.getActionCommand()=="����"){
			this.set();
			return;
		}
		if(e.getActionCommand()=="�˳�"){
			System.exit(0);
			return;
		}	
		if(e.getActionCommand()=="�޸�"){
			String IP=text_IP.getText();
			int port=8888;
			try{
				port=Integer.parseInt(text_port.getText());
			}
			catch(NumberFormatException e1){
				Client.remind("����˿ںű���Ϊ����");
				return;
			}
			c.setServer(IP, port);
			Client.remind("�޸ĳɹ�");
			return;
		}
		if(e.getActionCommand()=="����"){
			this.login();
		}	
		if(e.getActionCommand()=="�ָ�"){
			text_IP.setText("127.0.0.1");
			text_port.setText("8888");
			c.setServer("127.0.0.1", 8888);
		}
	}

	
    
    
	
	
	//�û���¼����
	public void userLogin() {	
		int i;
		for(i=0;i<Library.library.users.size();i++){
			Student user=Library.library.users.get(i);
			if((user.getUsername().equals(username))&&(user.getPassword().equals(password))){
				if(!user.isOn()){
					user.setOn(true);
					Client.user=user;
			    	this.setVisible(false);
			    	c.setVisible(true);
			    	c.add(new UserPanel(c));
					return;
				}
				else{
					Client.remind("���û��ѵ�¼�������ظ���¼��");
					return;
				}
			}
		}
		Client.remind("�û��������������");
	}

	
	
	
	
	//����Ա��½����
	public void managerLogin(){
		Manager m=Library.library.manager;
		if((m.getUsername().equals(username))&&(m.getPassword().equals(password))){
			if(!m.isOn()){
				m.setOn(true);
		    	this.setVisible(false);
		    	c.setVisible(true);
				c.add(new ManagerPanel(c));
			}
			else{
				Client.remind("���û��ѵ�¼�������ظ���¼��");
				return;
			}
		}
		else{
			Client.remind("�û��������������");
		}
	}
	
	
	
	
	//��ʼ����¼���
	public void initializeLoginPanel(){
		loginPanel=new JPanel();
		loginPanel.setLayout(new BoxLayout(loginPanel,BoxLayout.Y_AXIS));
		
		
		
		JPanel p1=new JPanel();
		p1.add(new JLabel("�û��˺�"));
		text_username=new JTextField(10);
		p1.add(text_username);
		loginPanel.add(p1);
		
		
		
		
		JPanel p2=new JPanel();
		p2.add(new JLabel("�û�����"));
		text_password=new JPasswordField(10);
		text_password.setEchoChar('\u25cf');      //���û����ַ�Ϊ'��'
		p2.add(text_password);
		loginPanel.add(p2);
		
		
		
		
		JPanel p3=new JPanel();
		p3.add(new JLabel("�û�����"));
		Object userkind[]={"��ͨ�û�           ","����Ա"};
		combobox_userkind=new JComboBox(userkind);
		p3.add(combobox_userkind);
		loginPanel.add(p3);
		
		
			
		JPanel p4=new JPanel();
		button_login=new JButton("��¼");
		button_login.addActionListener(this);
		p4.add(button_login);
		button_set=new JButton("����");
		button_set.addActionListener(this);
		p4.add(button_set);
		button_exit=new JButton("�˳�");
		button_exit.addActionListener(this);
		p4.add(button_exit);
		loginPanel.add(p4);
	
		
		
		JPanel p5=new JPanel();
		text_info=new TextArea("",5,25,TextArea.SCROLLBARS_VERTICAL_ONLY);
		text_info.setEditable(false);
		p5.add(text_info);
		loginPanel.add(p5);
	}
	
	
	
	
	//��ʼ���������
	public void initializeSettingPanel(){
		settingPanel=new JPanel();
		settingPanel.setLayout(new BoxLayout(settingPanel,BoxLayout.Y_AXIS));
		
	    
	    
		JPanel p1=new JPanel();
		p1.add(new JLabel("�� �� �� I  P"));
		text_IP=new JTextField(10);
		text_IP.setText(c.getIP());
		p1.add(text_IP);
		settingPanel.add(p1);
		
		
		JPanel p2=new JPanel();
		p2.add(new JLabel("�������˿�"));
		text_port=new JTextField(10);
		text_port.setText(c.getPort()+"");
		p2.add(text_port);
		settingPanel.add(p2);
		
		
			
		JPanel p3=new JPanel();
		button_modify=new JButton("�޸�");
		button_modify.addActionListener(this);
		p3.add(button_modify);
		button_return=new JButton("����");
		button_return.addActionListener(this);
		p3.add(button_return);
		button_recover=new JButton("�ָ�");
		button_recover.addActionListener(this);
		p3.add(button_recover);
		settingPanel.add(p3);
	}
	
	
	
	public void login(){
	    this.remove(settingPanel);
		this.add(loginPanel);
		this.validate();
		this.repaint();
	}
	
	
	
	public void set(){	
		this.remove(loginPanel);
		this.add(settingPanel);
		this.validate();
		this.repaint();
	}
	
	public static void remind(String string_remind){
		text_info.append(string_remind+"\n");
	}
}
