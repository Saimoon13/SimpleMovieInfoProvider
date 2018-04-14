package edu.java.connect;

import edu.java.domain.Member;
import edu.java.domain.Rate;
import edu.java.view.Main;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


public interface ProjectDAO {

	int insert(Member m) throws SQLException;
	List<Rate> select(Main window) throws SQLException;
	int insertRate(Rate r) throws SQLException;
	int selectByEmail(Member m) throws SQLException;
	Map<String, String> selectMovieByTitle(String title) throws SQLException;
	Map<String, String> selectMemberByEmail(String email) throws  SQLException;
}
