package br.com.samueltorga.spring.controller;

import br.com.samueltorga.spring.controller.dto.post.FindPostResponse;
import br.com.samueltorga.spring.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class PostController {

    private final PostService postService;

    @GetMapping("/{id}")
    public ResponseEntity<FindPostResponse> getUserById(@PathVariable("id") Integer id) {
        Optional<FindPostResponse> product = postService.findPostById(id);
        return product.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

}
