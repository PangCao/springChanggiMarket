package control;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dao.CartDao;
import dao.RecipeDao;
import dto.foodprice;
import dto.recipelist;

@Controller
public class SpringRecipeController {
	
	@Autowired
	private RecipeDao dao;
	
	@RequestMapping("/recipe/recipes")
	public String recipes(@RequestParam(defaultValue = "1") String page, @RequestParam(required=false) String order, @RequestParam(value="r_category", required=false) String category, @RequestParam(required=false) String search_title, Model model) {
		ArrayList<recipelist> fp = (ArrayList<recipelist>) dao.recipes(search_title, order, category, page);
		ArrayList<foodprice> foodprice = (ArrayList<dto.foodprice>) dao.price();
		model.addAttribute("page", page);
		if (order != null && !order.equals("null")) {
			if (order.equals("rowprice") || order.equals("highprice")) {
				model.addAttribute("food", dao.orderprice(order, fp, foodprice));
			}
			else {
				model.addAttribute("food", dao.recipes(search_title, order, category, page));
			}
		}
		else {
			model.addAttribute("food", dao.recipes(search_title, order, category, page));
		}
		model.addAttribute("cnt", dao.count(search_title, category));
		model.addAttribute("foodprice", dao.price());
		if (order != null && !order.equals("null")) {
			model.addAttribute("order", order);
			model.addAttribute("search_title", search_title);
			return "recipe/recipes";
		}
		else {
			model.addAttribute("search_title", search_title);
			return "recipe/recipes";
		}
	}
	
	@RequestMapping("/recipe/recipe")
	public String recipe(@RequestParam int id, Model model) {
		model.addAttribute("sel_recipe", dao.sel_recipe(id));
		model.addAttribute("foodprice", dao.price());
		return "recipe/recipe";
	}
	
	@RequestMapping("/recipe/addCartIcon")
	public String addCartIcon(HttpSession session, @RequestParam String id, @RequestParam String r_category, @RequestParam String page, @RequestParam String order, Model model, RedirectAttributes ra) {
		int userchk = dao.userchk(session);
		ra.addAttribute("id", id);
		ra.addAttribute("r_category", r_category);
		ra.addAttribute("page", page);
		ra.addAttribute("order", order);
		if (userchk == 1) {
			ArrayList<foodprice> fp = (ArrayList<foodprice>) dao.price();
			dao.addCartIcon(session, id, fp);
			model.addAttribute("foodprice", fp);
			ra.addAttribute("chk", "1");
			return "redirect:/recipe/recipes";
		}
		else if (userchk == 2) {
			ra.addAttribute("chk", "2");
			return "redirect:/recipe/recipes";
		}
		else if (userchk == 0) {
			ra.addAttribute("chk", "0");
			return "redirect:/recipe/recipes";
		}
		return "redirect:/recipe/recipes";
	}
	
	@RequestMapping("/recipe/foodsearch")
	public String foodsearch(@RequestParam(required = false) String search, Model model) {
		model.addAttribute("foodlist", dao.searchfood(search));
		return "recipe/foodsel";
	}
	
	@RequestMapping("/recipe/addrecipeprocess")
	public String addrecipe(HttpServletRequest request, RedirectAttributes ra) {
		dao.addrecipe(request);
		ra.addAttribute("r_category", "밥·죽");
		ra.addAttribute("page", "1");
		return "redirect:/recipe/recipes";
	}
	
	@RequestMapping("/recipe/delrecipe")
	public String delrecipe(String id, RedirectAttributes ra) {
		dao.delrecipe(id);
		ra.addAttribute("r_category", "밥·죽");
		ra.addAttribute("page", "1");
		return "redirect:/recipe/recipes";
	}
	
	@RequestMapping("/index")
	public String index(Model model) {
		model.addAttribute("recomm", dao.recom());
		return "index";
	}	
}
