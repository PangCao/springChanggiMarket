package dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dto.Boardlist;
import dto.comment;
import dto.oneqna;

public class BoardDao {
	
	private JdbcTemplate jdbcTemplate;
	
	public BoardDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public String previouspage(String category, String id) {


		String sql = "";
		
		if (category.equals("게시판")) {
		sql = "select b_id from bulletin where b_id > ? order by b_id asc";
		}
		else if (category.equals("공지사항")) {
			sql = "select n_id from notice where n_id > ? order by n_id asc";
		}
		else if (category.equals("1:1 문의")) {
			sql = "select oq_id from one_qna where oq_id > ? order by oq_id asc";
		}
		else if (category.equals("나만의 레시피")) {
			sql = "select r_id from r_review where r_id > ? order by r_id asc";
		}
		
		List<String> results = jdbcTemplate.query(sql, new RowMapper<String>() {

			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String ans = rs.getString(1);
				return ans;
			}
		},id);
		String previouspage = results.isEmpty() ? "-1" : results.get(0);
		return previouspage;
	}
	
	public String nextpage(String category, String id) {
		
		String sql = "";
		
		if (category.equals("게시판")) {
			sql = "select b_id from bulletin where b_id < ? order by b_id desc";
		}
		else if (category.equals("공지사항")) {
			sql = "select n_id from notice where n_id < ? order by n_id desc";
		}
		else if (category.equals("1:1 문의")) {
			sql = "select oq_id from one_qna where oq_id < ? order by oq_id desc";
		}
		else if (category.equals("나만의 레시피")) {
			sql = "select r_id from r_review where r_id < ? order by r_id desc";
		}
		
		List<String> results = jdbcTemplate.query(sql, new RowMapper<String>() {

			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String ans = rs.getString(1);
				return ans;
			}
		}
		,id);
		String nextpage = results.isEmpty() ? "-1" : results.get(0);
		
		return nextpage;
	}
		
	public List<comment> commentsearch(String category, String id) {
		int nid = Integer.valueOf(id);
		if (category.equals("나만의 레시피")) {
			category = "r_review";
		}
		else if (category.equals("공지사항")) {
			category = "notice";
		}
		else if (category.equals("게시판")) {
			category = "bulletin";
		}
		String sql = "select * from b_comment where bc_name=? and bc_id=? order by bc_num desc";
		
		List<comment> results = jdbcTemplate.query(sql,new RowMapper<comment>() {

			@Override
			public dto.comment mapRow(ResultSet rs, int rowNum) throws SQLException {
				comment cm = new comment();
				cm.setBc_id(rs.getInt("bc_num"));
				cm.setBc_writer(rs.getString("bc_writer"));
				cm.setBc_date(rs.getString("bc_date"));
				cm.setBc_content(rs.getString("bc_content"));
				return cm;
			}
		}, category, nid);
		
		return results;
	}
	
	public void comment(HttpSession session, int id, String category, String content) {
		
		String writer = (String)session.getAttribute("userid");
		SimpleDateFormat formeter = new SimpleDateFormat("yyyy-MM-dd");
		String date = formeter.format(new Date().getTime());
		
		if (category.equals("나만의 레시피")) {
			category = "r_review";
		}
		else if (category.equals("공지사항")) {
			category = "notice";
		}
		else if (category.equals("게시판")) {
			category = "bulletin";
		}		

		String sql = "insert into b_comment(bc_name, bc_id, bc_writer, bc_content, bc_date) values ('"+category+"',?,?,?,?)";
		jdbcTemplate.update(sql,id, writer,content,date);
	}
	
	public void delnoview(String category, String id) {
		String sql = "";
		if (category.equals("나만의 레시피")) {
			sql = "delete from r_review where r_id=?";
		}
		else if (category.equals("공지사항")) {
			sql = "delete from notice where n_id=?";
		}
		else if (category.equals("게시판")) {
			sql = "delete from bulletin where b_id=?";
		}
		else if (category.equals("1:1 문의")) {
			sql = "delete from one_qna where oq_id=?";
		}
		jdbcTemplate.update(sql, id);
	}
	
	public void likeup(HttpSession session, int id) {
		String c_id = (String)session.getAttribute("userid");
		
		String sql = "select r_like from r_review where r_id=?";
		List<Integer> results = jdbcTemplate.query(sql, new RowMapper<Integer>(){

			@Override
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				Integer ans = rs.getInt("r_like");
				return ans;
			}},id);
		int like = results.get(0) + 1;
		
		sql = "update r_review set r_like=? where r_id=?";
		jdbcTemplate.update(sql, like, id);
		
		sql = "insert into likelist (c_id, r_id) values (?,?)";
		jdbcTemplate.update(sql, c_id, id);
	}
	
	
	public Integer likechk(HttpSession session, int r_id) {
		String c_id = (String)session.getAttribute("userid");
		String s_id = (String)session.getAttribute("seller");
		int likechk = -1;
		if (c_id != null) {
			Integer results = jdbcTemplate.queryForObject("select count(*) from likelist where c_id=? and r_id=?", Integer.class, c_id, r_id);
			likechk = results;
		}
		else if (s_id != null) {
			likechk = 2;
		}
		
		return likechk;
	}	
	
	public List<oneqna> one(String page) {
		int SearchPage = (Integer.valueOf(page) - 1) * 10;
		
		String sql = "select * from one_qna order by oq_id desc limit "+SearchPage+", 10";
		
		List<oneqna> results= jdbcTemplate.query(sql, new RowMapper<oneqna>() {

			@Override
			public oneqna mapRow(ResultSet rs, int rowNum) throws SQLException {
				oneqna oq = new oneqna();
				oq.setId(rs.getInt("oq_id"));
				oq.setTitle(rs.getString("oq_title"));
				oq.setWriter(rs.getString("oq_writer"));
				oq.setContent(rs.getString("oq_content"));
				oq.setCategory(rs.getString("oq_category"));
				oq.setDate(rs.getString("oq_date"));
				oq.setHit(rs.getInt("oq_hit"));
				oq.setStat(rs.getString("oq_stat"));
				return oq;
			}});
		return results;
	}
	
	
	public List<Boardlist> bulletin(String search_title, String page) {
		String sql = "";
		int SearchPage = (Integer.valueOf(page)-1)*10;
		if (search_title == null || search_title.equals("") || search_title.equals("null")) {
			sql = "select * from bulletin order by b_id desc limit "+SearchPage+", 10";
		}
		else {
			sql = "select * from bulletin where b_title like '%"+search_title+"%' order by b_id desc limit "+SearchPage+", 10";
		}
		List<Boardlist> results = jdbcTemplate.query(sql, new RowMapper<Boardlist>() {

			@Override
			public Boardlist mapRow(ResultSet rs, int rowNum) throws SQLException {
				Boardlist bl = new Boardlist();
				bl.setId(rs.getString("b_id"));
				bl.setTitle(rs.getString("b_title"));
				bl.setWriter(rs.getString("b_writer"));
				bl.setContent(rs.getString("b_content"));
				bl.setImg(rs.getString("b_img").split(","));
				bl.setDate(rs.getString("b_date"));
				bl.setHit(rs.getInt("b_hit"));
				return bl;
			}
		});
		return results;
	}
		
	public Boardlist recipe_view(String id) {
		
		String sql = "select * from r_review where r_id=?";
		List<Boardlist> results = jdbcTemplate.query(sql, new RowMapper<Boardlist>() {

			@Override
			public Boardlist mapRow(ResultSet rs, int rowNum) throws SQLException {
				Boardlist bl = new Boardlist();
				bl.setId(id);
				bl.setTitle(rs.getString("r_title"));
				bl.setWriter(rs.getString("r_writer"));
				bl.setContent(rs.getString("r_content"));
				bl.setImg(rs.getString("r_img").split(","));
				bl.setDate(rs.getString("r_date"));
				bl.setHit(rs.getInt("r_hit")+1);
				return bl;
			}},id);
		sql = "update r_review set r_hit=? where r_id=?";
		jdbcTemplate.update(sql, results.get(0).getHit(), id);
		
		return results.get(0);
	}
	
	
	public Boardlist oneview(String id) {
			
		String sql = "select * from one_qna where oq_id=?";
		List<Boardlist> results = jdbcTemplate.query(sql, new RowMapper<Boardlist>() {

			@Override
			public Boardlist mapRow(ResultSet rs, int rowNum) throws SQLException {
				Boardlist bl = new Boardlist();
				bl.setId(id);
				bl.setTitle(rs.getString("oq_title"));
				bl.setWriter(rs.getString("oq_writer"));
				bl.setContent(rs.getString("oq_content"));
				bl.setDate(rs.getString("oq_date"));
				bl.setHit(rs.getInt("oq_hit")+1);
				return bl;
			}},id);
		sql = "update one_qna set oq_hit=? where oq_id=?";
		jdbcTemplate.update(sql, results.get(0).getHit(), id);
		
		return results.get(0);
	}
	
	public Boardlist bulletinview(String id) {	
			
		String sql = "select * from bulletin where b_id=?";
		List<Boardlist> results = jdbcTemplate.query(sql, new RowMapper<Boardlist>() {

			@Override
			public Boardlist mapRow(ResultSet rs, int rowNum) throws SQLException {
				Boardlist bl = new Boardlist();
				bl.setId(id);
				bl.setTitle(rs.getString("b_title"));
				bl.setWriter(rs.getString("b_writer"));
				bl.setContent(rs.getString("b_content"));
				bl.setImg(rs.getString("b_img").split(","));
				bl.setDate(rs.getString("b_date"));
				bl.setHit(rs.getInt("b_hit")+1);
				return bl;
			}},id);
		sql = "update bulletin set b_hit=? where b_id=?";
		jdbcTemplate.update(sql, results.get(0).getHit(), id);
		
		return results.get(0);
	}
	
	public Boardlist noticeview(String id) {
		String sql = "select * from notice where n_id=?";
		List<Boardlist> results = jdbcTemplate.query(sql, new RowMapper<Boardlist>() {

			@Override
			public Boardlist mapRow(ResultSet rs, int rowNum) throws SQLException {
				Boardlist bl = new Boardlist();
				bl.setId(id);
				bl.setTitle(rs.getString("n_title"));
				bl.setWriter(rs.getString("n_writer"));
				bl.setContent(rs.getString("n_content"));
				bl.setImg(rs.getString("n_img").split(","));
				bl.setDate(rs.getString("n_date"));
				bl.setHit(rs.getInt("n_hit")+1);
				return bl;
			}},id);
		sql = "update notice set n_hit=? where n_id=?";
		jdbcTemplate.update(sql, results.get(0).getHit(), id);
		
		return results.get(0);
	}
	
	
	public Integer faq_bopage(String search_title) {
		String sql = "";
		if ( search_title == null ||search_title.equals("") || search_title.equals("null")) {
			sql = "select count(*) from faq";
		}
		else {
			sql ="select count(*) from faq where f_title like '%"+search_title+"%'";
		}
		Integer results = jdbcTemplate.queryForObject(sql,Integer.class);
		
		return results;
		
	}
	
	
	public Integer review_bopage(String search_title) {
		String sql = "";
		if (search_title == null || search_title.equals("") || search_title.equals("null")) {
			sql = "select count(*) from r_review";
		}
		else {
			sql = "select count(*) from r_review where r_title like '%"+search_title+"%'";
		}
		Integer results = jdbcTemplate.queryForObject(sql, Integer.class);
		
		return results;
	}
	
	
	public Integer onebopage() {
		String sql = "select count(*) from one_qna";
		Integer results = jdbcTemplate.queryForObject(sql, Integer.class);
		return results;
	}
	
	public Integer bulletinbopage(String search_title) {
		String sql = "";
		if (search_title == null || search_title.equals("") || search_title.equals("null")) {
			sql = "select count(*) from bulletin";
		}
		else {
			sql = "select count(*) from bulletin where b_title like '%"+search_title+"%'";
		}
		Integer results = jdbcTemplate.queryForObject(sql, Integer.class);
		return results;
	}
	
	// 게시판 총 페이지 수 구하는 메서드
	public Integer bopage(String search_title) {
		String sql = "";
		if (search_title == null || search_title.equals("") || search_title.equals("null")) {
			sql = "select count(*) from notice";
		}
		else {
			sql = "select count(*) from notice where n_title like '%"+search_title+"%'";
		}
		Integer results = jdbcTemplate.queryForObject(sql, Integer.class);
		return results;
	}
	
	public List<Boardlist> faq(String search_title, String page) {
		String sql = "";
		
		int SearchPage = (Integer.valueOf(page) - 1) * 10;
		if (search_title == null || search_title.equals("") || search_title.equals("null")) {
			sql = "select * from faq order by f_id desc limit "+SearchPage+", 10";
		}
		else {
			sql = "select * from faq where f_title like '%"+search_title+"%' order by f_id desc limit "+SearchPage+", 10";
		}
		List<Boardlist> results = jdbcTemplate.query(sql, new RowMapper<Boardlist>() {

			@Override
			public Boardlist mapRow(ResultSet rs, int rowNum) throws SQLException {
				Boardlist bl = new Boardlist();
				bl.setId(rs.getString("f_id"));
				bl.setTitle(rs.getString("f_title"));
				bl.setContent(rs.getString("f_content"));
				return bl;
			}});

		return results;
	}
	
	public List<Boardlist> review(String search_title, String page) {
		String sql = "";
		
		int SearchPage = (Integer.valueOf(page) - 1) * 8;
		
		if (search_title == null || search_title.equals("") || search_title.equals("null")) {
			sql = "select * from r_review order by r_id desc limit "+SearchPage+", 8";
		}
		else {
			sql = "select * from r_review where r_title like '%"+search_title+"%' order by r_id desc limit "+SearchPage+", 8";
		}
		List<Boardlist> results = jdbcTemplate.query(sql, new RowMapper<Boardlist>() {

			@Override
			public Boardlist mapRow(ResultSet rs, int rowNum) throws SQLException {
				Boardlist bl = new Boardlist();
				bl.setId(rs.getString("r_id"));
				bl.setTitle(rs.getString("r_title"));
				bl.setWriter(rs.getString("r_writer"));
				bl.setContent(rs.getString("r_content"));
				bl.setImg(rs.getString("r_img").split(","));
				bl.setDate(rs.getString("r_date"));
				bl.setHit(rs.getInt("r_hit"));
				bl.setLike(rs.getInt("r_like"));
				return bl;
			}});
		
		return results;
	}
	
	public List<Boardlist> notice(String search_title, String page) {
		String sql = "";
		int SearchPage = (Integer.valueOf(page)-1) * 10;
		if (search_title == null || search_title.equals("") || search_title.equals("null")) {
			sql = "select * from notice order by n_id desc limit "+SearchPage+", 10";
		}
		else {
			sql = "select * from notice where n_title like '%"+search_title+"%' order by n_id desc limit "+SearchPage+", 10";
		}
		List<Boardlist> results = jdbcTemplate.query(sql, new RowMapper<Boardlist>() {

			@Override
			public Boardlist mapRow(ResultSet rs, int rowNum) throws SQLException {
				Boardlist bl = new Boardlist();
				bl.setId(rs.getString("n_id"));
				bl.setTitle(rs.getString("n_title"));
				bl.setWriter(rs.getString("n_writer"));
				bl.setContent(rs.getString("n_content"));
				bl.setImg(rs.getString("n_img").split(","));
				bl.setDate(rs.getString("n_date"));
				bl.setHit(rs.getInt("n_hit"));
				return bl;
			}});
		return results;
		
	}
	
	public void reviewwriter(HttpServletRequest request) {
		
		String realFolder = request.getSession().getServletContext().getRealPath("resources/images");
		MultipartRequest multi = null;
		try {
			multi = new MultipartRequest(request, realFolder, 10 * 1024 * 1024, "utf-8", new DefaultFileRenamePolicy());
		} catch (IOException e) {
			e.printStackTrace();
		} 
		String title = multi.getParameter("title");
		String id = multi.getParameter("id");
		String contents = multi.getParameter("contents");
		String filenames = "";
		Enumeration files = multi.getFileNames();
		int cnt = 0;
		while(files.hasMoreElements()) {
			String fname = (String)files.nextElement();
			if (multi.getFilesystemName(fname) != null) {
				if (cnt == 0) {
					filenames += multi.getFilesystemName(fname);
					cnt++;
				}
				else {
					filenames += ","+multi.getFilesystemName(fname);
				}
			}
		}	
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String date = format.format(Calendar.getInstance().getTime());
		String sql = "insert into r_review (r_title, r_writer, r_content, r_img, r_date) values (?,?,?,?,?)"; 

		jdbcTemplate.update(sql, title, id, contents, filenames, date);
	}
	
	public void bulletinwriter(HttpServletRequest request) {
		String realFolder = request.getSession().getServletContext().getRealPath("resources/images");
		MultipartRequest multi = null;
		try {
			multi = new MultipartRequest(request, realFolder, 10 * 1024 * 1024, "utf-8", new DefaultFileRenamePolicy());
		} catch (IOException e) {
			e.printStackTrace();
		} 
		String title = multi.getParameter("title");
		String id = multi.getParameter("id");
		String contents = multi.getParameter("contents");
		String filenames = "";
		Enumeration files = multi.getFileNames();
		int cnt = 0;
		while(files.hasMoreElements()) {
			String fname = (String)files.nextElement();
			if (multi.getFilesystemName(fname) != null) {
				if (cnt == 0) {
					filenames += multi.getFilesystemName(fname);
					cnt++;
				}
				else {
					filenames += ","+multi.getFilesystemName(fname);
				}
			}
		}	
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String date = format.format(Calendar.getInstance().getTime());
		String sql = "insert into bulletin (b_title, b_writer, b_content, b_img, b_date) values (?,?,?,?,?)"; 

		jdbcTemplate.update(sql, title, id, contents, filenames, date);
	}
	
	public void onewriter(String title, String id, String contents, String qnasel, String category) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String date = format.format(Calendar.getInstance().getTime());
		String sql = "insert into one_qna (oq_title, oq_writer, oq_content, oq_category, oq_date) values (?,?,?,?,?)"; 

		jdbcTemplate.update(sql, title, id, contents, category, date);
	}
	
	public void noticewriter(HttpServletRequest request) {
		String realFolder = request.getSession().getServletContext().getRealPath("resources/images");
		MultipartRequest multi = null;
		try {
			multi = new MultipartRequest(request, realFolder, 10 * 1024 * 1024, "utf-8", new DefaultFileRenamePolicy());
		} catch (IOException e) {
			e.printStackTrace();
		} 
		String title = multi.getParameter("title");
		String id = multi.getParameter("id");
		String contents = multi.getParameter("contents");
		String filenames = "";
		Enumeration files = multi.getFileNames();
		int cnt = 0;
		while(files.hasMoreElements()) {
			String fname = (String)files.nextElement();
			if (multi.getFilesystemName(fname) != null) {
				if (cnt == 0) {
					filenames += multi.getFilesystemName(fname);
					cnt++;
				}
				else {
					filenames += ","+multi.getFilesystemName(fname);
				}
			}
		}	
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String date = format.format(Calendar.getInstance().getTime());
		String sql = "insert into notice (n_title, n_writer, n_content, n_img, n_date) values (?,?,?,?,?)"; 

		jdbcTemplate.update(sql, title, id, contents, filenames, date);
	}	
}
