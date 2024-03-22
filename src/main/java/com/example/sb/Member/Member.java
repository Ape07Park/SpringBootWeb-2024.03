package com.example.sb.Member;

public class Member {
	private int mid;
	private String name;
	private int age;
	private String country;
	
	public Member() {}
	
	public Member(int mid, String name, int age, String country) {
		this.mid = mid;
		this.name = name;
		this.age = age;
		this.country = country;
	}
	
	

	public Member(String name, int age, String country) {
		this.name = name;
		this.age = age;
		this.country = country;
	}

	public int getMid() {
		return mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "Member [mid=" + mid + ", name=" + name + ", age=" + age + ", country=" + country + "]";
	}
	
	
	
	
}
