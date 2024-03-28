package edu.java.dto.kafka;

import java.net.URI;
import org.jetbrains.annotations.Nullable;

public record Message(
    long id,
    String command,

    @Nullable URI link
) {
}
