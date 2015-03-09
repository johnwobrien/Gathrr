package FightrConnection;

public class DatabaseSeed {
	public static void Seed()
	{
		FightrDBClient client = new FightrDBClient();
		
		String[] testImages = {"miketyson.jpeg", "Ryu_SF.jpg"};
		String[] genders = {"male", "female", "male"};
		int numUsers = 30;
		String id;
		String name;
		String gender;
		int weight;
		String imagePath;
		for(int i = 0; i < numUsers; i++)
		{
			id = "user" + i;
			name = "User" + i;
			gender = genders[i%genders.length];
			weight = (int)(Math.random()*150.0 + 120);
			imagePath = "src/androidTest/testImages/" + testImages[i%testImages.length];
			client.addUser(id, name, weight, gender, imagePath);
		}
	}
}
