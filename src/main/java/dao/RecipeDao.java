package dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dto.cartlist;
import dto.foodmanage;
import dto.foodprice;
import dto.recipelist;


public class RecipeDao {
	
	private JdbcTemplate jt;
	
	public RecipeDao(DataSource dataSource) {
		this.jt = new JdbcTemplate(dataSource);
	}
	
	public List<recipelist> recom() {
		String sql = "select * from recipe order by r_sell desc limit 5";
		
		List<recipelist> result = jt.query(sql, new RowMapper<recipelist>() {

			@Override
			public recipelist mapRow(ResultSet rs, int rowNum) throws SQLException {
				recipelist rl = new recipelist();
				rl.setR_name(rs.getString("r_name"));
				rl.setR_id(rs.getInt("r_id"));
				rl.setR_category(rs.getString("r_category"));
				rl.setR_desc(rs.getString("r_desc"));
				rl.setR_unit(rs.getString("r_unit"));
				rl.setR_tip(rs.getString("r_tip"));
				rl.setR_img(rs.getString("r_img"));
				return rl;
			}});

		return result;
	}

	public void delrecipe(String id) {
		String sql = "delete from recipe where r_id=?";
		jt.update(sql, id);
	}

	public List<recipelist> orderprice(String order, ArrayList<recipelist> fp, ArrayList<foodprice> foodprice) {
		int[] price = new int[fp.size()];
		for (int i = 0; i < fp.size(); i++) {
			recipelist rl = fp.get(i);			
			String[] foodname = rl.getR_product().split(",");
			String[] foodunit = rl.getR_unit().split(",");
			for (int j = 0; j < foodname.length; j++) {
				String name = foodname[j];
				String unit = foodunit[j];
				for (int x = 0; x < foodprice.size(); x++) {
					foodprice fpri = foodprice.get(x);
					if (fpri.getF_name().equals(name)) {
						price[i] += fpri.getF_price() * Integer.valueOf(unit);
						break;
					}
				}
			}
		}
		int[] priceindex = new int[price.length];
		for (int i = 0; i < price.length; i++) {
			priceindex[i] = i;
		}
		int tmp;
		int tmp2;
		for (int i = 0; i < price.length-1; i++) {
			for (int j = i+1; j < price.length; j++) {
				if(price[i] < price[j]) {
					tmp = price[i];
					price[i] = price[j];
					price[j] = tmp;
					tmp2 = priceindex[i];
					priceindex[i] = priceindex[j];
					priceindex[j] = tmp2;
				}
			}
		}
		ArrayList<recipelist> fp2 = new ArrayList<recipelist>();
		if (order.equals("highprice")) {
			for (int i = 0; i < fp.size(); i++) {
				fp2.add(fp.get(priceindex[i]));
			}
		}
		else {
			for (int i = 0; i < fp.size(); i++) {
				fp2.add(fp.get(priceindex[fp.size()-i-1]));
			}
		}
		return fp2;
	}

	public void addrecipe(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String path = request.getSession().getServletContext().getRealPath("resources/images");
		MultipartRequest multi = null;
		try {
			multi = new MultipartRequest(request,path, 1024 * 1024 * 10, "utf-8", new DefaultFileRenamePolicy());
		} catch (IOException e) {
			e.printStackTrace();
		}
		String r_writer = (String)session.getAttribute("userid");
		String r_name = multi.getParameter("rename");
		String r_desc = multi.getParameter("redes");
		String r_cate = multi.getParameter("catesel");
		String r_tip = multi.getParameter("retip");
		r_tip = r_tip.replace("\n", "<br>");
		String[] f_name = multi.getParameterValues("f_name_in");
		String[] f_unit = multi.getParameterValues("f_unit_in");
		String r_product = String.join(",", f_name).substring(1); 
		String r_unit = String.join(",", f_unit).substring(1);
		
		Enumeration<String> file = multi.getFileNames();
		String filename = file.nextElement();
		String r_img = multi.getFilesystemName(filename); 
		
		String sql = "insert into recipe(r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values (?,?,?,?,?,?,?,?)";
		jt.update(sql, r_writer, r_cate, r_name, r_desc, r_product, r_unit, r_tip, r_img);
	}
	
	public List<foodmanage> searchfood(String search) {
		String sql = "";
		if (search == null || search.equals("") || search.equals("null")) {
			sql = "select * from foodlist";
		}
		else {
			sql = "select * from foodlist where f_name like '%"+search+"%'";
		}
		List<foodmanage> result = jt.query(sql, new RowMapper<foodmanage>() {

			@Override
			public foodmanage mapRow(ResultSet rs, int rowNum) throws SQLException {
				foodmanage fm = new foodmanage();
				fm.setF_code(String.valueOf(rs.getInt("f_id")));
				fm.setF_code(rs.getString("f_category"));
				fm.setF_name(rs.getString("f_name"));
				fm.setF_price(rs.getInt("f_price"));
				fm.setF_unit(rs.getInt("f_unit"));
				return fm;
			}});

		return result;
	}

	public int userchk(HttpSession session) {
		String user = (String)session.getAttribute("userid");
		String seller = (String)session.getAttribute("seller");
		int chk = 0;
		if (user != null) {
			chk = 1;
		}
		else if (seller != null) {
			chk = 2;
		}
		return chk;	
	}

