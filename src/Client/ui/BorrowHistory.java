package Client.ui;

import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.data.general.DefaultPieDataset;

import Mysql.Mysql;

public class BorrowHistory extends JPanel{
	private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private DefaultTableModel mode;
	JTable bookTable = null;
	ChartPanel chartPanel;
	
	private DefaultTableModel mode1;
	
	JTable person_recom_table = null;
	public BorrowHistory(){
		init();
	}
	public void init(){
		jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(600, 600));
        setLayout(null);

        jLabel8.setText("历史借阅");
        add(jLabel8);
        jLabel8.setBounds(30, 0, 60, 20);

        jLabel9.setText("个性化推荐");
        add(jLabel9);
        jLabel9.setBounds(30, 440, 70, 20);

        jLabel10.setText("借阅分布");
        add(jLabel10);
        jLabel10.setBounds(480,20,70,20);
        
        String[] columnNames ={"ISBN","书名","作者","出版社","类别"};
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
		j.setBounds(30,30,450,400);
		
		
		String[] columnNames1 ={"ISBN","书名","作者","出版社","类别"};
		mode1 = new DefaultTableModel(columnNames1, 0);
		
		person_recom_table = new JTable(mode1){
			public boolean isCellEditable(int row, int column) {
		        return false;//重写这个方法是为了让表的单元格不能被用户编辑！！！
		      }
		};
		
		person_recom_table.setEnabled(true);
		JScrollPane  j1 = new JScrollPane();
		j1.setViewportView(person_recom_table);
		add(j1);
		j1.setBounds(30,460,450,100);
		
		
		drawRecommandType();
		update();
        
        
        
//        drawRecommandType();
	}
	public void update(){
		while(mode.getRowCount()!=0){
			mode.removeRow(0);
		}
		ResultSet rs = Mysql.getInstance().query("select * from borrowrecord, booklist where borrowrecord.book_id=booklist.book_id and borrowrecord.user_id="+Client.user.getID());
		try {
			while(rs.next()){
				Object rowData[]={rs.getString("isbn"),rs.getString("name"),rs.getString("author"),rs.getString("publisher"),rs.getString("booktype")};
				mode.addRow(rowData);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		while(mode1.getRowCount()!=0){
			mode1.removeRow(0);
		}
		ResultSet rs1 = Mysql.getInstance().query("select booktype,count(*) from borrowrecord where user_id="+Client.user.getID()+" group by booktype order  by count(*) desc  limit 4 ");

		
		try {
			ArrayList<String> types = new ArrayList<String>();
			while(rs1.next()){
				String type1 = rs1.getString("booktype");
				types.add(type1);
				
			}
			ResultSet rs2 = Mysql.getInstance().query("select t.isbn,t.name,t.author,t.publisher,t.booktype,t.book_id,count(*) as num from borrowrecord b,booklist t where b.book_id=t.book_id and" +
					"							 (b.booktype=\""+types.get(0)
												+"\" or b.booktype=\""+types.get(1)
												+"\" or b.booktype=\""+types.get(2)
												+"\" or b.booktype=\""+types.get(3)
												
												+"\") group by book_id order by count(*) desc  limit 8");
			while(rs2.next()){
				Object rowData[]={rs2.getString("isbn"),rs2.getString("name"),rs2.getString("author"),rs2.getString("publisher"),rs2.getString("booktype")};
				mode1.addRow(rowData);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	
	public void drawRecommandType(){
		if(chartPanel!=null){
			remove(chartPanel);
		}
		StandardChartTheme standardChartTheme=new StandardChartTheme("CN"); 
		standardChartTheme.setExtraLargeFont(new Font("隶书",Font.BOLD,20));
		standardChartTheme.setRegularFont(new Font("宋书",Font.PLAIN,15)); 
		standardChartTheme.setLargeFont(new Font("宋书",Font.PLAIN,15)); 
		ChartFactory.setChartTheme(standardChartTheme); 
		DefaultPieDataset dpd=new DefaultPieDataset(); //建立一个默认的饼图
		ResultSet rs = Mysql.getInstance().query("select booktype,count(*) from borrowrecord where user_id="+Client.user.getID()+"  group by booktype");
		try {
			while(rs.next()){
				dpd.setValue(rs.getString(1), rs.getInt(2));
				
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
        
        JFreeChart chart=ChartFactory.createPieChart("借阅历史分布",dpd,true,true,false); 
        //可以查具体的API文档,第一个参数是标题，第二个参数是一个数据集，第三个参数表示是否显示Legend，第四个参数表示是否显示提示，第五个参数表示图中是否存在URL
//        add(chart);
        chartPanel = new ChartPanel(chart);
        add(chartPanel);
        chartPanel.setBounds(480,40,400,450);
        repaint();
        validate();
       // ChartFrame chartFrame=new ChartFrame("某公司人员组织数据图",chart); 
        //chart要放在Java容器组件中，ChartFrame继承自java的Jframe类。该第一个参数的数据是放在窗口左上角的，不是正中间的标题。
      //  chartFrame.pack(); //以合适的大小展现图形
       // chartFrame.setVisible(true);//图形是否可见
			
	}
}
