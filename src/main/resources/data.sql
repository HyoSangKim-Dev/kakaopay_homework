
insert into tb_payment_info (trade_id, pay_gb, card_info, month_ins, price, vat, org_trade_id, reg_dt) values ('mSHq7RFnrv9mCSYkn1yz', 'PAYMENT', '/bEt1aefz4CDqxwTLR3CEnawMDnIfeMETn4k3lqDhE0+4nC7rZNtDw==', '0', 10000, 1000, null, current_timestamp);
insert into tb_payment_info (trade_id, pay_gb, card_info, month_ins, price, vat, org_trade_id, reg_dt) values ('wHhoV0j7S9yFWFbaP6w1', 'CANCEL', '/bEt1aefz4CDqxwTLR3CEnawMDnIfeMETn4k3lqDhE0+4nC7rZNtDw==', '00', 10000, 1000, 'mSHq7RFnrv9mCSYkn1yz', current_timestamp);

insert into tb_card_send (trade_id, send_data, status, reg_dt) values ('mSHq7RFnrv9mCSYkn1yz', 'SEND_DATA_A', 'OK', current_timestamp);
insert into tb_card_send (trade_id, send_data, status, reg_dt) values ('wHhoV0j7S9yFWFbaP6w1', 'SEND_DATA_B', 'OK', current_timestamp);