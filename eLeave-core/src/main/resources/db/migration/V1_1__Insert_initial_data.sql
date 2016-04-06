DROP PROCEDURE IF EXISTS eleavedb.createPrivilege;
DROP PROCEDURE IF EXISTS eleavedb.createUserRole;
DROP PROCEDURE IF EXISTS eleavedb.createUserRoleHasPrivilege;
DROP PROCEDURE IF EXISTS eleavedb.createUser;
DROP PROCEDURE IF EXISTS eleavedb.createUserHasRole;
DROP PROCEDURE IF EXISTS eleavedb.createLeaveType;
DROP PROCEDURE IF EXISTS eleavedb.createEmployee;
DROP PROCEDURE IF EXISTS eleavedb.createApprover;
DROP PROCEDURE IF EXISTS eleavedb.createAnnualBalanceLeave;
DROP PROCEDURE IF EXISTS eleavedb.createLeaveTaken;
DROP PROCEDURE IF EXISTS eleavedb.createHoliday;

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

create procedure eleavedb.createUserRoleHasPrivilege($user_role_id smallint, $privilege_name varchar(30))
begin
    declare _privilege_id int;
    select id from privilege where privilege_name = $privilege_name into _privilege_id;
    insert into user_role_privilege (user_role_id, privilege_id) values ($user_role_id, _privilege_id);
end //

create procedure eleavedb.createUser($name varchar(50), out $id int)
begin
    insert into user (version, active, user_name, password) values (1, true, $name, 'password');
    set $id := last_insert_id();
end //

create procedure eleavedb.createUserHasRole($user_id int, $role_id smallint)
begin
    insert into user_user_role (user_id, user_role_id) values ($user_id, $role_id);
end //

create procedure eleavedb.createLeaveType($leave_type_name varchar(50), $days smallint, out $id int)
begin
    insert into leave_type (version, leave_type_name, default_days_allowed, comment) values (1, $leave_type_name, $days, 'No comment');
    set $id := last_insert_id();
end //

create procedure eleavedb.createEmployee($email varchar(255), $first_name varchar(255), $last_name varchar(255), $user_id smallint, out $id int)
begin
    insert into employee (version, email, first_name, last_name, user_id) values (1, $email, $first_name, $last_name, $user_id);
    set $id := last_insert_id();
end //

create procedure eleavedb.createApprover($end_date datetime, $start_date datetime, $employee_id int, $approver_id int)
begin
    insert into approver(version, end_date, start_date, employee_id, approver_id) values (1, $end_date, $start_date, $employee_id, $approver_id);
end //

create procedure eleavedb.createAnnualBalanceLeave($leave_days_allowed int, $leave_days_remaining int, $validity_date datetime, $year int, $employee_id int, $leave_type_id int, out $id int)
begin
    insert into annual_balance_leave(version, leave_days_allowed, leave_days_remaining, validity_date, year, employee_id, leave_type_id)
                values(1, $leave_days_allowed, $leave_days_remaining, $validity_date, $year, $employee_id, $leave_type_id);
    set $id := last_insert_id();
end //

create procedure eleavedb.createTakenLeave($leave_days_taken int, $leave_from datetime, $comment varchar(255), $status_name varchar(255), $leave_to datetime, $annual_balance_leave_id int, $approver_id int)
begin
    insert into taken_leave(version, leave_days_taken, leave_from, comment, status_name, leave_to, annual_balance_leave_id, approver_id)
                values(1, $leave_days_taken, $leave_from, $comment, $status_name, $leave_to, $annual_balance_leave_id, $approver_id);
end //

create procedure eleavedb.createHoliday($name varchar(255), $comment varchar(255), $date datetime, $year int, $movable boolean)
begin
    insert into holiday(version, name, comment, date, year, movable)
                values(1, $name, $comment, $date, $year, $movable);
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

-- Create HR roles
call eleavedb.createUser('rita', @rita_user_id);
call eleavedb.createUserHasRole(@rita_user_id, @hr_role_id);

call eleavedb.createUser('beata', @beata_user_id);
call eleavedb.createUserHasRole(@beata_user_id, @hr_role_id);

-- Create Approver roles
call eleavedb.createUser('maciek', @maciek_user_id);
call eleavedb.createUserHasRole(@maciek_user_id, @approver_role_id);

call eleavedb.createUser('pawel', @pawel_user_id);
call eleavedb.createUserHasRole(@pawel_user_id, @approver_role_id);

-- Create User roles
call eleavedb.createUser('mariusz', @mariusz_user_id);
call eleavedb.createUserHasRole(@mariusz_user_id, @employee_role_id);

call eleavedb.createUser('sebastian', @seba_user_id);
call eleavedb.createUserHasRole(@seba_user_id, @employee_role_id);

call eleavedb.createUser('alex', @alex_user_id);
call eleavedb.createUserHasRole(@alex_user_id, @employee_role_id);

call eleavedb.createUser('liubomir', @liubomir_user_id);
call eleavedb.createUserHasRole(@liubomir_user_id, @employee_role_id);

