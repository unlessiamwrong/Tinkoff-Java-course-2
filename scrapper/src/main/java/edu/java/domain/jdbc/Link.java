package edu.java.domain.jdbc;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Link {

    private long id;
    private String name;
    private OffsetDateTime lastUpdate;
    private OffsetDateTime lastCheckForUpdate;

}
