package Client.ui;



import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.*;

import Common.Book;
import Common.Library;



public class BookReturn extends JPanel implements ActionListener,ItemListener  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int ID;
	private String name;
	private String author;
	private boolean rare;
	private JComboBox combobox_ID;
	private JTextField text_name;
	private JTextField text_author;
	private JTextField text_rare;
	private JButton button_return;
	private DefaultTableModel mode;
	
	
	
	
	public BookReturn(){
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		
		
		//ͼ��黹���
		JPanel returnPanel=new JPanel();
		returnPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "�黹ͼ��"));
		this.add(returnPanel);
		
		
		
		returnPanel.add(new JLabel("ID��"));
		combobox_ID=new JComboBox();
		combobox_ID.addItemListener(this);
		returnPanel.add(combobox_ID);

	
		
		returnPanel.add(new JLabel("������"));
		text_name=new JTextField(10);
		text_name.setEditable(false);
		returnPanel.add(text_name);

		
		
		returnPanel.add(new JLabel("���ߣ�"));
		text_author=new JTextField(10);
		text_author.setEditable(false);
		returnPanel.add(text_author);
		
		
		
		returnPanel.add(new JLabel("�䱾��"));
		text_rare=new JTextField(3);
		text_rare.setHorizontalAlignment(JTextField.CENTER);
		text_rare.setEditable(false);
		returnPanel.add(text_rare);
		
		
		

		
		
		
		
		button_return=new JButton("�黹");
		button_return.addActionListener(this);
		returnPanel.add(button_return);

		
		
		
		//ͼ����Ϣ���
		JPanel bookPanel=new JPanel();
		//bookPanel.setLayout(new BorderLayout());
		bookPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "ͼ����Ϣ"));	
		this.add(bookPanel);
		
		
		
		String[] columnNames ={"ID","����","����","�䱾"};
		mode=new DefaultTableModel(columnNames, 0);
		JTable bookTable = new JTable(mode);
		bookTable.setEnabled(false);
		bookPanel.add(new JScrollPane(bookTable));

		
		
		
		
		//�������ͼ����Ϣ
		for(int i=0;i<Client.user.getBooks().size();i++){
			Book b=Client.user.getBooks().get(i);
			ID=b.getID();
			combobox_ID.addItem(ID);
			name=b.getName();
			author=b.getAuthor();
			rare=b.isRare();
			Object rowData[]={ID,name,author,rare?"��":"��"};
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
    				break;
    			}
    		}	
    	}
    	else{
    		text_name.setText("");
			text_author.setText("");
			text_rare.setText("");
    	}
	}
	
	
    
    
    
	
	//��ť����
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand()=="�黹"){
			if(combobox_ID.getSelectedItem()!=null){
				Client.sendRequest();
				this.returnBook();
				Client.returnInfo();
			}
		}	
	}
	
	

	
	
	
	
	//�黹ͼ��
	public void returnBook(){
		ID=(Integer) combobox_ID.getSelectedItem();
		for(int i=0;i<Client.user.getBooks().size();i++){
			if(Client.user.getBooks().get(i).getID()==ID){
				Book b=Client.user.getBooks().get(i);
				b.returned();
				Client.user.returnBook(b);
				combobox_ID.removeItem(ID);
				if(b.isRequest()){
					b.borrowedBy(b.getRequester());
					b.getRequester().borrow(b);
					b.setRequest(false);
					b.setRequester(null);
					Client.user.getRequestBooks().remove(b);
				}
				break;
			}
		}
		
		
		

		
		
		//ͬ��ͼ����Ϣ���
		while(mode.getRowCount()!=0){
			mode.removeRow(0);
		}
		for(int i=0;i<Client.user.getBooks().size();i++){
			Book b=Client.user.getBooks().get(i);
			ID=b.getID();
			name=b.getName();
			author=b.getAuthor();
			rare=b.isRare();
			Object rowData[]={ID,name,author,rare?"��":"��"};
			mode.addRow(rowData);
		}	
		Client.remind("ͼ��黹�ɹ���");
	}	
}
