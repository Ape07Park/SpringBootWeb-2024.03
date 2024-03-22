package com.example.sb.Member;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class MemberController {
	@Autowired
	private MemberService memberService; 
	
	@GetMapping("/list")
	public String getMemberList(Model model) {
		List<Member> memberList = new ArrayList<>();
		
		memberList = memberService.getMemberList();
		
		model.addAttribute("memberList" , memberList);
		
		return "/member/list";
	}
	
	@GetMapping("/insert")
	public String insertForm() {
		return "/member/insert";
	}
	
	@PostMapping("/insert")
	public String insert(String name, int age, String country, Model model) {
		Member member = new Member(name, age, country);
		
		memberService.insertMember(member);
		
		
		return "/member/list";
	}
	
	
	
}
