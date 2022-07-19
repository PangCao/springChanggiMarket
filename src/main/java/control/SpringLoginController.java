package control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dao.loginDao;
import dto.customer;
import dto.seller;

@Controller
public class SpringLoginController {
	
	@Autowired
	private loginDao dao;
	
	@RequestMapping("/login/signupprocess")
	public String signup(@ModelAttribute customer cus, @RequestParam String pw, @RequestParam String addr1, @RequestParam String addr2, @RequestParam String addr3, @RequestParam String year, @RequestParam String month, @RequestParam String day, @RequestParam String smark, @RequestParam String emark, RedirectAttributes ra) {
		dao.signup(cus, pw, addr1, addr2, addr3, year, month, day, smark, emark);
		ra.addAttribute("signup", "1");
		return "redirect:/login/login";
	}
	
	@RequestMapping("/login/idchk")
	public String idchk(@RequestParam String id, RedirectAttributes ra) {
		int chkresult = dao.idchk(id);
		ra.addAttribute("idchk", chkresult);
		return "redirect:/login/idmailchk";
	}
	
	@RequestMapping("/login/emailchk")
	public String emailchk(@RequestParam String email, RedirectAttributes ra) {
		int chkresult = dao.emailchk(email);
		ra.addAttribute("emailchk", chkresult);
		return "redirect:/login/idmailchk";
	}
	
	@RequestMapping("/login/sellnumchk")
	public String sellnumchk(@RequestParam String sellnum, RedirectAttributes ra) {
		int chkresult = dao.sellnumchk(sellnum);
		ra.addAttribute("sellnumchk", chkresult);
		return "redirect:/login/idmailchk";
	}
	
	@RequestMapping("/login/pwchk")
	public String pwchk(HttpServletRequest request, @RequestParam String pw, RedirectAttributes ra, Model model) {
		int pwchkans = dao.pwchk(request, pw, model);
		if (pwchkans == 1) {
			return "login/modimypage";
		}
		else {
			ra.addAttribute("error","1");
			return "redirect:login/mydimypagechk";
		}
	}
	
	@RequestMapping("/login/selpwchk")
	public String selpwchk(HttpServletRequest request, @RequestParam String pw, Model model, RedirectAttributes ra) {
		int pwchkans = dao.selpwchk(request, pw, model);
		if (pwchkans == 1) {
			return "login/modiseller";
		}
		else {
			ra.addAttribute("error", "1");
			return "redirect:modisellerchk";
		}
	}
	
	@RequestMapping("/login/seller_signupprocess")
	public String seller_signup(seller sel, @RequestParam String pw, @RequestParam String addr1, @RequestParam String addr2, @RequestParam String addr3, @RequestParam String smark, @RequestParam String emark, RedirectAttributes ra) {
		dao.seller_signup(sel, pw, addr1, addr2, addr3, smark, emark);
		ra.addAttribute("signup", "1");
		return "redirect:/login/login";
	}
	
	@RequestMapping("/login/loginlo")
	public String login(HttpSession session, @RequestParam(required=false) String id, @RequestParam(required=false) String pw, RedirectAttributes ra) {
		boolean ans = dao.login(session, id, pw);
		if (ans) {
			return "redirect:/index";
		}
		else {
			ra.addAttribute("error", "1");
			return "redirect:/login/login";
		}
	}
	
	@RequestMapping("/login/logout")
	public String logout(HttpSession session) {
		dao.logout(session);
		return "redirect:/index";
	}

	@RequestMapping("/login/modi")
	public String modi(HttpSession session, customer cus, @RequestParam(required=false) String pwchk, @RequestParam(required=false) String addr1, @RequestParam(required=false) String addr2, @RequestParam(required=false) String addr3, RedirectAttributes ra) {
		dao.modi(session, cus, pwchk, addr1, addr2, addr3);
		ra.addAttribute("error", "2");
		return "redirect:modimypagechk";
	}
	
