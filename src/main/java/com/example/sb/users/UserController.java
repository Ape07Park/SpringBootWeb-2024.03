package com.example.sb.users;

import java.util.List; 

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller // 컨트롤러임을 알리기 위해
@RequestMapping("/user") // sb/user 관련된 것은 여기 컨트롤러가 처리
public class UserController {
	@Autowired  // UserServiceImpl component를 불러서 연결
	private UserService userSvc;

	/*
	 * http://localhost:8090/sb/user/list/1
	 * URL의 { }에는 패스 변수를 넣는다. 
	 * 패스 변수: {}로 둘러쌓인 값 ex: {page}
	 * URL 경로에서 변수 값을 추출해 매개변수(int page)에 할당
	 * sb/user/list/1이라는 URL이라면, page 파라미터에 1이라는 값이 들어올 것이다.
	 */
	
	// "/list/{page}"의 page 값을 받아 @PathVariable int page 에 넣음
	@GetMapping(value={"/list/{page}", "/list"} ) // get method 
	public String list(@PathVariable(required=false) Integer page, Model model) {
		page = (page == null) ? 1 : page;
		List<User> list = userSvc.getUserList(page);
		
		// parameter.setAttribute 대신 사용
		model.addAttribute("userList", list);
		
		return "user/list"; // rd.forward(request, response) 역할
	}

	@GetMapping("/register")
	public String register() {
		return "user/register"; 
	}

	@PostMapping("/register") // post method
	// * 파라미터로 받을 거를 메서드의 파라미터로 설정 시 스프링이 자동으로 가져옴
	public String registerProc(String uid, String pwd, String pwd2, String uname, String email) {
		if (userSvc.getUserByUid(uid) == null && pwd.equals(pwd2)) {
			String hashedPwd = BCrypt.hashpw(pwd, BCrypt.gensalt());
			User user = new User(uid, hashedPwd, uname, email);
			userSvc.registerUser(user);
		}
		// redirect는 context path x 
		return "redirect:/user/list/1"; // response.sendRedirect 역할
	}

	// update
	@GetMapping("/update/{uid}")
	public String update(@PathVariable String uid, Model model) {
		User user = userSvc.getUserByUid(uid);
		model.addAttribute("user", user);
		return "user/update";
	}

	@PostMapping("/update")
	public String updateProc(String uid, String pwd, String pwd2, String uname, String email) {
		// 바뀌기 전 상태 가져옴. 왜냐하면 생성자로 만들 때 pwd 안바꾸면 바꾸기 전의 hashedPwd를 처리 힘듦 
		User user = userSvc.getUserByUid(uid);
		// pwd 고치기
		if (pwd.equals(pwd2) && pwd != null) {
			String hashedPwd = BCrypt.hashpw(pwd, BCrypt.gensalt());
			user.setPwd(hashedPwd);
		}
		
		// 하나씩 uname, email 등 하나씩 고쳐나감 
		user.setUname(uname);
		user.setEmail(email);
		userSvc.updateUser(user);
		return "redirect:/user/list";
	}

	// delete
	@GetMapping("/delete/{uid}") //- /delete/1 하면 1번 지워지게 {uid} 추가했고 uid값 받기 위해 @PathVariable 추가  
	public String delete(@PathVariable String uid) {
		userSvc.deleteUser(uid);
		return "redirect:/user/list";
	}
	
	// login
	@GetMapping("/login")
	public String login() {
		return "user/login";
	}
	
	@PostMapping("/login")
	public String loginProc(String uid, String pwd, HttpSession session, Model model) {
		String msg = null, url = null;
		int result = userSvc.login(uid, pwd);
		if (result == userSvc.CORRECT_LOGIN) {
			User user = userSvc.getUserByUid(uid);
			session.setAttribute("sessUid", uid);
			session.setAttribute("sessUname", user.getUname());
			msg = user.getUname() + "님 환영합니다.";
			url = "/sb/user/list/1";
		} else if (result == userSvc.WRONG_PASSWORD) {
			msg = "패스워드가 틀립니다.";
			url = "/sb/user/login";
		} else {
			msg = "아이디 입력이 잘못되었습니다.";
			url = "/sb/user/login";
		}
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		return "user/alertMsg";
	}
		
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/user/login";
	}
	// syso 대신 사용
	// log.info(member.toString());

}
