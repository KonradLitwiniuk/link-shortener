package io.github.KonradLitwiniuk.link_shortener.link;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
@RestController
public class RedirectController {

    private final LinkService linkService;

    public RedirectController(LinkService linkService)
    {
        this.linkService = linkService;
    }
    @GetMapping("/{code}")
    public ResponseEntity<Void> redirect(@PathVariable String code)
    {
        String originalUrl = linkService.getOriginalUrl(code);
        URI location = URI.create(originalUrl);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(location)
                .build();
    }
}
