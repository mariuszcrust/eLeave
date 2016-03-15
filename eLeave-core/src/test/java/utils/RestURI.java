/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.text.MessageFormat;

public class RestURI {
    
    public static final String EMPLOYEES = "/employees";
    
    public static final String EMPLOYEE_WITH_ID = "/employees/{0}";
    
    public static final String EMPLOYEE_ASSIGN_APPROVER = "/employees/{0}/approver";
    
    public static String request(String pattern, Object... arguments) {
        return MessageFormat.format(pattern, arguments);
    }
}
