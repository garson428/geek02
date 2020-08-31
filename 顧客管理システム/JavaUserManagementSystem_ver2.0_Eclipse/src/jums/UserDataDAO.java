

package jums;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import base.DBManager;

/**
 * ユーザー情報を格納するテーブルに対しての操作処理を包括する
 * DB接続系はDBManagerクラスに一任
 * 基本的にはやりたい1種類の動作に対して1メソッド
 * @author hayashi-s
 */
public class UserDataDAO {

    //インスタンスオブジェクトを返却させてコードの簡略化
    public static UserDataDAO getInstance(){
        return new UserDataDAO();
    }

    /**
     * データの挿入処理を行う。現在時刻は挿入直前に生成
     * @param ud 対応したデータを保持しているJavaBeans
     * @throws SQLException 呼び出し元にcatchさせるためにスロー
     */
    public void insert(UserDataDTO ud) throws SQLException{
        Connection con = null;
        PreparedStatement st = null;
        try{
            con = DBManager.getConnection();
            st =  con.prepareStatement("INSERT INTO user_t(name,birthday,tel,type,comment,newDate) VALUES(?,?,?,?,?,?)");
            st.setString(1, ud.getName());
            st.setDate(2, new java.sql.Date(ud.getBirthday().getTime()));//指定のタイムスタンプ値からSQL格納用のDATE型に変更
            st.setString(3, ud.getTel());
            st.setInt(4, ud.getType());
            st.setString(5, ud.getComment());
            st.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            st.executeUpdate();
            System.out.println("insert completed");
        }catch(SQLException e){
            System.out.println(e.getMessage());
            throw new SQLException(e);
        }finally{
            if(con != null){
                con.close();
            }
            if(st != null) {
                st.close();
            }
        }

    }

