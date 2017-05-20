import java.sql.*;
import java.util.ArrayList;

import Data.User;

public class main {

	public static void main(String[] args) {
		//Examples of how to use API
		//1 is just an example insert any of existing IDs
		System.out.println(User.Friends(1));  
		System.out.println(User.friendsOfFriends(1));
		System.out.println(User.sugestedFriends(1));
		
		
		

	}
}
