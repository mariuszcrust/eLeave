-- DROP eLeave tables 
DROP PROCEDURE IF EXISTS eleavedb.createPrivilege;
DROP PROCEDURE IF EXISTS eleavedb.createUserRole;
DROP PROCEDURE IF EXISTS eleavedb.createUserRoleHasPrivilege;
DROP PROCEDURE IF EXISTS eleavedb.createUser;
DROP PROCEDURE IF EXISTS eleavedb.createUserHasRole;
DROP PROCEDURE IF EXISTS eleavedb.createLeaveType;

SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS eleavedb.approver;
DROP TABLE IF EXISTS eleavedb.employee;
DROP TABLE IF EXISTS eleavedb.user_role;
DROP TABLE IF EXISTS eleavedb.annual_balance_leave;
DROP TABLE IF EXISTS eleavedb.taken_leave;
DROP TABLE IF EXISTS eleavedb.leave_type;
DROP TABLE IF EXISTS eleavedb.user_role_privilege;
DROP TABLE IF EXISTS eleavedb.privilege;
DROP TABLE IF EXISTS eleavedb.user;
DROP TABLE IF EXISTS eleavedb.user_user_role;
DROP TABLE IF EXISTS eleavedb.schema_version;
SET FOREIGN_KEY_CHECKS = 1;