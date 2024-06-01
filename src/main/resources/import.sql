insert into tb_currency(currency_code, currency_name) values('KRW', '원');
insert into tb_currency(currency_code, currency_name) values('USD', '달러');

insert into tb_user(user_id, name) values('user1234', '홍길동');

insert into tb_wallet(wallet_id, user_id, currency_code, balnace) values('wallet1','user1234', 'USD', 1000.25);
insert into tb_wallet(wallet_id, user_id, currency_code, balnace) values('wallet2','user1234', 'KRW', 0);

insert into tb_merchant(merchant_id, merchant_name) values('merchantId1234', '테스트상점');

