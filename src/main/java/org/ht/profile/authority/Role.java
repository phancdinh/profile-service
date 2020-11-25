package org.ht.profile.authority;

public interface Role {
    interface BasicInfo {
        String MANAGE = "hasRole('PROFILE_MANAGE')";
        String VIEW = "hasAnyRole('PROFILE_ACCESS', 'PROFILE_MANAGE')";
    }

    interface DemoGraphics {
        String MANAGE = "hasRole('PROFILE_MANAGE')";
        String VIEW = "hasAnyRole('PROFILE_ACCESS', 'PROFILE_MANAGE')";
    }

    interface ContactInfo {
        String MANAGE = "hasRole('PROFILE_MANAGE')";
        String VIEW = "hasAnyRole('PROFILE_ACCESS', 'PROFILE_MANAGE')";
    }

    interface LegalInfo {
        String MANAGE = "hasRole('PROFILE_MANAGE')";
        String VIEW = "hasAnyRole('PROFILE_ACCESS', 'PROFILE_MANAGE')";
    }
}
