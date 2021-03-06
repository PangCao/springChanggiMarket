package control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dao.CartDao;
import dto.MarketDto;

@Controller
public class SpringCartController {
	
	@Autowired
	private CartDao dao;
	
	@RequestMapping("/recipe/addCart")
	public String addCart(HttpServletRequest request, @RequestParam String id, RedirectAttributes ra) {
		dao.addCart(request);
		ra.addAttribute("id", id);
		ra.addAttribute("add", true);
		return "redirect:/recipe/recipe";
	}
	
	@RequestMapping("/cart/cart")
	public String cart(Model model) {
		model.addAttribute("foodchk", dao.foodUnit());
		return "/cart/cart";
	}
	
	@RequestMapping("/recipe/goCart")
	public String goCart(HttpServletRequest request) {
		dao.addCart(request);
		return "redirect:/cart/cart";
	}
	
	@RequestMapping("cart/order")
	@Transactional
	public String order(HttpServletRequest request, @RequestParam String[] selchk) {
		dao.order(request);
		dao.sellcnt(request, selchk);
		return "cart/order_complete";
	}
	
	@RequestMapping("/login/mypage")
	public String mypage(HttpSession session,@RequestParam(defaultValue = "1") String page, @RequestParam String orderperiod, Model model) {
		model.addAttribute("mypage", dao.mypage(session, orderperiod, page));
		model.addAttribute("cnt", dao.pagecnt(session, orderperiod));
		model.addAttribute("orderperiod", orderperiod);
		model.addAttribute("page", page);
		return "login/mypage";
	}
	
	@RequestMapping("/cart/cartdel")
	public String cartdel(HttpSession session) {
		dao.cartdel(session);
		return "redirect:/cart/cart";
	}
	
	@RequestMapping("/cart/seldel")
	public String seldel(HttpSession session, @RequestParam String[] foodid) {
		dao.seldel(session, foodid);
		return "redirect:/cart/cart";
	}
	
	@RequestMapping("/cart/shipsel")
	public String shipsel(HttpSession session ,@RequestParam String num, Model model) {
		MarketDto dto = dao.customerMarker(session);
		model.addAttribute("customerMarker",dto);
		model.addAttribute("marketlist", dao.shipsel(session, dto));
		model.addAttribute("num", num);
		return "cart/shipselect";
	}
}
