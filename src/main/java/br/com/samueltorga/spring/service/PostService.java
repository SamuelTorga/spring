package br.com.samueltorga.spring.service;

import br.com.samueltorga.spring.controller.dto.post.FindPostResponse;
import br.com.samueltorga.spring.mappers.PostMapper;
import br.com.samueltorga.spring.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public Optional<FindPostResponse> findPostById(Integer id) {
        return postRepository.findById(id)
                .map(postMapper::toFindPostResponse);
    }

}
