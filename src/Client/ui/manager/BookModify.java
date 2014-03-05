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



public class BookModify extends JPanel implements ActionListener,ItemListener  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int ID;
	private String name;
	private String author;
	private boolean rare;
	private boolean borrowed;
	private JComboBox combobox_ID;
	private JTextField text_name;
	private JTextField text_author;
	private JComboBox combobox_rare;
	private JButton button_modify;
	private DefaultTableModel mode;
	ManagerPanel mp;
	
	
	
	public BookModify(ManagerPanel mp){
		this.mp=mp;
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		
		
		//�޸�ͼ�����
		JPanel modifyPanel=new JPanel();
		modifyPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "�޸�ͼ��"));
		this.add(modifyPanel);
		
		

		modifyPanel.add(new JLabel("ID��"));
		combobox_ID=new JComboBox();
		combobox_ID.addItemListener(this);
		modifyPanel.add(combobox_ID);
		
		

		modifyPanel.add(new JLabel("������"));
		text_name=new JTextField(10);
		modifyPanel.add(text_name);
		
		
		
		modifyPanel.add(new JLabel("���ߣ�"));
		text_author=new JTextField(10);
		modifyPanel.add(text_author);

		
		
		modifyPanel.add(new JLabel("�䱾��"));
		String string_rare[]={"��","��"};
		combobox_rare=new JComboBox(string_rare);
		modifyPanel.add(combobox_rare);
		
		
		
		button_modify=new JButton("�޸�");
		button_modify.addActionListener(this);
		modifyPanel.add(button_modify);

		
				
		
		
		JPanel bookPanel=new JPanel();
		//bookPanel.setLayout(new BorderLayout());
		bookPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "ͼ����Ϣ"));	
		this.add(bookPanel);
		
		
		String[] columnNames ={"ISBN","����","����","������","���"};
		mode = new DefaultTableModel(columnNames, 0);
		JTable bookTable = new JTable(mode);
		bookTable.setEnabled(false);
		bookPanel.add(new JScrollPane(bookTable));

		
		update();

		
		
		
		
		//����ͼ����Ϣ
//		for(int i=0;i<Library.library.books.size();i++){
//			Book b=Library.library.books.get(i);
//			ID=b.getID();
//			combobox_ID.addItem(ID);
//			name=b.getName();
//			author=b.getAuthor();
//			rare=b.isRare();
//			borrowed=b.isBorrowed();
//			Object rowData[]={ID,name,author,rare?"��":"��",borrowed?"�ѽ��":"δ���"};
//			mode.addRow(rowData);
//		}	
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
		if(e.getActionCommand()=="�޸�"){
			if(combobox_ID.getSelectedItem()!=null){
				Client.sendRequest();
				this.modifyBook();
				Client.returnInfo();
			}
		}	
	}
	
	
	
	
	
	//ͬ����ʾ�鼮��Ϣ
    public void itemStateChanged(ItemEvent e) {
    	if(combobox_ID.getSelectedItem()!=null){
    		ID=(Integer)combobox_ID.getSelectedItem();
    		for(int i=0;i<Library.library.books.size();i++){
    			if(ID==Library.library.books.get(i).getID()){
    				Book b=Library.library.books.get(i);
    				text_name.setText(b.getName());
    				text_author.setText(b.getAuthor());
    				combobox_rare.setSelectedItem(b.isRare()?"��":"��");
    				break;
    			}
    		}
    	}
	}
	
	
    
    
	
	//���ͼ��
	public void modifyBook(){
		ID=(Integer) combobox_ID.getSelectedItem();
		for(int i=0;i<Library.library.books.size();i++){
            if(Library.library.books.get(i).getID()==ID){	
        		name=text_name.getText();
        		author=text_author.getText();
        		rare=false;
        		borrowed=false;
        		if(combobox_rare.getSelectedItem()=="��"){
        			rare=true;
        		}
        		if(name.equals("")||author.equals("")){
        			Client.remind("������������Ϣ��");
        		}
        		else{
        			Library.library.books.get(i).setBook(name,author,rare);
        		}		
				break;
			}
		}
		
		
		
		
		
		//ͬ��ͼ����Ϣ���
		while(mode.getRowCount()!=0){
			mode.removeRow(0);
		}
		for(int i=0;i<Library.library.books.size();i++){
			Book b=Library.library.books.get(i);
			ID=b.getID();
			name=b.getName();
			author=b.getAuthor();
			rare=b.isRare();
			borrowed=b.isBorrowed();
			Object rowData[]={ID,name,author,rare?"��":"��",borrowed?"�ѽ��":"δ���"};
			mode.addRow(rowData);
		}	
		Client.remind("ͼ���޸ĳɹ���");
	}

}
