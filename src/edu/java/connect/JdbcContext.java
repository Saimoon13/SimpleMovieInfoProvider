package edu.java.connect;

import com.sun.rowset.CachedRowSetImpl;
import oracle.jdbc.OracleDriver;

import java.sql.*;

public class JdbcContext {

	public JdbcContext() {
		try {
			DriverManager.registerDriver(new OracleDriver());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int workWithStatementStrategyUpdate(StatementStrategy stmt) throws SQLException {
		int result =0;
		Connection c = null;
		PreparedStatement ps = null;
		try {
			c = DriverManager.getConnection(Queries.getQuery("URL"),
											Queries.getQuery("USER"),
											Queries.getQuery("PASSWORD"));
			ps = stmt.makePreparedStatement(c);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			if (ps != null) { try { ps.close(); } catch (SQLException e) {} }
			if (c != null) { try {c.close(); } catch (SQLException e) {} }
		}
		return result;
	}

	public CachedRowSetImpl workWithStatementStrategyQuery(StatementStrategy stmt) throws SQLException {

		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		CachedRowSetImpl crs = new CachedRowSetImpl();

		try {
			c = DriverManager.getConnection(Queries.getQuery("URL"),
					Queries.getQuery("USER"),
					Queries.getQuery("PASSWORD"));
			ps = stmt.makePreparedStatement(c);
			rs = ps.executeQuery();
			crs.populate(rs);
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null) { try { rs.close(); } catch (SQLException e) {} }
			if (ps != null) { try { ps.close(); } catch (SQLException e) {} }
			if (c != null) { try {c.close(); } catch (SQLException e) {} }
		}
		return crs;
	}
}

