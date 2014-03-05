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
		
		
		
		//个人信息面板
		JPanel userInfoPanel=new JPanel();
		userInfoPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "个人信息"));
		this.add(userInfoPanel);
		
		
		
		userInfoPanel.add(new JLabel("ID："));
		text_ID=new JTextField(Client.user.getID()+"",8);
		text_ID.setHorizontalAlignment(JTextField.CENTER);
		text_ID.setEditable(false);
		userInfoPanel.add(text_ID);
		
		

		userInfoPanel.add(new JLabel("用户账号："));
		text_username=new JTextField(Client.user.getUsername(),10);
		text_username.setHorizontalAlignment(JTextField.CENTER);
		text_username.setEditable(false);
		userInfoPanel.add(text_username);

		
		

		userInfoPanel.add(new JLabel("用户密码："));
		text_password=new JTextField(Client.user.getPassword(),10);
		text_password.setHorizontalAlignment(JTextField.CENTER);
		userInfoPanel.add(text_password);

		
		
		userInfoPanel.add(new JLabel("学历："));
		if(Client.user instanceof Teacher){
			text_undergraduate=new JTextField("教师",6);
			
		}
		else{
			text_undergraduate=new JTextField(Client.user.isUndergraduate()?"本科生":"研究生",6);
		}
		text_undergraduate.setHorizontalAlignment(JTextField.CENTER);
		text_undergraduate.setEditable(false);
		userInfoPanel.add(text_undergraduate);

		
		
		button_modify=new JButton("修改");
		button_modify.addActionListener(this);
		userInfoPanel.add(button_modify);

		
		
		
		//图书借阅信息面板
		JPanel bookPanel=new JPanel();
		bookPanel.setLayout(new BorderLayout());
		bookPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "借阅信息"));	
		this.add(bookPanel);
		
		
		
		String[] columnNames ={"ID","书名","作者","珍本","续借情况","借阅时间","应还时间"};
		mode=new DefaultTableModel(columnNames, 0);
		JTable bookTable = new JTable(mode);
		bookTable.setEnabled(false);
		bookPanel.add(new JScrollPane(bookTable));

		
		
		
		
		//载入借阅图书信息
		for(int i=0;i<Client.user.getBooks().size();i++){
			Book b=Client.user.getBooks().get(i);
			ID=b.getID();
			name=b.getName();
			author=b.getAuthor();
			rare=b.isRare();
			renewed=b.isRenewed();
			borrow_time=b.getBorrowTime();
			return_time=b.getReturnTime();
			Object rowData[]={ID,name,author,rare?"是":"否",renewed?"已续借":"未续借",borrow_time,return_time};
			mode.addRow(rowData);
		}	
	}
	

	
	
	
	//按钮监听
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand()=="修改"){
			Client.sendRequest();
			this.modifyPassword();
			Client.returnInfo();
		}	
	}
	
	
	
	
	
	public void modifyPassword(){
		if(text_password.getText().equals("")){
			Client.remind("密码不能为空！");
		}
		else{
			Client.user.setPassword(text_password.getText());
			Client.remind("密码修改成功！");
		}
	}
	
	

	
	
	
	

}
