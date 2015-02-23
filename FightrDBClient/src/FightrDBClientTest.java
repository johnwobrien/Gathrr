import java.util.ArrayList;

import org.junit.Test;

import com.mongodb.DBObject;

public class FightrDBClientTest {
	FightrDBClient client = new FightrDBClient();
	
	@Test
	public void updateUserTest() {
		
	} 
	
	@Test
	public void addGetUpdateRemoveUserTest() {
		String linkToPicture = "https://fbcdn-sphotos-f-a.akamaihd.net/hphotos-ak-xpa1/t31.0-8/q86/s960x960/886447_10201000978705929_1311356310_o.jpg";
		client.addUser("john.obrien.395", "John O'Brien", 245.5, "male", linkToPicture);
		
		String userID = "john.obrien.395";
		DBObject response = client.getUser(userID);
		assert(response.get("id").equals(userID));
		
		client.updateUser("john.obrien.395", "John O'Brien", 260.0, "male", linkToPicture, new ArrayList<Object>());

		response = client.getUser(userID);
		assert(response.get("weight")==(Object)260.0);
		
		client.deleteUser(userID);
		
		response = client.getUser(userID);
		assert(response==null);
	} 
}
