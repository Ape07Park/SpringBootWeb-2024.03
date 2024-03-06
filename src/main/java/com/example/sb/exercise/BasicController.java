package com.example.sb.exercise;

import org.mindrot.jbcrypt.BCrypt; 
import org.slf4j.Logger; 
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller; 
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

// 화면을 띄우는 3가지 대표적인 방법
@Controller // 컨트롤러임을 알리기 위해
@RequestMapping("/ex") // sb/ex는 여기 컨트롤러가 처리
public class BasicController {
	private final Logger log = LoggerFactory.getLogger(getClass());		
	
	// GET 방식 - view 파일 찾는 경우
	@GetMapping("/hello")  // http://localhost:8090/sb/ex/hello  :라우팅 페스 / url 한개에 대해 메서드 1개씩
	public String hello() {
		return "exercise/hello"; 	// hello.html - return은 rd.forword 효과
	}
	
	@ResponseBody // html 파일 직접 찾지 말고, 데이터를 직접 전송 - ajax를 쓸 때 사용 바뀌는 부분만 고치면 되기에 - 데이터를 쏘는 경우 
	@GetMapping("/noHtml")
	public String noHtml() {
		return "<h1>Hello Spring Boot<h1>"; 
	}
	
	@GetMapping("/redirect") // sendRedirect - sendRedirect하는 경우 
	public String redirect() {
		return "redirect:/ex/hello"; // 현재 context path 빠짐
	}
	
	// parameter 보내고 싶을 시
	@GetMapping("/params")
	public String params(Model model) {
		model.addAttribute("name", "james");
		return "exercise/params"; // exercise/params으로 보냄 
	}
	
	@GetMapping("/params2")
	public String params2(Model model, HttpServletRequest req) { // java.net.http.HttpRequest;
		String name = req.getParameter("name");
		model.addAttribute("name", name);
		return "exercise/params"; // exercise/params으로 보냄 
	}
	
	// * 파라미터로 받을 거를 메서드의 파라미터로 설정 시 스프링이 자동으로 가져옴 
	@GetMapping("/params3")
	public String params2(Model model, String name, int count) { // java.net.http.HttpRequest;
		model.addAttribute("name", name+count);
		return "exercise/params"; // exercise/params으로 보냄 
	}
	
	@GetMapping("/memberForm")
	public String memberForm() {
		return "exercise/memberForm";
	}
	
	@PostMapping("/memberProc")
	public String memberProc(Member member, Model model) {
		// syso 대신 사용
		log.info(member.toString());
		model.addAttribute("name", member.getName());
		return "exercise/params"; // exercise/params으로 보냄 
	}
	
	@GetMapping("login")
	public String login() {
		return "exercise/login";
	}
	
	@PostMapping("/login")
	public String loginProc(String uid, String pwd, HttpSession session, Model model) {
		String hashedPwd = "$2a$10$Yeq5yAF5wLUZJcHMX3VJq.0zRDN8HxycXXsFJ/kB5CGBGLP.IC8kW";
		if(uid.equals("james") && BCrypt.checkpw(pwd, hashedPwd)) {
			model.addAttribute("msg", uid + "님이 로그인");
			session.setAttribute("sessUid", uid);
			session.setAttribute("sessUname", "james");
			return "exercise/loginResult";
		}else {
			model.addAttribute("msg", uid + "비번 확인");
			return "exercise/loginResult";
		}
	}
			
	// bid에 값이 없으면 매핑 안됨			
	@GetMapping(value={"/path/{uid}/{bid}", "/path/{uid}"}) // 2가지 경우를 다 받아드리겠다 
	@ResponseBody // 결과를 바로 보여줄 수 있음
	public String path(@PathVariable String uid, @PathVariable(required = false) Integer bid) {
		bid = (bid == null) ? 0: bid;
		return "<h1>uid=" + uid + ", bid=" + bid + "</h1>"; 
	}
}
