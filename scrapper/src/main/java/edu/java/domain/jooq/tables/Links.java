/*
 * This file is generated by jOOQ.
 */

package edu.java.domain.jooq.tables;

import edu.java.domain.jooq.DefaultSchema;
import edu.java.domain.jooq.Keys;
import edu.java.domain.jooq.tables.records.LinksRecord;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Record;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import javax.annotation.processing.Generated;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.18.13"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes", "this-escape"})
public class Links extends TableImpl<LinksRecord> {

    /**
     * The reference instance of <code>LINKS</code>
     */
    public static final Links LINKS = new Links();
    private static final long serialVersionUID = 1L;
    /**
     * The column <code>LINKS.ID</code>.
     */
    public final TableField<LinksRecord, Long> ID =
        createField(DSL.name("ID"), SQLDataType.BIGINT.nullable(false).identity(true), this, "");
    /**
     * The column <code>LINKS.NAME</code>.
     */
    public final TableField<LinksRecord, String> NAME =
        createField(DSL.name("NAME"), SQLDataType.VARCHAR(200).nullable(false), this, "");
    /**
     * The column <code>LINKS.LAST_UPDATE</code>.
     */
    public final TableField<LinksRecord, OffsetDateTime> LAST_UPDATE =
        createField(DSL.name("LAST_UPDATE"), SQLDataType.TIMESTAMPWITHTIMEZONE(6).nullable(false), this, "");
    /**
     * The column <code>LINKS.LAST_CHECK_FOR_UPDATE</code>.
     */
    public final TableField<LinksRecord, OffsetDateTime> LAST_CHECK_FOR_UPDATE =
        createField(DSL.name("LAST_CHECK_FOR_UPDATE"), SQLDataType.TIMESTAMPWITHTIMEZONE(6), this, "");

    private Links(Name alias, Table<LinksRecord> aliased) {
        this(alias, aliased, null);
    }

    private Links(Name alias, Table<LinksRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>LINKS</code> table reference
     */
    public Links(String alias) {
        this(DSL.name(alias), LINKS);
    }

    /**
     * Create an aliased <code>LINKS</code> table reference
     */
    public Links(Name alias) {
        this(alias, LINKS);
    }

    /**
     * Create a <code>LINKS</code> table reference
     */
    public Links() {
        this(DSL.name("LINKS"), null);
    }

    public <O extends Record> Links(Table<O> child, ForeignKey<O, LinksRecord> key) {
        super(child, key, LINKS);
    }

    /**
     * The class holding records for this type
     */
    @Override
    @NotNull
    public Class<LinksRecord> getRecordType() {
        return LinksRecord.class;
    }

    @Override
    @Nullable
    public Schema getSchema() {
        return aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    @NotNull
    public Identity<LinksRecord, Long> getIdentity() {
        return (Identity<LinksRecord, Long>) super.getIdentity();
    }

    @Override
    @NotNull
    public UniqueKey<LinksRecord> getPrimaryKey() {
        return Keys.CONSTRAINT_451;
    }

    @Override
    @NotNull
    public List<UniqueKey<LinksRecord>> getUniqueKeys() {
        return Arrays.asList(Keys.CONSTRAINT_45);
    }

    @Override
    @NotNull
    public Links as(String alias) {
        return new Links(DSL.name(alias), this);
    }

    @Override
    @NotNull
    public Links as(Name alias) {
        return new Links(alias, this);
    }

    @Override
    @NotNull
    public Links as(Table<?> alias) {
        return new Links(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    @NotNull
    public Links rename(String name) {
        return new Links(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    @NotNull
    public Links rename(Name name) {
        return new Links(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    @NotNull
    public Links rename(Table<?> name) {
        return new Links(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row4 type methods
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Row4<Long, String, OffsetDateTime, OffsetDateTime> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function4<? super Long, ? super String, ? super OffsetDateTime, ? super OffsetDateTime, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(
        Class<U> toType,
        Function4<? super Long, ? super String, ? super OffsetDateTime, ? super OffsetDateTime, ? extends U> from
    ) {
        return convertFrom(toType, Records.mapping(from));
    }
}
