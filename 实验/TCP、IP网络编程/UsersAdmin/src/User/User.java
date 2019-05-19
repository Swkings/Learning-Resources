package User;
/*
 * 
 * 
 */
public class User {
	 //编号 姓名 性别 年龄
	private Integer id;
	private String name;
	private String sex;
	private Integer age;
	
	//生成set（），get（）方法， Source->Generate getters and setters
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
