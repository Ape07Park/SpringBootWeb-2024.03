package com.example.sb.Member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	private MemberDao memberDao;
	
	@Override
	public List<Member> getMemberList() {
		return memberDao.getMemberList();
	}

	@Override
	public void insertMember(Member member) {
		memberDao.insertMember(member);
		
	}

}
