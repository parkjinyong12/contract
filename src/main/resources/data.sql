CREATE TABLE PRODUCT(
  PRODUCT_NUMBER varchar(50),
  PRODUCT_NAME varchar(50),
  PRODUCT_USE_YN boolean
);
CREATE TABLE SECURITY(
  SECURITY_NUMBER varchar(50),
  SECURITY_NAME varchar(50),
  PRODUCT_NUMBER varchar(50)
);

insert into PRODUCT values ('P00001', '여행자보험', true);
insert into SECURITY values ('S00001', '상해치료비', 'P00001');
insert into SECURITY values ('S00002', '항공기 지연도착시 보상금', 'P00001');