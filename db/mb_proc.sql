CREATE  PROCEDURE `mb_process_msg`()
BEGIN
 DECLARE v_msg_id BIGINT;
 DECLARE v_msg_type INT;
 DECLARE no_more TINYINT(4);
 DECLARE v_content VARCHAR(300) charset utf8;
 DECLARE cur_msg CURSOR FOR SELECT msg_id,msg_type,content FROM mb_msg WHERE flag=0 ;
 DECLARE CONTINUE HANDLER FOR NOT FOUND SET no_more=1;  
 SET no_more=0; 
 OPEN cur_msg;
 REPEAT  
FETCH cur_msg INTO v_msg_id,v_msg_type,v_content;  
IF v_msg_type = 1 THEN
   INSERT INTO mb_user_msg(login_name,msg_id,notify_flag,create_date) SELECT loginname,v_msg_id, 0,NOW() FROM mb_user a INNER JOIN mb_role_func b ON a.rolecode=b.rolecode WHERE b.funccode='F020100';
END IF ;
IF v_msg_type = 2 THEN
   INSERT INTO mb_user_msg(login_name,msg_id,notify_flag,create_date) SELECT loginname,v_msg_id, 0,NOW() FROM mb_user a INNER JOIN mb_role_func b ON a.rolecode=b.rolecode WHERE b.funccode='F020203';
END IF ;
IF v_msg_type = 3 THEN
   INSERT INTO mb_user_msg(login_name,msg_id,notify_flag,create_date) SELECT loginname,v_msg_id, 0,NOW() FROM mb_user a INNER JOIN mb_role_func b ON a.rolecode=b.rolecode WHERE b.funccode='F020303';
END IF ;
IF v_msg_type = 4 THEN
   INSERT INTO mb_user_msg(login_name,msg_id,notify_flag,create_date) SELECT loginname,v_msg_id, 0,NOW() FROM mb_user a INNER JOIN mb_role_func b ON a.rolecode=b.rolecode WHERE b.funccode='F020203';
END IF ;
IF v_msg_type = 5 THEN
   INSERT INTO mb_user_msg(login_name,msg_id,notify_flag,create_date) SELECT loginname,v_msg_id, 0,NOW() FROM mb_user a INNER JOIN mb_role_func b ON a.rolecode=b.rolecode WHERE b.funccode='F020303';
END IF ;
 UPDATE mb_msg SET flag=1 WHERE msg_id=v_msg_id;
UNTIL no_more   
END REPEAT; 
END;

CREATE PROCEDURE `sb_order`(IN pre VARCHAR(5),OUT newOrderNo VARCHAR(21))
BEGIN
 DECLARE currentDate VARCHAR (12);
 DECLARE maxNo INT DEFAULT 0;
 DECLARE oldOrderNo VARCHAR (21) DEFAULT '';
 DECLARE newOrderNo1 VARCHAR (21) DEFAULT '';
 DECLARE endstr VARCHAR (2);
 SELECT DATE_FORMAT(NOW(), '%Y%m%d%H%i') INTO currentDate ;
 SELECT DATE_FORMAT(NOW(), '%s') INTO endstr ;
 SELECT IFNULL(order_no, '') INTO oldOrderNo FROM sb_test_order
 WHERE SUBSTRING(order_no, 3, 12) = currentDate AND SUBSTRING(order_no, 1, 2)=pre  ORDER BY id DESC LIMIT 1 ;
 IF oldOrderNo != '' THEN
   SET maxNo = CONVERT(SUBSTRING(oldOrderNo, 15,5), DECIMAL) ;
 END IF ;
 SELECT CONCAT(pre, currentDate,  LPAD((maxNo + 1), 5, '0'),endstr) INTO newOrderNo1 ;
 INSERT INTO sb_test_order (order_no) VALUES (newOrderNo1) ;
 SET newOrderNo = newOrderNo1 ;
END;

