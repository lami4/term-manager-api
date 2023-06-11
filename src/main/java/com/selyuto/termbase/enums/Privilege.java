package com.selyuto.termbase.enums;

public enum Privilege {
    USER_MANAGER(1L), TERM_MANAGER(2L), TERMBASE_MANAGER(3L);

    public final long id;

    Privilege(long id) {
        this.id = id;
    }
}