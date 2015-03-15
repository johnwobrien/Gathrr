package gathrr.utility;

import android.util.Log;

import org.json.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import java.util.ArrayList;
import java.util.List;

public class ApiHelper {

    private static String baseUrl = "http://fightr.herokuapp.com/api/";
    //private static String baseUrl = "http://127.0.0.1:8080/api/";

    private static String TAG = "ApiHelper";

    //The below passes the tag and the user name over to the JSON parser class
    public static JSONObject addUser(String id, String name, double weight, String sex, String picture){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id", id));
        params.add(new BasicNameValuePair("name", name));
        params.add(new BasicNameValuePair("weight", Double.toString(weight)));
        params.add(new BasicNameValuePair("sex", sex));
        params.add(new BasicNameValuePair("picture", picture));

        String url = baseUrl + "addUser";
        JSONObject json = JSONResponse.getJSONFromUrl(HttpType.POST, url, params);
        return json;
    }

    public static JSONObject getUser(String id){
        return users(id, HttpType.GET);
    }
    public static JSONObject updateUser(String id){
        return users(id, HttpType.POST);
    }
    public static JSONObject deleteUser(String id){
        return users(id, HttpType.DELETE);
    }

    public static JSONObject addSeen(String id, String idSeen)
    {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id", id));
        params.add(new BasicNameValuePair("idSeen", idSeen));

        String url = baseUrl + "addSeen";
        JSONObject json = JSONResponse.getJSONFromUrl(HttpType.POST, url, params);
        return json;
    }

    public static JSONObject getNextFighter(String id)
    {
        Log.i(TAG, "getNextFighter for id: " + id);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id", id));

        String url = baseUrl + "getNextFighter";
        JSONObject json = JSONResponse.getJSONFromUrl(HttpType.GET, url, params);
        return json;
    }

    public static JSONObject getAllFighters()
    {
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        String url = baseUrl + "getAllFighters";
        JSONObject json = JSONResponse.getJSONFromUrl(HttpType.GET, url, params);
        return json;
    }





    private static JSONObject users(String id, HttpType httpType)
    {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id", id));

        String url = baseUrl + "users";
        JSONObject json = JSONResponse.getJSONFromUrl(httpType, url, params);
        return json;
    }



}
