package com.metrodataacademy.service.intrf;

import com.metrodataacademy.domain.dto.AuthorizationDto;
import com.metrodataacademy.domain.dto.request.ReqPostDto;
import com.metrodataacademy.domain.dto.response.ResBaseDto;
import org.springframework.http.ResponseEntity;

public interface PostService {
    ResponseEntity<ResBaseDto> createPostToThreads(ReqPostDto reqCommentThreadsPostDto, AuthorizationDto authDto);

    ResponseEntity<ResBaseDto> getListPostFromThreads(String threadsId, String page);
}
