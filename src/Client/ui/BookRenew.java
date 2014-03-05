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
		
		
		
		//续借图书面板
		JPanel renewPanel=new JPanel();
		renewPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "续借图书"));
		this.add(renewPanel);
		
		
		
		renewPanel.add(new JLabel("ID："));
		combobox_ID=new JComboBox();
		combobox_ID.addItemListener(this);
		renewPanel.add(combobox_ID);

	
		
		renewPanel.add(new JLabel("书名："));
		text_name=new JTextField(10);
		text_name.setEditable(false);
		renewPanel.add(text_name);

		
		
		renewPanel.add(new JLabel("作者："));
		text_author=new JTextField(10);
		text_author.setEditable(false);
		renewPanel.add(text_author);
		
		
		
		renewPanel.add(new JLabel("珍本："));
		text_rare=new JTextField(3);
		text_rare.setHorizontalAlignment(JTextField.CENTER);
		text_rare.setEditable(false);
		renewPanel.add(text_rare);
		
		
		
		
		
		renewPanel.add(new JLabel("续借情况："));
		text_renewed=new JTextField(5);
		text_renewed.setHorizontalAlignment(JTextField.CENTER);
		text_renewed.setEditable(false);
		renewPanel.add(text_renewed);
		
		
		
		button_renew=new JButton("续借");
		button_renew.addActionListener(this);
		renewPanel.add(button_renew);

		
		
		
		//图书信息面板
		JPanel bookPanel=new JPanel();
		//bookPanel.setLayout(new BorderLayout());
		bookPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "图书信息"));	
		this.add(bookPanel);
		
		
		
		String[] columnNames ={"ID","书名","作者","珍本","续借情况"};
		mode=new DefaultTableModel(columnNames, 0);
		JTable bookTable = new JTable(mode);
		bookTable.setEnabled(false);
		bookPanel.add(new JScrollPane(bookTable));

		
		
		
		
		//载入借阅图书信息
		for(int i=0;i<Client.user.getBooks().size();i++){
			Book b=Client.user.getBooks().get(i);
			ID=b.getID();
			combobox_ID.addItem(ID);
			name=b.getName();
			author=b.getAuthor();
			rare=b.isRare();
			renewed=b.isRenewed();
			Object rowData[]={ID,name,author,rare?"是":"否",renewed?"已续借":"未续借"};
			mode.addRow(rowData);
		}	
	}
	
	
	
	
	
	
	//同步显示书籍信息
    public void itemStateChanged(ItemEvent e) {
    	if(combobox_ID.getSelectedItem()!=null){
    		ID=(Integer)combobox_ID.getSelectedItem();
    		for(int i=0;i<Library.library.books.size();i++){
    			if(ID==Library.library.books.get(i).getID()){
    				Book b=Library.library.books.get(i);
    				text_name.setText(b.getName());
    				text_author.setText(b.getAuthor());
    				text_rare.setText(b.isRare()?"是":"否");
    				text_renewed.setText(b.isRenewed()?"已续借":"未续借");
    				break;
    			}
    		}	
    	}
	}
	
	
    
    
    
	
	//按钮监听
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand()=="续借"){
			if(combobox_ID.getSelectedItem()!=null){
				Client.sendRequest();
				this.renewBook();
				Client.returnInfo();
			}
		}	
	}
	
	

	
	
	
	
	//借阅图书
	public void renewBook(){
		ID=(Integer) combobox_ID.getSelectedItem();
		for(int i=0;i<Client.user.getBooks().size();i++){
			if(Client.user.getBooks().get(i).getID()==ID){
				Book b=Client.user.getBooks().get(i);
				if(b.isRenewed()){
					Client.remind("每本书只能续借一次！");
					return;
				}
				else{
					b.renewed();
					break;
				}
			}
		}
		
		
		
		//同步续借图书面板
		text_renewed.setText("已续借");
		
		
		//同步图书信息面板
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
			Object rowData[]={ID,name,author,rare?"是":"否",renewed?"已续借":"未续借"};
			mode.addRow(rowData);
		}	
		Client.remind("图书借阅成功！");
	}	
}
