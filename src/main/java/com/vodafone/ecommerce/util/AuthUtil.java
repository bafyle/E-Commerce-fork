package com.vodafone.ecommerce.util;

import com.vodafone.ecommerce.exception.NotAuthorizedException;

public class AuthUtil {
    public static void isNotLoggedInUserThrowException(Long userId, Long loggedInUserId) {
        if (!userId.equals(loggedInUserId)) {
            throw new NotAuthorizedException();
        }
    }

    public static void isLoggedInUserThrowException(Long userId, Long loggedInUserId) {
        if (userId.equals(loggedInUserId)) {
            throw new NotAuthorizedException();
        }
    }
}
