package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.ui.Model;

import dto.BarcodesearchDto;
import dto.CustomerDto;
import dto.FoodmanageDto;
import dto.OrderlistDto;
import dto.SellerDto;

public class LoginDao {
	
	private JdbcTemplate jt;
	
	public LoginDao(DataSource dataSource) {
		this.jt = new JdbcTemplate(dataSource);
	}
	
	public void modifood(HttpServletRequest request, int num) {
		int price = Integer.valueOf(request.getParameter("foodprice"+num));
		int unit = Integer.valueOf(request.getParameter("foodunit"+num));
		int id = Integer.valueOf(request.getParameter("fooddel"+num));
		
		String sql = "update foodlist set f_price=?, f_unit=? where f_id=?";
		jt.update(sql, price, unit, id);	
	}
	
	public void unsign(HttpSession session) {
		String id1 = (String)session.getAttribute("userid");
		String id2 = (String)session.getAttribute("seller");
		String id = "";
		String user = "";
		String sql = "";
		
		if (id1 != null) {
			id = id1;
			user = "customer";
		}
		else {
			id = id2;
			user = "seller";
		}
		if (user.equals("customer")) {
			sql = "delete from customer where c_id=?";
		}
		else {
			sql = "delete from seller where s_id=?";
		}
			
		jt.update(sql, id);
		
		session.invalidate();
	}

	public void pwchange(String id, String user, String password) {
		String sql = "";
		
		if (user.equals("customer")) {
			sql = "update customer set c_password=? where c_id=?";
		}
		else {
			sql = "update seller set s_password=? where s_id=?";
		}
		
		jt.update(sql, password, id);	
	}

