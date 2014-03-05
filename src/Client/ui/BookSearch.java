package Client.ui;




import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.*;

import Client.ui.manager.ManagerPanel;
import Common.Book;
import Common.Library;




public class BookSearch extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int ID;
	private String name;
	private String author;
	private boolean rare;
	private boolean borrowed;
	private boolean search_exist;
	private JComboBox combobox_way;
	private JTextField text_book;
	private JButton button_search;
	private DefaultTableModel mode;
	UserPanel up;
	ManagerPanel mp;


	
	
	public BookSearch(UserPanel up){
		this();
		this.up=up;
	}
	
	
	public BookSearch(ManagerPanel mp){
		this();
		this.mp=mp;
	}
	
	
	
	
	public BookSearch(){
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		
		
		//��ѯ���
		JPanel searchPanel=new JPanel();
		searchPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "��ѯ����"));
		this.add(searchPanel);
		
		
		searchPanel.add(new JLabel("��ѯ��ʽ��"));
		Object way[]={"������","������"};
		combobox_way=new JComboBox(way);
		searchPanel.add(combobox_way);

		
		text_book=new JTextField(20);
		searchPanel.add(text_book);
		
		
		button_search=new JButton("��ѯ");
		button_search.addActionListener(this);
		searchPanel.add(button_search);
	
		
		
		//��ѯ������
		JPanel resultPanel=new JPanel();
		resultPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "��ѯ���"));
		this.add(resultPanel);
		
		
		
		String[] columnNames ={"ID","����","����","�䱾","�������"};
		mode = new DefaultTableModel(columnNames, 0);
		JTable bookTable = new JTable(mode);
		bookTable.setEnabled(false);
		resultPanel.add(new JScrollPane(bookTable));
		
		
		
		//����ͼ����Ϣ
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
	}
	
	
	
	
	
	 //��ť����
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand()=="��ѯ"){
			//���֮ǰ����
			while(mode.getRowCount()!=0){
				mode.removeRow(0);
			}
			if(combobox_way.getSelectedItem()=="������"){
				Client.sendRequest();
				this.searchByName();
				Client.returnInfo();
			}
			else{
				Client.sendRequest();
				this.searchByAuthor();
				Client.returnInfo();
			}	
			if(!search_exist){
				Client.remind("��Ҫ��ѯ�ص�ͼ�鲻���ڣ�");	
			}
		}
	}
	
	
	
	//����������
	public void searchByName(){		
		search_exist=false;
		name=text_book.getText();
		for(int i=0;i<Library.library.books.size();i++){
			if(Library.library.books.get(i).getName().equals(name)){
				Book b=Library.library.books.get(i);
				ID=b.getID();
				name=b.getName();
				author=b.getAuthor();
				rare=b.isRare();
				borrowed=b.isBorrowed();
				Object rowData[]={ID,name,author,rare?"��":"��",borrowed?"�ѽ��":"δ���"};
				mode.addRow(rowData);
				search_exist=true;
			}
		}	
	}
	
	
	
	
	//�����߲���
	public void searchByAuthor(){		
		search_exist=false;
		author=text_book.getText();
		for(int i=0;i<Library.library.books.size();i++){
			if(Library.library.books.get(i).getAuthor().equals(author)){
				Book b=Library.library.books.get(i);
				ID=b.getID();
				name=b.getName();
				author=b.getAuthor();
				rare=b.isRare();
				borrowed=b.isBorrowed();
				Object rowData[]={ID,name,author,rare?"��":"��",borrowed?"�ѽ��":"δ���"};
				mode.addRow(rowData);
				search_exist=true;
			}
		}
	}
}
