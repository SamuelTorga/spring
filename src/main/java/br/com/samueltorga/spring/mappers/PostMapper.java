package br.com.samueltorga.spring.mappers;

import br.com.samueltorga.spring.controller.dto.post.FindPostResponse;
import br.com.samueltorga.spring.repository.entity.Post;
import br.com.samueltorga.spring.repository.entity.PostComment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "userId", source = "user.id")
    FindPostResponse toFindPostResponse(Post post);

    @Mapping(target = "parentCommentId", source = "parentComment.id")
    @Mapping(target = "userId", source = "user.id")
    FindPostResponse.PostComment toPostComment(PostComment comment);

    default Set<FindPostResponse.PostComment> postCommentSetToPostCommentSet(Set<PostComment> set) {
        if (set == null) {
            return Collections.emptySet();
        }

        Set<FindPostResponse.PostComment> set1 = LinkedHashSet.newLinkedHashSet(set.size());
        for (PostComment postComment : set) {
            if (postComment.getParentComment() != null)
                continue;
            set1.add(toPostComment(postComment));
        }

        return set1;
    }

}
