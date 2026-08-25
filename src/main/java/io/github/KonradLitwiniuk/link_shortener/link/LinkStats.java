package io.github.KonradLitwiniuk.link_shortener.link;

import java.util.List;

public record LinkStats(long totalClicks, List<DailyClickCount> dailyBreakdown) {}
