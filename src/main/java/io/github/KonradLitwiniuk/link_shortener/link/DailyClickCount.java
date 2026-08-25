package io.github.KonradLitwiniuk.link_shortener.link;

import java.time.LocalDate;

public record DailyClickCount(long count, LocalDate date) {
}
