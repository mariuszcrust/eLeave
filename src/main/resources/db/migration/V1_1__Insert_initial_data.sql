DROP PROCEDURE IF EXISTS eleavedb.createPrivilege;
DROP PROCEDURE IF EXISTS eleavedb.createUserRole;
DROP PROCEDURE IF EXISTS eleavedb.createUserRoleHasPrivilege;
DROP PROCEDURE IF EXISTS eleavedb.createUser;
DROP PROCEDURE IF EXISTS eleavedb.createUserHasRole;

delimiter //
create procedure eleavedb.createPrivilege ($name varchar(30))
begin
    insert into privilege (version, privilege_name) values (1, $name);
end //

create procedure eleavedb.createUserRole ($name varchar(30), $comment varchar(255), out $id int)
begin
    insert into user_role (version, role_name, comment) values (1, $name, $comment);
    set $id := last_insert_id();
end //
delimiter ;

create procedure createUserRoleHasPrivilege($employee_role_id smallint, $privilege_name varchar(30))
begin
    declare _privilege_id int;
    select id from privilege where privilege_name = $privilege_name into _privilege_id;
    insert into user_role_privilege (employee_role_id, privilege_id) values ($employee_role_id, _privilege_id);
end //

create procedure createUser($name varchar(50), out $id int)
begin
    insert into user (version, user_name, password) values (1, $name, 'password');
    set $id := last_insert_id();
end //

create procedure createUserHasRole($user_id int, $role_id smallint)
begin
    insert into user_user_role (user_id, role_id) values ($user_id, $role_id);
end //

delimiter ;

-- Create privileges (for details please see com.company.eleave.security.entity.Privilege)
call eleavedb.createPrivilege('REQUEST_LEAVE');
call eleavedb.createPrivilege('APPROVED_LEAVE');
call eleavedb.createPrivilege('ADD_EMPLOYEE');
call eleavedb.createPrivilege('ADD_LEAVE_TYPE');
call eleavedb.createPrivilege('REMOVE_LEAVE_TYPE');
call eleavedb.createPrivilege('REMOVE_EMPLOYEE');

-- Create user roles (for details please see com.company.eleave.security.entity.UserRole)
call eleavedb.createUserRole('SUPER_USER', 'Designed for administrative purposes. Do not assign for any employee!', @super_user_role_id);
call eleavedb.createUserRoleHasPrivilege(@super_user_role_id, 'APPROVED_LEAVE');
call eleavedb.createUserRoleHasPrivilege(@super_user_role_id, 'ADD_EMPLOYEE');
call eleavedb.createUserRoleHasPrivilege(@super_user_role_id, 'ADD_LEAVE_TYPE');
call eleavedb.createUserRoleHasPrivilege(@super_user_role_id, 'REMOVE_LEAVE_TYPE');
call eleavedb.createUserRoleHasPrivilege(@super_user_role_id, 'REMOVE_EMPLOYEE');

call eleavedb.createUserRole('HR', 'For employees from Human Resources department.', @hr_role_id);
call eleavedb.createUserRoleHasPrivilege(@hr_role_id, 'REQUEST_LEAVE');
call eleavedb.createUserRoleHasPrivilege(@hr_role_id, 'APPROVED_LEAVE');
call eleavedb.createUserRoleHasPrivilege(@hr_role_id, 'ADD_EMPLOYEE');
call eleavedb.createUserRoleHasPrivilege(@hr_role_id, 'ADD_LEAVE_TYPE');

call eleavedb.createUserRole('APPROVER', 'For employees who can act as approver.', @approver_role_id);
call eleavedb.createUserRoleHasPrivilege(@approver_role_id, 'APPROVED_LEAVE');
call eleavedb.createUserRoleHasPrivilege(@approver_role_id, 'REQUEST_LEAVE');

call eleavedb.createUserRole('EMPLOYEE', 'Simply for employees.', @employee_role_id);
call eleavedb.createUserRoleHasPrivilege(@employee_role_id, 'REQUEST_LEAVE');

-- Create admin user
call eleavedb.createUser('admin', @admin_user_id);
call eleavedb.createUserHasRole(@admin_user_id, @super_user_role_id);

DROP PROCEDURE IF EXISTS eleavedb.createPrivilege;
DROP PROCEDURE IF EXISTS eleavedb.createUserRole;
DROP PROCEDURE IF EXISTS eleavedb.createUserRoleHasPrivilege;
DROP PROCEDURE IF EXISTS eleavedb.createUser;
DROP PROCEDURE IF EXISTS eleavedb.createUserHasRole;
