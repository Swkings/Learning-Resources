package User;
/*
 * 
 * 
 */
public class User {
	 //��� ���� �Ա� ����
	private Integer id;
	private String name;
	private String sex;
	private Integer age;
	
	//����set������get���������� Source->Generate getters and setters
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "User  "+"id:"+this.id+"   name:"+this.name+"   sex:"+this.sex+"   age:"+this.age;
	}
	
	
}
