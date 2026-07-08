package io.github.KonradLitwiniuk.link_shortener.link;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LinkRepository extends JpaRepository<Link, UUID> {
    Optional<Link> findByCode(String code);
}