package Data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import DataBase.DataBase;

public class User {
	private int ID;
	private String name;
	private String surName;
	private int age;
	private String gender;
	
	User(int ID,String name,String surName,int age,String gender){
		this.ID=ID;
		this.name=name;
		this.surName=surName;
		this.age=age;
		this.setGender(gender);
		
	}
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getName() {
		return name;
	}
	public String getSurName() {
		return surName;
	}
	public void setSurName(String surName) {
		this.surName = surName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {

		this.gender = gender;
	};
	public String toString(){
		return "\nID: "+this.ID
				+"\nfirstName: "+this.name
				+"\nlastName: "+this.surName
				+"\nAge:"+this.age
				+"\nGender: "+this.gender+"\n";
	}
	public static ArrayList<User> Friends(int userID){
		DataBase db=DataBase.getInstanca();
		ArrayList<User> temp=new ArrayList<User>();
		ResultSet rs=db.doSelect("SELECT * FROM `users` WHERE id IN( SELECT friend_two FROM friends WHERE friend_one="+userID+")");
		try{
			while(rs.next()){
			temp.add(new User(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getString(5)));
			
			}
		}
		catch(SQLException e){System.err.println("EROR ResultSet:"+e);}
		return temp;
	}
	private static ArrayList<User> secondLevelArray(int userID){
		ArrayList<User> tempUsersFriends=Friends(userID);
		ArrayList<User> tempFriendsOfFriends=new ArrayList<User>();
		for(int i=0;i<tempUsersFriends.size();i++){
			tempFriendsOfFriends.addAll(Friends(tempUsersFriends.get(i).getID()));
		}
		
		return tempFriendsOfFriends;
	}	
	private static ArrayList<User> removeDuplicatesFrom(ArrayList<User>list,int userID){
		for(int i=0;i<list.size()-1;i++){
			for(int j=i+1;j<list.size();j++){
				if(list.get(i).getID()==userID)list.remove(i);
				else if(list.get(j).getID()==userID)list.remove(j);
				else if(list.get(i).getID()==list.get(j).getID())list.remove(j);
				
			}
		}
		return list;
		
	}
	public static ArrayList<User> friendsOfFriends(int userID){return User.removeDuplicatesFrom(secondLevelArray(userID),userID);}
	public static ArrayList<User> sugestedFriends(int userID){
		
		ArrayList<User> tempsecondLevelArray=User.secondLevelArray(userID);
		ArrayList<User> tempSugestedFriends=new ArrayList<User>();
		
		for(int i=0;i<tempsecondLevelArray.size()-1;i++){
		for(int j=i+1;j<tempsecondLevelArray.size();j++){
			if(tempsecondLevelArray.get(i).getID()==userID)tempsecondLevelArray.remove(i);//removing himself from list (from 0 to size-1 position)
			else if(tempsecondLevelArray.get(j).getID()==userID)tempsecondLevelArray.remove(j);//removing himself from list in case he is on a last spot
			else if(tempsecondLevelArray.get(i).getID()==tempsecondLevelArray.get(j).getID())tempSugestedFriends.add(tempsecondLevelArray.get(i));//saving only users who were seen more then once in secondLevelArray
		}
		
	}
		return removeDirectFriends(tempSugestedFriends,userID);
}
	
	private static ArrayList<User> removeDirectFriends(ArrayList<User>list,int userID){
		ArrayList<User> tempUsersFriends=Friends(userID);
		
		for(int i=0;i<tempUsersFriends.size();i++){
			for(int j=0;j<list.size();j++){
				if(tempUsersFriends.get(i).getID()==list.get(j).getID())list.remove(j);
				
			}
		}
		return list;
		
	}
}
	


