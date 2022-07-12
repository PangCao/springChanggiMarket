package control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dao.BoardDao;

@Controller
public class SpringBoardController {
	
	@Autowired
	private BoardDao dao;
	
	@RequestMapping("/community/notice")
	public String notice(@RequestParam(required = false) String search_title, Model model) {
		model.addAttribute("totalpage", dao.bopage(search_title));
		model.addAttribute("category", "notice");
		model.addAttribute("notice", dao.notice(search_title));
		model.addAttribute("search_title", search_title);
		return "community/notice";
	}
	
	@RequestMapping("/community/bulletin")
	public String bulletin(@RequestParam(required = false) String search_title, Model model) {
		model.addAttribute("notice", dao.bulletin(search_title));
		model.addAttribute(model);
		model.addAttribute("totalpage", dao.bulletinbopage(search_title));
		model.addAttribute("category", "bulletin");
		model.addAttribute("search_title", search_title);
		return "community/notice";
	}
	
	@RequestMapping("/community/one_qna")
	public String one_qna(Model model) {
		model.addAttribute("oneqnalist", dao.one());
		model.addAttribute("totalpage", dao.onebopage());
		return "community/one_qna";
	}
	
	@RequestMapping("/community/review")
	public String review(@RequestParam String page, @RequestParam(required=false) String search_title, Model model) {
		model.addAttribute("review_list", dao.review(search_title));
		model.addAttribute("totalpage", dao.review_bopage(search_title));
		model.addAttribute("page", page);
		model.addAttribute("search_title", search_title);
		return "community/recipe_review";
	}
	
	@RequestMapping("/community/faq")
	public String faq(@RequestParam(required=false) String search_title, Model model) {
		model.addAttribute("faqlist", dao.faq(search_title));
		model.addAttribute("totalpage", dao.faq_bopage(search_title));
		model.addAttribute("search_title", search_title);
		return "community/faq";
	}
	
	@RequestMapping("/community/review_view")
	public String review_view(@RequestParam String page, @RequestParam String category, @RequestParam String id, Model model) {
		model.addAttribute("previouspage", dao.previouspage(category, id));
		model.addAttribute("nextpage", dao.nextpage(category, id));
		model.addAttribute("viewInfo", dao.recipe_view(id));
		model.addAttribute("totalpage", dao.review_bopage(id));
		model.addAttribute("commentlist", dao.commentsearch(category, id));
		model.addAttribute("page", page);
		model.addAttribute("category", category);
		return "community/notice_view";
	}
	
	@RequestMapping("/community/one_view")
	public String one_view(@RequestParam String page, @RequestParam String category, @RequestParam String id, Model model) {
		
		model.addAttribute("previouspage", dao.previouspage(category, id));
		model.addAttribute("nextpage", dao.nextpage(category, id));
		model.addAttribute("viewInfo" ,dao.oneview(id));
		model.addAttribute("totalpage", dao.onebopage());
		model.addAttribute("commentlist", dao.commentsearch(category, id));
		model.addAttribute("page", page);
		model.addAttribute("category", category);
		return "community/notice_view";
	}
	
	@RequestMapping("/community/notice_view")
	public String notice_view(@RequestParam(required = false) String page, @RequestParam String category, @RequestParam String id, @RequestParam(required = false) String search_title, Model model) {
		model.addAttribute("previouspage",  dao.previouspage(category, id));
		model.addAttribute("nextpage", dao.nextpage(category, id));
		model.addAttribute("viewInfo", dao.noticeview(id));
		model.addAttribute("totalpage", dao.bopage(search_title));
		model.addAttribute("commentlist", dao.commentsearch(category, id));
		model.addAttribute("category", "notice");
		model.addAttribute("page", page);
		return "community/notice_view";
	}

	
	@RequestMapping("/community/bulletin_view")
	public String bulletin_view(@RequestParam(required = false) String search_title, @RequestParam(required = false) String page, @RequestParam String category, @RequestParam String id, Model model) {
		model.addAttribute("previouspage", dao.previouspage(category, id));
		model.addAttribute("nextpage", dao.nextpage(category, id));
		model.addAttribute("viewInfo", dao.bulletinview(id));
		model.addAttribute("totalpage", dao.bulletinbopage(search_title));
		model.addAttribute("commentlist", dao.commentsearch(category, id));
		model.addAttribute("page", page);
		model.addAttribute("category", "bulletin");
		return "community/notice_view";
	}
	
	@RequestMapping("/community/notice_write")
	public String notice_write(HttpServletRequest request) {
		dao.noticewriter(request);
		return "redirect:/community/notice";
	}
	
	@RequestMapping("/community/bulletin_write")
	public String bulletin_write(HttpServletRequest request) {
		dao.bulletinwriter(request);
		return "redirect:/community/bulletin";
	}
	
	@RequestMapping("/community/one_write")
	public String one_write(@RequestParam String title, @RequestParam String id, @RequestParam String contents, @RequestParam String qnasel, @RequestParam String category) {
		dao.onewriter(title, id, contents, qnasel, category);
		return "redirect:/community/one_qna";
	}
	
	@RequestMapping("/community/review_write")
	public String review_write(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		dao.reviewwriter(request);
		redirectAttributes.addAttribute("page", "1");
		return "redirect:/community/review";
	}


	
	@RequestMapping("/community/review_like")
	public String review_like(HttpSession session, @RequestParam String page, RedirectAttributes ra, @RequestParam int id) {
		int likechk = dao.likechk(session, id);
		if (likechk == 0) {
			dao.likeup(session, id);
			likechk = 3;
		}
		ra.addAttribute("page", page);
		ra.addAttribute("likechk", likechk);
		return "redirect:/community/review";		
	}
	
	@RequestMapping("/community/delnoview")
	public String delnoview( @RequestParam String page, @RequestParam String id, @RequestParam String category, RedirectAttributes ra) {
		dao.delnoview(category, id);
		ra.addAttribute("page", page);
		ra.addAttribute("category", category);
		if (category.equals("나만의 레시피")) {
			return "redirect:/community/review";
		}
		else if (category.equals("게시판")) {
			return "redirect:/community/bulletin";
		}
		else if (category.equals("공지사항")) {
			return "redirect:/community/notice";
		}
		else if (category.equals("1:1 문의")) {
			return "redirect:/community/one_qna";
		}
		return "";
		
	}
	
	@RequestMapping("/community/comment")
	public String comment(HttpSession session, @RequestParam Integer id, @RequestParam String page, @RequestParam String category, @RequestParam String comment, RedirectAttributes ra) {
		dao.comment(session, id, category, comment);
		ra.addAttribute("page", page);
		ra.addAttribute("category", category);
		ra.addAttribute("id", id);
		
		if (category.equals("나만의 레시피")) {
			return "redirect:/community/review_view";
		}
		else if (category.equals("게시판")) {
			return "redirect:/community/bulletin_view";
		}
		else if (category.equals("공지사항")) {
			return "redirect:/community/notice_view";
		}
		else if (category.equals("1:1 문의")) {
			return "redirect:/community/one_view";
		}
		return "";
	}
}
