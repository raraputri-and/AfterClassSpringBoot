package com.metrodataacademy.service.intrf;

import com.metrodataacademy.domain.dto.AuthorizationDto;
import com.metrodataacademy.domain.dto.request.ReqCreateThreadsDto;
import com.metrodataacademy.domain.dto.request.ReqGetListThreads;
import com.metrodataacademy.domain.dto.response.ResBaseDto;
import org.springframework.http.ResponseEntity;

public interface ThreadsService {
    ResponseEntity<ResBaseDto> getThreadsById(String id);
    ResponseEntity<ResBaseDto> deleteThreads(String id);

    ResponseEntity<ResBaseDto> insertThreads(ReqCreateThreadsDto threadsDto, AuthorizationDto authDto);

    ResponseEntity<ResBaseDto> getListThreads(ReqGetListThreads reqGetListThreads);
}
