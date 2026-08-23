package io.github.KonradLitwiniuk.link_shortener.link;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.time.Instant;
import java.util.UUID;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name="clicks")
public class Click {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Instant clickedAt;
    @ManyToOne(fetch = FetchType.LAZY)
    private Link link;

}
