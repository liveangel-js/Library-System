package Client.ui.manager;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import Client.ui.Client;
import Mysql.Mysql;

public class RecommandPanel extends JPanel {
	private JLabel jLabel2;
	private DefaultTableModel mode;
	JTable bookTable = null;
	ChartPanel chartPanel;
	ChartPanel chartBar;
	boolean isPie;
	public RecommandPanel(ManagerPanel mp){
		init();
	}
	public void init(){
		isPie= true;
		setPreferredSize(new java.awt.Dimension(600, 600));
        setLayout(null);
        jLabel2 = new JLabel();
        jLabel2.setText("读者推荐列表");
        add(jLabel2);
        jLabel2.setBounds(250, 20, 100, 30);
        changeToBar = new JButton("查看柱状图");
        add(changeToBar);
        changeToBar.setBounds(700,10,100,25);
        changeToBar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(isPie){
					remove(chartPanel);
					drawRecommandBar();
					isPie = false;
					changeToBar.setText("查看饼状图");
				}else{
					remove(chartBar);
					drawRecommandType();
					isPie = true;
					changeToBar.setText("查看柱状图");
				}
				
				
			}
		});
        
        
        String[] columnNames ={"ISBN","书名","作者","出版社","类别","用户ID"};
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
		j.setBounds(10,70,450,480);
		
		update();
		
		Thread thread = new Thread(){
			public void run(){
				try {
					while(true){
						Thread.sleep(10000);
						System.out.println("更新推荐列表");
						update();
					}
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		};
		thread.start();
        
	}
	public void update(){
		while(mode.getRowCount()!=0){
			mode.removeRow(0);
		}
		ResultSet rs = Mysql.getInstance().query("select * from recommand ");
		try {
			while(rs.next()){
				Object rowData[]={rs.getString("isbn"),rs.getString("name"),rs.getString("author"),rs.getString("publisher"),rs.getString("booktype"),rs.getInt("user_id")};
				mode.addRow(rowData);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(true){
			drawRecommandType();
		}else{
			drawRecommandBar();
		}
		
	}
	
	public void drawRecommandBar(){
		if(chartBar!=null){
			remove(chartBar);
		}
		StandardChartTheme standardChartTheme=new StandardChartTheme("CN"); 
		standardChartTheme.setExtraLargeFont(new Font("隶书",Font.BOLD,20));
		standardChartTheme.setRegularFont(new Font("宋书",Font.PLAIN,15)); 
		standardChartTheme.setLargeFont(new Font("宋书",Font.PLAIN,15)); 
		ChartFactory.setChartTheme(standardChartTheme); 
		
		DefaultCategoryDataset dpd=new DefaultCategoryDataset(); //建立一个默认的饼图
		ResultSet rs = Mysql.getInstance().query("select booktype,count(*) from recommand  group by booktype");
		try {
			while(rs.next()){
				dpd.setValue( rs.getInt(2),rs.getString(1),rs.getString(1));
				
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JFreeChart chart=ChartFactory.createBarChart3D("hi", "类型分布", 
                "数量", dpd, PlotOrientation.VERTICAL, true, true, false); //创建一个JFreeChart
        chart.setTitle(new TextTitle("读者推荐书籍种类图",new Font("宋体",Font.BOLD+Font.ITALIC,20)));//可以重新设置标题，替换“hi”标题
        CategoryPlot plot=(CategoryPlot)chart.getPlot();//获得图标中间部分，即plot
        CategoryAxis categoryAxis=plot.getDomainAxis();//获得横坐标
        categoryAxis.setLabelFont(new Font("微软雅黑",Font.BOLD,12));//设置横坐标字体
        
        chartBar = new ChartPanel(chart);
        add(chartBar);
        chartBar.setBounds(470,40,400,450);
        repaint();
        validate();
		
		
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
		ResultSet rs = Mysql.getInstance().query("select booktype,count(*) from recommand  group by booktype");
		try {
			while(rs.next()){
				dpd.setValue(rs.getString(1), rs.getInt(2));
				
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
        
        JFreeChart chart=ChartFactory.createPieChart("读者推荐类别分布",dpd,true,true,false); 
        //可以查具体的API文档,第一个参数是标题，第二个参数是一个数据集，第三个参数表示是否显示Legend，第四个参数表示是否显示提示，第五个参数表示图中是否存在URL
//        add(chart);
        chartPanel = new ChartPanel(chart);
        add(chartPanel);
        chartPanel.setBounds(470,40,400,450);
        repaint();
        validate();
       // ChartFrame chartFrame=new ChartFrame("某公司人员组织数据图",chart); 
        //chart要放在Java容器组件中，ChartFrame继承自java的Jframe类。该第一个参数的数据是放在窗口左上角的，不是正中间的标题。
      //  chartFrame.pack(); //以合适的大小展现图形
       // chartFrame.setVisible(true);//图形是否可见
			
	}
	private JButton changeToBar;
	
	
	
}
