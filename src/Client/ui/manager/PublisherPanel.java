package Client.ui.manager;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.data.general.DefaultPieDataset;

import Common.Book;
import Mysql.Mysql;

public class PublisherPanel extends JPanel {
	private JButton test;
	private ArrayList<String> publisherList =null;
	private ArrayList<Integer> numberList = null;
	private DefaultTableModel mode;
	JTable bookTable = null;
	ChartPanel chartPanel;
	public PublisherPanel(ManagerPanel mp){
		
		init();
		
	}
	private  class ExcelFileFilter extends FileFilter {
		String ext;
		ExcelFileFilter(String ext) {

			   this.ext = ext;
			}
		@Override
		public boolean accept(File file) {
			// TODO Auto-generated method stub
			if (file.isDirectory())
			   {
			    return true;
			   }
			
			String fileName = file.getName();
			   int index = fileName.lastIndexOf('.');

			   if (index > 0 && index < fileName.length() - 1)
			   {
			    String extension = fileName.substring(index + 1).toLowerCase();
			    if (extension.equals(ext))
			     return true;
			   }
			   return false;
			
		}
		@Override
		public String getDescription() {
			// TODO Auto-generated method stub
			if (ext.equals("xls"))
			   {
			    return "Microsoft Excel文件(*.xls)";
			   }
			if (ext.equals("xlsx"))
			   {
			    return "Microsoft Excel文件(*.xlsx)";
			   }
			   return "";

		}
	}
	public void init(){
		setLayout(null);
		test = new JButton("导入数据");
		
	this.add(test);
	test.setBounds(300,10,100,30);
	test.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JFileChooser file = new JFileChooser (".");
			file.setAcceptAllFileFilterUsed(false);
			file.addChoosableFileFilter(new ExcelFileFilter("xls"));
			//file.addChoosableFileFilter(new ExcelFileFilter("xlsx"));
			int result = file.showOpenDialog(null);
			if(result == JFileChooser.APPROVE_OPTION)
			{

			
			   String path = file.getSelectedFile().getAbsolutePath();
			   System.out.println(path);
			   loadFile(path);
			}
			else
			{

			    System.out.println("你已取消并关闭了窗口！");
			}	
		}
	});
		setPreferredSize(new java.awt.Dimension(600, 600));
		
		
		String[] columnNames ={"出版社","在库种数"};
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
		j.setBounds(50,40,250,500);
//		bookTable.seTS
	
		update();
		
	}
	public void loadFile(String fileURL){
		try {
			POIFSFileSystem fs=new POIFSFileSystem(new FileInputStream(fileURL));
			HSSFWorkbook wb = new HSSFWorkbook(fs);  
			
			HSSFSheet sheet = wb.getSheetAt(0); 
			HSSFRow row = sheet.getRow(0); 
			HSSFCell cell = row.getCell(0);
			int i =1;
			System.out.println(cell.getStringCellValue());
			cell = row.getCell(1);
			
			while(!sheet.getRow(i).getCell(0).getStringCellValue().equals("结束")){
				String name = sheet.getRow(i).getCell(0).getStringCellValue();
				String author = sheet.getRow(i).getCell(1).getStringCellValue();
				String publisher = sheet.getRow(i).getCell(2).getStringCellValue();
				String isbn = sheet.getRow(i).getCell(3).getStringCellValue();
				String type = sheet.getRow(i).getCell(4).getStringCellValue();
				i++;
				Book tmp = new Book(name, author, publisher, isbn, type);
				Mysql.getInstance().addBook(tmp);
			}

			
			
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
		JOptionPane.showMessageDialog(null, "导入成功","导入数据", JOptionPane.INFORMATION_MESSAGE);
	}
	public void getData(){
		publisherList =new ArrayList<String>();
		numberList =new ArrayList<Integer>();
		//出版商在库数品种信息
		Mysql mysql = Mysql.getInstance();
		ResultSet rs = mysql.query("select publisher,count(*) from booklist group by publisher ");
		try {
			while(rs.next()){
				String publisher = rs.getString(1);
				int count = rs.getInt(2);
				publisherList.add(publisher);
				numberList.add(count);
				
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		bookTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				int row =bookTable.getSelectedRow();
				String publisher = (String) bookTable.getModel().getValueAt(row, 0);
				drawPublisherPic(publisher);
//				System.out.println(publisher);
			}
			
		});
		
	}
	public void drawPublisherPic(String publisher){
		if(chartPanel!=null){
			remove(chartPanel);
		}
		StandardChartTheme standardChartTheme=new StandardChartTheme("CN"); 
		standardChartTheme.setExtraLargeFont(new Font("隶书",Font.BOLD,20));
		standardChartTheme.setRegularFont(new Font("宋书",Font.PLAIN,15)); 
		standardChartTheme.setLargeFont(new Font("宋书",Font.PLAIN,15)); 
		ChartFactory.setChartTheme(standardChartTheme); 
		DefaultPieDataset dpd=new DefaultPieDataset(); //建立一个默认的饼图
		ResultSet rs = Mysql.getInstance().query("select booktype,count(*) from booklist where publisher=\""+publisher+"\" group by booktype");
		try {
			while(rs.next()){
				dpd.setValue(rs.getString(1), rs.getInt(2));
				
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
        
        JFreeChart chart=ChartFactory.createPieChart3D(publisher+"出版社",dpd,true,true,false); 
        //可以查具体的API文档,第一个参数是标题，第二个参数是一个数据集，第三个参数表示是否显示Legend，第四个参数表示是否显示提示，第五个参数表示图中是否存在URL
//        add(chart);
        chartPanel = new ChartPanel(chart);
        add(chartPanel);
        chartPanel.setBounds(320,40,570,500);
        repaint();
        validate();
       // ChartFrame chartFrame=new ChartFrame("某公司人员组织数据图",chart); 
        //chart要放在Java容器组件中，ChartFrame继承自java的Jframe类。该第一个参数的数据是放在窗口左上角的，不是正中间的标题。
      //  chartFrame.pack(); //以合适的大小展现图形
       // chartFrame.setVisible(true);//图形是否可见
			
	}
	
	public void update(){
		while(mode.getRowCount()!=0){
			mode.removeRow(0);
		}
		getData();
		for(int i =0 ;i<publisherList.size();i++){
			Object rowData[]={publisherList.get(i),numberList.get(i)};
			mode.addRow(rowData);
		}
		
	}

}
