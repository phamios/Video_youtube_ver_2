package admega.cartoon.movies.common.model.business;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import admega.cartoon.movies.common.utils.Logger;

import admega.cartoon.movies.BuildConfig;

public class JSONParser {
	// constructor
	public JSONParser() {

	}

	public static JSONObject getJSONFromUrl(String url) {
		JSONObject jObj = null;
		InputStream in = null;
		String jsonStr = "";
		// Making HTTP request
		try {
			HttpParams httpParameters = new BasicHttpParams();
			// Set the timeout in milliseconds until a connection is
			// established.
			// The default value is zero, that means the timeout is not used.
			int timeoutConnection = 30000;
			HttpConnectionParams.setConnectionTimeout(httpParameters,
					timeoutConnection);
			// Set the default socket timeout (SO_TIMEOUT)
			// in milliseconds which is the timeout for waiting for data.
			int timeoutSocket = 10000;
			HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

			DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);

			// defaultHttpClient
			// DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);

			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			in = httpEntity.getContent();

		} catch (UnsupportedEncodingException e) {
			if (BuildConfig.DEBUG)
				e.printStackTrace();
		} catch (ClientProtocolException e) {
			if (BuildConfig.DEBUG)
				e.printStackTrace();
		} catch (IOException e) {
			if (BuildConfig.DEBUG)
				e.printStackTrace();
		}
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					in, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			in.close();
			jsonStr = sb.toString();
		} catch (Exception e) {
			if (BuildConfig.DEBUG) {
				e.printStackTrace();
			}
		}
		// try parse the string to a JSON object
		try {
			jObj = new JSONObject(jsonStr);
		} catch (JSONException e) {
			Logger.e("JSON Parser", "Error parsing data " + e.toString());
		}

		// return JSON String
		return jObj;
	}

}
