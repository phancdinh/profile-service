package org.ht.profile.authority;

public class Role {
    public class BasicInfo {
        public static final String MANAGE = "hasRole('PROFILE_MANAGE')";
        public static final String VIEW = "hasAnyRole('PROFILE_ACCESS', 'PROFILE_MANAGE')";
    }
}