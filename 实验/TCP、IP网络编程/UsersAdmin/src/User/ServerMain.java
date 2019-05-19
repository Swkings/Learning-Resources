package User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ServerMain {
	
	private static List<User> list = new ArrayList();
	static Scanner cin = new Scanner(System.in);
	private static void ShowMenu() {
		System.out.println("*************��ӭʹ�á��������û�����ϵͳ**************");
		System.out.println("1���û��б�");
		System.out.println("2������û�");
		System.out.println("3��ɾ���û�");
		System.out.println("4���༭�û�");
		System.out.println("��ѡ��1-4����");
		//��ȡ��������
		int type = Integer.parseInt(cin.nextLine());
		switch(type){
		case 1:  //�û��б�
			findAll();
			break;
		case 2:  //����û�
			add();
			break;
		case 3:	 //ɾ���û�
			delete();
			break;
		case 4:  //�༭�û�
			update();
			break;
			
		}
		
	}
	public static void main(String[] args) {
		TestMySql log = new TestMySql();
		login();
	}


	
	
	
	
	//�û���¼
	private static void login(){
		System.out.println("***********��ӭʹ�á��������û�����ϵͳ*************");
		System.out.println("�û���¼����");
		System.out.println("�������û�����");
		String user_name = cin.nextLine();
		System.out.println("���������룺");
		String user_password = cin.nextLine();



		
		
		if("admin".equals(user_name)&&"admin".equals(user_password)) {
			ShowMenu();
		}else {
			login();
		}
	}
	
	
	//�����û�
	private static void update() {
		User user = new User();
		System.out.println("************�����û�*******************");
		System.out.println("������\t���\t����\t�Ա�\t����");
		for(int i=0; i<list.size(); i++) {
			System.out.println(i+"\t"+list.get(i).getId()+"\t"+
							   list.get(i).getName()+"\t"+
							   list.get(i).getSex()+"\t"+
							   list.get(i).getAge());
		}
		
		if(list.size() < 1) {
			
			System.out.println("�޿ɱ༭�û�");
			System.out.println("�������˵���������(�����ִ�Сд)��return");
			Scanner cin = new Scanner(System.in);
			String str = cin.nextLine();
			while(!"return".equals(str.toLowerCase())) {
				System.out.println("�������������(�����ִ�Сд)��return");
			    str = cin.nextLine();
			}
			
		}else {
			System.out.println("������Ҫ�����û��������ţ�");
			int index = Integer.parseInt(cin.nextLine());
			if(index<0 || index>list.size()-1) {
				System.out.println("*****��������Ч��������!****");
				update();
			}
			System.out.println("ԭ���Ϊ����"+list.get(index).getId()+"�����������±�ţ�");
			String id = cin.nextLine();
			System.out.println("ԭ����Ϊ����"+list.get(index).getName()+"������������������");
			String name = cin.nextLine();
			System.out.println("ԭ�Ա�Ϊ����"+list.get(index).getSex()+"�������������Ա�");
			String sex = cin.nextLine();
			System.out.println("ԭ����Ϊ����"+list.get(index).getAge()+"���������������䣺");
			String age = cin.nextLine();
			if(!"".equals(id)) {
				try {
					list.get(index).setId(Integer.parseInt(id));
				}catch(Exception e) {
					System.out.println("������Ϸ��ı�Ż����䣡");
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
					System.out.println("������Ϸ��ı�Ż����䣡");
					update();
					return;
				}
				
			}
			//list.add(index,user);
			user = list.get(index);
			System.out.println(user);
			System.out.println("**********���³ɹ�************");
		}
		ShowMenu();
		
	}

	//ɾ���û�
	private static void delete() {
		System.out.println("************ɾ���û�*******************");
		System.out.println("������\t���\t����\t�Ա�\t����");
		for(int i=0; i<list.size(); i++) {
			System.out.println(i+"\t"+list.get(i).getId()+"\t"+
							   list.get(i).getName()+"\t"+
							   list.get(i).getSex()+"\t"+
							   list.get(i).getAge());
		}
		
		if(list.size() < 1) {
			System.out.println("��ɾ���û�");
			System.out.println("�������˵���������(�����ִ�Сд)��return");
			Scanner cin = new Scanner(System.in);
			String str = cin.nextLine();
			while(!"return".equals(str.toLowerCase())) {
				System.out.println("�������������(�����ִ�Сд)��return");
			    str = cin.nextLine();
			}
		}else {
			System.out.println("������Ҫɾ���û��������ţ�");
			int index = Integer.parseInt(cin.nextLine());
			if(index<0 || index>list.size()-1) {
				System.out.println("***��������Ч��������!***");
				delete();
			}

			list.remove(index);
			System.out.println("************ɾ���ɹ�**************");
			
		}
		ShowMenu();
	}
	
	//����û�
	private static void add() {
		System.out.println("************����û�*******************");
		User use = new User();
		System.out.println("��ţ�");
		String id = cin.nextLine();
		System.out.println("������");
		String name = cin.nextLine();
		System.out.println("�Ա�");
		String sex = cin.nextLine();
		System.out.println("���䣺");
		String age = cin.nextLine();

		use.setName(name);
		use.setSex(sex);
		try {
			use.setId(Integer.parseInt(id));
			use.setAge(Integer.parseInt(age));
		}catch(Exception e) {
			System.out.println("������Ϸ��ı�Ż����䣡");
			add();
			return;
		}
		
		list.add(use);
		System.out.println("***********����û��ɹ�************");
		ShowMenu();
		
	}
	

	//��ѯ�û���Ϣ
	private static void findAll() {
		System.out.println("���\t����\t�Ա�\t����");
		for(int i=0; i<list.size(); i++) {
			System.out.println(list.get(i).getId()+"\t"+
							   list.get(i).getName()+"\t"+
							   list.get(i).getSex()+"\t"+
							   list.get(i).getAge());
		}
		if(list.size() == 0){
			System.out.println("�������ݣ�");
		}
		
		System.out.println("�������˵���������(�����ִ�Сд)��return");
		Scanner cin = new Scanner(System.in);
		String str = cin.nextLine();
		while(!"return".equals(str.toLowerCase())) {
			System.out.println("�������������(�����ִ�Сд)��return");
		    str = cin.nextLine();
		}
		ShowMenu();
		
	}


}
