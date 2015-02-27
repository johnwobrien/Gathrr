import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FightrDBClient {
	private MongoClient mongoClient;
	private DB fightrDB;
	private DBCollection userCollection;
	
	FightrDBClient(){
		MongoCredential credentials = MongoCredential.createCredential("fightr-user", "fightrdb", "fightr-password".toCharArray());
		List<MongoCredential> credList = new ArrayList<MongoCredential>();
		credList.add(credentials);
		ServerAddress sa = new ServerAddress("ds045531.mongolab.com",45531);
		mongoClient = new MongoClient(sa,credList);
		
		
		fightrDB = mongoClient.getDB("fightrdb");
		
		userCollection = fightrDB.getCollectionFromString("users");
	}
	
	private String sanitizeID(String id){
		String newID = "";
		
		for(int i = 0;i<id.length();i++){
			if(id.charAt(i)!='.'){
				newID += id.charAt(i);
			}
		}
		return newID;
	}
	
	public void addUser(String id, String name, double weight, String sex, String linkToPicture){
		/*
		 * User information:
		 * - Unique ID
		 * - Name
		 * - Weight
		 * - Sex
		 * - Link to profile picture
		 * - History
		 * - Fighters-Seen
		 */
		
		id = sanitizeID(id);
		Map<String, Object> documentMap = new HashMap<String, Object>();
		documentMap.put("id", id);
		documentMap.put("name", name);
		documentMap.put("weight", weight);
		documentMap.put("sex", sex);
		documentMap.put("picture", linkToPicture);
		documentMap.put("history", new ArrayList<Object>());
		documentMap.put("fighters-seen", new HashMap<String,Long>());
		
		userCollection.insert(new BasicDBObject(documentMap));
	}
	
	public DBObject getUser(String id){
		id = sanitizeID(id);
		BasicDBObject query = new BasicDBObject("id", id);
		DBCursor cursor = userCollection.find(query);
		try {
			if(cursor.hasNext()) {
				return cursor.next();
			}
		} 
		finally {
			cursor.close();
		}
		return null;
	}
	
	public void updateUser(String id, String name, double weight, String sex, String linkToPicture,ArrayList<Object> history,HashMap<String,Long> fightersSeen){
		/*
		 * User information:
		 * - Unique ID
		 * - Name
		 * - Weight
		 * - Sex
		 * - Link to profile picture
		 * - History
		 */
		id = sanitizeID(id);
		Map<String, Object> documentMap = new HashMap<String, Object>();
		documentMap.put("id", id);
		documentMap.put("name", name);
		documentMap.put("weight", weight);
		documentMap.put("sex", sex);
		documentMap.put("picture", linkToPicture);
		documentMap.put("history", history);
		documentMap.put("fighters-seen", fightersSeen);
		
		BasicDBObject query = new BasicDBObject("id", id);
		userCollection.update(query, new BasicDBObject(documentMap));
	}
	
	public void addSeen(String id, String idSeen){
		id = sanitizeID(id);
		idSeen = sanitizeID(idSeen);
		
		DBObject curUser = getUser(id);
		
		HashMap<String,Long> fightersSeen = ((HashMap<String,Long>)curUser.get("fighters-seen"));
		fightersSeen.put(idSeen,(Long)System.currentTimeMillis());
		
		updateUser(id,(String)curUser.get("name"),(Double)curUser.get("weight"),(String)curUser.get("sex"),(String)curUser.get("picture"),(ArrayList<Object>)curUser.get("history"),fightersSeen);
	}
	
	public ArrayList<DBObject> getAllNotSeen(String id){
		id = sanitizeID(id);
		
		DBObject user = getUser(id);
		HashMap<String,Long> seen = (HashMap<String,Long>)user.get("fighters-seen");
		String sex = (String)user.get("sex");
		
		BasicDBObject query = new BasicDBObject("sex", sex);
		
		ArrayList<DBObject> response = new ArrayList<DBObject>();
		DBCursor cursor = userCollection.find(query);
		
		try {
		    while (cursor.hasNext()) {
		        DBObject item = cursor.next();
		        if(id!=item.get("id") && !seen.containsKey(item.get("id"))){
		        	response.add(item);
		        }
		    }
		} finally {
		    cursor.close();
		}
		
		return response;
	}
	
	public void deleteUser(String id){
		id = sanitizeID(id);
		DBObject document = getUser(id);
		userCollection.remove(document);
	}
	
}
