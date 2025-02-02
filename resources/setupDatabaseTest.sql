delete from ticket;

delete from parking; 

insert into parking(PARKING_NUMBER,AVAILABLE,TYPE) values(1,true,'CAR');
insert into parking(PARKING_NUMBER,AVAILABLE,TYPE) values(2,false,'CAR');
insert into parking(PARKING_NUMBER,AVAILABLE,TYPE) values(3,true,'CAR');
insert into parking(PARKING_NUMBER,AVAILABLE,TYPE) values(4,true,'BIKE');
insert into parking(PARKING_NUMBER,AVAILABLE,TYPE) values(5,true,'BIKE');
insert into parking(PARKING_NUMBER,AVAILABLE,TYPE) values(6,true,'CAR');
insert into ticket(parking_number ,vehicle_reg_number,in_time) values (2, 'IMMAT_INIT','20250117');

select * from ticket;

select * from parking;