package com.metrodataacademy.service.intrf;

import com.metrodataacademy.domain.dto.AuthorizationDto;
import com.metrodataacademy.domain.dto.response.ResBaseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadToCdnService {
    ResponseEntity<ResBaseDto> uploadProfilePicture(MultipartFile image, AuthorizationDto authDto) throws IOException;

    ResponseEntity<ResBaseDto> uploadArticleImage(MultipartFile image) throws IOException;
}
