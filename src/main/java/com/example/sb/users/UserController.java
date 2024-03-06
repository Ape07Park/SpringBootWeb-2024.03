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
	@GetMapping("/list/{page}") // get method 
	public String list(@PathVariable int page, Model model) {
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
	@GetMapping("/update")
	public String update(@PathVariable String uid, Model model) {
		User user = userSvc.getUserByUid(uid);
		model.addAttribute("user", user);
		return "user/update";
	}

	@PostMapping("/update")
	public String updateProc(String uid, String pwd, String pwd2, String uname, String email) {
		String hashedPwd = null;
		
		if (pwd.equals(pwd2) && pwd != null) {
			hashedPwd = BCrypt.hashpw(pwd, BCrypt.gensalt());
			User user = new User(uid, hashedPwd, uname, email);
			userSvc.updateUser(user);
		}
		return "redirect:/user/list/1";
	}

	// delete
	@GetMapping("/delete")
	public String delete(String uid) {
		userSvc.deleteUser(uid);
		return "user/list";
	}
	
	// syso 대신 사용
	// log.info(member.toString());

}
