package dao;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import dto.CartlistDto;
import dto.CustomerDto;
import dto.FoodpriceDto;
import dto.MarketDto;

public class CartDao {
	
	private JdbcTemplate jt;
	
	public CartDao(DataSource dataSource) {
		this.jt = new JdbcTemplate(dataSource);
	}
	
	public Map<String, Integer> foodUnit() {
		String sql = "select * from foodlist";
		Map<String, Integer> result = new HashMap<String, Integer>();;
		jt.query(sql, new RowMapper<Object>() {

			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				result.put(rs.getString("f_name"), rs.getInt("f_unit"));
				return null;
			}});
		return result;
	}
	
	public void seldel(HttpSession session, String[] food) {
		ArrayList<CartlistDto> al = (ArrayList<CartlistDto>)session.getAttribute("myCart");
		int[] foods = new int[food.length];
		
		for (int j = 0; j < foods.length; j++) {
			foods[j] = Integer.valueOf(food[j]);
		}
		
		int delcnt = 0; 
		
		for (int i = 0; i < food.length; i++) {
			al.remove(foods[i]-delcnt);
			delcnt++;
		}
	}

	public void cartdel(HttpSession session) {
		session.removeAttribute("myCart");
	}
	
	public Integer pagecnt(HttpSession session, String orderperiod) {
		String id = (String)session.getAttribute("userid");
		
		int diff = 0;
		if (orderperiod.equals("1year")) {
			diff = 12;
		}
		else if(orderperiod.equals("1month")) {
			diff= 1;
		}
		else if(orderperiod.equals("3month")) {
			diff = 3;
		}
		else if(orderperiod.equals("6month")) {
			diff = 6;
		}
		LocalDateTime ldt = LocalDateTime.now().minusMonths(diff);
		String s_date = ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			
		String sql = "select count(*) from cusorder where o_id=? and o_date > ?";
		int cnt = jt.queryForObject(sql, Integer.class, id, s_date);	
		
		return cnt;
	}
	
	public List<CartlistDto> mypage(HttpSession session, String orderperiod, String page) {
		String id = (String)session.getAttribute("userid");
		

		int SearchPage = (Integer.valueOf(page) - 1) * 10;
		
		int diff = 0;
		if (orderperiod.equals("1year")) {
			diff = 12;
		}
		else if(orderperiod.equals("1month")) {
			diff= 1;
		}
		else if(orderperiod.equals("3month")) {
			diff = 3;
		}
		else if(orderperiod.equals("6month")) {
			diff = 6;
		}
		LocalDateTime ldt = LocalDateTime.now().minusMonths(diff);
		String s_date = ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

		String sql = "select * from cusorder where o_id=? and o_date > ? order by o_num desc limit "+SearchPage+", 10";
		List<CartlistDto> result = jt.query(sql, new RowMapper<CartlistDto>() {

			@Override
			public CartlistDto mapRow(ResultSet rs, int rowNum) throws SQLException {
				CartlistDto ca = new CartlistDto();
				ca.setNum(rs.getInt("o_num"));
				ca.setFoodName(rs.getString("o_f_name"));
				ca.setFoods(rs.getString("o_f_singname").split(","));
				ca.setFoodprice(rs.getString("o_f_singprice").split(","));
				ca.setFoodunit(rs.getString("o_f_singunit").split(","));
				ca.setDate(rs.getString("o_date"));
				ca.setFilename(rs.getString("o_f_img"));
				return ca;
			}}, id, s_date);
		
		return result;
	}

	public List<FoodpriceDto> price(HttpServletRequest request) {

		String sql = "select * from foodlist";
		List<FoodpriceDto> result = jt.query(sql, new RowMapper<FoodpriceDto>() {

			@Override
			public FoodpriceDto mapRow(ResultSet rs, int rowNum) throws SQLException {
				FoodpriceDto fp = new FoodpriceDto();
				fp.setF_name(rs.getString("f_name"));
				fp.setF_price(rs.getInt("f_price"));
				return null;
			}});
		
		return result;
	}

	public void addCart(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute("myCart") == null) {
			ArrayList<CartlistDto> al = new ArrayList<CartlistDto>();
			session.setAttribute("myCart", al);
		}
		CartlistDto cl = new CartlistDto();
		String name = request.getParameter("name");
		int len = Integer.parseInt(request.getParameter("len"));
		String[] foods = new String[len];
		String[] foodnum = new String[len];
		String[] foodprice = new String[len];
		String file = request.getParameter("file");
		int id = Integer.valueOf(request.getParameter("foodid"));
		for (int i = 0; i < len; i++) {
			foods[i] = request.getParameter("foods"+i);
			foodnum[i] = request.getParameter("foodnum"+i);
			foodprice[i] = request.getParameter("foodprice"+i);
		}
		cl.setFoodName(name);
		cl.setFoods(foods);
		cl.setFoodunit(foodnum);
		cl.setFoodprice(foodprice);
		cl.setFilename(file);
		cl.setNum(id);
		ArrayList<CartlistDto> al = (ArrayList<CartlistDto>)session.getAttribute("myCart");
		al.add(cl);
	}

	public void sellcnt(HttpServletRequest request, String[] selchk) {
		HttpSession session = request.getSession();
		
		ArrayList<CartlistDto> al = (ArrayList<CartlistDto>)session.getAttribute("myCart");
		String sql1 = "select r_sell from recipe where r_id = ?";
		String sql2 = "update recipe set r_sell= ? where r_id = ?";
		
		for (int i = 0; i < al.size(); i++) {
			CartlistDto ca = al.get(i);
			if (selchk[i].equals("1")) {
				int id = ca.getNum();
				jt.query(sql1, new RowMapper<Integer>() {
		
					@Override
					public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
						int sell = rs.getInt(1);
						jt.update(sql2, sell+1, id);
						return rs.getInt(1);
					}},id);
			}
		}
				
		int cnt = 0;
		int listcnt = al.size();
		for (int i = 0; i < listcnt; i++) {
			if (selchk[i].equals("1")) {
				al.remove(i-cnt);
				cnt++;
			}
		}
	}
	
	public void order(HttpServletRequest request) {
		HttpSession session = request.getSession();	
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String date = format.format(Calendar.getInstance().getTime());

		
		String c_id = (String)session.getAttribute("userid");
		String foodlen  = request.getParameter("foodlen");
		String[] singfoodlen = new String[Integer.valueOf(foodlen)];
		String[] foodimg = new String[Integer.valueOf(foodlen)];
		String[] foodname = new String[Integer.valueOf(foodlen)];
		String[] singfoodname = new String[Integer.valueOf(foodlen)];
		String[] singfoodprice = new String[Integer.valueOf(foodlen)];
		String[] singfoodunit = new String[Integer.valueOf(foodlen)];
		String cusaddr = request.getParameter("cusaddr");
		
		String[] selid = request.getParameterValues("food_sel_id");

		String[] selchk = request.getParameterValues("selchk");
		for (int i = 0; i < Integer.valueOf(foodlen); i++) {
			if (selchk[i].equals("1")) {
				singfoodlen[i] = request.getParameter("singfoodlen"+i);
				foodimg[i] = request.getParameter("foodimg"+i);
				foodname[i] = request.getParameter("foodname"+i);
			}
		}
		for (int j = 0; j < Integer.valueOf(foodlen); j++) {
			if (selchk[j].equals("1")) {
				int x = Integer.valueOf(singfoodlen[j]);
				for (int y = 0; y < x; y++) {
					if (y == 0) {
						singfoodname[j] = request.getParameter("singfoodname"+j+y);
						singfoodprice[j] = request.getParameter("foodprice"+j+y);
						singfoodunit[j] = request.getParameter("foodunit"+j+y);
					}
					else {
						singfoodname[j] += ","+request.getParameter("singfoodname"+j+y);
						singfoodprice[j] += ","+request.getParameter("foodprice"+j+y);
						singfoodunit[j] += ","+request.getParameter("foodunit"+j+y);
					}
				}
			}
		}
		String sql = "insert into cusorder (o_date, o_id, o_f_name, o_f_img, o_f_singname, o_f_singprice, o_f_singunit, o_addr, o_s_id) values (?,?,?,?,?,?,?,?,?)";
		for(int i = 0; i < Integer.valueOf(foodlen); i++) {
			if (selchk[i].equals("1")) {
				if (selid[i].equals("?????????")) {
					selid[i] = "master";
					
				}
				jt.update(sql, date, c_id, foodname[i], foodimg[i], singfoodname[i], singfoodprice[i], singfoodunit[i], cusaddr, selid[i]);
			}
		}
		sql = "update foodlist set f_unit = ? where f_name= ?";
		
		String sql2 = "select f_unit from foodlist where f_name=?";
		
		for (int i = 0; i < singfoodname.length; i++) {
			for (int j = 0; j < singfoodname[i].split(",").length; j++) {
				List<Integer> result = jt.query(sql2, new RowMapper<Integer>() {

					@Override
					public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getInt(1);
					}}, singfoodname[i].split(",")[j]);
				if (!result.isEmpty()) {
					jt.update(sql, result.get(0) - Integer.valueOf(singfoodunit[i].split(",")[j]), singfoodname[i].split(",")[j]);
				}
			}
		}
	}
	
	public List<MarketDto> shipsel(HttpSession session, MarketDto customerMarker) {
		CustomerDto dto = (CustomerDto)session.getAttribute("user");
		String addr = dto.getAddr();
		String[] addrs = addr.split(" ");
		String middle_addr = "";
		for (int i = 0; i < addrs.length; i++) {
			if (addrs[i].charAt(addrs[i].length()-1) == '???') {
				middle_addr = addrs[i];
				break;
			}
		}
		
		String sql = "select * from seller where s_addr like '%"+middle_addr+"%'";
		List<MarketDto> result = jt.query(sql, new RowMapper<MarketDto>() {

			@Override
			public MarketDto mapRow(ResultSet rs, int rowNum) throws SQLException {
				MarketDto dt = new MarketDto();
				dt.setId(rs.getString("s_id"));
				dt.setAddr(rs.getString("s_addr"));
				dt.setName(rs.getString("s_com_name"));
				String subaddr = dt.getAddr().substring(dt.getAddr().indexOf(")")+1);
				subaddr = subaddr.substring(0,subaddr.lastIndexOf(" "));
				MarketDto tmp = getGeoDataByAddress(subaddr);
				if(tmp != null) {
					dt.setX(tmp.getX());
					dt.setY(tmp.getY());
				}
				return dt;
			}});
		ArrayList<MarketDto> ans = new ArrayList<MarketDto>();
		for (int i = 0; i < result.size(); i++) {
			MarketDto dto2 = result.get(i);
			System.out.println(dto2.getX() +":"+ dto2.getY());
			
			if (customerMarker.getX()-0.02 <= dto2.getX() && dto2.getX() <= customerMarker.getX()+0.02 && customerMarker.getY()-0.02 <= dto2.getY() && dto2.getY() <= customerMarker.getY()+0.02) {
				ans.add(dto2);
			}
		}

		return ans;
	}
	
	public MarketDto customerMarker(HttpSession session) {
		CustomerDto dto = (CustomerDto)session.getAttribute("user");
		String addr = dto.getAddr();
		addr = addr.substring(addr.indexOf(")")+1);
		addr = addr.substring(0, addr.lastIndexOf(" "));
		MarketDto result = getGeoDataByAddress(addr);
		return result;
	}

	private MarketDto getGeoDataByAddress(String completeAddress) {
	       try {

	    	   //api??? ???????????? ?????? key 
//	           String API_KEY = "AIzaSyCgk1-U0stkIWIZD8Dw9fIAB9-XuaRZZzw";
	           String API_KEY= "AIzaSyDXMN6Lg3WVVzi20NcbAEEBJu6Xw9Ai3Cs";

	           //api??? ???????????? ?????? url??? ?????? ???????????? ????????? ??????, key??? ?????????. ????????? ????????? ????????? URLEncoder??? ???????????? UTF-8??? ???????????????.
	           String surl = "https://maps.googleapis.com/maps/api/geocode/json?address="+URLEncoder.encode(completeAddress, "UTF-8")+"&key="+API_KEY;
	           //URL??? ????????? ??? url ?????? ????????? ??????
	           URL url = new URL(surl);

	           // openConnection()?????? ?????? url??? ????????? ???????????? urlConnection ??? ?????????.
	           // getInputStream()?????? urlConnection ????????? ?????? ??? ?????? InputStream ????????? ?????????.
	           InputStream is = url.openConnection().getInputStream();

	           //InpuStreamReader??? is??? ?????? ?????? ??? BufferedReader??? ???????????? ?????????.
	           BufferedReader streamReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

	           //StringBuilder ????????? ????????????. String ?????? ?????? ????????? ????????? ??????.
	           StringBuilder responseStrBuilder = new StringBuilder();

	           //String ?????? ?????? ??? ?????? ????????? ????????????.
	           String inputStr;
//	           System.out.println(">>>>>>>>>> >>>>>>>>>> InputStream Start <<<<<<<<<< <<<<<<<<<<");

	           // while ?????? ?????? streamReader??? ?????? ????????? ?????? ??? ?????? inputStr??? ????????????. ?????? null??? ??????????????? ????????????.
	           while ((inputStr = streamReader.readLine()) != null) {
//	               System.out.println(">>>>>>>>>>     "+inputStr);

	        	   // ?????? ????????? StringBuilder ????????? inputStr ?????? ????????????.(????????? ?????????)
	               responseStrBuilder.append(inputStr);
	           }
//	           System.out.println(">>>>>>>>>> >>>>>>>>>> InputStream End <<<<<<<<<< <<<<<<<<<<");

	           // JSONObject ????????? ????????? ??? while??? ?????? ?????? responseStrBuilder??? ????????????. ???-??? ????????? ????????????.(???:???)
	           JSONObject jo = new JSONObject(responseStrBuilder.toString());

	           // ?????? ????????? ?????? ????????????.(?????? results)
	           JSONArray results = jo.getJSONArray("results");

	           // ??? ????????? ????????????.(hashmap?????? ???????????? map????????? ???????????? ???(?????????):???(?????????)?????? ?????????.)
	           Map<String, String> ret = new HashMap<String, String>();

	           // results??? ?????? 0?????? ????????? ??????(getJSONArray??? ????????? ??? ?????? ??????????????? ????????????)
	           if(results.length() > 0) {
	               JSONObject jsonObject;
	               // results??? JSONObject?????? 0?????? ????????? ?????? ?????????.
	               jsonObject = results.getJSONObject(0);

	               // jsonObject?????? ?????? 'geometry'??? ????????? ?????? 'location'??? ????????? ??????'lat'??? double ?????? ?????? lat??? ????????????.
	               Double lat = jsonObject.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
	               // jsonObject?????? ?????? 'geometry'??? ????????? ?????? 'location'??? ????????? ??????'lng'??? double ?????? ?????? lng??? ????????????.
	               Double lng = jsonObject.getJSONObject("geometry").getJSONObject("location").getDouble("lng");

	               //????????? ????????? ??? ret??? ???'lat' ??? lat.toString()(?????????)?????? ????????????. 
	               ret.put("lat", lat.toString());
	               //????????? ????????? ??? lng??? ???'lng' ??? lng.toString()(?????????)?????? ????????????.
	               ret.put("lng", lng.toString());

	               // dto??? ????????????. (?????? ???????????? ???????????? ????????? ??????)
	               MarketDto dto = new MarketDto();

	               // x????????? lat, y????????? lng??? set??????. 
	               dto.setX(lat);
	               dto.setY(lng);


	               // jsonObject ???????????? ?????? 'address_components'??? ???????????? ???????????? ja??? ????????????.
	               JSONArray ja = jsonObject.getJSONArray("address_components");

	               // ???????????? ja??? ???????????? ????????????.
	               for(int l=0; l<ja.length(); l++) {

	            	   // ja?????? JSONObject??? ????????? ??? ????????? ????????? ???????????? curjo??? ????????????.
	                   JSONObject curjo = ja.getJSONObject(l);

	                   // curjo?????? ?????? 'types'??? ???????????? ???????????? ???????????? type??? ????????????.
	                   String type = curjo.getJSONArray("types").getString(0);

	                   // curjo?????? ?????? 'short_name'??? ?????? ???????????? short_name ????????? ????????????.
	                   String short_name = curjo.getString("short_name");

	                   //type??? 'postal_code' ????????? ??????
	                   if(type.equals("postal_code")) {

	                       // ??? ret??? ??? 'zip'?????? ??? short_name??? ?????????.
	                       ret.put("zip", short_name);
	                   }
	                   //type??? 'administrative_area_level_3' ????????? ??????
	                   else if(type.equals("administrative_area_level_3")) {
	                       // ??? ret??? ??? 'city'?????? ??? short_name??? ?????????.
	                       ret.put("city", short_name);
	                   }
	                   //type??? 'administrative_area_level_2' ????????? ??????
	                   else if(type.equals("administrative_area_level_2")) {
	                       // ??? ret??? ??? 'province'?????? ??? short_name??? ?????????.
	                       ret.put("province", short_name);
	                   }
	                   //type??? 'administrative_area_level_1' ????????? ??????
	                   else if(type.equals("administrative_area_level_1")) {
	                       // ??? ret??? ??? 'region'?????? ??? short_name??? ?????????.
	                       ret.put("region", short_name);
	                   }                    
	               }
	               // ????????? dto??? ????????????.
	               return dto;
	           }

	       } catch (Exception e) {
	           e.printStackTrace();
	       }
	       // ????????? ???????????? return??? null??? ??????.
	       return null;
	   }
	
}
