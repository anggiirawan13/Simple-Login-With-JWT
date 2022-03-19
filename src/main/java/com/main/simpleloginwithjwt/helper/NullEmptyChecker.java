package com.main.simpleloginwithjwt.helper;

import java.util.AbstractMap;
import java.util.Collection;

public class NullEmptyChecker {

    public static boolean isNullOrEmpty(Object obj) {
        if (obj == null) {
            return true;
        } else {
            if (obj instanceof Collection) {
                if (((Collection) obj).isEmpty() || ((Collection) obj).size() < 1) {
                    return true;
                }
            } else if (obj instanceof AbstractMap) {
                if (((AbstractMap) obj).isEmpty() || ((AbstractMap) obj).size() < 0) {
                    return true;
                }
            } else if (obj.toString().trim().equals("")) {
                return true;
            }
        }

        return false;
    }

    public static boolean isNotNullOrEmpty(Object obj) {
        return !isNullOrEmpty(obj);
    }

}
