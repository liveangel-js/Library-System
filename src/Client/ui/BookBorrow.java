package Client.ui;



import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.*;

import Common.Book;
import Common.Library;
import Common.Teacher;




public class BookBorrow extends JPanel implements ActionListener,ItemListener  {
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
	private JTextField text_rare;
	private JTextField text_borrowed;
	private JButton button_borrow;
	private JButton button_request;
	private DefaultTableModel mode;
	
	
	
	public BookBorrow(){
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		
		
		//����ͼ�����
		JPanel borrowPanel=new JPanel();
		borrowPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "����ͼ��"));
		this.add(borrowPanel);
		
		
		
		borrowPanel.add(new JLabel("ID��"));
		combobox_ID=new JComboBox();
		combobox_ID.addItemListener(this);
		borrowPanel.add(combobox_ID);

	
		
		borrowPanel.add(new JLabel("������"));
		text_name=new JTextField(10);
		text_name.setEditable(false);
		borrowPanel.add(text_name);

		
		
		borrowPanel.add(new JLabel("���ߣ�"));
		text_author=new JTextField(10);
		text_author.setEditable(false);
		borrowPanel.add(text_author);
		
		
		
		borrowPanel.add(new JLabel("�䱾��"));
		text_rare=new JTextField(3);
		text_rare.setHorizontalAlignment(JTextField.CENTER);
		text_rare.setEditable(false);
		borrowPanel.add(text_rare);
		
		
		
		borrowPanel.add(new JLabel("���������"));
		text_borrowed=new JTextField(5);
		text_borrowed.setHorizontalAlignment(JTextField.CENTER);
		text_borrowed.setEditable(false);
		borrowPanel.add(text_borrowed);
		
		
		
		button_borrow=new JButton("����");
		button_borrow.addActionListener(this);
		borrowPanel.add(button_borrow);

		
		if(Client.user instanceof Teacher){
			button_request=new JButton("Ҫ����ǰ�黹");
			button_request.addActionListener(this);
			borrowPanel.add(button_request);
		}

		
		
		
		
		
		//ͼ����Ϣ���
		JPanel bookPanel=new JPanel();
		//bookPanel.setLayout(new BorderLayout());
		bookPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "ͼ����Ϣ"));	
		this.add(bookPanel);
		
		
		
		String[] columnNames ={"ID","����","����","�䱾","�������"};
		mode=new DefaultTableModel(columnNames, 0);
		JTable bookTable = new JTable(mode);
		bookTable.setEnabled(false);
		bookPanel.add(new JScrollPane(bookTable));

		
		
		
		
		//����ͼ����Ϣ
		for(int i=0;i<Library.library.books.size();i++){
			Book b=Library.library.books.get(i);
			ID=b.getID();
			combobox_ID.addItem(ID);
			name=b.getName();
			author=b.getAuthor();
			rare=b.isRare();
			borrowed=b.isBorrowed();
			Object rowData[]={ID,name,author,rare?"��":"��",b.isBorrowed()?"�ѽ��":"δ���"};
			mode.addRow(rowData);
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
    				text_borrowed.setText(b.isBorrowed()?"�ѽ��":"δ���");
    				break;
    			}
    		}	
    	}
	}
	
	
    
    
    
	
	//��ť����
	public void actionPerformed(ActionEvent e) {
		if(combobox_ID.getSelectedItem()!=null){
			if(e.getActionCommand()=="����"){
				Client.sendRequest();
				this.borrowBook();
				Client.returnInfo();
				return;
			}
			if(e.getActionCommand()=="Ҫ����ǰ�黹"){
				Client.sendRequest();
				this.requestReturnInAdvance();
				Client.returnInfo();
				return;
			}

		}	
	}
	
	

	
	
	
	
	//����ͼ��
	public void borrowBook(){
		ID=(Integer) combobox_ID.getSelectedItem();
		for(int i=0;i<Library.library.books.size();i++){
			if(Library.library.books.get(i).getID()==ID){
				Book b=Library.library.books.get(i);
				if(b.isBorrowed()){
					Client.remind("��Ҫ���ĵ����Ѿ������˽��ģ�");
					return;
				}
				else{
					if(b.isRare()&&Client.user.isUndergraduate()){
						Client.remind("��Ҫ���ĵ���Ϊ�䱾������Ȩ�޽��Ĵ��飡");
						return;
					}
					else{
						b.borrowedBy(Client.user);
						Client.user.borrow(b);
					}
				}
			}
		}
		
		
		
		
		//ͬ������ͼ�����
		text_borrowed.setText("�ѽ��");
		
	
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
		Client.remind("ͼ����ĳɹ���");
	}	
	
	
	
	public void requestReturnInAdvance(){
		ID=(Integer) combobox_ID.getSelectedItem();
		for(int i=0;i<Library.library.books.size();i++){
			if(Library.library.books.get(i).getID()==ID){
				Book b=Library.library.books.get(i);
				if(b.isBorrowed()){
					if(b.getBorrower() instanceof Teacher){
						Client.remind("��ͼ�鱻��ʦ���裬����Ҫ����ǰ�黹");
					}
					else{
						if(b.isRequest()){
							Client.remind("�����Ѿ�����һ����ʦԤԼ");
						}
						else{
							Client.user.getRequestBooks().add(b);
							b.getBorrower().getRequestBooks().add(b);
							b.setRequest(true);
							b.setRequester(Client.user);
							Client.remind("�����ѷ���");
						}
					}
				}
				else{
					Client.remind("��ͼ��δ����������ֱ�ӽ���");
				}
			}
		}
	}
	
}
