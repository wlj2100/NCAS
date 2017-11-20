drop procedure if exists withdraw;

delimiter //
create procedure withdraw(
in code char(8), 
in id int(8), 
in currentyear int(8), 
in currentquarter char(2))

begin
	declare errorNo int default 0;
    declare exit handler for sqlexception set errorNo = 1;
	declare exit handler for sqlwarning set errorNo = 1;
    START TRANSACTION;
	delete from transcript where StudId = id and UoSCode = code and Year = currentyear and semester = currentquarter;
    update uosoffering set Enrollment = Enrollment - 1 where UoSCode = code and Year = currentyear and semester = currentquarter;
    if errorNo = 1 then
		rollback;
	else
		commit;
end if;
end; //
delimiter ;

drop procedure if exists enroll;

delimiter $$
create procedure enroll(
in ID int, 
in courseID char(20), 
in c_year int, 
in semester char(2))
BEGIN

declare result int default -1;

declare errorNo int default 0;
declare exit handler for sqlexception set errorNo = 1;
declare exit handler for sqlwarning set errorNo = 1;
START TRANSACTION;
if courseId in (select UoSCode 
				from transcript
				where StudId = ID and Semester = semester) then
		set result = 1;
-- if the student is already enrolled in the course
elseif exists(select *
			from uosoffering
            where UosCode = courseID and Enrollment = MaxEnrollment) then
		set result = 2;
-- if class is already full
elseif exists(select uosoffering.UoSCode, PrereqUoSCode
			from uosoffering join requires
			where Year = c_year and Semester = semester
				and uosoffering.UoSCode = courseID
				and PrereqUoSCode not in (select UoSCode
												from transcript
												where StudId = ID)
			group by PrereqUoSCode) then
	set result = 3;
-- if prerequist is not met
else
	set result = 0;
    -- select result;
    insert into transcript
	value(ID,courseID,semester,c_year,NULL);
	update uosoffering
	set new.Enrollment = old.Enrollment + 1
	where UoSCode = courseID and Semester = semester and Year = c_year;
-- ennroll successfull
end if;
select result;
if errorNo = 1 then
	select result;
	rollback;
else
	commit;
end if;

END$$
delimiter ;

create table belowMaxEnrollment (UoSCode char(8), Semester char(2), Year int(11));

delimiter //
create procedure initMaxEnroll()
begin
    DECLARE coursecode char(8);
    declare cyear int(11);
    declare cquarter char(2);
    declare maxenroll int(11);
    declare enroll int(11);
    DECLARE done int(1);
    DECLARE c CURSOR FOR (select UoSCode, Semester, Year, Enrollment, MaxEnrollment from uosoffering);
    DECLARE  continue handler FOR SQLSTATE '02000' SET done = 1;
    open c;
    rloop:loop
        fetch c into coursecode, cquarter, cyear, enroll, maxenroll;
        IF done = 1 THEN 
            LEAVE rloop; 
        END IF;
        if  enroll < 0.5*maxenroll then
            insert into belowMaxEnrollment(UoSCode, Semester, Year) values (coursecode, cquarter, cyear);
        end if;
    end loop;
    close c;
end; //
delimiter ;
call initMaxEnroll();

delimiter //
create trigger enrollment_num_insertcheck after insert on uosoffering
for each row
begin
    if new.Enrollment < 0.5*new.MaxEnrollment then
        if not exists (select * from belowMaxEnrollment B where B.UoSCode = new.UoSCode and B.Year = new.Year and B.Semester = new.Semester) then
            insert into belowMaxEnrollment(UoSCode, Semester, Year) values (new.UoSCode, new.Semester, new.Year);
        end if;
    end if;
    if new.Enrollment >= 0.5*new.MaxEnrollment then
        if exists (select * from belowMaxEnrollment B where B.UoSCode = new.UoSCode and B.Year = new.Year and B.Semester = new.Semester) then
            delete from belowMaxEnrollment where UoSCode = new.UoSCode and Year = new.Year and Semester = new.Semester;
        end if;
    end if;
end; //
delimiter ;
delimiter //
create trigger enrollment_num_updatecheck after update on uosoffering
for each row
begin
    if new.Enrollment < 0.5*new.MaxEnrollment then
        if not exists (select * from belowMaxEnrollment B where B.UoSCode = new.UoSCode and B.Year = new.Year and B.Semester = new.Semester) then
            insert into belowMaxEnrollment(UoSCode, Semester, Year) values (new.UoSCode, new.Semester, new.Year);
        end if;
    end if;
    if new.Enrollment >= 0.5*new.MaxEnrollment then
        if exists (select * from belowMaxEnrollment B where B.UoSCode = new.UoSCode and B.Year = new.Year and B.Semester = new.Semester) then
            delete from belowMaxEnrollment where UoSCode = new.UoSCode and Year = new.Year and Semester = new.Semester;
        end if;
    end if;
end; //
delimiter ;

