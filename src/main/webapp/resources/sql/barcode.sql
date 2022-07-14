create table  foodbarcode(
	fb_id int auto_increment,
    fb_main varchar(20) not null,
    fb_middle varchar(20) not null,
    fb_sub varchar(20) not null unique,
    primary key(fb_id)    
)default charset=utf8mb4;



insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('01(농산)', '01(곡류)','01(쌀)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('01(농산)', '01(곡류)','02(보리)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('01(농산)', '01(곡류)','03(현미)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('01(농산)', '01(곡류)','04(대두)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('01(농산)', '01(곡류)','05(검은콩)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('01(농산)', '01(곡류)','06(작두콩)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('01(농산)', '01(곡류)','07(잡곡)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('01(농산)', '01(곡류)','08(찹쌀)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('01(농산)', '01(곡류)','09(기타 곡류)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('01(농산)', '02(채소류)','01(양파)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('01(농산)', '02(채소류)','02(감자)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('01(농산)', '02(채소류)','03(당근)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('01(농산)', '02(채소류)','04(오이)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('01(농산)', '02(채소류)','05(배추)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('01(농산)', '02(채소류)','06(대파)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('01(농산)', '02(채소류)','07(파)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('01(농산)', '02(채소류)','08(무)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('01(농산)', '02(채소류)','09(쌈채소)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('01(농산)', '02(채소류)','10(고추)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('01(농산)', '02(채소류)','11(마늘)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('01(농산)', '02(채소류)','12(우엉)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('01(농산)', '02(채소류)','13(콩나물)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('01(농산)', '02(채소류)','14(양배추)');

insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('02(축산)', '01(돼지고기)','01(삼겹살)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('02(축산)', '01(돼지고기)','02(목살)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('02(축산)', '01(돼지고기)','03(등심)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('02(축산)', '01(돼지고기)','04(안심)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('02(축산)', '01(돼지고기)','05(앞다리살)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('02(축산)', '01(돼지고기)','06(뒷다리살)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('02(축산)', '01(돼지고기)','07(기타잡육)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('02(축산)', '02(소고기)','01(등심)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('02(축산)', '02(소고기)','02(안심)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('02(축산)', '02(소고기)','03(갈비)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('02(축산)', '02(소고기)','04(차돌박이)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('02(축산)', '02(소고기)','05(사골)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('02(축산)', '02(소고기)','06(특수부위)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('02(축산)', '03(닭고기)','01(닭)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('02(축산)', '03(닭고기)','02(닭다리)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('02(축산)', '03(닭고기)','03(닭날개)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('02(축산)', '03(닭고기)','04(닭목)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('02(축산)', '03(닭고기)','05(닭내장)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('02(축산)', '03(닭고기)','06(기타잡육)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('02(축산)', '04(기타육류)','01(양고기)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('02(축산)', '04(기타육류)','02(오리고기)');

insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('03(수산)', '01(생선류)','01(북어)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('03(수산)', '01(생선류)','02(갈치)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('03(수산)', '01(생선류)','03(꽁치)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('03(수산)', '01(생선류)','04(전갱이)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('03(수산)', '01(생선류)','05(연어)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('03(수산)', '01(생선류)','06(돔)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('03(수산)', '01(생선류)','07(대구)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('03(수산)', '01(생선류)','08(아구)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('03(수산)', '01(생선류)','09(장어)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('03(수산)', '01(생선류)','10(노래미)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('03(수산)', '01(생선류)','11(고등어)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('03(수산)', '01(생선류)','12(기타 생선류)');

insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('03(수산)', '02(패류)','01(바지락)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('03(수산)', '02(패류)','02(가리비)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('03(수산)', '02(패류)','03(홍합)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('03(수산)', '02(패류)','04(키조개)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('03(수산)', '02(패류)','05(백합조개)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('03(수산)', '02(패류)','06(동죽)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('03(수산)', '02(패류)','07(개조개)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('03(수산)', '02(패류)','08(굴)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('03(수산)', '02(패류)','09(전복)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('03(수산)', '02(패류)','10(기타 패류)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('03(수산)', '03(해산물)','01(멍게)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('03(수산)', '03(해산물)','02(해삼)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('03(수산)', '03(해산물)','03(성게)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('03(수산)', '03(해산물)','04(개불)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('03(수산)', '03(해산물)','05(기타)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('03(수산)', '04(해조류)','01(미역)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('03(수산)', '04(해조류)','02(다시마)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('03(수산)', '04(해조류)','03(우뭇가사리)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('03(수산)', '04(해조류)','04(김)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('03(수산)', '04(해조류)','05(기타 해조류)');

insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('04(가공)', '01(김치류)','01(배추김치)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('04(가공)', '01(김치류)','02(총각김치)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('04(가공)', '01(김치류)','03(깍두기)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('04(가공)', '01(김치류)','04(물김치)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('04(가공)', '01(김치류)','05(백김치)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('04(가공)', '01(김치류)','06(기타김치)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('04(가공)', '02(양념류)','01(된장)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('04(가공)', '02(양념류)','02(카레)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('04(가공)', '02(양념류)','03(고추장)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('04(가공)', '02(양념류)','04(간장)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('04(가공)', '02(양념류)','05(식초)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('04(가공)', '02(양념류)','06(마요네즈)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('04(가공)', '02(양념류)','07(케첩)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('04(가공)', '02(양념류)','08(설탕)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('04(가공)', '02(양념류)','09(소금)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('04(가공)', '02(양념류)','10(기타 가공류)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('04(가공)', '03(면류)','01(라면)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('04(가공)', '03(면류)','02(국수)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('04(가공)', '03(면류)','03(파스타)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('04(가공)', '03(면류)','04(당면)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('04(가공)', '03(면류)','05(기타 면류)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('04(가공)', '04(통조림)','01(프레스햄)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('04(가공)', '04(통조림)','02(참치캔)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('04(가공)', '04(통조림)','03(꽁치통조림)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('04(가공)', '04(통조림)','04(고등어통조림)');
insert into foodbarcode(fb_main, fb_middle, fb_sub) values ('04(가공)', '04(통조림)','05(기타통조림)');

select * from foodbarcode;
