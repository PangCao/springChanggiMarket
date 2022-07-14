create database changgimarket;

use changgimarket;

create table if not exists customer 
   (c_id varchar(20) not null,
    c_password varchar(20) not null,
    c_name varchar(10) not null,
    c_mail varchar(40) not null,
    c_phone varchar(15) not null,
    c_addr varchar(100) not null,
    c_gender varchar(4),
    c_birth date,
    c_s_Marketting boolean default false,
    c_e_marketting boolean default false,
    c_point int default 0,
    c_class varchar(10) default 'Bronze', 
    primary key(c_id))default charset=utf8mb4;
    
insert into customer values ('cus1', '1234', '고객1', 'cus1@cus.cus', '010-1234-9876', '(51321)경남 창원시 마산회원구 봉양로 9 1', '선택안함','2022-05-17',true, true, 0, 'GOLD');
insert into customer values ('cus2', '1234', '고객2', 'cus2@cus.cus', '010-5678-5432', '(51321)경남 창원시 마산회원구 봉양로 13 302호', '선택안함','2022-05-17',true, true, 0, 'SILVER');
insert into customer values ('cus3', '1234', '고객3', 'cus3@cus.cus', '010-9101-1098', '(51345)경남 창원시 마산회원구 봉양로 55 1층', '선택안함','2022-05-17',true, true, 0, 'BRONZE');
insert into customer values ('cus4', '1234', '고객4', 'cus4@cus.cus', '010-1121-8765', '(51366)경남 창원시 마산회원구 산호천동길 12 1층', '여성','2022-05-17',true, true, 0, 'BRONZE');

create table if not exists seller (
   s_id varchar(20) not null,
    s_password varchar(20) not null,
    s_com_name varchar(10) not null,
    s_com_number varchar(30) not null,
    s_owner_name varchar(10) not null,
    s_mail varchar(40) not null,
    s_phone varchar(15) not null,
    s_addr varchar(100) not null,
    s_s_Marketting boolean default false,
    s_e_marketting boolean default false,
    primary key(s_id))default charset=utf8mb4;

insert into seller values ('admin1','1234','1번 상점','512-2151-12425','상점주인1','seller1@seller.se','010-1234-8764','(51339)경남 창원시 마산회원구 무역로 32 102호', true, true);
insert into seller values ('admin2','1234','2번 상점','512-2151-11111','상점주인1','seller2@seller.se','010-4567-7894','(51339)경남 창원시 마산회원구 무역로 22 401호', true, true);
insert into seller values ('admin3','1234','3번 상점','512-2151-45648','상점주인1','seller3@seller.se','010-5646-7956','(51339)경남 창원시 마산회원구 무역로 27 (마산자유무역지역관리원) 1층', true, true);
insert into seller values ('admin4','1234','4번 상점','512-2151-72312','상점주인1','seller4@seller.se','010-4568-7856','(51339)경남 창원시 마산회원구 무역로 57 ((주)삼일) 1층', true, true);
insert into seller values ('admin5','1234','5번 상점','512-2151-78964','상점주인1','seller5@seller.se','010-4567-1854','(51339)경남 창원시 마산회원구 무역로 73 (성동산업) 1층', true, true);

create table if not exists recipe(
   r_id int not null auto_increment,
    r_writer varchar(20) not null,
    r_category varchar(20) not null,
    r_name varchar(20) not null,
    r_desc varchar(100),
    r_product varchar(500) not null,
    r_unit varchar(200),
    r_tip varchar(1000),
    r_img varchar(100),
    r_sell int default 0,
    primary key(r_id)
)default charset=utf8mb4;
select * from recipe;

insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus1','국·탕·찌개·전골','된장찌개1','구수함이 끝내주는 된장찌개','양파(100g),감자(100g),된장(100g)','1,1,1','1.물을 보글보글 끓여주세요.<br>2.된장을 풀어주세요.<br>3.완성!!!', 'djj.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus1','국·탕·찌개·전골','된장찌개2','구수함이 끝내주는 된장찌개','양파(100g),감자(100g),된장(100g)','1,1,1','1.물을 보글보글 끓여주세요.<br>2.된장을 풀어주세요.<br>3.완성!!!', 'djj.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus1','국·탕·찌개·전골','된장찌개3','구수함이 끝내주는 된장찌개','양파(100g),감자(100g),된장(100g)','1,1,1','1.물을 보글보글 끓여주세요.<br>2.된장을 풀어주세요.<br>3.완성!!!', 'djj.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus1','국·탕·찌개·전골','된장찌개4','구수함이 끝내주는 된장찌개','양파(100g),감자(100g),된장(100g)','1,1,1','1.물을 보글보글 끓여주세요.<br>2.된장을 풀어주세요.<br>3.완성!!!', 'djj.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus1','국·탕·찌개·전골','된장찌개5','구수함이 끝내주는 된장찌개','양파(100g),감자(100g),된장(100g)','1,1,1','1.물을 보글보글 끓여주세요.<br>2.된장을 풀어주세요.<br>3.완성!!!', 'djj.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus1','국·탕·찌개·전골','된장찌개6','구수함이 끝내주는 된장찌개','양파(100g),감자(100g),된장(100g)','1,1,1','1.물을 보글보글 끓여주세요.<br>2.된장을 풀어주세요.<br>3.완성!!!', 'djj.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus1','국·탕·찌개·전골','된장찌개7','구수함이 끝내주는 된장찌개','양파(100g),감자(100g),된장(100g)','1,1,1','1.물을 보글보글 끓여주세요.<br>2.된장을 풀어주세요.<br>3.완성!!!', 'djj.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus1','국·탕·찌개·전골','된장찌개8','구수함이 끝내주는 된장찌개','양파(100g),감자(100g),된장(100g)','1,1,1','1.물을 보글보글 끓여주세요.<br>2.된장을 풀어주세요.<br>3.완성!!!', 'djj.jpg');

insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus2','국·탕·찌개·전골','김치찌개1','묵은지의 깊은 맛이 느껴지는 김치찌개','양파(100g),감자(100g),김치(500g)','1,1,1','1.물을 보글보글 끓여주세요.<br>2.김치와 재료를 넣어주세요.<br>3.완성!!!', 'kjj.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus2','국·탕·찌개·전골','김치찌개2','묵은지의 깊은 맛이 느껴지는 김치찌개','양파(100g),감자(100g),김치(500g)','1,1,1','1.물을 보글보글 끓여주세요.<br>2.김치와 재료를 넣어주세요.<br>3.완성!!!', 'kjj.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus2','국·탕·찌개·전골','김치찌개3','묵은지의 깊은 맛이 느껴지는 김치찌개','양파(100g),감자(100g),김치(500g)','1,1,1','1.물을 보글보글 끓여주세요.<br>2.김치와 재료를 넣어주세요.<br>3.완성!!!', 'kjj.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus2','국·탕·찌개·전골','김치찌개4','묵은지의 깊은 맛이 느껴지는 김치찌개','양파(100g),감자(100g),김치(500g)','1,1,1','1.물을 보글보글 끓여주세요.<br>2.김치와 재료를 넣어주세요.<br>3.완성!!!', 'kjj.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus2','국·탕·찌개·전골','김치찌개5','묵은지의 깊은 맛이 느껴지는 김치찌개','양파(100g),감자(100g),김치(500g)','1,1,1','1.물을 보글보글 끓여주세요.<br>2.김치와 재료를 넣어주세요.<br>3.완성!!!', 'kjj.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus2','국·탕·찌개·전골','김치찌개6','묵은지의 깊은 맛이 느껴지는 김치찌개','양파(100g),감자(100g),김치(500g)','1,1,1','1.물을 보글보글 끓여주세요.<br>2.김치와 재료를 넣어주세요.<br>3.완성!!!', 'kjj.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus2','국·탕·찌개·전골','김치찌개7','묵은지의 깊은 맛이 느껴지는 김치찌개','양파(100g),감자(100g),김치(500g)','1,1,1','1.물을 보글보글 끓여주세요.<br>2.김치와 재료를 넣어주세요.<br>3.완성!!!', 'kjj.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus2','국·탕·찌개·전골','김치찌개8','묵은지의 깊은 맛이 느껴지는 김치찌개','양파(100g),감자(100g),김치(500g)','1,1,1','1.물을 보글보글 끓여주세요.<br>2.김치와 재료를 넣어주세요.<br>3.완성!!!', 'kjj.jpg');

insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus3','국·탕·찌개·전골','북어야채국1','깔끔하고 건강한 맛','북어(100g),양파(100g),감자(100g),당근(100g)','1,1,2,1','1.물을 보글보글 끓여주세요.<br>2.야채를 넣어주세요.<br>3.완성!!!', 'bs.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus2','국·탕·찌개·전골','김치찌개9','묵은지의 깊은 맛이 느껴지는 김치찌개','양파(100g),감자(100g),김치(500g)','1,1,1','1.물을 보글보글 끓여주세요.<br>2.김치와 재료를 넣어주세요.<br>3.완성!!!', 'kjj.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus3','국·탕·찌개·전골','북어야채국2','깔끔하고 건강한 맛','북어(100g),양파(100g),감자(100g),당근(100g)','1,1,2,1','1.물을 보글보글 끓여주세요.<br>2.야채를 넣어주세요.<br>3.완성!!!', 'bs.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus2','국·탕·찌개·전골','김치찌개10','묵은지의 깊은 맛이 느껴지는 김치찌개','양파(100g),감자(100g),김치(500g)','1,1,1','1.물을 보글보글 끓여주세요.<br>2.김치와 재료를 넣어주세요.<br>3.완성!!!', 'kjj.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus3','국·탕·찌개·전골','북어야채국3','깔끔하고 건강한 맛','북어(100g),양파(100g),감자(100g),당근(100g)','1,1,2,1','1.물을 보글보글 끓여주세요.<br>2.야채를 넣어주세요.<br>3.완성!!!', 'bs.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus2','국·탕·찌개·전골','김치찌개11','묵은지의 깊은 맛이 느껴지는 김치찌개','양파(100g),감자(100g),김치(500g)','1,1,1','1.물을 보글보글 끓여주세요.<br>2.김치와 재료를 넣어주세요.<br>3.완성!!!', 'kjj.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus3','국·탕·찌개·전골','북어야채국4','깔끔하고 건강한 맛','북어(100g),양파(100g),감자(100g),당근(100g)','1,1,2,1','1.물을 보글보글 끓여주세요.<br>2.야채를 넣어주세요.<br>3.완성!!!', 'bs.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus2','국·탕·찌개·전골','김치찌개12','묵은지의 깊은 맛이 느껴지는 김치찌개','양파(100g),감자(100g),김치(500g)','1,1,1','1.물을 보글보글 끓여주세요.<br>2.김치와 재료를 넣어주세요.<br>3.완성!!!', 'kjj.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus3','국·탕·찌개·전골','북어야채국5','깔끔하고 건강한 맛','북어(100g),양파(100g),감자(100g),당근(100g)','1,1,2,1','1.물을 보글보글 끓여주세요.<br>2.야채를 넣어주세요.<br>3.완성!!!', 'bs.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus2','국·탕·찌개·전골','김치찌개13','묵은지의 깊은 맛이 느껴지는 김치찌개','양파(100g),감자(100g),김치(500g)','1,1,1','1.물을 보글보글 끓여주세요.<br>2.김치와 재료를 넣어주세요.<br>3.완성!!!', 'kjj.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus3','국·탕·찌개·전골','북어야채국6','깔끔하고 건강한 맛','북어(100g),양파(100g),감자(100g),당근(100g)','1,1,2,1','1.물을 보글보글 끓여주세요.<br>2.야채를 넣어주세요.<br>3.완성!!!', 'bs.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus2','국·탕·찌개·전골','김치찌개14','묵은지의 깊은 맛이 느껴지는 김치찌개','양파(100g),감자(100g),김치(500g)','1,1,1','1.물을 보글보글 끓여주세요.<br>2.김치와 재료를 넣어주세요.<br>3.완성!!!', 'kjj.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus3','국·탕·찌개·전골','북어야채국7','깔끔하고 건강한 맛','북어(100g),양파(100g),감자(100g),당근(100g)','1,1,2,1','1.물을 보글보글 끓여주세요.<br>2.야채를 넣어주세요.<br>3.완성!!!', 'bs.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus2','국·탕·찌개·전골','김치찌개15','묵은지의 깊은 맛이 느껴지는 김치찌개','양파(100g),감자(100g),김치(500g)','1,1,1','1.물을 보글보글 끓여주세요.<br>2.김치와 재료를 넣어주세요.<br>3.완성!!!', 'kjj.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus3','국·탕·찌개·전골','북어야채국8','깔끔하고 건강한 맛','북어(100g),양파(100g),감자(100g),당근(100g)','1,1,2,1','1.물을 보글보글 끓여주세요.<br>2.야채를 넣어주세요.<br>3.완성!!!', 'bs.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus2','국·탕·찌개·전골','김치찌개16','묵은지의 깊은 맛이 느껴지는 김치찌개','양파(100g),감자(100g),김치(500g)','1,1,1','1.물을 보글보글 끓여주세요.<br>2.김치와 재료를 넣어주세요.<br>3.완성!!!', 'kjj.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus3','국·탕·찌개·전골','북어야채국9','깔끔하고 건강한 맛','북어(100g),양파(100g),감자(100g),당근(100g)','1,1,2,1','1.물을 보글보글 끓여주세요.<br>2.야채를 넣어주세요.<br>3.완성!!!', 'bs.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus2','국·탕·찌개·전골','김치찌개17','묵은지의 깊은 맛이 느껴지는 김치찌개','양파(100g),감자(100g),김치(500g)','1,1,1','1.물을 보글보글 끓여주세요.<br>2.김치와 재료를 넣어주세요.<br>3.완성!!!', 'kjj.jpg');

insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus4','밥·죽','카레라이스1','간단하고 맛있는 한끼식사','양파(100g),감자(100g),당근(100g),카레(100g)','1,2,1,1','1.야채를 볶아주세요.<br>2.물을 넣고 카레가루를 넣어주세요.<br>3.완성!!!', 'crice.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus4','밥·죽','카레라이스2','간단하고 맛있는 한끼식사','양파(100g),감자(100g),당근(100g),카레(100g)','1,2,1,1','1.야채를 볶아주세요.<br>2.물을 넣고 카레가루를 넣어주세요.<br>3.완성!!!', 'crice.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus4','밥·죽','카레라이스3','간단하고 맛있는 한끼식사','양파(100g),감자(100g),당근(100g),카레(100g)','1,2,1,1','1.야채를 볶아주세요.<br>2.물을 넣고 카레가루를 넣어주세요.<br>3.완성!!!', 'crice.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus4','밥·죽','카레라이스4','간단하고 맛있는 한끼식사','양파(100g),감자(100g),당근(100g),카레(100g)','1,2,1,1','1.야채를 볶아주세요.<br>2.물을 넣고 카레가루를 넣어주세요.<br>3.완성!!!', 'crice.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus4','밥·죽','카레라이스5','간단하고 맛있는 한끼식사','양파(100g),감자(100g),당근(100g),카레(100g)','1,2,1,1','1.야채를 볶아주세요.<br>2.물을 넣고 카레가루를 넣어주세요.<br>3.완성!!!', 'crice.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus4','밥·죽','카레라이스6','간단하고 맛있는 한끼식사','양파(100g),감자(100g),당근(100g),카레(100g)','1,2,1,1','1.야채를 볶아주세요.<br>2.물을 넣고 카레가루를 넣어주세요.<br>3.완성!!!', 'crice.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus4','밥·죽','카레라이스7','간단하고 맛있는 한끼식사','양파(100g),감자(100g),당근(100g),카레(100g)','1,2,1,1','1.야채를 볶아주세요.<br>2.물을 넣고 카레가루를 넣어주세요.<br>3.완성!!!', 'crice.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus4','밥·죽','카레라이스8','간단하고 맛있는 한끼식사','양파(100g),감자(100g),당근(100g),카레(100g)','1,2,1,1','1.야채를 볶아주세요.<br>2.물을 넣고 카레가루를 넣어주세요.<br>3.완성!!!', 'crice.jpg');

insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus1','면','라멘1','야식의 끝','양파(100g),감자(100g),당근(100g),라멘(1인분)','1,2,1,1','1.물에 야채를 넣고 채수를 끓인다.<br>2.스프를 넣는다.<br>3.면을 넣고 완성', 'ramen.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus1','면','라멘2','야식의 끝','양파(100g),감자(100g),당근(100g),라멘(1인분)','1,2,1,1','1.물에 야채를 넣고 채수를 끓인다.<br>2.스프를 넣는다.<br>3.면을 넣고 완성', 'ramen.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus1','면','라멘3','야식의 끝','양파(100g),감자(100g),당근(100g),라멘(1인분)','1,2,1,1','1.물에 야채를 넣고 채수를 끓인다.<br>2.스프를 넣는다.<br>3.면을 넣고 완성', 'ramen.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus1','면','라멘4','야식의 끝','양파(100g),감자(100g),당근(100g),라멘(1인분)','1,2,1,1','1.물에 야채를 넣고 채수를 끓인다.<br>2.스프를 넣는다.<br>3.면을 넣고 완성', 'ramen.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus1','면','라멘5','야식의 끝','양파(100g),감자(100g),당근(100g),라멘(1인분)','1,2,1,1','1.물에 야채를 넣고 채수를 끓인다.<br>2.스프를 넣는다.<br>3.면을 넣고 완성', 'ramen.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus1','면','라멘6','야식의 끝','양파(100g),감자(100g),당근(100g),라멘(1인분)','1,2,1,1','1.물에 야채를 넣고 채수를 끓인다.<br>2.스프를 넣는다.<br>3.면을 넣고 완성', 'ramen.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus1','면','라멘7','야식의 끝','양파(100g),감자(100g),당근(100g),라멘(1인분)','1,2,1,1','1.물에 야채를 넣고 채수를 끓인다.<br>2.스프를 넣는다.<br>3.면을 넣고 완성', 'ramen.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus1','면','라멘8','야식의 끝','양파(100g),감자(100g),당근(100g),라멘(1인분)','1,2,1,1','1.물에 야채를 넣고 채수를 끓인다.<br>2.스프를 넣는다.<br>3.면을 넣고 완성', 'ramen.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus1','면','라멘9','야식의 끝','양파(100g),감자(100g),당근(100g),라멘(1인분)','1,2,1,1','1.물에 야채를 넣고 채수를 끓인다.<br>2.스프를 넣는다.<br>3.면을 넣고 완성', 'ramen.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus1','면','라멘10','야식의 끝','양파(100g),감자(100g),당근(100g),라멘(1인분)','1,2,1,1','1.물에 야채를 넣고 채수를 끓인다.<br>2.스프를 넣는다.<br>3.면을 넣고 완성', 'ramen.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus1','면','라멘11','야식의 끝','양파(100g),감자(100g),당근(100g),라멘(1인분)','1,2,1,1','1.물에 야채를 넣고 채수를 끓인다.<br>2.스프를 넣는다.<br>3.면을 넣고 완성', 'ramen.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus1','면','라멘12','야식의 끝','양파(100g),감자(100g),당근(100g),라멘(1인분)','1,2,1,1','1.물에 야채를 넣고 채수를 끓인다.<br>2.스프를 넣는다.<br>3.면을 넣고 완성', 'ramen.jpg');

insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus2','찜·조림','낙지찜1','매콤함이 끝내주는 찜','양파(100g),콩나물(100g),파(100g),낙지(1마리),고춧가루(100g)','1,2,1,1,1','1.야채를 넣고 볶는다.<br>2.고춧가루를 넣고 볶는다.<br>3.낙지를 넣고 살짝 볶는다.', 'jjim.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus2','찜·조림','낙지찜2','매콤함이 끝내주는 찜','양파(100g),콩나물(100g),파(100g),낙지(1마리),고춧가루(100g)','1,2,1,1,1','1.야채를 넣고 볶는다.<br>2.고춧가루를 넣고 볶는다.<br>3.낙지를 넣고 살짝 볶는다.', 'jjim.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus2','찜·조림','낙지찜3','매콤함이 끝내주는 찜','양파(100g),콩나물(100g),파(100g),낙지(1마리),고춧가루(100g)','1,2,1,1,1','1.야채를 넣고 볶는다.<br>2.고춧가루를 넣고 볶는다.<br>3.낙지를 넣고 살짝 볶는다.', 'jjim.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus2','찜·조림','낙지찜4','매콤함이 끝내주는 찜','양파(100g),콩나물(100g),파(100g),낙지(1마리),고춧가루(100g)','1,2,1,1,1','1.야채를 넣고 볶는다.<br>2.고춧가루를 넣고 볶는다.<br>3.낙지를 넣고 살짝 볶는다.', 'jjim.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus2','찜·조림','낙지찜5','매콤함이 끝내주는 찜','양파(100g),콩나물(100g),파(100g),낙지(1마리),고춧가루(100g)','1,2,1,1,1','1.야채를 넣고 볶는다.<br>2.고춧가루를 넣고 볶는다.<br>3.낙지를 넣고 살짝 볶는다.', 'jjim.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus2','찜·조림','낙지찜6','매콤함이 끝내주는 찜','양파(100g),콩나물(100g),파(100g),낙지(1마리),고춧가루(100g)','1,2,1,1,1','1.야채를 넣고 볶는다.<br>2.고춧가루를 넣고 볶는다.<br>3.낙지를 넣고 살짝 볶는다.', 'jjim.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus2','찜·조림','낙지찜7','매콤함이 끝내주는 찜','양파(100g),콩나물(100g),파(100g),낙지(1마리),고춧가루(100g)','1,2,1,1,1','1.야채를 넣고 볶는다.<br>2.고춧가루를 넣고 볶는다.<br>3.낙지를 넣고 살짝 볶는다.', 'jjim.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus2','찜·조림','낙지찜8','매콤함이 끝내주는 찜','양파(100g),콩나물(100g),파(100g),낙지(1마리),고춧가루(100g)','1,2,1,1,1','1.야채를 넣고 볶는다.<br>2.고춧가루를 넣고 볶는다.<br>3.낙지를 넣고 살짝 볶는다.', 'jjim.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus2','찜·조림','낙지찜9','매콤함이 끝내주는 찜','양파(100g),콩나물(100g),파(100g),낙지(1마리),고춧가루(100g)','1,2,1,1,1','1.야채를 넣고 볶는다.<br>2.고춧가루를 넣고 볶는다.<br>3.낙지를 넣고 살짝 볶는다.', 'jjim.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus2','찜·조림','낙지찜10','매콤함이 끝내주는 찜','양파(100g),콩나물(100g),파(100g),낙지(1마리),고춧가루(100g)','1,2,1,1,1','1.야채를 넣고 볶는다.<br>2.고춧가루를 넣고 볶는다.<br>3.낙지를 넣고 살짝 볶는다.', 'jjim.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus2','찜·조림','낙지찜11','매콤함이 끝내주는 찜','양파(100g),콩나물(100g),파(100g),낙지(1마리),고춧가루(100g)','1,2,1,1,1','1.야채를 넣고 볶는다.<br>2.고춧가루를 넣고 볶는다.<br>3.낙지를 넣고 살짝 볶는다.', 'jjim.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus2','찜·조림','낙지찜12','매콤함이 끝내주는 찜','양파(100g),콩나물(100g),파(100g),낙지(1마리),고춧가루(100g)','1,2,1,1,1','1.야채를 넣고 볶는다.<br>2.고춧가루를 넣고 볶는다.<br>3.낙지를 넣고 살짝 볶는다.', 'jjim.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus2','찜·조림','낙지찜13','매콤함이 끝내주는 찜','양파(100g),콩나물(100g),파(100g),낙지(1마리),고춧가루(100g)','1,2,1,1,1','1.야채를 넣고 볶는다.<br>2.고춧가루를 넣고 볶는다.<br>3.낙지를 넣고 살짝 볶는다.', 'jjim.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus2','찜·조림','낙지찜14','매콤함이 끝내주는 찜','양파(100g),콩나물(100g),파(100g),낙지(1마리),고춧가루(100g)','1,2,1,1,1','1.야채를 넣고 볶는다.<br>2.고춧가루를 넣고 볶는다.<br>3.낙지를 넣고 살짝 볶는다.', 'jjim.jpg');
insert into recipe (r_writer, r_category, r_name, r_desc, r_product, r_unit, r_tip, r_img) values ('cus2','찜·조림','낙지찜15','매콤함이 끝내주는 찜','양파(100g),콩나물(100g),파(100g),낙지(1마리),고춧가루(100g)','1,2,1,1,1','1.야채를 넣고 볶는다.<br>2.고춧가루를 넣고 볶는다.<br>3.낙지를 넣고 살짝 볶는다.', 'jjim.jpg');

create table if not exists foodbarcode(
   fb_id int auto_increment,
    fb_main varchar(20) not null unique,
    fb_middle varchar(20) not null unique,
    fb_sub varchar(20) not null unique,
    primary key(fb_id)    
)default charset=utf8mb4;

create table if not exists foodlist (
   f_id int auto_increment,
    f_category varchar(10) not null,
    f_name varchar(50) not null unique,
    f_price int,
    f_unit int,
    primary key(f_id)
)default charset=utf8mb4;
-- main 01(농산) 02(축산) 03(수산) 04(가공)

