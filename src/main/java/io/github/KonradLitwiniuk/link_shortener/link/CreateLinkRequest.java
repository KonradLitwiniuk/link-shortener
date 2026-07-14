package io.github.KonradLitwiniuk.link_shortener.link;

import jakarta.validation.constraints.NotBlank;

public record CreateLinkRequest(
        @NotBlank(message = "URL address cannot be empty or consist only of spaces") String url) {}
