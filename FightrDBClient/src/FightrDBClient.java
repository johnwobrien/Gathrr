import com.mongodb.BasicDBObject;
import com.mongodb.BulkWriteOperation;
import com.mongodb.BulkWriteResult;
import com.mongodb.Cursor;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ParallelScanOptions;
import com.mongodb.ServerAddress;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.concurrent.TimeUnit.SECONDS;

public class FightrDBClient {
	private MongoClient mongoClient;
	private DB fightrDB;
	private DBCollection userCollection;
	
	FightrDBClient(){
		MongoCredential credentials = MongoCredential.createCredential("fightr-user", "fightrdb", "fightr-password".toCharArray());
		List<MongoCredential> credList = new ArrayList<MongoCredential>();
		credList.add(credentials);
		ServerAddress sa = new ServerAddress("ds045531.mongolab.com",45531);
		mongoClient = new MongoClient( sa,credList);
		
		
		fightrDB = mongoClient.getDB("fightrdb");
		
		userCollection = fightrDB.getCollectionFromString("users");
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
		 */
		
		Map<String, Object> documentMap = new HashMap<String, Object>();
		documentMap.put("id", id);
		documentMap.put("name", name);
		documentMap.put("weight", weight);
		documentMap.put("sex", sex);
		documentMap.put("picture", linkToPicture);
		documentMap.put("history", new ArrayList<Object>());
		
		userCollection.insert(new BasicDBObject(documentMap));
	}
	
	public DBObject getUser(String id){
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
	
	public void updateUser(String id, String name, double weight, String sex, String linkToPicture,ArrayList<Object> history){
		/*
		 * User information:
		 * - Unique ID
		 * - Name
		 * - Weight
		 * - Sex
		 * - Link to profile picture
		 * - History
		 */
		
		Map<String, Object> documentMap = new HashMap<String, Object>();
		documentMap.put("id", id);
		documentMap.put("name", name);
		documentMap.put("weight", weight);
		documentMap.put("sex", sex);
		documentMap.put("picture", linkToPicture);
		documentMap.put("history", history);
		
		BasicDBObject query = new BasicDBObject("id", id);
		userCollection.update(query, new BasicDBObject(documentMap));
	}
	
	public void deleteUser(String id){
		DBObject document = getUser(id);
		userCollection.remove(document);
	}
	
}
