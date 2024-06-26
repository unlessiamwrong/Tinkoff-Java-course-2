/*
 * This file is generated by jOOQ.
 */

package edu.java.domain.jooq;

import edu.java.domain.jooq.tables.Links;
import edu.java.domain.jooq.tables.UserLinks;
import edu.java.domain.jooq.tables.Users;
import edu.java.domain.jooq.tables.records.LinksRecord;
import edu.java.domain.jooq.tables.records.UserLinksRecord;
import edu.java.domain.jooq.tables.records.UsersRecord;
import javax.annotation.processing.Generated;
import org.jooq.ForeignKey;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;

/**
 * A class modelling foreign key relationships and constraints of tables in the
 * default schema.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.18.13"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes", "this-escape"})
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<LinksRecord> CONSTRAINT_45 =
        Internal.createUniqueKey(Links.LINKS, DSL.name("CONSTRAINT_45"), new TableField[] {Links.LINKS.NAME}, true);
    public static final UniqueKey<LinksRecord> CONSTRAINT_451 =
        Internal.createUniqueKey(Links.LINKS, DSL.name("CONSTRAINT_451"), new TableField[] {Links.LINKS.ID}, true);
    public static final UniqueKey<UserLinksRecord> CONSTRAINT_C67 = Internal.createUniqueKey(
        UserLinks.USER_LINKS,
        DSL.name("CONSTRAINT_C67"),
        new TableField[] {UserLinks.USER_LINKS.USER_ID, UserLinks.USER_LINKS.LINK_ID},
        true
    );
    public static final UniqueKey<UsersRecord> CONSTRAINT_4 =
        Internal.createUniqueKey(Users.USERS, DSL.name("CONSTRAINT_4"), new TableField[] {Users.USERS.ID}, true);

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<UserLinksRecord, UsersRecord> CONSTRAINT_C =
        Internal.createForeignKey(
            UserLinks.USER_LINKS,
            DSL.name("CONSTRAINT_C"),
            new TableField[] {UserLinks.USER_LINKS.USER_ID},
            Keys.CONSTRAINT_4,
            new TableField[] {Users.USERS.ID},
            true
        );
    public static final ForeignKey<UserLinksRecord, LinksRecord> CONSTRAINT_C6 =
        Internal.createForeignKey(
            UserLinks.USER_LINKS,
            DSL.name("CONSTRAINT_C6"),
            new TableField[] {UserLinks.USER_LINKS.LINK_ID},
            Keys.CONSTRAINT_451,
            new TableField[] {Links.LINKS.ID},
            true
        );
}
