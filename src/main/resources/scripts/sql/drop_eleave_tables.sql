-- DROP eLeave tables 
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS eleavedb.approver;
DROP TABLE IF EXISTS eleavedb.employee;
DROP TABLE IF EXISTS eleavedb.role;
DROP TABLE IF EXISTS eleavedb.annual_balance_leave;
DROP TABLE IF EXISTS eleavedb.taken_leave;
DROP TABLE IF EXISTS eleavedb.leave_type;
DROP TABLE IF EXISTS eleavedb.schema_version;
SET FOREIGN_KEY_CHECKS = 1;