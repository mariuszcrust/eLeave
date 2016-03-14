/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.text.MessageFormat;

public class RestURI {
    
    public static final String GET_ALL_EMPLOYEES = "/employees";
    
    public static final String GET_EMPLOYEE_BY_ID = "/employees/{0}";
    
    public static String request(String pattern, Object... arguments) {
        return MessageFormat.format(pattern, arguments);
    }
}
