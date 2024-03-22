package com.example.sb.Member;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MemberDao {
	
	@Select("select * from member")
	List<Member> getMemberList();
	
	@Insert("insert into member values (default, #{name}, #{age}, #{country})")
	void insertMember(Member member);
}
