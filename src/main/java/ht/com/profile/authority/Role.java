package ht.com.profile.authority;

public class Role {
    public class Profile{
        public static final String MANAGE = "hasAuthority('PROFILE_MANAGE')";
        public static final String VIEW = "hasAnyRole('PROFILE_ACCESS', 'PROFILE_MANAGE')";
    }
}