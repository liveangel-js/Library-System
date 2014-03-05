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
        jLabel2.setText("�����Ƽ��б�");
        add(jLabel2);
        jLabel2.setBounds(250, 20, 100, 30);
        changeToBar = new JButton("�鿴��״ͼ");
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
					changeToBar.setText("�鿴��״ͼ");
				}else{
					remove(chartBar);
					drawRecommandType();
					isPie = true;
					changeToBar.setText("�鿴��״ͼ");
				}
				
				
			}
		});
        
        
        String[] columnNames ={"ISBN","����","����","������","���","�û�ID"};
		mode = new DefaultTableModel(columnNames, 0);
		
		 bookTable = new JTable(mode){
			public boolean isCellEditable(int row, int column) {
		        return false;//��д���������Ϊ���ñ�ĵ�Ԫ���ܱ��û��༭������
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
						System.out.println("�����Ƽ��б�");
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
		standardChartTheme.setExtraLargeFont(new Font("����",Font.BOLD,20));
		standardChartTheme.setRegularFont(new Font("����",Font.PLAIN,15)); 
		standardChartTheme.setLargeFont(new Font("����",Font.PLAIN,15)); 
		ChartFactory.setChartTheme(standardChartTheme); 
		
		DefaultCategoryDataset dpd=new DefaultCategoryDataset(); //����һ��Ĭ�ϵı�ͼ
		ResultSet rs = Mysql.getInstance().query("select booktype,count(*) from recommand  group by booktype");
		try {
			while(rs.next()){
				dpd.setValue( rs.getInt(2),rs.getString(1),rs.getString(1));
				
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JFreeChart chart=ChartFactory.createBarChart3D("hi", "���ͷֲ�", 
                "����", dpd, PlotOrientation.VERTICAL, true, true, false); //����һ��JFreeChart
        chart.setTitle(new TextTitle("�����Ƽ��鼮����ͼ",new Font("����",Font.BOLD+Font.ITALIC,20)));//�����������ñ��⣬�滻��hi������
        CategoryPlot plot=(CategoryPlot)chart.getPlot();//���ͼ���м䲿�֣���plot
        CategoryAxis categoryAxis=plot.getDomainAxis();//��ú�����
        categoryAxis.setLabelFont(new Font("΢���ź�",Font.BOLD,12));//���ú���������
        
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
		standardChartTheme.setExtraLargeFont(new Font("����",Font.BOLD,20));
		standardChartTheme.setRegularFont(new Font("����",Font.PLAIN,15)); 
		standardChartTheme.setLargeFont(new Font("����",Font.PLAIN,15)); 
		ChartFactory.setChartTheme(standardChartTheme); 
		DefaultPieDataset dpd=new DefaultPieDataset(); //����һ��Ĭ�ϵı�ͼ
		ResultSet rs = Mysql.getInstance().query("select booktype,count(*) from recommand  group by booktype");
		try {
			while(rs.next()){
				dpd.setValue(rs.getString(1), rs.getInt(2));
				
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
        
        JFreeChart chart=ChartFactory.createPieChart("�����Ƽ����ֲ�",dpd,true,true,false); 
        //���Բ�����API�ĵ�,��һ�������Ǳ��⣬�ڶ���������һ�����ݼ���������������ʾ�Ƿ���ʾLegend�����ĸ�������ʾ�Ƿ���ʾ��ʾ�������������ʾͼ���Ƿ����URL
//        add(chart);
        chartPanel = new ChartPanel(chart);
        add(chartPanel);
        chartPanel.setBounds(470,40,400,450);
        repaint();
        validate();
       // ChartFrame chartFrame=new ChartFrame("ĳ��˾��Ա��֯����ͼ",chart); 
        //chartҪ����Java��������У�ChartFrame�̳���java��Jframe�ࡣ�õ�һ�������������Ƿ��ڴ������Ͻǵģ��������м�ı��⡣
      //  chartFrame.pack(); //�Ժ��ʵĴ�Сչ��ͼ��
       // chartFrame.setVisible(true);//ͼ���Ƿ�ɼ�
			
	}
	private JButton changeToBar;
	
	
	
}
