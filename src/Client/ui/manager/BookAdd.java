package Client.ui.manager;



import javax.swing.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.*;

import Client.ui.Client;
import Common.Book;
import Common.Library;
import Mysql.Mysql;



public class BookAdd extends JPanel implements ActionListener  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int ID;
	private String name;
	private String author;
	private boolean rare;
	private boolean borrowed;
	private JTextField text_ISBN;
	private JTextField text_name;
	private JTextField text_author;
	private JTextField text_publisher;
	private JTextField text_type;
	private JComboBox combobox_rare;
	private JButton button_add;
	private DefaultTableModel mode;
	ManagerPanel mp;
	
	
	
	public BookAdd(ManagerPanel mp){
		this.mp=mp;
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		
		
		
		//���ͼ�����
		JPanel addPanel=new JPanel();
		addPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "���ͼ��"));
		this.add(addPanel);
		
		
		
		addPanel.add(new JLabel("ID��"));
		text_ISBN=new JTextField(13);
		text_ISBN.setHorizontalAlignment(JTextField.CENTER);
		text_ISBN.setEditable(true);
		addPanel.add(text_ISBN);

		
		
		addPanel.add(new JLabel("������"));
		text_name=new JTextField(10);
		addPanel.add(text_name);

		

		addPanel.add(new JLabel("���ߣ�"));
		text_author=new JTextField(10);
		addPanel.add(text_author);
		
		
		
//		addPanel.add(new JLabel("�䱾��"));
		String string_rare[]={"��","��"};
		combobox_rare=new JComboBox(string_rare);
//		addPanel.add(combobox_rare);
		
		addPanel.add(new JLabel("������"));
		text_publisher = new JTextField(10);
		addPanel.add(text_publisher);
		addPanel.add(new JLabel("����"));
		text_type = new JTextField(10);
		addPanel.add(text_type);
		

		button_add=new JButton("���");
		button_add.addActionListener(this);
		addPanel.add(button_add);

		
		
		
		
		
		//ͼ����Ϣ���
		JPanel bookPanel=new JPanel();
		//bookPanel.setLayout(new BorderLayout());
		bookPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "ͼ����Ϣ"));	
		this.add(bookPanel);
		
		
		String[] columnNames ={"ISBN","����","����","������","���"};
		mode = new DefaultTableModel(columnNames, 0);
		JTable bookTable = new JTable(mode);
		bookTable.setEnabled(false);
		bookPanel.add(new JScrollPane(bookTable));	
		//����ͼ����Ϣ
//		for(int i=0;i<Library.library.books.size();i++){
//			Book b=Library.library.books.get(i);
//			ID=b.getID();
//			name=b.getName();
//			author=b.getAuthor();
//			rare=b.isRare();
//			borrowed=b.isBorrowed();
//			Object rowData[]={ID,name,author,rare?"��":"��",borrowed?"�ѽ��":"δ���"};
//			mode.addRow(rowData);
//		}
		update();
	}
	
	public void update(){
		while(mode.getRowCount()!=0){
			mode.removeRow(0);
		}
		Mysql mysql = Mysql.getInstance();
		ResultSet rs = mysql.query("select * from booklist order by publisher ");
		try {
			while(rs.next()){
				Object rowData[]={rs.getString("isbn"),rs.getString("name"),rs.getString("author"),rs.getString("publisher"),rs.getString("booktype")};
				mode.addRow(rowData);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	//��ť����
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand()=="���"){
			Client.sendRequest();
			this.addBook();
			Client.returnInfo();
		}	
	}
	
	
	
	
	
	//���ͼ��
	public void addBook(){
		ID=Book.getNextID();
		
		
//		ResultSet rs = Mysql.getInstance().query("select * from booklist");
//		try {
//			while(rs.next()){
//				ID=Book.getNextID();
//				name=rs.getString("name");
//				author=rs.getString("author");
//				Book b=new Book(name,author,false);
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return ;
		
		
		name=text_name.getText();
		author=text_author.getText();
		rare=false;
		borrowed=false;
		combobox_rare.setSelectedItem("��");
		if(combobox_rare.getSelectedItem()=="��"){
			rare=true;
		}
		if(name.equals("")||author.equals("")){
			Client.remind("������������Ϣ��");
			return;
		}
		else{
			Book b=new Book(name,author,rare);
//			text_ISBN.setText(""+(Book.getNextID()));
		}
		
		
		
		
		
		
		String [] cc = {"����","��ѧ","����ѧ����","����","����","����","��ѧ","��ҵ����","��Ȼ��ѧ����","���պ���","��ͨ����","�ۺ���ͼ��","��ʷ","�����ѧ","����ѧ"};
		int temp =(int)(Math.random()*cc.length);
		if(temp==cc.length) temp--;

		text_type.setText(cc[temp]);
		
		
		Mysql mysql = Mysql.getInstance();
		Book a = new Book(text_name.getText(), text_author.getText(), text_publisher.getText(), text_ISBN.getText(), text_type.getText());
		mysql.addBook(a);
		update();
		text_ISBN.setText("");
		
		
	}
}
