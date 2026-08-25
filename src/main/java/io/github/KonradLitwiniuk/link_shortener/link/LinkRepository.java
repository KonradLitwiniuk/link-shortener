package io.github.KonradLitwiniuk.link_shortener.link;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LinkRepository extends JpaRepository<Link, UUID> {
    //SELECT count(clicks.link_id), clicks.link_id  FROM clicks inner join links on clicks.link_id = links.id where links.code = '17cc0e00' group by clicks.link_id
    //SELECT count(clicks.link_id), (clicked_at AT TIME ZONE 'Europe/Warsaw')::date   FROM clicks inner join links on clicks.link_id = links.id where links.code = '17cc0e00' group by (clicked_at AT TIME ZONE 'Europe/Warsaw')::date
    Optional<Link> findByCode(String code);
    @Query(value = "SELECT count(clicks.link_id) AS count, (clicked_at AT TIME ZONE 'Europe/Warsaw')::date AS click_date " +
            "FROM clicks " +
            "INNER JOIN links ON clicks.link_id = links.id " +
            "WHERE links.code = :code " +
            "GROUP BY (clicked_at AT TIME ZONE 'Europe/Warsaw')::date", nativeQuery = true)
    List<DailyClickCount> getDailyClickCounts(@Param("code") String code);
}