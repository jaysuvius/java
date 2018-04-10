/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plannerapp.validation;

import static java.util.Objects.isNull;
import java.util.Optional;
import plannerapp.exception.ValidationException;

/**
 *
 * @author Dad
 */
public class Conditions {
    public static boolean isEmpty(String s) {
        if (isNull(s)) return true;
        return s.length() == 0;
    }
    public static void requireNotEmpty(String s, String msg) {
        if (isEmpty(s))
            throw new ValidationException(msg); // todo;
    }
    public static boolean isInRange(int value, int lowerInclusive, int upperExclusive) {
        return value >= lowerInclusive && value < upperExclusive;
    }
    public static void requireInRange(int value, int lowerInclusive, int upperExclusive, String msg) {
        if (!isInRange(value, lowerInclusive, upperExclusive))
            throw new ValidationException(msg);
    }
    public static boolean isObjectNull(Object object){
        if (object instanceof Optional){
            return !((Optional)object).isPresent();
        }
        return object == null;
    }
    public static boolean isDate(Object object){
        return object != null;
    }
}
