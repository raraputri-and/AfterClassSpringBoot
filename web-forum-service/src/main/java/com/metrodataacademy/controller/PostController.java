package com.metrodataacademy.controller;

import com.metrodataacademy.domain.constant.ConstantVariable;
import com.metrodataacademy.domain.dto.AuthorizationDto;
import com.metrodataacademy.domain.dto.request.ReqPostDto;
import com.metrodataacademy.domain.dto.response.ResBaseDto;
import com.metrodataacademy.service.intrf.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/threads")
public class PostController {

    private final PostService postServiceImpl;

    @PostMapping("/comment")
    public ResponseEntity<ResBaseDto> createPostToThreads(@Valid @RequestBody ReqPostDto reqCommentThreadsPostDto, HttpServletRequest servletRequest) {
        AuthorizationDto authDto = (AuthorizationDto) servletRequest.getAttribute(ConstantVariable.USER);
        return postServiceImpl.createPostToThreads(reqCommentThreadsPostDto, authDto);
     }

     @GetMapping("/comment")
    public ResponseEntity<ResBaseDto> getListPostFromThreads(@RequestParam String threadsId, @RequestParam String page) {
        return postServiceImpl.getListPostFromThreads(threadsId, page);
     }

}
