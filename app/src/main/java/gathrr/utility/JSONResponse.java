package gathrr.utility;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.message.AbstractHttpMessage;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import android.util.Log;
import java.net.URL;
import java.net.HttpURLConnection;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.HttpEntity;

public class JSONResponse{

    static InputStream is = null;
    static JSONObject json = null;
    static String outPut = "";

    public static JSONObject getJSONFromUrl(HttpType httpType, String url, List<NameValuePair> params) {

        //HttpRequestBase req = null;
        HttpResponse httpResponse = null;
        // Making the HTTP request
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            String paramString = "";

            switch(httpType)
            {
                case POST:
                    //req = new HttpPost(url);
                    //((HttpPost)req).setEntity(new UrlEncodedFormEntity(params));
                    //break;
                case GET:
                    HttpGet req = new HttpGet(url);
                    paramString = URLEncodedUtils.format(params, "utf-8");
                    url += "?" + paramString;
                    httpResponse = httpClient.execute(req);
                    break;
                case DELETE:
                    //req = new HttpDelete(url);
                    //paramString = URLEncodedUtils.format(params, "utf-8");
                    //url += "?" + paramString;
                    //break;
                default:
                    return null;
            }
            //req.setHeader("Content-Type", "text/html");
            //req.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");

            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();
            outPut = convertStreamToString(is);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            json = new JSONObject(outPut);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON String
        return json;

    }

    private static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}