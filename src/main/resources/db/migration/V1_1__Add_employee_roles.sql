DROP PROCEDURE IF EXISTS eleavedb.createEmployeeRole;

delimiter //
create procedure eleavedb.createEmployeeRole ($name varchar(30), $comment varchar(255))
begin
    insert into role (version, role_name, comment) values (1, $name, $comment);
end //
delimiter ;

-- Create employee roles
call eleavedb.createEmployeeRole('SUPER_USER', 'Designed for administrative purposes. Do not assign for any employee!');
call eleavedb.createEmployeeRole('HR', 'For employees from Human Resources department.');
call eleavedb.createEmployeeRole('DIRECTOR', 'For employees at the director position.');
call eleavedb.createEmployeeRole('MANAGER', 'For employees at the manager position.');
call eleavedb.createEmployeeRole('ENGINEER', 'For strictly technical employees.');

DROP PROCEDURE IF EXISTS eleavedb.createEmployeeRole;
