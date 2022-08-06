package club.shmoke.client.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Web {
	
	public static ArrayList<String> getPageData(String url) throws IOException {
		ArrayList<String> data = new ArrayList<String>();
		URL page = new URL(url);

		URLConnection con = page.openConnection();
		InputStream is = con.getInputStream();

		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line = null;

		while ((line = br.readLine()) != null) {
			data.add(line);
		}
		return data;
	}

	public static String getPage(String url) throws IOException {
		URL page = new URL(url);

		URLConnection con = page.openConnection();
		InputStream is = con.getInputStream();

		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line = null;

		while ((line = br.readLine()) != null) {
			return line;
		}
		return null;
	}

}
