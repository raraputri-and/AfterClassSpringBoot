package com.metrodataacademy.service.impl;

import com.metrodataacademy.domain.constant.ConstantVariable;
import com.metrodataacademy.domain.dto.AuthorizationDto;
import com.metrodataacademy.domain.dto.request.ReqCreateThreadsDto;
import com.metrodataacademy.domain.dto.request.ReqGetListThreads;
import com.metrodataacademy.domain.dto.response.ResBaseDto;
import com.metrodataacademy.domain.dto.response.ResGetThreadsDto;
import com.metrodataacademy.domain.dto.response.ResStagingUserDto;
import com.metrodataacademy.domain.entity.Post;
import com.metrodataacademy.domain.entity.StagingUser;
import com.metrodataacademy.domain.entity.Threads;
import com.metrodataacademy.domain.mapper.StagingUserMapper;
import com.metrodataacademy.domain.mapper.ThreadsMapper;
import com.metrodataacademy.repository.PostRepository;
import com.metrodataacademy.repository.StagingUserRepository;
import com.metrodataacademy.repository.ThreadsRepository;
import com.metrodataacademy.service.intrf.ThreadsService;
import com.metrodataacademy.util.SlugUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ThreadsServiceImpl implements ThreadsService {
    private final ThreadsRepository threadsRepository;
    private final StagingUserRepository stagingUserRepository;
    private final ThreadsMapper threadsMapper;
    private final StagingUserMapper stagingUserMapper;
    private final PostRepository postRepository;

    @Override
    public ResponseEntity<ResBaseDto> getThreadsById(String id) {
        Threads threads = threadsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Threads ID is not found!!!!"));

        threads.setCounter(threads.getCounter()+1);
        threadsRepository.save(threads);

        ResGetThreadsDto resGetThreadsDto = threadsMapper.threadsToResGetThreads(threads);

        ResStagingUserDto resStagingUserDto = stagingUserMapper.stagingUserToResStagingUserDto(threads.getAuthor());

        resGetThreadsDto.setAuthor(resStagingUserDto);

        resGetThreadsDto.setTotalPostComments(threadsRepository.getTotalComments(id));

        return new ResponseEntity<>(new ResBaseDto(resGetThreadsDto, ConstantVariable.SUCCESS), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResBaseDto> deleteThreads(String id) {
        try {
            Threads threads = threadsRepository.findById(id).get();
            List<Post> listPost = threads.getPosts();

            threadsRepository.deleteById(id);

            listPost.forEach(post -> {
                postRepository.delete(post);
            });

            return new ResponseEntity<>(new ResBaseDto(null, ConstantVariable.SUCCESS), HttpStatus.OK);
        } catch (ResponseStatusException e){
            throw new ResponseStatusException(e.getStatusCode(), e.getReason());
        }
    }

    @Override
    public ResponseEntity<ResBaseDto> insertThreads(ReqCreateThreadsDto threadsDto, AuthorizationDto authDto) {
        try {
            Threads threads = threadsMapper.ReqCreateThreadsDtoToThreads(threadsDto);

            /*
              this is to set attribute value then insert it to table threads
             */

            threads.setSlug(SlugUtil.toSlug(threadsDto.getTitle()));
            threads.setCounter(0);
            threads.setActive(true);

            Optional<StagingUser> optStagingUser = stagingUserRepository.findById(authDto.getId());
            StagingUser stagingUser = new StagingUser();
            if (optStagingUser.isEmpty()) {
                stagingUser.setId(authDto.getId());
                stagingUser.setName(authDto.getName());
                stagingUser = stagingUserRepository.save(stagingUser);
            } else {
                stagingUser = optStagingUser.get();
            }

            threads.setAuthor(stagingUser);

            threadsRepository.save(threads);

            ResGetThreadsDto resGetThreadsDto = new ResGetThreadsDto();
            resGetThreadsDto.setId(threads.getId());

            return new ResponseEntity<>(new ResBaseDto(resGetThreadsDto, ConstantVariable.SUCCESS), HttpStatus.OK);
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getReason());
        }
    }

    @Override
    public ResponseEntity<ResBaseDto> getListThreads(ReqGetListThreads reqGetListThreads) {
        try {
            Page<Threads> pageResult = Page.empty();
            Pageable pageable = PageRequest.of(reqGetListThreads.getPage() -1,
                    ConstantVariable.DATA_PER_PAGE_THREADS);
            if (reqGetListThreads.getFilterBy().equalsIgnoreCase("title") && reqGetListThreads.getSortBy().equalsIgnoreCase("") ||
                    reqGetListThreads.getFilterBy().equalsIgnoreCase("title") && reqGetListThreads.getSortBy().equalsIgnoreCase("D")) {
                pageResult = threadsRepository.findAllThreadsWithKeywordDesc(reqGetListThreads.getKeyword(), pageable);
            }

            if (reqGetListThreads.getFilterBy().equalsIgnoreCase("title") && reqGetListThreads.getSortBy().equalsIgnoreCase("A")) {
                pageResult = threadsRepository.findAllThreadsWithKeywordAsc(reqGetListThreads.getKeyword(), pageable);
            }

            if (reqGetListThreads.getFilterBy().equalsIgnoreCase("totalComments") && reqGetListThreads.getSortBy().equalsIgnoreCase("D") ||
                    reqGetListThreads.getFilterBy().equalsIgnoreCase("totalComments") && reqGetListThreads.getSortBy().equalsIgnoreCase("")) {
                pageResult = threadsRepository.findAllThreadsByTotalCommentsDesc(pageable);
            }

            if (reqGetListThreads.getFilterBy().equalsIgnoreCase("totalComments") && reqGetListThreads.getSortBy().equalsIgnoreCase("A")) {
                pageResult = threadsRepository.findAllThreadsByTotalCommentsAsc(pageable);
            }

            if (reqGetListThreads.getFilterBy().equalsIgnoreCase("totalViews") && reqGetListThreads.getSortBy().equalsIgnoreCase("D") ||
                    reqGetListThreads.getFilterBy().equalsIgnoreCase("totalViews") && reqGetListThreads.getSortBy().equalsIgnoreCase("")) {
                pageResult = threadsRepository.findAllThreadsByTotalViewsDesc(pageable);
            }

            if (reqGetListThreads.getFilterBy().equalsIgnoreCase("totalViews") && reqGetListThreads.getSortBy().equalsIgnoreCase("A")) {
                pageResult = threadsRepository.findAllThreadsByTotalViewsAsc(pageable);
            }

            if (reqGetListThreads.getFilterBy().equalsIgnoreCase("") && reqGetListThreads.getSortBy().equalsIgnoreCase("D") ||
                    reqGetListThreads.getFilterBy().equalsIgnoreCase("") && reqGetListThreads.getSortBy().equalsIgnoreCase("")) {
                pageResult = threadsRepository.findAllThreadsDesc(pageable);
            }

            if (reqGetListThreads.getFilterBy().equalsIgnoreCase("") && reqGetListThreads.getSortBy().equalsIgnoreCase("A")) {
                pageResult = threadsRepository.findAllThreadsAsc(pageable);
            }

            List<ResGetThreadsDto> listThreadsList = new ArrayList<>();
            pageResult.forEach(response -> {
                ResGetThreadsDto resGetThreadsDto = threadsMapper.threadsToResGetThreads(response);
                resGetThreadsDto.setAuthor(stagingUserMapper.stagingUserToResStagingUserDto(response.getAuthor()));
                resGetThreadsDto.setTotalPostComments(threadsRepository.getTotalComments(resGetThreadsDto.getId()));

                listThreadsList.add(resGetThreadsDto);

            });

            return new ResponseEntity<>(new ResBaseDto(listThreadsList, ConstantVariable.SUCCESS), HttpStatus.OK);
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getReason());
        }
    }
}

