/*create schema homework AUTHORIZATION DBA;
set schema homework;*/
 
drop table tb_payment_info if exists;
CREATE TABLE tb_payment_info (
  trade_id varchar(20) NOT NULL,
  pay_gb varchar(10) NOT NULL,
  card_info varchar(300) NOT NULL,
  month_ins varchar(2) NOT NULL,
  price bigint NOT NULL,
  vat bigint NOT NULL,
  org_trade_id varchar(20),
  reg_dt datetime NOT NULL,
  PRIMARY KEY(trade_id)
);

COMMENT ON TABLE tb_payment_info IS '결제/취소 정보';
COMMENT ON COLUMN tb_payment_info.trade_id IS '관리번호';
COMMENT ON COLUMN tb_payment_info.pay_gb IS '거래 구분[PAYMENT=승인, CANCEL=취소]';
COMMENT ON COLUMN tb_payment_info.card_info IS '카드정보[카드번호|유효기간|cvc, 암호화저장]';
COMMENT ON COLUMN tb_payment_info.month_ins IS '할부계월수[결제=0,1~12, 취소=00]';
COMMENT ON COLUMN tb_payment_info.price IS '거래금액[원]';
COMMENT ON COLUMN tb_payment_info.vat IS '부가가치세[원]';
COMMENT ON COLUMN tb_payment_info.org_trade_id IS '원거래 관리번호[결제취소시에만 저장]';
COMMENT ON COLUMN tb_payment_info.reg_dt IS '등록일자';

drop table tb_payment_card if exists;
CREATE TABLE tb_payment_lock (
  pay_gb varchar(10) NOT NULL,
  private_key varchar(100) NOT NULL,
  PRIMARY KEY(pay_gb, private_key)
);

COMMENT ON TABLE tb_payment_lock IS '결제/취소가 진행중인 중인 정보';
COMMENT ON COLUMN tb_payment_lock.pay_gb IS '거래 구분[PAYMENT=승인, CANCEL=취소]';
COMMENT ON COLUMN tb_payment_lock.private_key IS '확인 고유값';

drop table tb_card_send if exists;
CREATE TABLE tb_card_send (
  trade_id varchar(20) NOT NULL,
  send_data varchar(450) NOT NULL,
  status varchar(2),
  reg_dt datetime NOT NULL,
  PRIMARY KEY(trade_id)
);

COMMENT ON TABLE tb_card_send IS '카드사 전송 데이터';
COMMENT ON COLUMN tb_card_send.trade_id IS '관리번호';
COMMENT ON COLUMN tb_card_send.send_data IS '전송 데이터[450바이트, 헤더=34, 바디=416(공백=47)]';
COMMENT ON COLUMN tb_card_send.status IS '전송결과[OK=성공]';
COMMENT ON COLUMN tb_card_send.reg_dt IS '등록일자';
