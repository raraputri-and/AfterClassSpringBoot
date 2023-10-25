package com.metrodataacademy.domain.mapper;

import com.metrodataacademy.domain.dto.response.ResGetPostDto;
import com.metrodataacademy.domain.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "postId", source = "id")
    @Mapping(target = "threadsId", expression = "java(post.getThreads().getId())")
    ResGetPostDto postToResGetListPostDto(Post post);
}
