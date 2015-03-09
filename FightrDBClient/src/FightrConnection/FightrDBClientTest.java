package FightrConnection;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import FightrConnection.FightrDBClient;

import com.mongodb.DBObject;

public class FightrDBClientTest {
	FightrDBClient client = new FightrDBClient();
	
	@Test
	public void addGetUpdateRemoveUserTest() {
		String linkToPicture = "https://fbcdn-sphotos-f-a.akamaihd.net/hphotos-ak-xpa1/t31.0-8/q86/s960x960/886447_10201000978705929_1311356310_o.jpg";
		String userID = "test.user21";
		
		client.addUser(userID, "John O'Brien", 245.5, "male", linkToPicture);
		
		DBObject response = client.getUserDB(userID);
		assert(response.get("id").equals(userID));
		
		client.updateUser(userID, "John O'Brien", 260.0, "male", linkToPicture, new ArrayList<Object>(), new  HashMap<String,Long>());

		response = client.getUserDB(userID);
		assert(response.get("weight")==(Object)260.0);
		
		client.addSeen(userID, "coolguy69");
		client.addSeen(userID, "randygoodman1");
		
		//client.deleteUser(userID);
		
		//response = client.getUser(userID);
		//assert(response==null);
	} 
	
	@Test
	public void testSeed()
	{
		DatabaseSeed.Seed();
		client.printAllUsers();
		
		assert(true);
	}
}
