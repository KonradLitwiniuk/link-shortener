package io.github.KonradLitwiniuk.link_shortener.link;

        import jakarta.validation.Valid;
        import org.springframework.http.HttpStatus;
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.bind.annotation.*;

        import java.net.URI;

@RestController @RequestMapping("/links")
public class LinkController {
    private final LinkService linkService;
    public LinkController(LinkService linkService)
    {
        this.linkService = linkService;
    }
    @PostMapping
    public ResponseEntity<CreateLinkResponse> createShortLink(@Valid @RequestBody CreateLinkRequest request)
    {
        CreateLinkResponse response = linkService.createShortLink(request.url());
        URI locationHeader = URI.create(response.shortUrl());
        return ResponseEntity
                .created(locationHeader)
                .body(response);
    }
    @GetMapping("/{code}/stats")
    public ResponseEntity<LinkStats> getStats(@PathVariable String code)
    {
        LinkStats foundLink = linkService.getLinkStats(code);
        return ResponseEntity.ok(foundLink);
    }
}
