package Utilitaires;

import java.io.File;
import java.net.URLDecoder;

public class path {
	
	private static final String imagesFolderPath = "/Images";
	
	public static String get() {
		try {
			String pathString = URLDecoder.decode(ClassLoader.getSystemClassLoader().getResource(".").getPath(), "UTF-8");
			
			if (pathString.length()>=5)
			{
				if (pathString.substring(pathString.length()-5).contains("bin"))
					return URLDecoder.decode(new File(ClassLoader.getSystemClassLoader().getResource(".").getPath()).getParentFile().getPath(), "UTF-8");
			}
			if (pathString.length()>=9)
			{
				if (pathString.substring(pathString.length()-9).contains("classes"))
					return URLDecoder.decode(new File(ClassLoader.getSystemClassLoader().getResource(".").getPath()).getParentFile().getParentFile().getPath(), "UTF-8");
			}
			
			return pathString;
		} catch (Exception e) {
			e.printStackTrace();
			return "ERROR : " + e.getLocalizedMessage();
		}
	}
	
	public static String getImagePath(String image) {
		return get()+imagesFolderPath+"/"+image;
	}
}
