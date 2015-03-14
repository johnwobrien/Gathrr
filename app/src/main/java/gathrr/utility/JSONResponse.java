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
import java.io.OutputStream;
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
    public static JSONObject getJSONFromUrl(HttpType httpType, String url, List<NameValuePair> params) {
        InputStream is = null;
        JSONObject json = null;
        String outPut = "";

        try {
            String paramString = URLEncodedUtils.format(params, "utf-8");
            url = url + "?" + paramString;
            URL uri = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) uri.openConnection();

            switch(httpType)
            {
                case POST:
                    String charset = "utf-8";
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setDoOutput(true);
                    urlConnection.setRequestProperty("Accept-Charset", charset);
                    urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);

                    try{
                        OutputStream output = urlConnection.getOutputStream();
                        output.write(paramString.getBytes(charset));
                    }
                    catch(IOException ex){}

                    int responseCode = urlConnection.getResponseCode();
                    System.out.println("Response Code: " + responseCode);

                    break;
                case GET:
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();
                    break;
                case DELETE:
                    urlConnection.setRequestMethod("DELETE");
                    urlConnection.connect();
                    break;
                default:
                    return null;
            }

            is = urlConnection.getInputStream();
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