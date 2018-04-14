package edu.java.connect;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.sun.rowset.CachedRowSetImpl;
import edu.java.domain.Member;
import edu.java.domain.Rate;
import edu.java.view.Main;

public class ProjectDAOImple implements ProjectDAO {

    private JdbcContext jdbcContext;

    private static ProjectDAOImple instance = null;
    public static ProjectDAOImple getInstance() {
        if (instance == null) {
            instance = new ProjectDAOImple();
        }
        return instance;
    }

    private ProjectDAOImple() {
        jdbcContext = new JdbcContext();
    }

    @Override
    public int insert(Member m) throws SQLException { // conn, pstmt
        int result = 0;
        result = this.jdbcContext.workWithStatementStrategyUpdate(
                (Connection c) -> {
                    System.out.println(Queries.getQuery("SQL_INSERT_MEMBER"));
                    PreparedStatement ps =
                            c.prepareStatement(Queries.getQuery("SQL_INSERT_MEMBER"));
                    ps.setString(1, m.getMname());
                    ps.setString(2, m.getMemail());

                    StringBuffer sb = new StringBuffer();
                    for (char g : m.getMpw()) {
                        sb.append(g);
                    }
                    String temp = sb.toString();
                    ps.setString(3, temp);

                    return ps;
                }
        );
        return result;
    } // conn, pstmt

    @Override
    public int insertRate(Rate r) throws SQLException {
        int result = 0;
        result = this.jdbcContext.workWithStatementStrategyUpdate(
                (Connection c) -> {
                    PreparedStatement ps =
                            c.prepareStatement(Queries.getQuery("SQL_INSERT_REVIEW"));
                    ps.setString(1, r.getCrn());
                    ps.setString(2, r.getCrc());
                    ps.setString(3, r.getTitle());
                    return ps;
                }
        );
        return result;
    }

    @Override
    public List<Rate> select(Main window) throws SQLException {

        CachedRowSetImpl crs;
        List<Rate> list = new ArrayList<>();

        crs = this.jdbcContext.workWithStatementStrategyQuery(
                (Connection c) -> {
                    PreparedStatement ps =
                            c.prepareStatement(Queries.getQuery("SQL_SELECT_ALL"));
                    ps.setString(1, window.getDbtitleMain());
                    return ps;
                }
        );

        while (crs.next()) {
            String crn = crs.getString("crn");
            String crc = crs.getString("crc");
            Rate r = new Rate(crn, crc);
            list.add(r);
        } // end while()

        return list;
    } // end select

    @Override
    public int selectByEmail(Member m) throws SQLException {

        CachedRowSetImpl crs;
        String id = m.getMemail(), dbid = null;
        int temp = 0;

        crs = this.jdbcContext.workWithStatementStrategyQuery(
                (Connection c) -> {
                    PreparedStatement ps =
                            c.prepareStatement(Queries.getQuery("SQL_SELECT_BY_EMAIL"));
                    ps.setString(1, id);
                    return ps;
                }
        );

        if (crs.next()) { dbid = crs.getString("memail"); }

        if (dbid != null) {
            JOptionPane.showMessageDialog(new JFrame(), "The email you ask for is already registered");
            temp = 1;
        }

        return temp;
    }

    @Override
    public Map<String, String> selectMovieByTitle(String title) throws SQLException {

        Map<String, String> hashMap = new HashMap<>();
        CachedRowSetImpl crs;

        crs = this.jdbcContext.workWithStatementStrategyQuery(
                (Connection c) -> {
                    PreparedStatement ps =
                            c.prepareStatement(Queries.getQuery("SQL_SELECTED_BY_TITLE"));
                    ps.setString(1, title.toUpperCase());
                    return ps;
                }
        );

        if(crs.next()){
            hashMap.put("dbtitle", crs.getString(1));
            hashMap.put("moveinfo", crs.getString(2));
            hashMap.put("tomato", crs.getString(3));
            hashMap.put("poster", crs.getString(4));
        }

        return hashMap;
    }

    @Override
    public Map<String, String> selectMemberByEmail(String email) throws SQLException {

        Map<String, String> hashMap = new HashMap<>();
        CachedRowSetImpl crs;

        crs = this.jdbcContext.workWithStatementStrategyQuery(
                (Connection c) -> {
                    PreparedStatement ps =
                            c.prepareStatement(Queries.getQuery("SQL_SELECED_BY_ID"));
                    ps.setString(1, email);
                    return ps;
                }
        );

        if(crs.next()){
            hashMap.put("mname", crs.getString(1));
            hashMap.put("memail", crs.getString(2));
            hashMap.put("mpw", crs.getString(3));
        }

        return hashMap;
    }


}
