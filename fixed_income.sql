USE fixedincome;
create table MasterSecurity(Isin char(12) PRIMARY KEY ,security ENUM("T-Bills","Treasury Bonds","Certificate of Deposits","Commercial Papers","Corporate Bonds","Municipal Bonds") NOT NULL, issuerName varchar(50) not null,faceValue double not null, dayCountConvention enum("actual/actual","actual/365","actual/360","30/360","actual/364") not null,maturityDate date not null,couponRate double not null);
create table CouponInfo(couponId int PRIMARY KEY auto_increment,Isin char(12) , couponDates date,constraint ms_isin FOREIGN KEY(Isin) REFERENCES MasterSecurity(Isin) );
insert into MasterSecurity values ('IN0020190295','T-Bills','Govt of India T-Bill 2019',25000,'actual/364','2020-09-25',0);
insert into MasterSecurity values ('IN0020200182','T-Bills','Govt of India T-Bill 2020',25000,'actual/364','2021-04-09',0);
insert into MasterSecurity values ('IN0020140079','Treasury Bonds','Govt of India GSec Bond 2014',10000,'30/360','2024-07-08',5.8);
insert into MasterSecurity values ('IN0020060019','Treasury Bonds','Govt of India GSec Bond 2006',10000,'30/360','2026-01-08',6.46);
alter TABLE MasterSecurity MODIFY COLUMN dayCountConvention ENUM("actual/actual","actual/365","actual/360","30/360","actual/364","actual/366");
insert into MasterSecurity values ('INF760K01JG7','Certificate of Deposits','Canara Bank Certificate of Deposits',100000,'actual/366','2020-10-26',7);
insert into MasterSecurity values ('INF03LN01013','Certificate of Deposits','Bajaj Finserv Certificate of Deposit',100000,'actual/365','2021-02-10',7.5);
insert into MasterSecurity values ('INF021A01026','Commercial Papers','Asian Paints Commercial Paper',500000,'actual/360','2020-12-21',0);
insert into MasterSecurity values ('INF158A01026','Commercial Papers','Hero MotoCorp Commercial Paper',500000,'actual/360','2020-12-21',0);
insert into MasterSecurity values ('INF956O01016','Corporate Bonds','Lenskart Solutions Corporate Bond',10000,'30/360','2021-05-07',8);
insert into MasterSecurity values ('INF758T01015','Corporate Bonds','Zomato',10000,'30/360','2022-11-28',9);
insert into MasterSecurity values ('INF204KB13C7','Corporate Bonds','RIL Corporate Bond',10000,'30/360','2021-06-09',12);
insert into MasterSecurity values ('INE117E24018','Municipal Bonds','AMC Municipal Bond',50000,'30/360','2024-01-10',8.7);
insert into MasterSecurity values ('INE117E07039','Municipal Bonds','PMC Municipal Bond',50000,'30/360','2027-10-13',7.59);

/*entering data in coupon info*/

insert into CouponInfo (Isin,couponDates) values('IN0020140079','2020-07-08');
insert into CouponInfo (Isin,couponDates) values('IN0020140079','2021-01-08');
insert into CouponInfo (Isin,couponDates) values('IN0020060019','2020-07-08');
insert into CouponInfo (Isin,couponDates) values('IN0020060019','2021-01-08');
insert into CouponInfo (Isin,couponDates) values('INF760K01JG7','2020-10-26');
insert into CouponInfo (Isin,couponDates) values('INF03LN01013','2021-02-10');
insert into CouponInfo (Isin,couponDates) values('INF956O01016','2020-05-06');
insert into CouponInfo (Isin,couponDates) values('INF758T01015','2020-11-27');
insert into CouponInfo (Isin,couponDates) values('INF204KB13C7','2021-06-09');
insert into CouponInfo (Isin,couponDates) values('INE117E24018','2021-01-10');
insert into CouponInfo (Isin,couponDates) values('INE117E07039','2020-04-13');
insert into CouponInfo (Isin,couponDates) values('INE117E07039','2020-10-13');

/*select * from CouponInfo;*/

