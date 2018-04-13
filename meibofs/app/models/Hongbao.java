package models;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import util.Sp;

public class Hongbao {
	
	public Long mb_hongbao_id;
	public Long cust_id;
	public String gift_id;
	public String login_name;
	public Date create_date;
	public BigDecimal credit;
	public Integer status;
	public String content;
	public String level;
	
	
	  public static List<Hongbao> NTgetToday(Long cust_id){
		  String sql="SELECT * FROM mb_hongbao WHERE create_date>=CURDATE() and cust_id=?";
		  List<Hongbao> list=Sp.get().getDefaultJdbc().query(sql,new Object[]{cust_id},new HongbaoRowMap());
		  return list;
	  }
	  
	  public static int NTgetCountToday(Long cust_id){
		  String sql="SELECT COUNT(1) FROM mb_hongbao WHERE create_date>CURDATE() AND cust_id=?";
		  return Sp.get().getDefaultJdbc().queryForObject(sql,new Object[]{cust_id},Integer.class);
	  }
	  
	  public static List<Hongbao> NTgetAll(Long cust_id){
		  String sql="SELECT * FROM mb_hongbao WHERE  cust_id=?";
		  List<Hongbao> list=Sp.get().getDefaultJdbc().query(sql,new Object[]{cust_id},new HongbaoRowMap());
		  return list;
	  }
	  
	  public static long createHongbao(Hongbao hongbao){
		  String sql="insert into  mb_hongbao (cust_id,gift_id,login_name,create_date,credit,status,content,level) values(?,?,?,now(),?,0,?,?)";
		  Object[] objs=new Object[]{hongbao.cust_id,hongbao.gift_id,hongbao.login_name,hongbao.credit,hongbao.content,hongbao.level};
		  KeyHolder keyHolder = new GeneratedKeyHolder();
		  Sp.get().getDefaultJdbc().update(new MyPreparedStatementCreator(sql,objs), keyHolder);
		  return keyHolder.getKey().longValue();
	 }
	  
	  public static boolean createHongbao(Long cust_id,String login_name,BigDecimal credit,String content,String gift_no){
			
			 String sql="insert into  mb_hongbao (cust_id,login_name,create_date,credit,status,content,gift_no) values(?,?,now(),?,0,?,?)";
			 Object [] objs =new Object[]{cust_id,login_name,credit,content,gift_no };
		     int i=Sp.get().getDefaultJdbc().update(sql, objs);  
		     return i>0;
			 
		}
	  
	 public static boolean finishHongbao(String mb_hongbao_id){
		  String sql="update mb_hongbao set status=1 where mb_hongbao_id=?";
		  int count=Sp.get().getDefaultJdbc().update(sql, new Object[]{mb_hongbao_id});
		  return count>0;
	 }
	  
}
