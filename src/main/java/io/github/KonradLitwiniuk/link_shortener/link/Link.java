package io.github.KonradLitwiniuk.link_shortener.link;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter @Setter @NoArgsConstructor @ToString
@Table(name="links")
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String url;
    @Column(unique = true)
    private String code;
    private Instant createdAt;
    public Link(String url, String code){
        this.url = url;
        this.code = code;
        this.createdAt = Instant.now();
    }
}

