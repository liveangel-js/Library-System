package Client.ui;



import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.*;

import Common.Book;
import Common.Library;



public class BookRenew extends JPanel implements ActionListener,ItemListener  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int ID;
	private String name;
	private String author;
	private boolean rare;
	private boolean renewed;
	private JComboBox combobox_ID;
	private JTextField text_name;
	private JTextField text_author;
	private JTextField text_rare;
	private JTextField text_renewed;
	private JButton button_renew;
	private DefaultTableModel mode;
	
	
	
	
	public BookRenew(){
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		
		
		//����ͼ�����
		JPanel renewPanel=new JPanel();
		renewPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "����ͼ��"));
		this.add(renewPanel);
		
		
		
		renewPanel.add(new JLabel("ID��"));
		combobox_ID=new JComboBox();
		combobox_ID.addItemListener(this);
		renewPanel.add(combobox_ID);

	
		
		renewPanel.add(new JLabel("������"));
		text_name=new JTextField(10);
		text_name.setEditable(false);
		renewPanel.add(text_name);

		
		
		renewPanel.add(new JLabel("���ߣ�"));
		text_author=new JTextField(10);
		text_author.setEditable(false);
		renewPanel.add(text_author);
		
		
		
		renewPanel.add(new JLabel("�䱾��"));
		text_rare=new JTextField(3);
		text_rare.setHorizontalAlignment(JTextField.CENTER);
		text_rare.setEditable(false);
		renewPanel.add(text_rare);
		
		
		
		
		
		renewPanel.add(new JLabel("���������"));
		text_renewed=new JTextField(5);
		text_renewed.setHorizontalAlignment(JTextField.CENTER);
		text_renewed.setEditable(false);
		renewPanel.add(text_renewed);
		
		
		
		button_renew=new JButton("����");
		button_renew.addActionListener(this);
		renewPanel.add(button_renew);

		
		
		
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

		
		
		
		
		//�������ͼ����Ϣ
		for(int i=0;i<Client.user.getBooks().size();i++){
			Book b=Client.user.getBooks().get(i);
			ID=b.getID();
			combobox_ID.addItem(ID);
			name=b.getName();
			author=b.getAuthor();
			rare=b.isRare();
			renewed=b.isRenewed();
			Object rowData[]={ID,name,author,rare?"��":"��",renewed?"������":"δ����"};
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
    				text_renewed.setText(b.isRenewed()?"������":"δ����");
    				break;
    			}
    		}	
    	}
	}
	
	
    
    
    
	
	//��ť����
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand()=="����"){
			if(combobox_ID.getSelectedItem()!=null){
				Client.sendRequest();
				this.renewBook();
				Client.returnInfo();
			}
		}	
	}
	
	

	
	
	
	
	//����ͼ��
	public void renewBook(){
		ID=(Integer) combobox_ID.getSelectedItem();
		for(int i=0;i<Client.user.getBooks().size();i++){
			if(Client.user.getBooks().get(i).getID()==ID){
				Book b=Client.user.getBooks().get(i);
				if(b.isRenewed()){
					Client.remind("ÿ����ֻ������һ�Σ�");
					return;
				}
				else{
					b.renewed();
					break;
				}
			}
		}
		
		
		
		//ͬ������ͼ�����
		text_renewed.setText("������");
		
		
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
			renewed=b.isRenewed();
			Object rowData[]={ID,name,author,rare?"��":"��",renewed?"������":"δ����"};
			mode.addRow(rowData);
		}	
		Client.remind("ͼ����ĳɹ���");
	}	
}
