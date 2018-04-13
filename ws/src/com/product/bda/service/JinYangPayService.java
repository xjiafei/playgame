package com.product.bda.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import bsz.exch.core.MyPreparedStatementCreator;
import bsz.exch.utils.LogHelper;

public class JinYangPayService {

	 private static Logger logger=Logger.getLogger(JinYangPayService.class);
		
	    private JdbcTemplate template;
		private String datasource;
			
		public JinYangPayService(JdbcTemplate template,String datasource){
				this.template=template;
				this.datasource=datasource;
		}
		
		public  Long createXftp(String pay_id,String login_name,
				String amount,String ip,
				String pay_method,String remark,String return_url){
			  String sql="insert into mb_xftp(pay_id,login_name,amount,payip,pay_method,remark,"
			  		+ "create_date,state,return_url) values(?,?,?,?,?,?,now(),0,?)";
			  Object[] objs=new Object[]{pay_id,login_name,amount,ip,pay_method,remark,return_url};
			  KeyHolder keyHolder = new GeneratedKeyHolder();
			  logger.info(LogHelper.dbLogObj(datasource,sql,Arrays.asList(objs)));
			  template.update(new MyPreparedStatementCreator(sql,objs), keyHolder);
			  return keyHolder.getKey().longValue();	
		}
		
		public boolean isNotDoYdp(String pay_id){
			 String sql="select count(1) from mb_xftp where state= 0 and pay_id =?  ";
			 Object[] objs=new Object[]{pay_id};
			 logger.info(LogHelper.dbLogObj(datasource,sql,Arrays.asList(objs)));
			 int count=template.queryForObject(sql, objs, Integer.class);
			 return count>0;
		}
		
		public boolean update(String pay_id,String orderNo,String remark,String state,BigDecimal amount,BigDecimal rate){
			 String sql="update mb_xftp set order_no=?,remark=?, state=?,  "
			 		+ "modify_time=now(),real_pay_method=pay_method,real_amount=?,  finished_date =now() where pay_id=? ";
			 Object[] objs=new Object[]{orderNo,remark,state,amount.multiply(rate).setScale(2, RoundingMode.HALF_UP),pay_id};
			 logger.info(LogHelper.dbLogObj(datasource,sql,Arrays.asList(objs)));
			 return  template.update(sql,objs)>0;
		}
		
		public String queryPayType(String pay_id){
			 String sql="select pay_method from mb_xftp  where pay_id=? ";
			 Object[] objs=new Object[]{pay_id};
			 logger.info(LogHelper.dbLogObj(datasource,sql,Arrays.asList(objs)));
			 return  template.queryForObject(sql, objs, String.class);
		}
		
}
