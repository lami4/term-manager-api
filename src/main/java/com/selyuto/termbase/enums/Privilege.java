package com.selyuto.termbase.enums;

public enum Privilege {
    USER_MANAGER(1L), TERM_MANAGER(2L), TERM_GRID_MANAGER(3L), SUGGESTION_MANAGER(4L);

    public final long id;

    Privilege(long id) {
        this.id = id;
    }
}