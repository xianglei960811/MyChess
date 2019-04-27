package com.bysj.xl.chess.mychess.entity;

/**
 * author:鍚戠 date:2018/9/21 Describe:鐢ㄦ埛淇℃伅鍩虹绫�
 */
public class User {
	String usr_phone;
	int usr_age;
	String usr_AccountId;
	String usr_passWord;
	String usr_Sex;
	String usr_cretTime;
	String user_name;
	String user_headIma;
	String user_grade;

	public String getUser_headIma() {
		return user_headIma;
	}

	public void setUser_headIma(String user_headIma) {
		this.user_headIma = user_headIma;
	}

	public String getUser_grade() {
		return user_grade;
	}

	public void setUser_grade(String user_grade) {
		this.user_grade = user_grade;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUsr_phone() {
		return usr_phone;
	}

	public void setUsr_phone(String usr_phone) {
		this.usr_phone = usr_phone;
	}

	public int getUsr_age() {
		return usr_age;
	}

	public void setUsr_age(int usr_age) {
		this.usr_age = usr_age;
	}

	public String getUsr_AccountId() {
		return usr_AccountId;
	}

	public void setUsr_AccountId(String usr_AccountId) {
		this.usr_AccountId = usr_AccountId;
	}

	public String getUsr_passWord() {
		return usr_passWord;
	}

	public void setUsr_passWord(String usr_passWord) {
		this.usr_passWord = usr_passWord;
	}

	public String getUsr_Sex() {
		return usr_Sex;
	}

	public void setUsr_Sex(String usr_Sex) {
		this.usr_Sex = usr_Sex;
	}

	public String getUsr_cretTime() {
		return usr_cretTime;
	}

	public void setUsr_cretTime(String usr_cretTime) {
		this.usr_cretTime = usr_cretTime;
	}

	@Override
	public String toString() {
		return "User{" + "usr_phone=" + usr_phone + ", usr_age=" + usr_age + ", usr_AccountId='" + usr_AccountId + '\''
				+ ", usr_passWord='" + usr_passWord + '\'' + ", usr_Sex='" + usr_Sex + '\'' + ", usr_cretTime='"
				+ usr_cretTime + '\'' + '}';
	}
}
