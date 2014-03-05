package Client.ui;


import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.*;

import Client.ui.manager.ManagerPanel;
import Common.Book;
import Common.Library;
import Common.Teacher;



public class BookInfo extends JPanel implements ActionListener,ItemListener  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int ID;
	private String name;
	private String author;
	private boolean rare;
	private boolean borrowed;
	private boolean renewed;
	private String borrow_time;
	private String return_time;
	private int userID;
	private String username;
	private String password;
	private boolean undergraduate;
	private JComboBox combobox_ID;
	private JTextField text_name;
	private JTextField text_author;
	private JTextField text_rare;
	private JButton button_search;
	private DefaultTableModel mode;
	ManagerPanel mp;
	
	
	
	
	
	public BookInfo(ManagerPanel mp){
		this.mp=mp;
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		
		
		//��ѯͼ�����
		JPanel searchPanel=new JPanel();
		searchPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "ͼ����Ϣ"));
		this.add(searchPanel);
		
		
		
		searchPanel.add(new JLabel("ID��"));
		combobox_ID=new JComboBox();
		combobox_ID.addItemListener(this);
		searchPanel.add(combobox_ID);

	
		
		searchPanel.add(new JLabel("������"));
		text_name=new JTextField(10);
		text_name.setEditable(false);
		searchPanel.add(text_name);

		
		
		searchPanel.add(new JLabel("���ߣ�"));
		text_author=new JTextField(10);
		text_author.setEditable(false);
		searchPanel.add(text_author);
		
		
		
		searchPanel.add(new JLabel("�䱾��"));
		text_rare=new JTextField(3);
		text_rare.setHorizontalAlignment(JTextField.CENTER);
		text_rare.setEditable(false);
		searchPanel.add(text_rare);
		
		
		
		button_search=new JButton("��ѯ");
		button_search.addActionListener(this);
		searchPanel.add(button_search);

		
		
		
		//ͼ�������Ϣ���
		JPanel bookInfoPanel=new JPanel();
		bookInfoPanel.setLayout(new BorderLayout());
		bookInfoPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "������Ϣ"));	
		this.add(bookInfoPanel);
		
		
		
		String[] columnNames ={"ID","�û��˺�","�û�����","ѧ��","�������","�������","����ʱ��","Ӧ��ʱ��"};
		mode=new DefaultTableModel(columnNames, 0);
		JTable bookInfoTable = new JTable(mode);
		bookInfoTable.setEnabled(false);
		bookInfoPanel.add(new JScrollPane(bookInfoTable));

		
		
		
		
		
		//����ͼ�������Ϣ
		for(int i=0;i<Library.library.books.size();i++){
			Book b=Library.library.books.get(i);
			ID=b.getID();
			combobox_ID.addItem(ID);
			borrowed=b.isBorrowed();
			if(borrowed){
				name=b.getName();
				author=b.getAuthor();
				rare=b.isRare();
				renewed=b.isRenewed();
				borrow_time=b.getBorrowTime();
				return_time=b.getReturnTime();
				userID=b.getBorrower().getID();
				username=b.getBorrower().getUsername();
				password=b.getBorrower().getPassword();
				undergraduate=b.getBorrower().isUndergraduate();
				if(b.getBorrower() instanceof Teacher){
					Object rowData[]={userID,username,password,"��ʦ","�ѽ��",renewed?"������":"δ����",borrow_time,return_time};
					mode.addRow(rowData);
				}
				else{
					Object rowData[]={userID,username,password,undergraduate?"������":"�о���","�ѽ��",renewed?"������":"δ����",borrow_time,return_time};
					mode.addRow(rowData);
				}
			}
			else{
				Object rowData[]={"","","","","δ���","","",""};
				mode.addRow(rowData);
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
    				text_rare.setText(b.isRare()?"��":"��");
    				break;
    			}
    		}	
    	}
	}
    
    
    
	
    
    
    
	//��ť����
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand()=="��ѯ"){
			if(combobox_ID.getSelectedItem()!=null){
				Client.sendRequest();
				this.search();
				Client.returnInfo();
			}	
		}	
	}
	
	
	
	
	
	public void search(){
		//���ͼ����Ϣ���
		while(mode.getRowCount()!=0){
			mode.removeRow(0);
		}
		
		ID=(Integer) combobox_ID.getSelectedItem();
		for(int i=0;i<Library.library.books.size();i++){
            if(Library.library.books.get(i).getID()==ID){	
    			Book b=Library.library.books.get(i);
    			ID=b.getID();
    			borrowed=b.isBorrowed();
    			if(borrowed){
    				name=b.getName();
    				author=b.getAuthor();
    				rare=b.isRare();
    				renewed=b.isRenewed();		
    				borrow_time=b.getBorrowTime();
    				return_time=b.getReturnTime();
    				userID=b.getBorrower().getID();
    				username=b.getBorrower().getUsername();
    				password=b.getBorrower().getPassword();
    				undergraduate=b.getBorrower().isUndergraduate();
    				if(b.getBorrower() instanceof Teacher){
    					Object rowData[]={userID,username,password,"��ʦ","�ѽ��",renewed?"������":"δ����",borrow_time,return_time};
    					mode.addRow(rowData);
    				}
    				else{
    					Object rowData[]={userID,username,password,undergraduate?"������":"�о���","�ѽ��",renewed?"������":"δ����",borrow_time,return_time};
    					mode.addRow(rowData);
    				}
    			}
    			else{
    				Object rowData[]={"","","","","δ���","","",""};
    				mode.addRow(rowData);
    			}
				break;
			}
		}
	}
}
