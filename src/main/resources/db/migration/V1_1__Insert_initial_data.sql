DROP PROCEDURE IF EXISTS eleavedb.createPrivilege;
DROP PROCEDURE IF EXISTS eleavedb.createEmployeeRole;
DROP PROCEDURE IF EXISTS eleavedb.createEmployeeRoleHasPrivilege;

delimiter //
create procedure eleavedb.createPrivilege ($name varchar(30))
begin
    insert into privilege (version, privilege_name) values (1, $name);
end //


delimiter //
create procedure eleavedb.createEmployeeRole ($name varchar(30), $comment varchar(255), out $id int)
begin
    insert into employee_role (version, role_name, comment) values (1, $name, $comment);
    set $id := last_insert_id();
end //
delimiter ;

create procedure createEmployeeRoleHasPrivilege($employee_role_id smallint, $privilege_name varchar(30))
begin
    declare _privilege_id int;
    select id from privilege where privilege_name = $privilege_name into _privilege_id;
    insert into employee_role_privilege (employee_role_id, privilege_id) values ($employee_role_id, _privilege_id);
end //

-- Create privileges (for details please see com.company.eleave.employee.entity.Privilege)
call eleavedb.createPrivilege('READ');
call eleavedb.createPrivilege('WRITE');
call eleavedb.createPrivilege('DELETE');

-- Create employee roles
call eleavedb.createEmployeeRole('SUPER_USER', 'Designed for administrative purposes. Do not assign for any employee!', @super_user_role_id);
call eleavedb.createEmployeeRoleHasPrivilege(@super_user_role_id, 'READ');
call eleavedb.createEmployeeRoleHasPrivilege(@super_user_role_id, 'WRITE');
call eleavedb.createEmployeeRoleHasPrivilege(@super_user_role_id, 'DELETE');

call eleavedb.createEmployeeRole('HR', 'For employees from Human Resources department.', @hr_role_id);
call eleavedb.createEmployeeRoleHasPrivilege(@hr_role_id, 'READ');
call eleavedb.createEmployeeRoleHasPrivilege(@hr_role_id, 'WRITE');

call eleavedb.createEmployeeRole('DIRECTOR', 'For employees at the director position.', @director_role_id);
call eleavedb.createEmployeeRoleHasPrivilege(@director_role_id, 'READ');


call eleavedb.createEmployeeRole('MANAGER', 'For employees at the manager position.', @manager_role_id);
call eleavedb.createEmployeeRoleHasPrivilege(@manager_role_id, 'READ');

call eleavedb.createEmployeeRole('ENGINEER', 'For strictly technical employees.', @engineer_role_id);
call eleavedb.createEmployeeRoleHasPrivilege(@engineer_role_id, 'READ');

DROP PROCEDURE IF EXISTS eleavedb.createPrivilege;
DROP PROCEDURE IF EXISTS eleavedb.createEmployeeRole;
DROP PROCEDURE IF EXISTS eleavedb.createEmployeeRoleHasPrivilege;
