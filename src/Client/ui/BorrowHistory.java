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

        jLabel8.setText("��ʷ����");
        add(jLabel8);
        jLabel8.setBounds(30, 0, 60, 20);

        jLabel9.setText("���Ի��Ƽ�");
        add(jLabel9);
        jLabel9.setBounds(30, 440, 70, 20);

        jLabel10.setText("���ķֲ�");
        add(jLabel10);
        jLabel10.setBounds(480,20,70,20);
        
        String[] columnNames ={"ISBN","����","����","������","���"};
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
		j.setBounds(30,30,450,400);
		
		
		String[] columnNames1 ={"ISBN","����","����","������","���"};
		mode1 = new DefaultTableModel(columnNames1, 0);
		
		person_recom_table = new JTable(mode1){
			public boolean isCellEditable(int row, int column) {
		        return false;//��д���������Ϊ���ñ�ĵ�Ԫ���ܱ��û��༭������
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
		standardChartTheme.setExtraLargeFont(new Font("����",Font.BOLD,20));
		standardChartTheme.setRegularFont(new Font("����",Font.PLAIN,15)); 
		standardChartTheme.setLargeFont(new Font("����",Font.PLAIN,15)); 
		ChartFactory.setChartTheme(standardChartTheme); 
		DefaultPieDataset dpd=new DefaultPieDataset(); //����һ��Ĭ�ϵı�ͼ
		ResultSet rs = Mysql.getInstance().query("select booktype,count(*) from borrowrecord where user_id="+Client.user.getID()+"  group by booktype");
		try {
			while(rs.next()){
				dpd.setValue(rs.getString(1), rs.getInt(2));
				
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
        
        JFreeChart chart=ChartFactory.createPieChart("������ʷ�ֲ�",dpd,true,true,false); 
        //���Բ�����API�ĵ�,��һ�������Ǳ��⣬�ڶ���������һ�����ݼ���������������ʾ�Ƿ���ʾLegend�����ĸ�������ʾ�Ƿ���ʾ��ʾ�������������ʾͼ���Ƿ����URL
//        add(chart);
        chartPanel = new ChartPanel(chart);
        add(chartPanel);
        chartPanel.setBounds(480,40,400,450);
        repaint();
        validate();
       // ChartFrame chartFrame=new ChartFrame("ĳ��˾��Ա��֯����ͼ",chart); 
        //chartҪ����Java��������У�ChartFrame�̳���java��Jframe�ࡣ�õ�һ�������������Ƿ��ڴ������Ͻǵģ��������м�ı��⡣
      //  chartFrame.pack(); //�Ժ��ʵĴ�Сչ��ͼ��
       // chartFrame.setVisible(true);//ͼ���Ƿ�ɼ�
			
	}
}
