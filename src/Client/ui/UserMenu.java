package Client.ui;


import javax.swing.*;
import java.awt.event.*;



public class UserMenu extends JPanel implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton button_search;
	private JButton button_borrow;
	private JButton button_renew;
	private JButton button_return;
	private JButton button_borrowInfo;
	private JButton button_remind;
	private JButton button_logout;
	private JButton button_exit;
	private JButton button_recommand;
	private JButton button_myhistory;
	private JButton button_history;
	UserPanel up;
	 
	
	
	
	public UserMenu(UserPanel up){
		this.up=up;
		
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "���˵�"));
		   
		
		
		this.add(Box.createVerticalStrut(30));
	    button_search=new JButton("��ѯͼ��");
	    button_search.addActionListener(this);
		this.add(button_search);
		
		
		this.add(Box.createVerticalStrut(5));
		button_borrow=new JButton("����ͼ��");
		button_borrow.addActionListener(this);
	    this.add(button_borrow);
	        
	    
	    this.add(Box.createVerticalStrut(40));
	    button_renew=new JButton("����ͼ��");
	    button_renew.addActionListener(this);
	    this.add(button_renew);
	    
	    
	    
	    this.add(Box.createVerticalStrut(5));
	    button_return=new JButton("�黹ͼ��");
	    button_return.addActionListener(this);
	    this.add(button_return);
	    
	    
	    
	    this.add(Box.createVerticalStrut(5));
	    button_borrowInfo=new JButton("������Ϣ");
	    button_borrowInfo.addActionListener(this);
	    this.add(button_borrowInfo);
	    
	    
	    
	    this.add(Box.createVerticalStrut(5));
	    button_remind=new JButton("��Ϣ����");
	    button_remind.addActionListener(this);
	    this.add(button_remind);
	      
	    

	    this.add(Box.createVerticalStrut(40));
	    button_logout=new JButton("ע���û�");
	    button_logout.addActionListener(this);
	    this.add(button_logout);
	    
	    
	    this.add(Box.createVerticalStrut(5));
	    button_exit=new JButton("�˳�ϵͳ");
	    button_exit.addActionListener(this);
	    this.add(button_exit);
	    
	    
	    this.add(Box.createVerticalStrut(15));
	    button_recommand = new JButton("�Ƽ�ͼ��");
	    button_recommand.addActionListener(this);
	    this.add(button_recommand);
	    
	    this.add(Box.createVerticalStrut(5));
	    button_myhistory = new JButton("������ʷ");
	    button_myhistory.addActionListener(this);
	    this.add(button_myhistory);
	}






	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand()=="��ѯͼ��"){
			up.bs=new BookSearch(up);
			up.remove(1);
			up.add(up.bs);
			up.validate();
			up.repaint();
			return;
		}
		if(e.getActionCommand()=="����ͼ��"){
			up.bb=new BookBorrow();
			up.remove(1);
			up.add(up.bb);
			up.validate();
			up.repaint();
			return;
		}
        if(e.getActionCommand()=="����ͼ��"){
			up.br=new BookRenew();
			up.remove(1);
			up.add(up.br);
			up.validate();
			up.repaint();
			return;
		}
        if(e.getActionCommand()=="�黹ͼ��"){
			up.breturn=new BookReturn();
			up.remove(1);
			up.add(up.breturn);
			up.validate();
			up.repaint();
			return;
		}
        if(e.getActionCommand()=="������Ϣ"){
			up.bi=new BorrowInfo();
			up.remove(1);
			up.add(up.bi);
			up.validate();
			up.repaint();
			return;
		}
        if(e.getActionCommand()=="��Ϣ����"){
        	Client.sendRequest();
        	Client.returnInfo();
			up.r=new BookRemind();
			up.remove(1);
			up.add(up.r);
			up.validate();
			up.repaint();
			return;
		}
        if(e.getActionCommand()=="ע���û�"){
        	Client.sendRequest();
        	Client.user.setOn(false);
        	Client.returnInfo();
        	up.c.remove(up);
        	up.c.login();
        	up.c.validate();  
        	return;
		}
        if(e.getActionCommand()=="�˳�ϵͳ"){    
        	Client.sendRequest();
        	Client.user.setOn(false);
        	Client.returnInfo();
        	Client.breakServer();
        	System.exit(0);
        }
        if(e.getActionCommand()=="�Ƽ�ͼ��"){    
        	up.rp=new RecommondPanel();
			up.remove(1);
			up.add(up.rp);
			up.validate();
			up.repaint();
			return;
        }
        if(e.getActionCommand()=="������ʷ"){    
        	up.bh=new BorrowHistory();
			up.remove(1);
			up.add(up.bh);
			up.validate();
			up.repaint();
			return;
        }
	}
}
