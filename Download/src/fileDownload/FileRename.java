/**
 * 
 */
package fileDownload;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.io.IOException;

public class FileRename {
	public static void main(String[] args) throws ParseException,
			ClassNotFoundException, SQLException, IOException {
		Connection con = null;
		String driver = null;
		String url = null;
		String user = null;
		String password = null;
		// 加载数据库驱动
		try {
			driver = ObtainConfig.config("jdbc.driver");
			url = ObtainConfig.config("jdbc.url");
			user = ObtainConfig.config("jdbc.username");
			password = ObtainConfig.config("jdbc.password");
		} catch (IOException e) {
			e.printStackTrace();
		}
		Class.forName(driver);
		Statement statement = null;
		try {
			con = DriverManager.getConnection(url, user, password);
			if (!con.isClosed()) {
				System.out.println("Succeeded connecting to the Database!");
			}
			statement = con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// 组装下载路径及下载文件
		ObtainFiledata.fileLocaldata((com.mysql.jdbc.Statement) statement, con);

		con.close();

		System.out.println("运行结束！");
	}
}