	public void addCartIcon(HttpSession session, String id, ArrayList<foodprice> fp) {
		if (session.getAttribute("myCart") == null) {
			ArrayList<cartlist> al = new ArrayList<cartlist>();
			session.setAttribute("myCart", al);
		}
		String sql = "select * from recipe where r_id=?";
		List<cartlist> result = jt.query(sql, new RowMapper<cartlist>() {

			@Override
			public cartlist mapRow(ResultSet rs, int rowNum) throws SQLException {
				cartlist cl = new cartlist();
				String name = rs.getString("r_name");
				String r_foods = rs.getString("r_product");
				String r_foodnum = rs.getString("r_unit");
				String file = rs.getString("r_img");
				String[] foods = r_foods.split(",");
				String[] foodnum = r_foodnum.split(",");
				String[] foodprice = new String[foods.length];
				int r_id = rs.getInt("r_id");
				
				for (int i = 0; i < foods.length; i++) {
					for (int j = 0; j < fp.size(); j++) {
						foodprice fpdto = fp.get(j);
						if(fpdto.getF_name().equals(foods[i])) {
							foodprice[i] = String.valueOf(fpdto.getF_price());
							break;
						}
					} 
				}
				cl.setNum(r_id);
				cl.setFoodName(name);
				cl.setFoods(foods);
				cl.setFoodunit(foodnum);
				cl.setFoodprice(foodprice);
				cl.setFilename(file);
				return cl;
			}},id);

			ArrayList<cartlist> al = (ArrayList<cartlist>)session.getAttribute("myCart");
			al.add(result.get(0));
	}

	public recipelist sel_recipe(int id) {

		String sql = "select * from recipe where r_id=?";
		List<recipelist> result = jt.query(sql, new RowMapper<recipelist>() {

			@Override
			public recipelist mapRow(ResultSet rs, int rowNum) throws SQLException {
				recipelist rp = new recipelist();
				rp.setR_id(rs.getInt("r_id"));
				rp.setR_writer(rs.getString("r_writer"));
				rp.setR_category(rs.getString("r_category"));
				rp.setR_name(rs.getString("r_name"));
				rp.setR_desc(rs.getString("r_desc"));
				rp.setR_product(rs.getString("r_product"));
				rp.setR_unit(rs.getString("r_unit"));
				rp.setR_tip(rs.getString("r_tip"));
				rp.setR_img(rs.getString("r_img"));
				return rp;
			}}, id);

		return result.isEmpty() ? null : result.get(0);
	}

	public Integer count(String search_title, String category) {

		String sql = "";

		if (search_title == null || search_title.equals("") || search_title.equals("null")) {
			sql = "select count(*) from recipe where r_category = ?";
		}
		else {
			sql = "select count(*) from recipe where  r_category = ? and r_name like '%"+search_title+"%'";
		}
		Integer count = jt.queryForObject(sql, Integer.class, category);

		return count;
	}

	public List<recipelist> recipes(String search_title, String order, String category, String page) {
		
		String sql = "";
	
		int SearchPage = (Integer.valueOf(page)-1)*20;
		
		if (search_title == null || search_title.equals("") || search_title.equals("null")) {
			if (order != null && order.equals("new")) {
				sql = "select * from recipe where r_category = ? order by r_id desc limit "+SearchPage+", 20";
			}
			else if (order != null && order.equals("sell")) {
				sql = "select * from recipe where r_category = ? order by r_sell desc limit "+SearchPage+", 20";
			}
			else {
				sql = "select * from recipe where r_category = ? limit "+SearchPage+", 20";
			}
		}
		else {
			if (order != null && order.equals("new")) {
				sql = "select * from recipe where r_category = ? and r_name like '%"+search_title+"%' order by r_id desc limit "+SearchPage+", 20";
			}
			else if (order != null && order.equals("sell")) {
				sql = "select * from recipe where r_category = ? and r_name like '%"+search_title+"%' order by r_sell desc limit "+SearchPage+", 20";
			}
			else {
				sql = "select * from recipe where r_category = ? and r_name like '%"+search_title+"%' limit "+SearchPage+", 20";
			}
		}
		
		List<recipelist> result = jt.query(sql, new RowMapper<recipelist>() {

			@Override
			public recipelist mapRow(ResultSet rs, int rowNum) throws SQLException {
				recipelist rp = new recipelist();
				rp.setR_writer(rs.getString("r_writer"));
				rp.setR_id(rs.getInt("r_id"));
				rp.setR_category(rs.getString("r_category"));
				rp.setR_name(rs.getString("r_name"));
				rp.setR_desc(rs.getString("r_desc"));
				rp.setR_product(rs.getString("r_product"));
				rp.setR_unit(rs.getString("r_unit"));
				rp.setR_tip(rs.getString("r_tip"));
				rp.setR_img(rs.getString("r_img"));
				return rp;
			}}, category);
			
		return result; 
	}

	public List<foodprice> price() {
		
		String sql = "select * from foodlist";
		List<foodprice> result = jt.query(sql, new RowMapper<foodprice>() {

			@Override
			public foodprice mapRow(ResultSet rs, int rowNum) throws SQLException {
				foodprice fp = new foodprice();
				fp.setF_name(rs.getString("f_name"));
				fp.setF_price(rs.getInt("f_price"));
				return fp;
			}});
		
		return result;
	}
}
