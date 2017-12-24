/**
 * 
 */
package fileDownload;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class ObtainFiledata {
	public static void fileLocaldata(Statement statement, Connection con)
			throws ClassNotFoundException, SQLException, IOException {
		// 获取时间
		int firstDate = 0;
		int lastDate = 0;
		try {
			String ObtainFirstDate = ObtainConfig.config("startDate");
			String ObtainLastDate = ObtainConfig.config("endDate");
			if (ObtainFirstDate != null && ObtainFirstDate.trim().length() != 0) {
				firstDate = Integer.valueOf(ObtainFirstDate);
			} else {
				firstDate = 0;
			}
			if (ObtainLastDate != null && ObtainLastDate.trim().length() != 0) {
				lastDate = Integer.valueOf(ObtainLastDate);
			} else {
				lastDate = 0;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		String sqlAh = ObtainSqlSentence.sqlSentenceAh(firstDate, lastDate);
		ResultSet rsAh = statement.executeQuery(sqlAh);
		while (rsAh.next()) {
			String ah = rsAh.getString("ah");
			ObtainPathAndFile opf = new ObtainPathAndFile();
			opf.pathDown(ah, con);
		}
		rsAh.close();
	}
}
