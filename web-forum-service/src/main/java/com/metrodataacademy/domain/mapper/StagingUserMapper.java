package com.metrodataacademy.domain.mapper;

import com.metrodataacademy.domain.dto.response.ResStagingUserDto;
import com.metrodataacademy.domain.entity.StagingUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StagingUserMapper {
    ResStagingUserDto stagingUserToResStagingUserDto(StagingUser stagingUser);
}
