package com.yffd.jysg.java.v7.collection.p2;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年11月20日 上午11:19:36 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class Persion implements Comparable<Persion> {
	private String name;
	private Integer age;
	private Integer version;

	public Persion(String name, Integer age) {
		super();
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Override
	public int hashCode() {
		System.out.println("*******hashCode**********");
		final int prime = 31;
		int result = 1;
		result = prime * result + ((age == null) ? 0 : age.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		System.out.println("*******equals**********");
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Persion other = (Persion) obj;
		if (age == null) {
			if (other.age != null)
				return false;
		} else if (!age.equals(other.age))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public int compareTo(Persion o) {
		System.out.println("*******compareTo**********");
//		return this.age.compareTo(o.age);
		return 1;
	}

	@Override
	public String toString() {
		return "Persion [name=" + name + ", age=" + age + ", version=" + version + "]";
	}

}

