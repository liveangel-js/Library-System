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
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "主菜单"));
		   
		
		
		this.add(Box.createVerticalStrut(20));
	    button_searchBook=new JButton("查询图书");
	    button_searchBook.addActionListener(this);
		this.add(button_searchBook);
		
		
		this.add(Box.createVerticalStrut(5));
		button_searchUser=new JButton("查询用户");
		button_searchUser.addActionListener(this);
	    this.add(button_searchUser);
	    
	    
	    
	    
	    
	    this.add(Box.createVerticalStrut(10));
	    
	    
	    this.add(Box.createVerticalStrut(5));
	    button_bookInfo=new JButton("图书信息");
	    button_bookInfo.addActionListener(this);
	    this.add(button_bookInfo);
	    
	    
	    this.add(Box.createVerticalStrut(5));
	    button_userInfo=new JButton("用户信息");
	    button_userInfo.addActionListener(this);
	    this.add(button_userInfo);
	    
	    
	    
	    this.add(Box.createVerticalStrut(10));
	    
	    
	    
	    this.add(Box.createVerticalStrut(5));
	    button_bookAdd=new JButton("添加图书");
	    button_bookAdd.addActionListener(this);
	    this.add(button_bookAdd);
	    
	    
	    this.add(Box.createVerticalStrut(5));
	    button_bookDelete=new JButton("删除图书");
	    button_bookDelete.addActionListener(this);
	    this.add(button_bookDelete);
	    
	    
	    this.add(Box.createVerticalStrut(5));
	    button_bookModify=new JButton("修改图书");
	    button_bookModify.addActionListener(this);
	    this.add(button_bookModify);
	    
	    
	    
	    this.add(Box.createVerticalStrut(10));
	    
	    
	    
	   
	    this.add(Box.createVerticalStrut(5));
	    button_userAdd=new JButton("添加用户");
	    button_userAdd.addActionListener(this);
	    this.add(button_userAdd);
	    
	    
	    this.add(Box.createVerticalStrut(5));
	    button_userDelete=new JButton("删除用户");
	    button_userDelete.addActionListener(this);
	    this.add(button_userDelete);
	    
	    
	    this.add(Box.createVerticalStrut(5));
	    button_userModify=new JButton("修改用户");
	    button_userModify.addActionListener(this);
	    this.add(button_userModify);
	    
	    
	    
	    this.add(Box.createVerticalStrut(10));
	    
	    
	    
	    
	    this.add(Box.createVerticalStrut(5));
	    button_logout=new JButton("注销用户");
	    button_logout.addActionListener(this);
	    this.add(button_logout);
	    
	    
	    this.add(Box.createVerticalStrut(5));
	    button_exit=new JButton("退出系统");
	    button_exit.addActionListener(this);
	    this.add(button_exit);
	    
	    this.add(Box.createVerticalStrut(10));
	    
	    this.add(Box.createVerticalStrut(5));
	    button_publisher=new JButton("出版社");
	    button_publisher.addActionListener(this);
	    this.add(button_publisher);
	    
	    this.add(Box.createVerticalStrut(5));
	    button_bookCondition=new JButton("图书状况");
	    button_bookCondition.addActionListener(this);
	    this.add(button_bookCondition);
	    
	    this.add(Box.createVerticalStrut(5));
	    button_recommand=new JButton("读者推荐");
	    button_recommand.addActionListener(this);
	    this.add(button_recommand);
	}






	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand()=="查询图书"){
			mp.bs=new BookSearch(mp);
			mp.remove(1);
			mp.add(mp.bs);
			mp.validate();
			mp.repaint();
			return;
		}
		if(e.getActionCommand()=="查询用户"){
			mp.us=new UserSearch(mp);
			mp.remove(1);
			mp.add(mp.us);
			mp.validate();
			mp.repaint();
			return;
		}
        if(e.getActionCommand()=="图书信息"){
			mp.bi=new BookInfo(mp);
			mp.remove(1);
			mp.add(mp.bi);
			mp.validate();
			mp.repaint();
			return;
		}
        if(e.getActionCommand()=="用户信息"){
			mp.ui=new UserInfo(mp);
			mp.remove(1);
			mp.add(mp.ui);
			mp.validate();
			mp.repaint();
			return;
		}
        if(e.getActionCommand()=="添加图书"){
			mp.ba=new BookAdd(mp);
			mp.remove(1);
			mp.add(mp.ba);
			mp.validate();
			mp.repaint();
			return;
		}
        if(e.getActionCommand()=="删除图书"){
			mp.bd=new BookDelete(mp);
			mp.remove(1);
			mp.add(mp.bd);
			mp.validate();
			mp.repaint();
			return;
		}
        if(e.getActionCommand()=="修改图书"){
			mp.bm=new BookModify(mp);
			mp.remove(1);
			mp.add(mp.bm);
			mp.validate();
			mp.repaint();
			return;
		}
        if(e.getActionCommand()=="添加用户"){
			mp.ua=new UserAdd(mp);
			mp.remove(1);
			mp.add(mp.ua);
			mp.validate();
			mp.repaint();
			return;
		}
        if(e.getActionCommand()=="删除用户"){
			mp.ud=new UserDelete(mp);
			mp.remove(1);
			mp.add(mp.ud);
			mp.validate();
			mp.repaint();
			return;
		}
        if(e.getActionCommand()=="修改用户"){
			mp.um=new UserModify(mp);
			mp.remove(1);
			mp.add(mp.um);
			mp.validate();
			mp.repaint();
			return;
		}
        if(e.getActionCommand()=="注销用户"){
        	Client.sendRequest();
        	Library.library.manager.setOn(false);
        	Client.returnInfo();
        	mp.c.remove(mp);
        	mp.c.login();
        	mp.c.validate();
			return;
		}
        if(e.getActionCommand()=="退出系统"){
        	Client.sendRequest();
        	Library.library.manager.setOn(false);
        	Client.returnInfo();
			Client.breakServer();
        	System.exit(0);
        	return;
        }if(e.getActionCommand()=="出版社"){
        	mp.pp=new PublisherPanel(mp);
			mp.remove(1);
			mp.add(mp.pp);
			mp.validate();
			mp.repaint();
        	return;
        }
        if(e.getActionCommand()=="图书状况"){
        	
        	mp.bp=new BookConditionPanel(mp);
			mp.remove(1);
			mp.add(mp.bp);
			mp.validate();
			mp.repaint();
        	
        	return;
        }
        if(e.getActionCommand()=="读者推荐"){
        	mp.rp=new RecommandPanel(mp);
			mp.remove(1);
			mp.add(mp.rp);
			mp.validate();
			mp.repaint();
        	
        	return;
        } 
	}
}
