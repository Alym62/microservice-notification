package org.ms.email.enums;

public enum StatusEmail {
    SENT("Enviado"),
    ERROR("Erro");

    private final String description;

    StatusEmail(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
