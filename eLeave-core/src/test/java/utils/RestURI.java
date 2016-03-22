/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

public class RestURI {

    public static final String EMPLOYEES = "/employees";

    public static final String EMPLOYEE_BY_ID = EMPLOYEES + "/{0}";

    public static final String EMPLOYEE_ASSIGN_APPROVER = EMPLOYEES + "/{0}/approver";

    public static final String EMPLOYEE_REASSIGN_APPROVER = EMPLOYEES + "/{0}/approver/{1}";

    public static final String ANNUAL_BALANCE_LEAVES = "/annualBalanceLeaves";

    public static final String ANNUAL_BALANCE_LEAVES_BY_EMPLOYEE = ANNUAL_BALANCE_LEAVES + "/employee/{0}";

}
