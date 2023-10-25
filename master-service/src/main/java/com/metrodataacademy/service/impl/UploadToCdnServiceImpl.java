package com.metrodataacademy.service.impl;

import com.metrodataacademy.domain.dto.AuthorizationDto;
import com.metrodataacademy.domain.dto.response.ResBaseDto;
import com.metrodataacademy.service.intrf.UploadToCdnService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.UUID;

@Service
public class UploadToCdnServiceImpl implements UploadToCdnService {
    @Override
    public ResponseEntity<ResBaseDto> uploadProfilePicture(MultipartFile image, AuthorizationDto authDto) throws IOException {
        // TODO: implementasi logic upload profile picture ke cloud storage
        return null;
    }

    @Override
    public ResponseEntity<ResBaseDto> uploadArticleImage(MultipartFile image) throws IOException {
        //TODO: implementasi logic upload article image ke cloud storage
        return null;
    }

    private void uploadToCloudStorage(MultipartFile file, String fileName) throws IOException {
        // TODO: implementasi logic upload ke cloud storage
    }

    private void deleteFileInCloudStorage(String fileName) {
        // TODO: implementasi logic delete file dari cloud storage
    }
}
