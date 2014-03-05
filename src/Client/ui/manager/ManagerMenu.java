package Client.ui.manager;


import javax.swing.*;

import Client.ui.BookInfo;
import Client.ui.BookSearch;
import Client.ui.Client;
import Client.ui.UserInfo;
import Client.ui.UserModify;
import Client.ui.UserSearch;
import Common.Library;

import java.awt.event.*;


public class ManagerMenu extends JPanel implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton button_searchBook;
	private JButton button_searchUser;
	private JButton button_bookInfo;
	private JButton button_userInfo;
	private JButton button_bookAdd;
	private JButton button_bookDelete;
	private JButton button_bookModify;
	private JButton button_userAdd;
	private JButton button_userDelete;
	private JButton button_userModify;
	private JButton button_logout;
	private JButton button_exit;
	
	private JButton button_publisher;
	private JButton button_bookCondition;
	private JButton button_recommand;
	
	ManagerPanel mp;
	
	
	
	
	public ManagerMenu(ManagerPanel mp){
		this.mp=mp;
		
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "���˵�"));
		   
		
		
		this.add(Box.createVerticalStrut(20));
	    button_searchBook=new JButton("��ѯͼ��");
	    button_searchBook.addActionListener(this);
		this.add(button_searchBook);
		
		
		this.add(Box.createVerticalStrut(5));
		button_searchUser=new JButton("��ѯ�û�");
		button_searchUser.addActionListener(this);
	    this.add(button_searchUser);
	    
	    
	    
	    
	    
	    this.add(Box.createVerticalStrut(10));
	    
	    
	    this.add(Box.createVerticalStrut(5));
	    button_bookInfo=new JButton("ͼ����Ϣ");
	    button_bookInfo.addActionListener(this);
	    this.add(button_bookInfo);
	    
	    
	    this.add(Box.createVerticalStrut(5));
	    button_userInfo=new JButton("�û���Ϣ");
	    button_userInfo.addActionListener(this);
	    this.add(button_userInfo);
	    
	    
	    
	    this.add(Box.createVerticalStrut(10));
	    
	    
	    
	    this.add(Box.createVerticalStrut(5));
	    button_bookAdd=new JButton("���ͼ��");
	    button_bookAdd.addActionListener(this);
	    this.add(button_bookAdd);
	    
	    
	    this.add(Box.createVerticalStrut(5));
	    button_bookDelete=new JButton("ɾ��ͼ��");
	    button_bookDelete.addActionListener(this);
	    this.add(button_bookDelete);
	    
	    
	    this.add(Box.createVerticalStrut(5));
	    button_bookModify=new JButton("�޸�ͼ��");
	    button_bookModify.addActionListener(this);
	    this.add(button_bookModify);
	    
	    
	    
	    this.add(Box.createVerticalStrut(10));
	    
	    
	    
	   
	    this.add(Box.createVerticalStrut(5));
	    button_userAdd=new JButton("����û�");
	    button_userAdd.addActionListener(this);
	    this.add(button_userAdd);
	    
	    
	    this.add(Box.createVerticalStrut(5));
	    button_userDelete=new JButton("ɾ���û�");
	    button_userDelete.addActionListener(this);
	    this.add(button_userDelete);
	    
	    
	    this.add(Box.createVerticalStrut(5));
	    button_userModify=new JButton("�޸��û�");
	    button_userModify.addActionListener(this);
	    this.add(button_userModify);
	    
	    
	    
	    this.add(Box.createVerticalStrut(10));
	    
	    
	    
	    
	    this.add(Box.createVerticalStrut(5));
	    button_logout=new JButton("ע���û�");
	    button_logout.addActionListener(this);
	    this.add(button_logout);
	    
	    
	    this.add(Box.createVerticalStrut(5));
	    button_exit=new JButton("�˳�ϵͳ");
	    button_exit.addActionListener(this);
	    this.add(button_exit);
	    
	    this.add(Box.createVerticalStrut(10));
	    
	    this.add(Box.createVerticalStrut(5));
	    button_publisher=new JButton("������");
	    button_publisher.addActionListener(this);
	    this.add(button_publisher);
	    
	    this.add(Box.createVerticalStrut(5));
	    button_bookCondition=new JButton("ͼ��״��");
	    button_bookCondition.addActionListener(this);
	    this.add(button_bookCondition);
	    
	    this.add(Box.createVerticalStrut(5));
	    button_recommand=new JButton("�����Ƽ�");
	    button_recommand.addActionListener(this);
	    this.add(button_recommand);
	}






	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand()=="��ѯͼ��"){
			mp.bs=new BookSearch(mp);
			mp.remove(1);
			mp.add(mp.bs);
			mp.validate();
			mp.repaint();
			return;
		}
		if(e.getActionCommand()=="��ѯ�û�"){
			mp.us=new UserSearch(mp);
			mp.remove(1);
			mp.add(mp.us);
			mp.validate();
			mp.repaint();
			return;
		}
        if(e.getActionCommand()=="ͼ����Ϣ"){
			mp.bi=new BookInfo(mp);
			mp.remove(1);
			mp.add(mp.bi);
			mp.validate();
			mp.repaint();
			return;
		}
        if(e.getActionCommand()=="�û���Ϣ"){
			mp.ui=new UserInfo(mp);
			mp.remove(1);
			mp.add(mp.ui);
			mp.validate();
			mp.repaint();
			return;
		}
        if(e.getActionCommand()=="���ͼ��"){
			mp.ba=new BookAdd(mp);
			mp.remove(1);
			mp.add(mp.ba);
			mp.validate();
			mp.repaint();
			return;
		}
        if(e.getActionCommand()=="ɾ��ͼ��"){
			mp.bd=new BookDelete(mp);
			mp.remove(1);
			mp.add(mp.bd);
			mp.validate();
			mp.repaint();
			return;
		}
        if(e.getActionCommand()=="�޸�ͼ��"){
			mp.bm=new BookModify(mp);
			mp.remove(1);
			mp.add(mp.bm);
			mp.validate();
			mp.repaint();
			return;
		}
        if(e.getActionCommand()=="����û�"){
			mp.ua=new UserAdd(mp);
			mp.remove(1);
			mp.add(mp.ua);
			mp.validate();
			mp.repaint();
			return;
		}
        if(e.getActionCommand()=="ɾ���û�"){
			mp.ud=new UserDelete(mp);
			mp.remove(1);
			mp.add(mp.ud);
			mp.validate();
			mp.repaint();
			return;
		}
        if(e.getActionCommand()=="�޸��û�"){
			mp.um=new UserModify(mp);
			mp.remove(1);
			mp.add(mp.um);
			mp.validate();
			mp.repaint();
			return;
		}
        if(e.getActionCommand()=="ע���û�"){
        	Client.sendRequest();
        	Library.library.manager.setOn(false);
        	Client.returnInfo();
        	mp.c.remove(mp);
        	mp.c.login();
        	mp.c.validate();
			return;
		}
        if(e.getActionCommand()=="�˳�ϵͳ"){
        	Client.sendRequest();
        	Library.library.manager.setOn(false);
        	Client.returnInfo();
			Client.breakServer();
        	System.exit(0);
        	return;
        }if(e.getActionCommand()=="������"){
        	mp.pp=new PublisherPanel(mp);
			mp.remove(1);
			mp.add(mp.pp);
			mp.validate();
			mp.repaint();
        	return;
        }
        if(e.getActionCommand()=="ͼ��״��"){
        	
        	mp.bp=new BookConditionPanel(mp);
			mp.remove(1);
			mp.add(mp.bp);
			mp.validate();
			mp.repaint();
        	
        	return;
        }
        if(e.getActionCommand()=="�����Ƽ�"){
        	mp.rp=new RecommandPanel(mp);
			mp.remove(1);
			mp.add(mp.rp);
			mp.validate();
			mp.repaint();
        	
        	return;
        } 
	}
}