-- Create employees
call eleavedb.createEmployee('admin@softserve.com','super','admin', @admin_user_id, @admin_employee_id);
call eleavedb.createEmployee('rita@softserve.com','rita','prockow', @rita_user_id, @rita_employee_id);
call eleavedb.createEmployee('beata@softserve.com','beata','kepska', @beata_user_id, @beata_employee_id);
call eleavedb.createEmployee('maciek@softserve.com','maciek','urynowicz', @maciek_user_id, @maciek_employee_id);
call eleavedb.createEmployee('pawel@softserve.com','pawel','lopatka', @pawel_user_id, @pawel_employee_id);
call eleavedb.createEmployee('mariusz@softserve.com','mariusz','danielewski', @mariusz_user_id, @mariusz_employee_id);
call eleavedb.createEmployee('sebastian@softserve.com','sebastian','szlachetka', @seba_user_id, @seba_employee_id);
call eleavedb.createEmployee('alex@softserve.com','alex','belugorov', @alex_user_id, @alex_employee_id);
call eleavedb.createEmployee('liubomir@softserve.com','liubomir','mir', @liubomir_user_id, @liubomir_employee_id);

-- Create approvers
call eleavedb.createApprover('2017-01-01', null, @mariusz_employee_id, @maciek_employee_id);
call eleavedb.createApprover('2017-01-01', null, @seba_employee_id, @maciek_employee_id);
call eleavedb.createApprover('2017-01-01', null, @alex_employee_id, @maciek_employee_id);

call eleavedb.createApprover('2017-01-01', null, @maciek_employee_id, @rita_employee_id);
call eleavedb.createApprover('2017-01-01', null, @rita_employee_id, @pawel_employee_id);

-- Create leave types
call eleavedb.createLeaveType('Annual holiday', 26, @annual_leave_type_id);
call eleavedb.createLeaveType('On demand', 4, @on_demand_leave_type_id);
call eleavedb.createLeaveType('Special holiday', 2, @special_holiday_leave_type_id);
call eleavedb.createLeaveType('Paid child care', 2, @paid_child_leave_type_id);
call eleavedb.createLeaveType('Unpaid holiday', 5, @unpaid_holiday_leave_type_id);
call eleavedb.createLeaveType('Blood donation', 1, @blood_donation_leave_type_id);
call eleavedb.createLeaveType('Justified paid absence', 1, @justified_paid_absence_leave_type_id);
call eleavedb.createLeaveType('Justified unpaid absence', 1, @justified_unpaid_absence_leave_type_id);
call eleavedb.createLeaveType('Overtime', 1, @overtime_leave_type_id);
call eleavedb.createLeaveType('Other', 1, @other_leave_type_id);

-- Create annual balance leaves
call eleavedb.createAnnualBalanceLeave (26, 20, '2016-12-12', 2016, @mariusz_employee_id, @annual_leave_type_id, @mariusz_annual_leave);
call eleavedb.createAnnualBalanceLeave (2, 2, '2016-12-12', 2016, @mariusz_employee_id, @special_holiday_leave_type_id, @mariusz_special_holiday_annual_leave);
call eleavedb.createAnnualBalanceLeave (2, 0, '2016-12-12', 2016, @mariusz_employee_id, @paid_child_leave_type_id, @mariusz_paid_child_care_annual_leave);

call eleavedb.createAnnualBalanceLeave (26, 20, '2015-12-12', 2015, @seba_employee_id, @annual_leave_type_id, @seba_annual_leave_2015);
call eleavedb.createAnnualBalanceLeave (26, 26, '2016-12-12', 2016, @seba_employee_id, @annual_leave_type_id, @seba_annual_leave_2016);

-- Create taken leaves
call eleavedb.createTakenLeave(1, '2016-01-01', 'some holiday', 'REJECTED', '2016-01-01', @mariusz_annual_leave, @pawel_employee_id);
call eleavedb.createTakenLeave(1, '2016-01-01', 'some holiday', 'APPROVED', '2016-01-01', @mariusz_annual_leave, @maciek_employee_id);
call eleavedb.createTakenLeave(5, '2016-02-02', 'some holiday', 'APPROVED', '2016-02-06', @mariusz_annual_leave, @maciek_employee_id);
call eleavedb.createTakenLeave(2, '2016-02-02', 'some holiday', 'APPROVED', '2016-02-03', @mariusz_paid_child_care_annual_leave, @maciek_employee_id);

call eleavedb.createTakenLeave(6, '2016-02-02', 'some holiday', 'APPROVED', '2016-02-07', @seba_annual_leave_2015, @maciek_employee_id);

-- Create holiday
call eleavedb.createHoliday('Three kings', 'This is church holiday', '2016-01-06', 2016, false);
call eleavedb.createHoliday('Wielki Poniedzialek', 'Nie wiem jak to jest po angielsku', '2016-03-26', 2016, true);
call eleavedb.createHoliday('Work holiday', 'This is comunism holiday :)', '2016-05-01', 2016, false);
call eleavedb.createHoliday('Polish consitution', 'National holiday', '2016-05-03', 2016, false);
call eleavedb.createHoliday('Hallowen', 'Holiday of dead people', '2016-11-01', 2016, false);


DROP PROCEDURE IF EXISTS eleavedb.createPrivilege;
DROP PROCEDURE IF EXISTS eleavedb.createUserRole;
DROP PROCEDURE IF EXISTS eleavedb.createUserRoleHasPrivilege;
DROP PROCEDURE IF EXISTS eleavedb.createUser;
DROP PROCEDURE IF EXISTS eleavedb.createUserHasRole;
DROP PROCEDURE IF EXISTS eleavedb.createLeaveType;
DROP PROCEDURE IF EXISTS eleavedb.createEmployee;
DROP PROCEDURE IF EXISTS eleavedb.createApprover;
DROP PROCEDURE IF EXISTS eleavedb.createAnnualBalanceLeave;
DROP PROCEDURE IF EXISTS eleavedb.createTakenLeave;
DROP PROCEDURE IF EXISTS eleavedb.createHoliday;