    /**
     * データの検索処理を行う。
     * @param ud 対応したデータを保持しているJavaBeans
     * @throws SQLException 呼び出し元にcatchさせるためにスロー
     * @return 検索結果
     */
    public List<UserDataDTO> search(UserDataDTO ud) throws SQLException{
    	System.out.println("search completed");
        Connection con = null;
        PreparedStatement st = null;
        List<UserDataDTO> dto = new ArrayList<UserDataDTO>();
        try{
            con = DBManager.getConnection();

            //
            String sql = "SELECT * FROM user_t";
            boolean flag = false;
            if (!ud.getName().equals("")) {
                sql += " WHERE name like ?";
                flag = true;
            }
            if (ud.getBirthday()!=null) {
                if(!flag){
                    sql += " WHERE birthday like ?";
                    flag = true;
                }else{
                    sql += " AND birthday like ?";
                }
            }
            if (ud.getType()!=0) {
                if(!flag){
                    sql += " WHERE type like ?";
                }else{
                    sql += " AND type like ?";
                }
            }


            if(ud.getName().equals("")&&ud.getBirthday()==null&&ud.getType()==0) {
            	st = con.prepareStatement("select * from user_t");

            ResultSet rs = st.executeQuery();

            while(rs.next()) {
            UserDataDTO resultUd = new UserDataDTO();
            resultUd.setUserID(rs.getInt(1));
            resultUd.setName(rs.getString(2));
            resultUd.setBirthday(rs.getDate(3));
            resultUd.setTel(rs.getString(4));
            resultUd.setType(rs.getInt(5));
            resultUd.setComment(rs.getString(6));
            resultUd.setNewDate(rs.getTimestamp(7));
            //list型dtoにresultUdを格納
            dto.add(resultUd);
            }
            return dto;

            }else {
            st = con.prepareStatement(sql);
            //SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            //java.util.Date dateStr = ud.getBirthday();
            //String strBirthday = sdf.format(dateStr);
            //sdf.format(new Date(dateStr.getTime()));


            if(!ud.getName().equals("") && ud.getBirthday()!=null && ud.getType()!=0) {
            	st.setString(1, "%"+ud.getName()+"%");
            	st.setString(2, "%"+ new SimpleDateFormat("yyyy").format(ud.getBirthday())+"%");
            	st.setInt(3, ud.getType());            ////全部

            }else if(ud.getName().equals("") && ud.getBirthday() == null && ud.getType() != 0) {
            	st.setInt(1, ud.getType());            ////種別

            }else if(ud.getName().equals("") && ud.getBirthday() != null && ud.getType() == 0) {
            	st.setString(1, "%"+ new SimpleDateFormat("yyyy").format(ud.getBirthday())+"%");
            	////生年

            }else if(!ud.getName().equals("") && ud.getBirthday() == null && ud.getType() == 0) {
            	st.setString(1, "%"+ud.getName()+"%"); ////名前

            }else if(ud.getName().equals("") && ud.getBirthday() != null && ud.getType() != 0) {
            	st.setString(1, "%"+ new SimpleDateFormat("yyyy").format(ud.getBirthday())+"%");
            	st.setInt(2, ud.getType());  ////生年＆種別

            }else if(!ud.getName().equals("") && ud.getBirthday() != null && ud.getType() == 0) {
            	st.setString(1, "%"+ud.getName()+"%"); ////名前＆生年
            	st.setString(2, "%"+ new SimpleDateFormat("yyyy").format(ud.getBirthday())+"%");

            }else if(!ud.getName().equals("") && ud.getBirthday() == null && ud.getType() != 0) {
            	st.setString(1, "%"+ud.getName()+"%"); ////名前＆種別
            	st.setInt(2, ud.getType());
            }

            ResultSet rs = st.executeQuery();
            while(rs.next()) {
            	UserDataDTO resultUd = new UserDataDTO();

            	resultUd.setUserID(rs.getInt(1));
                resultUd.setName(rs.getString(2));
                resultUd.setBirthday(rs.getDate(3));
                resultUd.setTel(rs.getString(4));
                resultUd.setType(rs.getInt(5));
                resultUd.setComment(rs.getString(6));
                resultUd.setNewDate(rs.getTimestamp(7));
                dto.add(resultUd);
            }
            return dto;

            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
            throw new SQLException(e);
        }finally{
            if(con != null){
                con.close();
            }
            if(st != null) {
                st.close();
            }
        }

    }

    /**
     * ユーザーIDによる1件のデータの検索処理を行う。
     * @param ud 対応したデータを保持しているJavaBeans
     * @throws SQLException 呼び出し元にcatchさせるためにスロー
     * @return 検索結果
     */
    public UserDataDTO searchByID(UserDataDTO ud) throws SQLException{

        Connection con = null;
        PreparedStatement st = null;
        try{
            con = DBManager.getConnection();

            String sql = "SELECT * FROM user_t WHERE userID = ?";

            st =  con.prepareStatement(sql);
            st.setInt(1, ud.getUserID());

            ResultSet rs = st.executeQuery();
            rs.next();
            UserDataDTO resultUd = new UserDataDTO();
            resultUd.setUserID(rs.getInt(1));
            resultUd.setName(rs.getString(2));
            resultUd.setBirthday(rs.getDate(3));
            resultUd.setTel(rs.getString(4));
            resultUd.setType(rs.getInt(5));
            resultUd.setComment(rs.getString(6));
            resultUd.setNewDate(rs.getTimestamp(7));

            System.out.println("searchByID completed");

            return resultUd;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            throw new SQLException(e);
        }finally{
            if(con != null){
                con.close();
            }
            if(st != null) {
                st.close();
            }
        }
    }

    public static List<UserDataDTO> update(UserDataDTO ud) throws SQLException{
        List<UserDataDTO> dto = new ArrayList<UserDataDTO>();
        Connection con = null;
        PreparedStatement st = null;
        try{
            con = DBManager.getConnection();

            st =  con.prepareStatement("UPDATE user_t SET name = ?,birthday = ?,tel = ?, type = ?, comment = ?, newDate = ? WHERE userID = ?");
            st.setString(1, ud.getName());
            st.setDate(2, new java.sql.Date(ud.getBirthday().getTime()));//指定のタイムスタンプ値からSQL格納用のDATE型に変更
            st.setString(3, ud.getTel());
            st.setInt(4, ud.getType());
            st.setString(5, ud.getComment());
            st.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            st.setInt(7, ud.getUserID());
            st.executeUpdate();

            st.close();

            UserDataDTO updateUd = new UserDataDTO();
            st = con.prepareStatement("SELECT * FROM user_t");

            ResultSet rs = st.executeQuery();
            while(rs.next()) {
            updateUd.setUserID(rs.getInt(1));
            updateUd.setName(rs.getString(2));
            updateUd.setBirthday(rs.getDate(3));
            updateUd.setTel(rs.getString(4));
            updateUd.setType(rs.getInt(5));
            updateUd.setComment(rs.getString(6));
            updateUd.setNewDate(rs.getTimestamp(7));

            dto.add(updateUd);

            }
            System.out.println("update completed");

            return dto;

        }catch(SQLException e){
            System.out.println(e.getMessage());
            throw new SQLException(e);
        }finally{
            if(con != null){
                con.close();
            }
            if(st != null) {
                st.close();
            }
        }

    }

    public static void delete(UserDataDTO ud) throws SQLException{
        Connection con = null;
        PreparedStatement st = null;
        try{
            con = DBManager.getConnection();

            st =  con.prepareStatement("DELETE FROM user_t WHERE userID = ?");
            st.setInt(1, ud.getUserID());
            st.executeUpdate();

            System.out.println("delete completed");

        }catch(SQLException e){
            System.out.println(e.getMessage());
            throw new SQLException(e);
        }finally{
            if(con != null){
                con.close();
            }
            if(st != null) {
                st.close();
            }
        }

    }

}
