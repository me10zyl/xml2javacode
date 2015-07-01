package net.xicp.zyl_me.xml2javacode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class Utils {
	public static String readFile(File file) throws UnsupportedEncodingException, FileNotFoundException, IOException
	{
		return readInputStream(new FileInputStream(file));
	}
	
	public static String readInputStream(InputStream in) throws UnsupportedEncodingException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));
		StringBuilder sb = new StringBuilder();
		String str = null;
		while ((str = br.readLine()) != null) {
			sb.append(str);
		}
		br.close();
		return sb.toString();
	}
}
