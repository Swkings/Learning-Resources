package User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ServerMain {
	
	private static List<User> list = new ArrayList();
	static Scanner cin = new Scanner(System.in);
	private static void ShowMenu() {
		System.out.println("*************欢迎使用【大网】用户管理系统**************");
		System.out.println("1、用户列表");
		System.out.println("2、添加用户");
		System.out.println("3、删除用户");
		System.out.println("4、编辑用户");
		System.out.println("请选择（1-4）：");
		//获取输入类型
		int type = Integer.parseInt(cin.nextLine());
		switch(type){
		case 1:  //用户列表
			findAll();
			break;
		case 2:  //添加用户
			add();
			break;
		case 3:	 //删除用户
			delete();
			break;
		case 4:  //编辑用户
			update();
			break;
			
		}
		
	}
	public static void main(String[] args) {
		TestMySql log = new TestMySql();
		login();
	}


	
	
	
	
	//用户登录
	private static void login(){
		System.out.println("***********欢迎使用【大网】用户管理系统*************");
		System.out.println("用户登录界面");
		System.out.println("请输入用户名：");
		String user_name = cin.nextLine();
		System.out.println("请输入密码：");
		String user_password = cin.nextLine();



		
		
		if("admin".equals(user_name)&&"admin".equals(user_password)) {
			ShowMenu();
		}else {
			login();
		}
	}
	
	
	//更新用户
	private static void update() {
		User user = new User();
		System.out.println("************更新用户*******************");
		System.out.println("索引号\t编号\t姓名\t性别\t年龄");
		for(int i=0; i<list.size(); i++) {
			System.out.println(i+"\t"+list.get(i).getId()+"\t"+
							   list.get(i).getName()+"\t"+
							   list.get(i).getSex()+"\t"+
							   list.get(i).getAge());
		}
		
		if(list.size() < 1) {
			
			System.out.println("无可编辑用户");
			System.out.println("返回主菜单，请输入(不区分大小写)：return");
			Scanner cin = new Scanner(System.in);
			String str = cin.nextLine();
			while(!"return".equals(str.toLowerCase())) {
				System.out.println("输入错误，请输入(不区分大小写)：return");
			    str = cin.nextLine();
			}
			
		}else {
			System.out.println("请输入要更新用户的索引号：");
			int index = Integer.parseInt(cin.nextLine());
			if(index<0 || index>list.size()-1) {
				System.out.println("*****请输入有效的索引号!****");
				update();
			}
			System.out.println("原编号为：【"+list.get(index).getId()+"】，请输入新编号：");
			String id = cin.nextLine();
			System.out.println("原姓名为：【"+list.get(index).getName()+"】，请输入新姓名：");
			String name = cin.nextLine();
			System.out.println("原性别为：【"+list.get(index).getSex()+"】，请输入新性别：");
			String sex = cin.nextLine();
			System.out.println("原年龄为：【"+list.get(index).getAge()+"】，请输入新年龄：");
			String age = cin.nextLine();
			if(!"".equals(id)) {
				try {
					list.get(index).setId(Integer.parseInt(id));
				}catch(Exception e) {
					System.out.println("请输入合法的编号或年龄！");
					update();
					return;
				}		
			}
			if(!"".equals(name)) {
				list.get(index).setName(name);
			}
			if(!"".equals(sex)) {
				list.get(index).setSex(sex);
			}
			if(!"".equals(age)) {
				try {
					list.get(index).setAge(Integer.parseInt(age));
				}catch(Exception e) {
					System.out.println("请输入合法的编号或年龄！");
					update();
					return;
				}
				
			}
			//list.add(index,user);
			user = list.get(index);
			System.out.println(user);
			System.out.println("**********更新成功************");
		}
		ShowMenu();
		
	}

	//删除用户
	private static void delete() {
		System.out.println("************删除用户*******************");
		System.out.println("索引号\t编号\t姓名\t性别\t年龄");
		for(int i=0; i<list.size(); i++) {
			System.out.println(i+"\t"+list.get(i).getId()+"\t"+
							   list.get(i).getName()+"\t"+
							   list.get(i).getSex()+"\t"+
							   list.get(i).getAge());
		}
		
		if(list.size() < 1) {
			System.out.println("无删除用户");
			System.out.println("返回主菜单，请输入(不区分大小写)：return");
			Scanner cin = new Scanner(System.in);
			String str = cin.nextLine();
			while(!"return".equals(str.toLowerCase())) {
				System.out.println("输入错误，请输入(不区分大小写)：return");
			    str = cin.nextLine();
			}
		}else {
			System.out.println("请输入要删除用户的索引号：");
			int index = Integer.parseInt(cin.nextLine());
			if(index<0 || index>list.size()-1) {
				System.out.println("***请输入有效的索引号!***");
				delete();
			}

			list.remove(index);
			System.out.println("************删除成功**************");
			
		}
		ShowMenu();
	}
	
	//添加用户
	private static void add() {
		System.out.println("************添加用户*******************");
		User use = new User();
		System.out.println("编号：");
		String id = cin.nextLine();
		System.out.println("姓名：");
		String name = cin.nextLine();
		System.out.println("性别：");
		String sex = cin.nextLine();
		System.out.println("年龄：");
		String age = cin.nextLine();

		use.setName(name);
		use.setSex(sex);
		try {
			use.setId(Integer.parseInt(id));
			use.setAge(Integer.parseInt(age));
		}catch(Exception e) {
			System.out.println("请输入合法的编号或年龄！");
			add();
			return;
		}
		
		list.add(use);
		System.out.println("***********添加用户成功************");
		ShowMenu();
		
	}
	

	//查询用户信息
	private static void findAll() {
		System.out.println("编号\t姓名\t性别\t年龄");
		for(int i=0; i<list.size(); i++) {
			System.out.println(list.get(i).getId()+"\t"+
							   list.get(i).getName()+"\t"+
							   list.get(i).getSex()+"\t"+
							   list.get(i).getAge());
		}
		if(list.size() == 0){
			System.out.println("暂无数据！");
		}
		
		System.out.println("返回主菜单，请输入(不区分大小写)：return");
		Scanner cin = new Scanner(System.in);
		String str = cin.nextLine();
		while(!"return".equals(str.toLowerCase())) {
			System.out.println("输入错误，请输入(不区分大小写)：return");
		    str = cin.nextLine();
		}
		ShowMenu();
		
	}


}
