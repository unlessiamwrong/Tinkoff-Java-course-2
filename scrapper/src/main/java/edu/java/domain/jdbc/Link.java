package edu.java.domain.jdbc;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Link {

    private final long id;
    private final String name;
    private OffsetDateTime lastUpdate;
    private OffsetDateTime lastCheckForUpdate;

}
