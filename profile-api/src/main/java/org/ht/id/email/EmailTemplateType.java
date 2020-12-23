package org.ht.id.email;

public enum EmailTemplateType {
    ACTIVATION("activation-template"),
    RESET_PASSWORD("reset-password-template"),
    EMAIL_VERIFICATION("email-verification-template");

    private String name;

    EmailTemplateType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
