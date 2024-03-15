/*
 * This file is generated by jOOQ.
 */

package edu.java.domain.jooq.tables.pojos;

import jakarta.validation.constraints.Size;
import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.time.OffsetDateTime;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
public class Links implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private OffsetDateTime lastUpdate;
    private OffsetDateTime lastCheckForUpdate;

    public Links() {
    }

    public Links(Links value) {
        this.id = value.id;
        this.name = value.name;
        this.lastUpdate = value.lastUpdate;
        this.lastCheckForUpdate = value.lastCheckForUpdate;
    }

    @ConstructorProperties({"id", "name", "lastUpdate", "lastCheckForUpdate"})
    public Links(
        @Nullable Long id,
        @NotNull String name,
        @NotNull OffsetDateTime lastUpdate,
        @Nullable OffsetDateTime lastCheckForUpdate
    ) {
        this.id = id;
        this.name = name;
        this.lastUpdate = lastUpdate;
        this.lastCheckForUpdate = lastCheckForUpdate;
    }

    /**
     * Getter for <code>LINKS.ID</code>.
     */
    @Nullable
    public Long getId() {
        return this.id;
    }

    /**
     * Setter for <code>LINKS.ID</code>.
     */
    public void setId(@Nullable Long id) {
        this.id = id;
    }

    /**
     * Getter for <code>LINKS.NAME</code>.
     */
    @jakarta.validation.constraints.NotNull
    @Size(max = 200)
    @NotNull
    public String getName() {
        return this.name;
    }

    /**
     * Setter for <code>LINKS.NAME</code>.
     */
    public void setName(@NotNull String name) {
        this.name = name;
    }

    /**
     * Getter for <code>LINKS.LAST_UPDATE</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public OffsetDateTime getLastUpdate() {
        return this.lastUpdate;
    }

    /**
     * Setter for <code>LINKS.LAST_UPDATE</code>.
     */
    public void setLastUpdate(@NotNull OffsetDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Getter for <code>LINKS.LAST_CHECK_FOR_UPDATE</code>.
     */
    @Nullable
    public OffsetDateTime getLastCheckForUpdate() {
        return this.lastCheckForUpdate;
    }

    /**
     * Setter for <code>LINKS.LAST_CHECK_FOR_UPDATE</code>.
     */
    public void setLastCheckForUpdate(@Nullable OffsetDateTime lastCheckForUpdate) {
        this.lastCheckForUpdate = lastCheckForUpdate;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Links other = (Links) obj;
        if (this.id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!this.id.equals(other.id)) {
            return false;
        }
        if (this.name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!this.name.equals(other.name)) {
            return false;
        }
        if (this.lastUpdate == null) {
            if (other.lastUpdate != null) {
                return false;
            }
        } else if (!this.lastUpdate.equals(other.lastUpdate)) {
            return false;
        }
        if (this.lastCheckForUpdate == null) {
            if (other.lastCheckForUpdate != null) {
                return false;
            }
        } else if (!this.lastCheckForUpdate.equals(other.lastCheckForUpdate)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
        result = prime * result + ((this.lastUpdate == null) ? 0 : this.lastUpdate.hashCode());
        result = prime * result + ((this.lastCheckForUpdate == null) ? 0 : this.lastCheckForUpdate.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Links (");

        sb.append(id);
        sb.append(", ").append(name);
        sb.append(", ").append(lastUpdate);
        sb.append(", ").append(lastCheckForUpdate);

        sb.append(")");
        return sb.toString();
    }
}