	public int idsearch(Map<String,Object> requestValues, Model model) {
		int ans = 0;
		ArrayList<String> ids = new ArrayList<String>();
		String phone = requestValues.get("phone1") + "-" + requestValues.get("phone2") + "-" + requestValues.get("phone3");
		String sql = "";
		String sql2 = "";
		
		if (phone.length() < 12) {
			phone = "";
		}
		if (phone.equals("")) {
			sql = "select c_id from customer where c_mail=? ";
			List<String> result = jt.query(sql, new RowMapper<String>() {

				@Override
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("c_id");
				}}, requestValues.get("mail"));
			if (!result.isEmpty()) {
				ans = 1;
				ids = (ArrayList<String>) result;
			}
			
			sql2 = "select s_id from seller where s_mail=? ";
			List<String> result2 = jt.query(sql2, new RowMapper<String>() {

				@Override
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("s_id");
				}}, requestValues.get("mail"));
			if (!result2.isEmpty()) {
				ans = 1;
				ids = (ArrayList<String>) result2;
			}
		}
		else {
			sql = "select c_id from customer where c_phone=? ";
			List<String> result = jt.query(sql, new RowMapper<String>() {

				@Override
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("c_id");
				}}, phone);
			
			if (!result.isEmpty()) {
				ans = 1;
				ids = (ArrayList<String>) result;
			}
		
			sql2 = "select s_id from seller where s_phone=? ";
			List<String> result2 = jt.query(sql2, new RowMapper<String>() {

				@Override
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("s_id");
				}}, phone);
			if (!result2.isEmpty()) {
				ans = 1;
				ids = (ArrayList<String>) result2;
			}
		}
		
		 model.addAttribute("ans", ids);

		return ans;
	}

	public int pwsearch(Map<String, Object> requestValues, Model model) {
		int ans = 0;
		String user = "";
		String sql = "";
		String sql2 = "";
		String phone = requestValues.get("phone1") + "-" + requestValues.get("phone2") + "-" + requestValues.get("phone3");
		
		if (phone.length() < 12) {
			phone = "";
		}
		if (phone.equals("")) {
			sql = "select count(*) from customer where c_mail=? and c_id=?";
			Integer result = jt.queryForObject(sql, Integer.class, requestValues.get("mail"), requestValues.get("id"));
			
			if (result == 1) {
				ans = 1;
				user = "customer";
			}
			
			sql2 = "select count(*) from seller where s_mail=? and s_id=?";
			Integer result2 = jt.queryForObject(sql2, Integer.class, requestValues.get("mail"), requestValues.get("id"));
			
			if (result2 == 1) {
				ans = 1;
				user = "seller";
			}			 
		}
		else {
			sql = "select count(*) from customer where c_phone=? and c_id=?";
			Integer result = jt.queryForObject(sql, Integer.class , phone, requestValues.get("id"));
			
			if (result == 1) {
				ans = 1;
				user = "customer";
			}
			
			sql2 = "select count(*) from seller where s_phone=? and s_id=?";
			Integer result2 = jt.queryForObject(sql2, Integer.class, phone, requestValues.get("id"));
			
			if (result2 == 1) {
				ans = 1;
				user = "seller";
			}
		}
		
		model.addAttribute("id", requestValues.get("id"));
		model.addAttribute("user", user);
		
		return ans;
	}

	public Integer totalpage(HttpSession session) {
		String s_id = (String)session.getAttribute("seller");
		String sql = "select count(*) from cusorder where o_s_id=?";
		Integer result = jt.queryForObject(sql, Integer.class, s_id);
		
		return result;
	}

	public void ordersub(String[] orderchk, String[] orderid) {
		String sql = "update cusorder set o_chk=1 where o_num = ?";
		
		for(int i = 0; i < orderchk.length; i++) {
			if (orderchk[i].equals("on")) {
				jt.update(sql, orderid[i]);
			}
		}
	}

	public List<OrderlistDto> orderlist(HttpSession session , String order, String page) {
		String sql = "";
		String s_id = (String)session.getAttribute("seller");
		int SearchPage = (Integer.valueOf(page) - 1) * 10;  
		
		if (order.equals("waiting")) {
			sql = "select * from cusorder where o_s_id=? order by o_chk asc limit "+SearchPage+", 10";
		}
		else if (order.equals("complete")) {
			sql = "select * from cusorder where o_s_id=? order by o_chk desc limit "+SearchPage+", 10";
		}
		
		List<OrderlistDto> orderlist = jt.query(sql, new RowMapper<OrderlistDto>() {

			@Override
			public dto.OrderlistDto mapRow(ResultSet rs, int rowNum) throws SQLException {
				OrderlistDto ol = new OrderlistDto();
				ol.setId(rs.getInt("o_num"));
				ol.setDate(rs.getString("o_date"));
				ol.setF_singname(rs.getString("o_f_singname"));
				ol.setF_singunit(rs.getString("o_f_singunit"));
				ol.setAddr(rs.getString("o_addr"));
				ol.setChk(rs.getInt("o_chk"));
				return ol;
			}}, s_id);
		
		return orderlist;
	}

	public void modi(HttpSession session, CustomerDto cus, Map<String, Object> reuquestValues) {

		String id = cus.getId();
		String name = cus.getName();
		String phone = cus.getPhone();
		String email = cus.getMail();
		String addr = "("+reuquestValues.get("addr1")+")"+reuquestValues.get("addr2")+" "+reuquestValues.get("addr3");
		String gender = cus.getGender();
		String sql = "";
		
		CustomerDto cu = (CustomerDto)session.getAttribute("user");
		cu.setName(name);
		
		if (reuquestValues.get("pwchk").equals("") && reuquestValues.get("addr1").equals("() ")) {
			sql = "update customer set c_name=?, c_phone=?, c_gender=? ,c_mail=? where c_id=?";
			jt.update(sql, name, phone, gender, email, id);
			cu.setName(name);
			cu.setPhone(phone);
		}
		else if (reuquestValues.get("pwchk").equals("") && !reuquestValues.get("addr1").equals("() ")) {
			sql = "update customer set c_name=?, c_phone=?, c_gender=?, c_addr=? ,c_mail=? where c_id=?";
			jt.update(sql, name, phone, gender, addr, email, id);
			cu.setAddr(addr);
			cu.setName(name);
			cu.setPhone(phone);
		}
		else if (!reuquestValues.get("pwchk").equals("") && reuquestValues.get("addr1").equals("() ")) {
			sql = "update customer set c_name=?, c_phone=?, c_gender=?, c_password=? ,c_mail=? where c_id=?";
			jt.update(sql, name, phone, gender, reuquestValues.get("pwchk"), email, id);
			cu.setName(name);
			cu.setPhone(phone);
		}
		else {
			sql = "update customer set c_name=?, c_phone=?, c_gender=?, c_addr=?, c_password=? ,c_mail=? where c_id=?";
			jt.update(sql, name, phone, gender, addr, reuquestValues.get("pwchk"), email, id);
			cu.setAddr(addr);
			cu.setName(name);
			cu.setPhone(phone);
		}
	}

	public void selmodi(SellerDto sel, Map<String, Object> requestValues) {

		String id = sel.getS_id();
		String com_name = sel.getS_com_name();
		String owner_name = sel.getS_owner_name();
		String phone = sel.getS_phone();
		String email = sel.getS_mail();
		String addr = "("+requestValues.get("addr1")+")"+requestValues.get("addr2")+" "+requestValues.get("addr3");
		
		String sql = "";
		
		if (requestValues.get("pwchk").equals("") && addr.equals("() ")) {
			sql = "update seller set s_com_name=?, s_owner_name=?, s_mail=?, s_phone=? where s_id=?";
			jt.update(sql, com_name, owner_name, email, phone, id);
		}
		else if (requestValues.get("pwchk").equals("") && !addr.equals("() ")) {
			sql = "update seller set s_com_name=?, s_owner_name=?, s_mail=?, s_phone=?, s_addr=? where s_id=?";
			jt.update(sql, com_name, owner_name, email, phone, addr, id);
		}
		else if (!requestValues.get("pwchk").equals("") && addr.equals("() ")) {
			sql = "update seller set s_com_name=?, s_owner_name=?, s_mail=?, s_phone=?, s_password=? where s_id=?";
			jt.update(sql, com_name, owner_name, email, phone, requestValues.get("pwchk"), id);
		}
		else {
			sql = "update seller set s_com_name=?,s_owner_name=?, s_mail=?, s_phone=?, s_addr=?, s_password=? where s_id=?";
			jt.update(sql, com_name, owner_name, email, phone, addr, requestValues.get("pwchk"), id);
		}
	}

	public int selpwchk(HttpSession session, String pw, Model model) {
		String id = (String)session.getAttribute("seller");
		
		String sql = "select s_com_name, s_com_number, s_owner_name, s_mail, s_phone, s_addr from seller where s_id = ? and s_password = ?";
		List<SellerDto> result = jt.query(sql, new RowMapper<SellerDto>() {

			@Override
			public SellerDto mapRow(ResultSet rs, int rowNum) throws SQLException {
				SellerDto sell = new SellerDto();
				sell.setS_com_name(rs.getString("s_com_name"));
				sell.setS_com_number(rs.getString("s_com_number"));
				sell.setS_owner_name(rs.getString("s_owner_name"));
				sell.setS_mail(rs.getString("s_mail"));
				sell.setS_phone(rs.getString("s_phone"));
				sell.setS_addr(rs.getString("s_addr"));
				
				return sell;
			}}, id, pw);
		model.addAttribute("sellinfo",  result.isEmpty() ? null : result.get(0));
		int ans = result.isEmpty() ? 0 : 1; 
		return ans;
	}

	public int pwchk(HttpSession session, String pw, Model model) {
		String id = (String)session.getAttribute("userid");
		
		
		String sql = "select c_name, c_mail, c_phone, c_addr, c_gender from customer where c_id = ? and c_password = ?";
	
		List<CustomerDto> result = jt.query(sql, new RowMapper<CustomerDto>() {

			@Override
			public CustomerDto mapRow(ResultSet rs, int rowNum) throws SQLException {
				CustomerDto ct = new CustomerDto();
				ct.setName(rs.getString("c_name"));
				ct.setMail(rs.getString("c_mail"));
				ct.setPhone(rs.getString("c_phone"));
				ct.setAddr(rs.getString("c_addr"));
				ct.setGender(rs.getString("c_gender"));
				return ct;
			}}, id, pw);
		
		
		int ans = result.isEmpty() ? 0 : 1;
		model.addAttribute("userinfo", result.isEmpty() ? null : result.get(0));


		return ans;
	}

	public int sellnumchk(String sellnum) {

		String sql = "select count(*) from seller where s_com_name=?";

		Integer chkresult =  jt.queryForObject(sql, Integer.class, sellnum);
		
		if (sellnum.equals("")) {
			chkresult = -1;
		}
		
		return chkresult; 
	}

	public int emailchk(String email) {

		int chkresult = 0;
		
		String sql = null;
		
		sql = "select count(*) from customer where c_mail=?";
		chkresult = jt.queryForObject(sql, Integer.class, email);
		sql = "select count(*) from seller where s_mail=?";
		chkresult += jt.queryForObject(sql, Integer.class, email);

		if (email.equals("")) {
			chkresult = -1;
		}
		
		return chkresult; 
	}

	public int idchk(String id) {
		
		int chkresult = 0;
		
		String sql = null;
		
		sql = "select count(*) from customer where c_id=?";
		chkresult = jt.queryForObject(sql, Integer.class, id);
		sql = "select count(*) from seller where s_id=?";
		chkresult += jt.queryForObject(sql, Integer.class, id);

		if (id.equals("")) {
			chkresult = -1;
		}
		
		return chkresult; 
	}

	public void logout(HttpSession session) {
		session.invalidate();
	}

	public int checkId(String id) {

		int re_idchk = 2;
		
		String sql = "select count(*) from seller where s_id=?";
		
		re_idchk = jt.queryForObject(sql, Integer.class, id);
		
		return re_idchk;
	}

	public void signup(CustomerDto cus, Map<String, Object> requestValues) {
		String id = cus.getId();
		String name = cus.getName();
		String email = cus.getMail();
		String phone = cus.getPhone();
		String gender = cus.getGender();

		String birth = requestValues.get("year") + "-" + requestValues.get("month") + "-" + requestValues.get("day");
		String addr = "("+requestValues.get("addr1")+")"+requestValues.get("addr2")+" "+requestValues.get("addr3");
		
		boolean smarkvalue = false, emarkvalue = false;
		if (requestValues.get("smark").equals("on")) {
			smarkvalue = true;
		}
		if (requestValues.get("emark").equals("on")) {
			emarkvalue = true;
		}

		String sql = "insert into customer values (?,?,?,?,?,?,?,?,?,?,?,?)";
		
		jt.update(sql, id, requestValues.get("pw"), name, email, phone, addr, gender, birth, smarkvalue, emarkvalue, 0, "bronze");
	}

	public void seller_signup(SellerDto sel, Map<String, Object> requestValues) {
		String id = sel.getS_id();
		String name = sel.getS_com_name();
		String number = sel.getS_com_number();
		String ownername = sel.getS_owner_name();
		String mail = sel.getS_mail();
		String phone = sel.getS_phone();

		String addr = "("+requestValues.get("addr1")+")"+requestValues.get("addr2")+" "+requestValues.get("addr3");
		
		boolean smarkvalue = false, emarkvalue = false;
		if (requestValues.get("smark").equals("on")) {
			smarkvalue = true;
		}
		if (requestValues.get("emark").equals("on")) {
			emarkvalue = true;
		}
		
		String sql = "insert into seller values (?,?,?,?,?,?,?,?,?,?)";
		
		jt.update(sql, id, requestValues.get("pw"), name, number, ownername, mail, phone, addr, smarkvalue, emarkvalue);
	}

	public boolean login(HttpSession session , String id, String pw) {
		
		boolean login_ans= false;
		String sql = "select c_id, c_name, c_addr, c_point, c_class from customer where c_id=? and c_password=?";
		List<CustomerDto> result = jt.query(sql, new RowMapper<CustomerDto>() {

			@Override
			public CustomerDto mapRow(ResultSet rs, int rowNum) throws SQLException {
				CustomerDto dto = new CustomerDto();
				
				dto.setId(rs.getString(1));
				dto.setName(rs.getString(2));
				dto.setAddr(rs.getString(3));
				dto.setPoint(rs.getInt(4));
				dto.setC_class(rs.getString(5));
				return dto;
			}}, id, pw);
		
		String sqlans = result.isEmpty() ? null : result.get(0).getId();
		login_ans = result.isEmpty() ? false : true;
		if (login_ans) {
			if (session.getAttribute("user") == null) {
				session.setAttribute("user", result.get(0));
			}
			if (session.getAttribute("userid") == null) {
				session.setAttribute("userid", sqlans);
			}
		}
		
		sql = "select s_id from seller where s_id=? and s_password=?";
		List<String> result2 = jt.query(sql, new RowMapper<String>() {

			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString(1);
			}}, id, pw);
		
		String sqlans2 = result2.isEmpty() ? null : result2.get(0);
		
		if (!login_ans) {
			login_ans = result2.isEmpty() ? false : true;
		}
		if (login_ans) {
			if(session.getAttribute("seller") == null) {
				session.setAttribute("seller", sqlans2);
			}
		}
		return login_ans;
	}

	public void delfood(HttpServletRequest request, int num) {
		
		String idsql = "fooddel"+num;
		int id = Integer.valueOf(request.getParameter(idsql));
		String sql = "delete from foodlist where f_id = ?";
		jt.update(sql, id);
	}	

	public Integer totalpage(String foodname) {
		int total = 0;
		String sql = "";
		if (foodname == null) {
			sql = "select count(*) from foodlist";
		}
		else {
			sql = "select count(*) from foodlist where f_name like '%"+foodname+"%'";
		}
		total = jt.queryForObject(sql,Integer.class);
		
		return total;
	}

	public List<FoodmanageDto> productmanage(String foodname, String page) {
		String sql = "";
		int SearchPage = (Integer.valueOf(page)-1) * 10;
		if (foodname == null) {
			sql = "select * from foodlist order by f_id desc limit "+SearchPage+", 10";
		}
		else {
			sql = "select * from foodlist where f_name like '%"+foodname+"%' order by f_id desc limit "+SearchPage+", 10";
		}
		List<FoodmanageDto> result = jt.query(sql, new RowMapper<FoodmanageDto>() {

			@Override
			public FoodmanageDto mapRow(ResultSet rs, int rowNum) throws SQLException {
				FoodmanageDto fm = new FoodmanageDto();
				fm.setF_id(String.valueOf(rs.getInt("f_id")));
				fm.setF_code(rs.getString("f_category"));
				fm.setF_name(rs.getString("f_name"));
				fm.setF_price(rs.getInt("f_price"));
				fm.setF_unit(rs.getInt("f_unit"));
				return fm;
			}});
		return result;

	}

	public int addchk(String name) {

		int chk = 0;
		String sql = "select count(*) from foodlist where f_name=?";
		chk = jt.queryForObject(sql, Integer.class, name);
		
		return chk;
	}

	public void addfood(String foodcate, String name, int price, int unit) {
			String sql = "insert into foodlist(f_category, f_name, f_price, f_unit) values (?,?,?,?)";
			jt.update(sql, foodcate, name, price, unit);
	}

	public List<BarcodesearchDto> f_search(String search) {
		String sql = "select * from foodbarcode where fb_sub like '%"+search+"%'";
		List<BarcodesearchDto> result = jt.query(sql, new RowMapper<BarcodesearchDto>() {

			@Override
			public BarcodesearchDto mapRow(ResultSet rs, int rowNum) throws SQLException {
				BarcodesearchDto bs = new BarcodesearchDto();
				bs.setFb_main(rs.getString("fb_main"));
				bs.setFb_middle(rs.getString("fb_middle"));
				bs.setFb_sub(rs.getString("fb_sub"));
				return bs;
			}});
		
		return result;
	}
}
