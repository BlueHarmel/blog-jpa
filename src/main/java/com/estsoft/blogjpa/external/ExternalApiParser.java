package com.estsoft.blogjpa.external;

import com.estsoft.blogjpa.dto.AddArticleRequest;
import com.estsoft.blogjpa.service.BlogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;

@Slf4j
public class ExternalApiParser {
    private final BlogService service;

    public ExternalApiParser(BlogService service){
        this.service = service;
    }

    public void parseAndSave() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://jsonplaceholder.typicode.com/posts";
        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("body: {}", response.getBody());
            List<LinkedHashMap<String,Object>> list = response.getBody();
            for (LinkedHashMap<String, Object> map : list) {
                String title = (String) map.get("title");
                String content = (String) map.get("body");

                service.save(new AddArticleRequest(title, content));
            }
        }
    }
}