package Client.ui.manager;

import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;

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

public class BookConditionPanel extends JPanel{
	private DefaultTableModel mode;
	private DefaultTableModel mode1;
	private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    JTable missTable = null;
    JTable buyTable = null;
    ChartPanel chartPanel;
	
	public BookConditionPanel(ManagerPanel mp){
		init();
	}
	public void init(){
		jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(600, 600));
        setLayout(null);

        jLabel8.setText("借阅分布");
        add(jLabel8);
        jLabel8.setBounds(30, 0, 60, 30);

        jLabel9.setText("推荐增购");
        add(jLabel9);
        jLabel9.setBounds(30, 440, 70, 30);

        jLabel10.setText("下表图书可能存在遗失");
        add(jLabel10);
        jLabel10.setBounds(600,20, 160, 30);
        drawRecommandType();
        
        
        
        
        String[] columnNames ={"ISBN","书名","出版社","遗失"};
		mode = new DefaultTableModel(columnNames, 0);
		
		missTable = new JTable(mode){
			public boolean isCellEditable(int row, int column) {
		        return false;//重写这个方法是为了让表的单元格不能被用户编辑！！！
		      }
		};
		
		missTable.setEnabled(true);
		JScrollPane  j = new JScrollPane();
		j.setViewportView(missTable);
		add(j);
		j.setBounds(600,55,300,480);
		
		
		String[] columnNames1 ={"ISBN","书名","作者","出版社","类别","被借阅次数"};
		mode1 = new DefaultTableModel(columnNames1, 0);
		
		buyTable = new JTable(mode1){
			public boolean isCellEditable(int row, int column) {
		        return false;//重写这个方法是为了让表的单元格不能被用户编辑！！！
		      }
		};
		
		buyTable.setEnabled(true);
		JScrollPane  j1 = new JScrollPane();
		j1.setViewportView(buyTable);
		add(j1);
		j1.setBounds(30,475,550,70);
		update();
	}
	
	public void update(){
		while(mode1.getRowCount()!=0){
			mode1.removeRow(0);
		}
		ResultSet rs = Mysql.getInstance().query("select t.isbn,t.name,t.author,t.publisher,t.booktype,t.book_id,count(*) as num from borrowrecord b,booklist t where b.book_id=t.book_id group by book_id order by count(*) desc  limit 10");
		try {
			while(rs.next()){
				Object rowData[]={rs.getString("isbn"),rs.getString("name"),rs.getString("author"),rs.getString("publisher"),rs.getString("booktype"),rs.getInt("num")};
				mode1.addRow(rowData);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while(mode.getRowCount()!=0){
			mode.removeRow(0);
		}
		ResultSet rs1 = Mysql.getInstance().query("select isbn, name ,publisher from booklist where book_id not in (select t.book_id from borrowrecord t)");
		System.out.println("正在开始查询");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("5秒结束");
		try {
			while(rs1.next()){
				Object rowData[]={rs1.getString("isbn"),rs1.getString("name"),rs1.getString("publisher"),"可能遗失"};
				mode.addRow(rowData);
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
		ResultSet rs = Mysql.getInstance().query("select booktype,count(*) from borrowrecord  group by booktype");
		try {
			while(rs.next()){
				dpd.setValue(rs.getString(1), rs.getInt(2));
				
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
        
        JFreeChart chart=ChartFactory.createPieChart("借阅类别分布",dpd,true,true,false); 
        //可以查具体的API文档,第一个参数是标题，第二个参数是一个数据集，第三个参数表示是否显示Legend，第四个参数表示是否显示提示，第五个参数表示图中是否存在URL
//        add(chart);
        chartPanel = new ChartPanel(chart);
        add(chartPanel);
        chartPanel.setBounds(30,35,550,400);
        repaint();
        validate();
     
			
	}
}
