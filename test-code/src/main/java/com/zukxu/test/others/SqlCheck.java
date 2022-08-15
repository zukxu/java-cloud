package com.zukxu.test.others;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * sql 语法检测
 * </p>
 *
 * @author xupu
 * @since 2022/8/15 11:49:18
 */
public class SqlCheck {

    public static void main(String[] args) {

    }

    /* * 加载解析sql文件
     * @param sqlFile
     * @return
     * @throws Exception
     */
    private static List<String> loadSql(String sqlFile) throws Exception {
        List<String> sqlList = new ArrayList<>();
        try {
            InputStream sqlFileIn = Files.newInputStream(Paths.get(sqlFile));
            StringBuffer sqlSb = new StringBuffer();
            byte[] buff = new byte[1024];
            int byteRead;
            while((byteRead = sqlFileIn.read(buff)) != -1) {
                sqlSb.append(new String(buff, 0, byteRead, StandardCharsets.UTF_8));
            }
            sqlFileIn.close();

            // Windows 下换行是 \r\n, Linux 下是 \n
            String[] sqlArr = sqlSb.toString().split("(;\\s*\\r\\n)|(;\\s*\\n)");

            for(String s : sqlArr) {
                String sql = s.replaceAll("--.*", "").trim();
                if(!sql.equals("")) {
                    sqlList.add(sql);
                }
            }
            return sqlList;
        } catch(Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }



    private List<String> loadSql2(String sqlFile) throws Exception {
        List<String> sqlList = new ArrayList<>();
        try {
            FileReader fr = new FileReader(sqlFile);
            BufferedReader br = new BufferedReader(fr);
            String s = "";
            StringBuffer sb = new StringBuffer();
            while((s = br.readLine()) != null) {
                if(s.startsWith("/*") || s.startsWith("--")) {
                    System.out.println("注释 跳过");
                } else if(s.endsWith(";")) {
                    sb.append(s);
                    sqlList.add(sb.toString());
                    sb.delete(0, sb.length());
                } else {
                    sb.append(s);
                }
            }
            fr.close();
            br.close();
        } catch(Exception e) {
            throw new Exception(e.getMessage());
        }
        return sqlList;
    }

}
