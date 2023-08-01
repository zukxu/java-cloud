package com.zukxu.test.others;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022/8/11 14:44:06
 */

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import cn.hutool.core.util.StrUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RemoteExecuteCommand {

    //字符编码默认是utf-8

    private static String DEFAULT_CHART = "UTF-8";

    private static Connection conn;

    private String ip;

    private String userName;

    private String userPwd;

    public RemoteExecuteCommand(String ip, String userName, String userPwd) {
        this.ip = ip;
        this.userName = userName;
        this.userPwd = userPwd;
    }

    public RemoteExecuteCommand() {

    }

    /**
     * 解析脚本执行返回的结果集
     *
     * @param in      输入流对象
     * @param charset 编码
     * @return 以纯文本的格式返回
     * @author Ickes
     * @since V0.1
     */

    public static String processStdout(InputStream in, String charset) {
        InputStream stdout = new StreamGobbler(in);
        StringBuffer buffer = new StringBuffer();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(stdout, charset));
            String line = null;
            while ((line = br.readLine()) != null) {
                buffer.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    public static void main(String[] args) {
        RemoteExecuteCommand rec = new RemoteExecuteCommand("10.22.33.44", "root", "root");// 参数分别为服务器IP ，用户名，密码
        //执行命令
        try {
            if (rec.login()) {
                System.out.println("=====第一个步骤=====");
                Session session = conn.openSession();//打开一个会话//TODO:多条命令
                session.execCommand("sh /app/Saiku/saikuRefreshShell.sh");//执行命令，这里需要确定刷新脚本存在的绝对路径哦
                String result = processStdout(session.getStdout(), DEFAULT_CHART);
                //如果为得到标准输出为空，说明脚本执行出错了
                if (StrUtil.isBlank(result)) {
                    System.out.println("脚本出错");
                    result = processStdout(session.getStderr(), DEFAULT_CHART);
                }
                System.out.println(result);
                session.close();
                conn.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setCharset(String charset) {
        DEFAULT_CHART = charset;
    }

    /**
     * 远程登录linux的主机
     *
     * @return 登录成功返回true，否则返回false
     * @author Ickes
     * @since V0.1
     */

    public Boolean login() {
        boolean flg = false;
        try {
            conn = new Connection(ip);
            conn.connect();//连接
            flg = conn.authenticateWithPassword(userName, userPwd);//认证
            if (flg) {
                System.out.println("认证成功！");
            }
        } catch (IOException e) {

            e.printStackTrace();

        }
        return flg;

    }

    /**
     * @param cmd 即将执行的命令
     * @return 命令执行完后返回的结果值
     * @author Ickes
     * <p>
     * 远程执行shll脚本或者命令
     * @since V0.1
     */

    public String execute(String cmd) {
        String result = "";
        try {
            if (login()) {
                Session session = conn.openSession();//打开一个会话
                session.execCommand(cmd);//执行命令
                result = processStdout(session.getStdout(), DEFAULT_CHART);
                //如果为得到标准输出为空，说明脚本执行出错了
                if (StrUtil.isBlank(result)) {
                    result = processStdout(session.getStderr(), DEFAULT_CHART);
                }
                conn.close();
                session.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;

    }

    /**
     * @param cmd 即将执行的命令
     * @return 命令执行成功后返回的结果值，如果命令执行失败，返回空字符串，不是null
     * @author Ickes
     * <p>
     * 远程执行shll脚本或者命令
     * @since V0.1
     */

    public String executeSuccess(String cmd) {
        String result = "";
        try {
            if (login()) {
                Session session = conn.openSession();//打开一个会话
                session.execCommand(cmd);//执行命令
                result = processStdout(session.getStdout(), DEFAULT_CHART);
                conn.close();
                session.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

}
