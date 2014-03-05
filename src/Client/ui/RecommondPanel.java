package Client.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Client.ui.manager.ManagerPanel;
import Mysql.Mysql;

public class RecommondPanel extends JPanel{
	private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private DefaultTableModel mode;
	private JTextField isbn = null;
	private JButton isbn_query = null;
	private JButton recommand=null;
	JTable bookTable = null;

	public RecommondPanel(){
		init();
	}
	public void init(){
		setLayout(null);
		jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(600, 600));
        setLayout(null);
        add(jTextField1);
        jTextField1.setBounds(160, 90, 100, 21);

        jLabel1.setText("推荐图书");
        add(jLabel1);
        jLabel1.setBounds(170, 20, 80, 40);

        jButton1.setText("查询");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1);
        jButton1.setBounds(280, 90, 57, 23);

        jLabel2.setText("ISBN");
        add(jLabel2);
        jLabel2.setBounds(90, 90, 24, 20);

        jLabel3.setText("书名");
        add(jLabel3);
        jLabel3.setBounds(90, 150, 24, 15);

        jTextField2.setEditable(false);
        add(jTextField2);
        jTextField2.setBounds(160, 150, 100, 21);

        jLabel4.setText("作者");
        add(jLabel4);
        jLabel4.setBounds(90, 200, 24, 15);

        jTextField3.setEditable(false);
        add(jTextField3);
        jTextField3.setBounds(160, 200, 100, 21);

        jLabel5.setText("出版社");
        add(jLabel5);
        jLabel5.setBounds(90, 250, 36, 15);

        jLabel6.setText("类别");
        add(jLabel6);
        jLabel6.setBounds(90, 300, 24, 15);

        jTextField4.setEditable(false);
        add(jTextField4);
        jTextField4.setBounds(160, 250, 100, 21);

        jTextField5.setEditable(false);
        add(jTextField5);
        jTextField5.setBounds(160, 300, 100, 21);

        jButton2.setText("推荐");
        add(jButton2);
        jButton2.setBounds(280, 300, 81, 23);

        jLabel7.setText("我的推荐");
        add(jLabel7);
        jLabel7.setBounds(440, 30, 80, 40);
        jButton2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				Mysql m = Mysql.getInstance();
				int a = m.update("insert into recommand values("+Client.user.getID()+","+"\""+jTextField2.getText()+"\",\""+jTextField3.getText()+"\",\""+jTextField4.getText()+"\",\""+jTextField1.getText()+"\",\""+jTextField5.getText()+"\")");
				if(a==-1){
					JOptionPane.showMessageDialog(null, "失败","", JOptionPane.INFORMATION_MESSAGE);
				}
				update();
				
			}
		});
        
        
        
        
        String[] columnNames ={"ISBN","书名","作者","出版社"};
		mode = new DefaultTableModel(columnNames, 0);
		
		 bookTable = new JTable(mode){
			public boolean isCellEditable(int row, int column) {
		        return false;//重写这个方法是为了让表的单元格不能被用户编辑！！！
		      }
		};
		
		bookTable.setEnabled(true);
		JScrollPane  j = new JScrollPane();
		j.setViewportView(bookTable);
		add(j);
		j.setBounds(440,70,400,500);
		update();
	}
	public void update(){
		while(mode.getRowCount()!=0){
			mode.removeRow(0);
		}
		ResultSet rs = Mysql.getInstance().query("select * from recommand where user_id="+Client.user.getID());
		try {
			while(rs.next()){
				Object rowData[]={rs.getString("isbn"),rs.getString("name"),rs.getString("author"),rs.getString("publisher")};
				mode.addRow(rowData);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
			
		
	}
	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {         
		
		
		
		String isbnUrl = "http://api.douban.com/book/subject/isbn/"; 
		GetBookData a =new GetBookData();
		try {
			String xml = a.fetchBookInfoByXML(jTextField1.getText());
			
			
			String name = "name=\"title\"";
			String isbn = "9787111298854";
			String author = "name=\"author\"";
			boolean rare= false;
			String publisher= "name=\"publisher\"";
			
			String text_name=a.getAttribute(name,xml);
			if(text_name==null){
				JOptionPane.showMessageDialog(null, "无此ISBN","", JOptionPane.INFORMATION_MESSAGE);
				return;
				
			}
			
			
			jTextField2.setText(text_name);
			jTextField3.setText(a.getAttribute(author,xml));
			jTextField4.setText(a.getAttribute(publisher,xml));
			
			String [] cc = {"艺术","哲学","社会科学总论","政法","军事","经济","文学","工业技术","自然科学总论","航空航天","交通运输","综合性图书","历史","生物科学","天文学"};
			int temp =(int)(Math.random()*cc.length);
			if(temp==cc.length) temp--;
			jTextField5.setText(cc[temp]);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        // TODO add your handling code here:
    }    
}
