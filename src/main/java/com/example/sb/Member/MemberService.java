package com.example.sb.Member;

import java.util.List;

public interface MemberService {
	
	List<Member> getMemberList();
	
	void insertMember(Member member); 
}
