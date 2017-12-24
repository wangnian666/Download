package fileDownload;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ObtainPathAndFile {
	String[] serverList;
	public void  pathDown(String ah, Connection con) throws SQLException{
		String sqlPath = "select path,real_Name from ts_jzfile_store WHERE ( file_type='jpg' OR file_type='rtf' OR file_type='png' OR file_type='jpeg' OR file_type='trff' OR file_type='triff' ) AND case_id  in "
				+ "(select id from ws_ajxx where sync_status=1 and ah = "
				+ "'" + ah + "'" + ");";
		Statement st = con.createStatement();
		ResultSet rsPath = st.executeQuery(sqlPath);
		while (rsPath.next()) {
			String	path = rsPath.getString("path");
			String	real_name = rsPath.getString("real_Name");
			ObtainPathAndFile obf = new ObtainPathAndFile();
			obf.pathStructSound(path, real_name, ah);
		}
		rsPath.close();
	}
	public void pathStructSound(String path1, String real_name, String ah) {
		String fdfsServer = null;
		try {
			fdfsServer = ObtainConfig.config("fdfsServer");
			serverList = fdfsServer.split("\\|");
		} catch (IOException e) {
			e.printStackTrace();
		}
//		String downLoadpath = fdfsServer + "/" + path1;
		String filePath = "";
		filePath = "D:\\资料\\" + ah + "\\" + real_name;
		File fileOutputNameFile = new File(filePath);
		// 下载文件
		if (fileOutputNameFile == null || !fileOutputNameFile.exists()
				|| fileOutputNameFile.length() == 0) {
			try {
				downloadFile(path1, filePath);
			} catch (NullPointerException n) {
				return;
			}
		}

	}

	public void downloadFile(String path, String filePath) {
		URL urlfile = null;
		HttpURLConnection httpUrl = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			File f = new File(filePath);
			if (!f.getParentFile().exists()) {
				if (!f.getParentFile().mkdirs()) {
					System.out.println("创建文件失败！");
				}
			}
			
			for (String server : serverList) {
				String downLoadpath = server + "/" + path;
				urlfile = new URL(downLoadpath);
				httpUrl = (HttpURLConnection) urlfile.openConnection();
				httpUrl.connect();
				InputStream inputStream ;
				try{
				 inputStream = httpUrl.getInputStream();
				}catch(FileNotFoundException e){
					continue;
				}
//				if(inputStream==null){
//					continue;
//				}else{
					bis = new BufferedInputStream(inputStream);
					System.out.println("下载的文件存储路径为：D:\\资料                            "+filePath+"下载中...");
					break;
//				}
			}
			bos = new BufferedOutputStream(new FileOutputStream(f));
			int len = 2048;
			byte[] b = new byte[len];
			while ((len = bis.read(b)) != -1) {
				bos.write(b, 0, len);
			}
			bos.flush();
			bis.close();
			httpUrl.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			try {
				bis.close();
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
