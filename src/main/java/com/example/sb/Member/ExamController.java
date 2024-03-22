package com.example.sb.Member;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/exam")
public class ExamController {
	List<Member> memberList = new ArrayList<>();
	
	private Member member1 = new Member(1, "james", 21, "미국");
	private Member member2 = new Member(2, "park", 26, "한국");
	private Member member3 = new Member(3, "maria", 27, "영국");
	private Member member4 = new Member(4, "jack", 17, "캐나다");
	private Member member5 = new Member(5, "daniel", 19, "호주");
	
	@GetMapping("/member")
	public String getMemberList(Model model) {
		memberList.add(member1);
		memberList.add(member2);
		memberList.add(member3);
		memberList.add(member4);
		memberList.add(member5);
		model.addAttribute("memberList", memberList);
		
		return "member/memberList"; // html이 있는 폴더의 경로
	}
	
	
}
