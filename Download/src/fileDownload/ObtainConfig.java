package fileDownload;

import java.util.Properties;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ObtainConfig {

	public static String config(String key) throws IOException {
		Properties prop = new Properties();
		InputStream in = new BufferedInputStream(new FileInputStream(
				"./configs/config.properties"));
		prop.load(in);
		String value = prop.getProperty(key);
		return value;
	}
}