	@RequestMapping("/login/selmodi")
	public String selmodi(@RequestParam(required=false) String pwchk, seller sel, @RequestParam(required=false) String addr1, @RequestParam(required=false) String addr2, @RequestParam(required=false) String addr3, RedirectAttributes ra) {
		dao.selmodi(sel, pwchk, addr1, addr2, addr3);
		ra.addAttribute("error", "2");
		return "redirect:modisellerchk";
	}
	
	@RequestMapping("/login/store_management")
	public String stre_management(HttpSession session, @RequestParam String order, @RequestParam(defaultValue = "1") String page, Model model) {
		model.addAttribute("orderlist", dao.orderlist(session, order, page));
		model.addAttribute("totalpage",String.valueOf(dao.totalpage(session)));
		model.addAttribute("page", page);
		model.addAttribute("order", order);
		return "login/store_management";
	}
	
	@RequestMapping("/login/ordersub")
	public String ordersub(@RequestParam String[] orderchk, @RequestParam String[] orderid, RedirectAttributes ra) {
		dao.ordersub(orderchk, orderid);
		ra.addAttribute("order", "waiting");
		ra.addAttribute("page", "1");
		return "redirect:store_management";
	}
	
	@RequestMapping("/login/idsearch")
	public String idsearch(@RequestParam(required=false) String phone1, @RequestParam(required=false) String phone2, @RequestParam(required=false) String phone3, @RequestParam(required=false) String mail, Model model) {
		int ans = dao.idsearch(phone1, phone2, phone3, mail, model);
		if (ans == 1) {
			return "login/find_id_ans";
		}
		else {
			model.addAttribute("error", "0");
			return "login/find_id_ans";
		}
	}
	
	@RequestMapping("/login/pwsearch")
	public String pwsearch(@RequestParam String id, @RequestParam(required=false) String phone1, @RequestParam(required=false) String phone2, @RequestParam(required=false) String phone3, @RequestParam(required=false) String mail, Model model) {
		int ans = dao.pwsearch(id, phone1, phone2, phone3, mail, model);
		if (ans == 0) {
			model.addAttribute("error", "0");
			return "login/find_pw_ans";
		}
		else {
			return "login/find_pw_ans";
		}
	}
	
	@RequestMapping("/login/pwchange")
	public String pwchange(@RequestParam String id, @RequestParam String user, @RequestParam String pw, Model model) {
		System.out.println("유저"+user);
		System.out.println("아이디"+id);
		System.out.println("비밀번호"+pw);
		dao.pwchange(id, user, pw);
		model.addAttribute("error", "1");
		return "login/find_pw_ans";
	}
	
	@RequestMapping("/login/unsign")
	public String unsign(HttpSession session, RedirectAttributes ra) {
		dao.unsign(session);
		ra.addAttribute("error", "1");
		return "redirect:index";
	}
	
	@RequestMapping("/login/productmanage")
	public String productmanage(@RequestParam(defaultValue = "1") String page, @RequestParam(required=false) String search_food, Model model) {
		model.addAttribute("foodmanage", dao.productmanage(search_food, page));
		model.addAttribute("totalpage", dao.totalpage(search_food));
		model.addAttribute("page", page);
		return "login/productmanage";
	}
	
	@RequestMapping("/login/foodsearch")
	public String foodsearch(@RequestParam(required=false) String search, Model model) {
		model.addAttribute("barcodesearch", dao.f_search(search));
		return "login/foodsearch";
	}
	
	@RequestMapping("/login/addfood")
	public String addfood(@RequestParam String name, @RequestParam String foodcate, @RequestParam int price, @RequestParam int unit, RedirectAttributes ra) {
		int chk = dao.addchk(name);
		ra.addAttribute("page", "1");
		if (chk == 0) {
			dao.addfood(foodcate, name, price, unit);
			return "redirect:productmanage";
		}
		else {
			ra.addAttribute("addchk", "1");
			return "redirect:productmanage";
		}
	}
	
	@RequestMapping("/login/delfood")
	public String delfood(HttpServletRequest request, @RequestParam int num) {
		dao.delfood(request, num);
		return "redirect:productmanage";
	}
	
	@RequestMapping("/login/modifood")
	public String modifood(HttpServletRequest request, @RequestParam int num) {
		dao.modifood(request, num);
		return "redirect:productmanage";
	}	
}
