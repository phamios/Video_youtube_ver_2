package admega.cartoon.movies.common.utils;

import java.net.MalformedURLException;
import java.net.URL;

public class YoutubeUtils {
	public static String extractYoutubeId(String url)
			throws MalformedURLException {
		String id = null;
		try {
			String query = new URL(url).getQuery();
			if (query != null) {
				String[] param = query.split("&");
				for (String row : param) {
					String[] param1 = row.split("=");
					if (param1[0].equals("v")) {
						id = param1[1];
					}
				}
			} else {
				if (url.contains("embed")) {
					id = url.substring(url.lastIndexOf("/") + 1);
				}
			}
		} catch (Exception ex) {
			Logger.e("Exception", ex.toString());
		}
		return id;
	}
}
