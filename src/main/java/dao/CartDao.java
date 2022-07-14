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

import dto.cartlist;
import dto.customer;
import dto.foodprice;
import dto.marketDto;

public class CartDao {
	
	private JdbcTemplate jt;
	
	public CartDao(DataSource dataSource) {
		this.jt = new JdbcTemplate(dataSource);
	}
	
	public void seldel(HttpSession session, String[] food) {
		ArrayList<cartlist> al = (ArrayList<cartlist>)session.getAttribute("myCart");
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
	
	public List<cartlist> mypage(HttpSession session, String orderperiod) {
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

		String sql = "select * from cusorder where o_id=? and o_date > ? order by o_num desc";
		List<cartlist> result = jt.query(sql, new RowMapper<cartlist>() {

			@Override
			public cartlist mapRow(ResultSet rs, int rowNum) throws SQLException {
				cartlist ca = new cartlist();
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

	public List<foodprice> price(HttpServletRequest request) {

		String sql = "select * from foodlist";
		List<foodprice> result = jt.query(sql, new RowMapper<foodprice>() {

			@Override
			public foodprice mapRow(ResultSet rs, int rowNum) throws SQLException {
				foodprice fp = new foodprice();
				fp.setF_name(rs.getString("f_name"));
				fp.setF_price(rs.getInt("f_price"));
				return null;
			}});
		
		return result;
	}

	public void addCart(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute("myCart") == null) {
			ArrayList<cartlist> al = new ArrayList<cartlist>();
			session.setAttribute("myCart", al);
		}
		cartlist cl = new cartlist();
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
		ArrayList<cartlist> al = (ArrayList<cartlist>)session.getAttribute("myCart");
		al.add(cl);
	}

	public void sellcnt(HttpServletRequest request, String[] selchk) {
		HttpSession session = request.getSession();
		
		ArrayList<cartlist> al = (ArrayList<cartlist>)session.getAttribute("myCart");
		String sql1 = "select r_sell from recipe where r_id = ?";
		String sql2 = "update recipe set r_sell= ? where r_id = ?";
		
		for (int i = 0; i < al.size(); i++) {
			cartlist ca = al.get(i);
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
				jt.update(sql, date, c_id, foodname[i], foodimg[i], singfoodname[i], singfoodprice[i], singfoodunit[i], cusaddr, selid[i]);
			}
		}
	}
	
	public List<marketDto> shipsel(HttpSession session, marketDto customerMarker) {
		customer dto = (customer)session.getAttribute("user");
		String addr = dto.getAddr();
		String[] addrs = addr.split(" ");
		String middle_addr = "";
		for (int i = 0; i < addrs.length; i++) {
			if (addrs[i].charAt(addrs[i].length()-1) == '구') {
				middle_addr = addrs[i];
				break;
			}
		}
		
		String sql = "select * from seller where s_addr like '%"+middle_addr+"%'";
		List<marketDto> result = jt.query(sql, new RowMapper<marketDto>() {

			@Override
			public marketDto mapRow(ResultSet rs, int rowNum) throws SQLException {
				marketDto dt = new marketDto();
				dt.setId(rs.getString("s_id"));
				dt.setAddr(rs.getString("s_addr"));
				dt.setName(rs.getString("s_com_name"));
				String subaddr = dt.getAddr().substring(dt.getAddr().indexOf(")")+1);
				subaddr = subaddr.substring(0,subaddr.lastIndexOf(" "));
				marketDto tmp = getGeoDataByAddress(subaddr);
				if(tmp != null) {
					dt.setX(tmp.getX());
					dt.setY(tmp.getY());
				}
				return dt;
			}});
		ArrayList<marketDto> ans = new ArrayList<marketDto>();
		for (int i = 0; i < result.size(); i++) {
			marketDto dto2 = result.get(i);
			System.out.println(dto2.getX() +":"+ dto2.getY());
			
			if (customerMarker.getX()-0.02 <= dto2.getX() && dto2.getX() <= customerMarker.getX()+0.02 && customerMarker.getY()-0.02 <= dto2.getY() && dto2.getY() <= customerMarker.getY()+0.02) {
				ans.add(dto2);
			}
		}

		return ans;
	}
	
	public marketDto customerMarker(HttpSession session) {
		customer dto = (customer)session.getAttribute("user");
		String addr = dto.getAddr();
		addr = addr.substring(addr.indexOf(")")+1);
		addr = addr.substring(0, addr.lastIndexOf(" "));
		marketDto result = getGeoDataByAddress(addr);
		return result;
	}

	private marketDto getGeoDataByAddress(String completeAddress) {
	       try {

	    	   //api를 사용하기 위한 key 
//	           String API_KEY = "AIzaSyCgk1-U0stkIWIZD8Dw9fIAB9-XuaRZZzw";
	           String API_KEY= "AIzaSyDXMN6Lg3WVVzi20NcbAEEBJu6Xw9Ai3Cs";

	           //api를 사용하기 위한 url과 쿼리 스트링을 이용한 주소, key를 받는다. 한글이 깨지지 않도록 URLEncoder를 사용해서 UTF-8로 인코딩한다.
	           String surl = "https://maps.googleapis.com/maps/api/geocode/json?address="+URLEncoder.encode(completeAddress, "UTF-8")+"&key="+API_KEY;
	           //URL을 생성할 때 url 값을 넣어서 생성
	           URL url = new URL(surl);

	           // openConnection()으로 해당 url에 연결된 클래스인 urlConnection 을 얻는다.
	           // getInputStream()으로 urlConnection 객체를 읽을 수 있는 InputStream 객체를 얻는다.
	           InputStream is = url.openConnection().getInputStream();

	           //InpuStreamReader로 is의 값을 읽을 때 BufferedReader를 사용하여 읽는다.
	           BufferedReader streamReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

	           //StringBuilder 객체를 생성한다. String 값을 담기 위해서 준비만 한다.
	           StringBuilder responseStrBuilder = new StringBuilder();

	           //String 값을 담을 수 있는 변수를 생성한다.
	           String inputStr;
//	           System.out.println(">>>>>>>>>> >>>>>>>>>> InputStream Start <<<<<<<<<< <<<<<<<<<<");

	           // while 문을 통해 streamReader의 값을 한줄씩 읽고 그 값을 inputStr에 저장한다. 값이 null이 아닐때까지 반복한다.
	           while ((inputStr = streamReader.readLine()) != null) {
//	               System.out.println(">>>>>>>>>>     "+inputStr);

	        	   // 위에 생성한 StringBuilder 객체에 inputStr 값을 추가한다.(문자열 더하기)
	               responseStrBuilder.append(inputStr);
	           }
//	           System.out.println(">>>>>>>>>> >>>>>>>>>> InputStream End <<<<<<<<<< <<<<<<<<<<");

	           // JSONObject 객체를 생성할 때 while을 통해 받은 responseStrBuilder를 저장한다. 키-값 형태로 저장된다.(키:값)
	           JSONObject jo = new JSONObject(responseStrBuilder.toString());

	           // 키를 통해서 값을 검색한다.(키는 results)
	           JSONArray results = jo.getJSONArray("results");

	           // 맵 객체를 생성한다.(hashmap으로 생성하여 map기능만 사용하고 키(스트링):값(스트링)으로 받는다.)
	           Map<String, String> ret = new HashMap<String, String>();

	           // results의 값이 0보다 크다면 실행(getJSONArray로 받았을 때 값이 존재한다면 실행한다)
	           if(results.length() > 0) {
	               JSONObject jsonObject;
	               // results의 JSONObject에서 0번째 인덱스 값을 받는다.
	               jsonObject = results.getJSONObject(0);

	               // jsonObject에서 키가 'geometry'인 값에서 키가 'location'인 값에서 키가'lat'인 double 값을 변수 lat에 저장한다.
	               Double lat = jsonObject.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
	               // jsonObject에서 키가 'geometry'인 값에서 키가 'location'인 값에서 키가'lng'인 double 값을 변수 lng에 저장한다.
	               Double lng = jsonObject.getJSONObject("geometry").getJSONObject("location").getDouble("lng");

	               //위에서 생성한 맵 ret에 키'lat' 값 lat.toString()(문자열)으로 저장한다. 
	               ret.put("lat", lat.toString());
	               //위에서 생성한 맵 lng에 키'lng' 값 lng.toString()(문자열)으로 저장한다.
	               ret.put("lng", lng.toString());

	               // dto를 생성한다. (값을 저장해서 반환하기 위해서 생성)
	               marketDto dto = new marketDto();

	               // x값으로 lat, y값으로 lng을 set한다. 
	               dto.setX(lat);
	               dto.setY(lng);


	               // jsonObject 변수에서 키가 'address_components'인 배열값을 반환받아 ja에 저장한다.
	               JSONArray ja = jsonObject.getJSONArray("address_components");

	               // 반환받은 ja의 길이만큼 반복한다.
	               for(int l=0; l<ja.length(); l++) {

	            	   // ja에서 JSONObject를 가져올 때 인덱스 값으로 가져와서 curjo에 저장한다.
	                   JSONObject curjo = ja.getJSONObject(l);

	                   // curjo에서 키가 'types'인 배열값을 반환받아 문자열로 type에 저장한다.
	                   String type = curjo.getJSONArray("types").getString(0);

	                   // curjo에서 키가 'short_name'인 값을 반환받아 short_name 변수에 저장한다.
	                   String short_name = curjo.getString("short_name");

	                   //type이 'postal_code' 인경우 실행
	                   if(type.equals("postal_code")) {

	                       // 맵 ret에 키 'zip'으로 값 short_name을 넣는다.
	                       ret.put("zip", short_name);
	                   }
	                   //type이 'administrative_area_level_3' 인경우 실행
	                   else if(type.equals("administrative_area_level_3")) {
	                       // 맵 ret에 키 'city'으로 값 short_name을 넣는다.
	                       ret.put("city", short_name);
	                   }
	                   //type이 'administrative_area_level_2' 인경우 실행
	                   else if(type.equals("administrative_area_level_2")) {
	                       // 맵 ret에 키 'province'으로 값 short_name을 넣는다.
	                       ret.put("province", short_name);
	                   }
	                   //type이 'administrative_area_level_1' 인경우 실행
	                   else if(type.equals("administrative_area_level_1")) {
	                       // 맵 ret에 키 'region'으로 값 short_name을 넣는다.
	                       ret.put("region", short_name);
	                   }                    
	               }
	               // 저장한 dto를 리턴한다.
	               return dto;
	           }

	       } catch (Exception e) {
	           e.printStackTrace();
	       }
	       // 예외가 발생하면 return을 null로 한다.
	       return null;
	   }
	
}
