package Client.ui;



import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;

import Common.Book;
import Common.Teacher;




public class BorrowInfo extends JPanel implements ActionListener  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int ID;
	private String name;
	private String author;
	private boolean rare;
	private boolean renewed;
	private String borrow_time;
	private String return_time;
	private JTextField text_ID;
	private JTextField text_username;
	private JTextField text_password;
	private JTextField text_undergraduate;
	private JButton button_modify;
	private DefaultTableModel mode;
	
	
	
	
	public BorrowInfo(){
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		
		
		//������Ϣ���
		JPanel userInfoPanel=new JPanel();
		userInfoPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "������Ϣ"));
		this.add(userInfoPanel);
		
		
		
		userInfoPanel.add(new JLabel("ID��"));
		text_ID=new JTextField(Client.user.getID()+"",8);
		text_ID.setHorizontalAlignment(JTextField.CENTER);
		text_ID.setEditable(false);
		userInfoPanel.add(text_ID);
		
		

		userInfoPanel.add(new JLabel("�û��˺ţ�"));
		text_username=new JTextField(Client.user.getUsername(),10);
		text_username.setHorizontalAlignment(JTextField.CENTER);
		text_username.setEditable(false);
		userInfoPanel.add(text_username);

		
		

		userInfoPanel.add(new JLabel("�û����룺"));
		text_password=new JTextField(Client.user.getPassword(),10);
		text_password.setHorizontalAlignment(JTextField.CENTER);
		userInfoPanel.add(text_password);

		
		
		userInfoPanel.add(new JLabel("ѧ����"));
		if(Client.user instanceof Teacher){
			text_undergraduate=new JTextField("��ʦ",6);
			
		}
		else{
			text_undergraduate=new JTextField(Client.user.isUndergraduate()?"������":"�о���",6);
		}
		text_undergraduate.setHorizontalAlignment(JTextField.CENTER);
		text_undergraduate.setEditable(false);
		userInfoPanel.add(text_undergraduate);

		
		
		button_modify=new JButton("�޸�");
		button_modify.addActionListener(this);
		userInfoPanel.add(button_modify);

		
		
		
		//ͼ�������Ϣ���
		JPanel bookPanel=new JPanel();
		bookPanel.setLayout(new BorderLayout());
		bookPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "������Ϣ"));	
		this.add(bookPanel);
		
		
		
		String[] columnNames ={"ID","����","����","�䱾","�������","����ʱ��","Ӧ��ʱ��"};
		mode=new DefaultTableModel(columnNames, 0);
		JTable bookTable = new JTable(mode);
		bookTable.setEnabled(false);
		bookPanel.add(new JScrollPane(bookTable));

		
		
		
		
		//�������ͼ����Ϣ
		for(int i=0;i<Client.user.getBooks().size();i++){
			Book b=Client.user.getBooks().get(i);
			ID=b.getID();
			name=b.getName();
			author=b.getAuthor();
			rare=b.isRare();
			renewed=b.isRenewed();
			borrow_time=b.getBorrowTime();
			return_time=b.getReturnTime();
			Object rowData[]={ID,name,author,rare?"��":"��",renewed?"������":"δ����",borrow_time,return_time};
			mode.addRow(rowData);
		}	
	}
	

	
	
	
	//��ť����
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand()=="�޸�"){
			Client.sendRequest();
			this.modifyPassword();
			Client.returnInfo();
		}	
	}
	
	
	
	
	
	public void modifyPassword(){
		if(text_password.getText().equals("")){
			Client.remind("���벻��Ϊ�գ�");
		}
		else{
			Client.user.setPassword(text_password.getText());
			Client.remind("�����޸ĳɹ���");
		}
	}
	
	

	
	
	
	

}
