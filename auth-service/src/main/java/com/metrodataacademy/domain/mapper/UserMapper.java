package com.metrodataacademy.domain.mapper;

import com.metrodataacademy.domain.dto.request.ReqRegisterDto;
import com.metrodataacademy.domain.dto.request.ReqUpdateUserDto;
import com.metrodataacademy.domain.dto.response.ResUserProfileDto;
import com.metrodataacademy.domain.dto.response.ResValidateTokenDto;
import com.metrodataacademy.domain.entity.Role;
import com.metrodataacademy.domain.entity.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    ResValidateTokenDto userToResValidateTokenDto(User user);

    User reqRegisterDtoToUser(ReqRegisterDto reqRegisterDto);

    ResUserProfileDto userToResProfileDto(User user);

    void update(@MappingTarget User user, ReqUpdateUserDto reqUpdateUserDto);

    @AfterMapping
    default void mappingRoles(@MappingTarget ResValidateTokenDto resValidateTokenDto, User user) {
        List<String> roles = new ArrayList<>();
        for (Role role : user.getRole()) {
            roles.add(role.getName());
        }
        resValidateTokenDto.setRoles(roles);
    }
}