insert into foodlist (f_category, f_name, f_price, f_unit)values ('010201','양파(100g)', 1000, 1000);
insert into foodlist (f_category, f_name, f_price, f_unit)values ('010202','감자(100g)', 1500, 1000);
insert into foodlist (f_category, f_name, f_price, f_unit)values ('010203','당근(100g)', 1800, 1000);
insert into foodlist (f_category, f_name, f_price, f_unit)values ('010204','오이(100g)', 900, 1000);
insert into foodlist (f_category, f_name, f_price, f_unit)values ('040201','된장(100g)', 2000, 1000);
insert into foodlist (f_category, f_name, f_price, f_unit)values ('040101','김치(500g)', 8000, 1000);
insert into foodlist (f_category, f_name, f_price, f_unit)values ('040202','카레(100g)', 1200, 1000);
insert into foodlist (f_category, f_name, f_price, f_unit)values ('030101','북어(100g)', 4000, 1000);
insert into foodlist (f_category, f_name, f_price, f_unit)values ('040301','라멘(1인분)', 2500, 1000);
insert into foodlist (f_category, f_name, f_price, f_unit)values ('010207','파(100g)', 1500, 1000);
insert into foodlist (f_category, f_name, f_price, f_unit)values ('040210','고춧가루(100g)', 5000, 1000);

create table if not exists cusorder (
	o_num int auto_increment,
    o_date date,
	o_id varchar(20) not null,
	o_s_id varchar(20) not null,
    o_f_name varchar(30),
    o_f_img varchar(100),
    o_f_singname varchar(500),
    o_f_singprice varchar(500),
    o_f_singunit varchar(200),
    o_addr varchar(100),
    o_chk int default 0,
    primary key(o_num),
	foreign key(o_id) references customer(c_id),
	foreign key(o_s_id) references seller(s_id)
)default charset=utf8mb4;

create table if not exists notice (
   n_num int auto_increment,
    n_title varchar(50) not null,
    n_writer varchar(20) not null,
    n_content varchar(2000) not null,
    n_img varchar(400),
    n_date date,
    n_hit int default 0,
    primary key(n_num)
)default charset=utf8mb4;

create table if not exists bulletin (
   b_id int auto_increment,
    b_title varchar(50) not null,
    b_writer varchar(20) not null,
    b_content varchar(2000) not null,
    b_img varchar(400),
    b_date date,
    b_hit int default 0,
    primary key(b_id)
)default charset=utf8mb4;

create table if not exists one_qna (
   oq_id int auto_increment,
    oq_title varchar(50) not null,
    oq_writer varchar(20) not null,
    oq_content varchar(200) not null,
    oq_category varchar(30),
    oq_date date,
    oq_hit int default 0,
    oq_stat varchar(30) default '문의 등록',
    primary key(oq_id)
)default charset=utf8mb4;

create table if not exists faq (
   f_id int auto_increment,
    f_title varchar(50) not null,
    f_content varchar(200) not null,
    primary key(f_id)
)default charset=utf8mb4;

insert into faq (f_title, f_content) values ('자주하는 질문 1', '그에 대한 답변1');
insert into faq (f_title, f_content) values ('자주하는 질문 2', '그에 대한 답변2');
insert into faq (f_title, f_content) values ('자주하는 질문 3', '그에 대한 답변3');
insert into faq (f_title, f_content) values ('자주하는 질문 4', '그에 대한 답변4');
insert into faq (f_title, f_content) values ('자주하는 질문 5', '그에 대한 답변5');

create table if not exists r_review (
   r_id int auto_increment,
    r_title varchar(50) not null,
    r_writer varchar(20) not null,
    r_content varchar(200) not null,
    r_date date,
    r_img varchar(400),
    r_hit int default 0,
    r_like int default 0,
    primary key(r_id)
)default charset=utf8mb4;

create table if not exists likelist (
   l_num int auto_increment,
    c_id varchar(20) not null,
    r_id int not null,
    primary key(l_num),
    foreign key(c_id) references customer(c_id) on delete cascade,
    foreign key(r_id) references r_review(r_id) on delete cascade
)default charset=utf8mb4;

create table if not exists b_comment (
   bc_num int auto_increment,
    bc_name varchar(20),
    bc_id int,
    bc_writer varchar(20),
    bc_content varchar(1000),
    bc_date date,
    primary key(bc_num)
)default charset=utf8mb4;