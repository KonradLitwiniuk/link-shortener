package io.github.KonradLitwiniuk.link_shortener.link;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LinkService {
    private final LinkRepository linkRepository;
    private final String baseUrl;
    public LinkService(
            LinkRepository linkRepository,
            @Value("${app.base-url}") String baseUrl){
        this.linkRepository = linkRepository;
        this.baseUrl = baseUrl;
    }
    public CreateLinkResponse createShortLink(String givenUrl){
        // TODO: proper generator
        String randCode = UUID.randomUUID().toString().substring(0, 8);
        Link newLink = new Link(givenUrl, randCode);
        Link saved = linkRepository.save(newLink);
        String fullShortenUrl = baseUrl + randCode;
        return new CreateLinkResponse(fullShortenUrl, givenUrl);
    }
}
