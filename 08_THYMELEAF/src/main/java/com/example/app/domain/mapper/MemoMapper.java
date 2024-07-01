package com.example.app.domain.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.example.app.domain.dto.MemoDto;

@Mapper
public interface MemoMapper {

	
	@SelectKey(statement="select max(id)+1 from tbl_memo",keyProperty="id",before=false,resultType=int.class)
	@Insert("insert into tbl_memo values(null,#{text})")
	public int Insert(MemoDto memoDto);
	
	@Update("update tbl_memo set text=#{text} where id=#{id}")
	public int Update(MemoDto memoDto);
	
	@Delete("delete from tbl_memo where id=#{id}")
	public int Delete(int id);
	
	@Select("select * from tbl_memo where id=#{id}")
	public MemoDto SelectAt(@Param("id") int aa);
	
	@Select("select * from tbl_memo")
	public List<MemoDto> SelectAll();
	
	@Results(id="MemoResultMap",value= {
			@Result(property="id",column="id"),
			@Result(property="text",column="text"),
	})
	
	@Select("select * from tbl_memo")
	public List< Map<String,Object> > SelectAllResultMap();
	
	//MemoMapper.xml 
	public int InsertXML(MemoDto memoDto);
	public int UpdateXML(MemoDto memoDto);
	public int DeleteXML(int id);
	public MemoDto SelectAtXML(@Param("id") int aa);
	public List<MemoDto> SelectAllXML();
	public List< Map<String,Object> > SelectAllResultMapXML();
	
	//동적쿼리 적용함수
	public List< Map<String,Object> > SelectIf( Map<String,Object> map);
	public List< Map<String,Object> > SelectWhen( Map<String,Object> map );
	
	
}
