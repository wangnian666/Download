/**
 * 
 */
package fileDownload;
public class ObtainSqlSentence {
	public static String sqlSentenceAh(int firstDate, int lastDate
			) {
		String sqlLocalCase = null;
		if ((firstDate != 0) & (lastDate != 0)) {
			sqlLocalCase = "select ah FROM ws_ajxx WHERE "
					+ "  CONVERT(LARQ,SIGNED) > "
					+ firstDate
					+ " AND CONVERT(LARQ,SIGNED) < " + lastDate + ";";
		} else if ((firstDate != 0) & (lastDate == 0)) {
			sqlLocalCase = "select ah FROM ws_ajxx WHERE "
					+ "   CONVERT(LARQ,SIGNED) > "
					+ firstDate + ";";
		} else if ((firstDate == 0) & (lastDate != 0)) {
			sqlLocalCase = "select ah FROM ws_ajxx WHERE "
					+ "  CONVERT(LARQ,SIGNED) < "
					+ lastDate + ";";
		//	System.out.println(sqlLocalCase);
		} else if ((firstDate == 0) & (lastDate == 0)) {
			sqlLocalCase = "select ah FROM ws_ajxx ";
		} else {
			System.out.println("配置文件里日期格式输入错误！");
		}
		return sqlLocalCase;
	}
}
