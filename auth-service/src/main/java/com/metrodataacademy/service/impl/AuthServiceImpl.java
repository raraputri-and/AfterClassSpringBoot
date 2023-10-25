package com.metrodataacademy.service.impl;

import com.metrodataacademy.domain.constant.ConstantVariables;
import com.metrodataacademy.domain.constant.ExceptionMessages;
import com.metrodataacademy.domain.dto.request.ReqLoginDto;
import com.metrodataacademy.domain.dto.request.ReqRegisterDto;
import com.metrodataacademy.domain.dto.response.ResLoginDto;
import com.metrodataacademy.domain.dto.response.ResTemplateDto;
import com.metrodataacademy.domain.dto.response.ResValidateTokenDto;
import com.metrodataacademy.domain.entity.Role;
import com.metrodataacademy.domain.entity.User;
import com.metrodataacademy.domain.mapper.UserMapper;
import com.metrodataacademy.exception.AuthorizationException;
import com.metrodataacademy.repository.RoleRepository;
import com.metrodataacademy.repository.UserRepository;
import com.metrodataacademy.service.interfaces.AuthService;
import com.metrodataacademy.service.interfaces.UserService;
import com.metrodataacademy.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.assertj.core.util.Sets;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    @Override
    public ResponseEntity<ResTemplateDto> register(ReqRegisterDto data) {
        try {
            Map<String, List<String>> errors = new HashMap<>();

            User userCheck = userRepository.findByEmail(data.getEmail());
            if (userCheck != null) {
                errors.put("email", new ArrayList<>(List.of("Email has already been registered")));
            }

            userCheck = userRepository.findByUsername(data.getUsername());
            if (userCheck != null) {
                errors.put("username", new ArrayList<>(List.of("Username has already been registered.")));
            }

            if (!errors.isEmpty()) {
                //Return a map of field errors and a "Bad Request" status
                return new ResponseEntity<>(new ResTemplateDto(errors, "Email or username has already been registered."), HttpStatus.BAD_REQUEST);
            }

            User user = userMapper.reqRegisterDtoToUser(data);
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            Role role = roleRepository.findByName("ROLE_USER");

            user.setRole(Sets.set(role));

            userRepository.save(user);

            role.getUser().add(user);
            roleRepository.save(role);

            data.setPassword(null);
            return new ResponseEntity<>(new ResTemplateDto(data, ConstantVariables.SUCCESS_MESSAGE), HttpStatus.CREATED);
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getReason());
        }
    }

    @Override
    public ResponseEntity<ResTemplateDto> login(ReqLoginDto data, HttpServletResponse response) {
        try {
            User user = userRepository.findByEmailOrUsername(data.getEmailorusername(), data.getEmailorusername());
            if (user == null) {
                throw new AuthorizationException(ConstantVariables.FAILED_MESSAGE);
            }

            if (!passwordEncoder.matches(data.getPassword(), user.getPassword())) {
                throw new AuthorizationException(ConstantVariables.FAILED_MESSAGE);
            }

            String accessToken = jwtUtil.generateToken(createPayload(user), user.getUsername());
            String refreshToken = jwtUtil.generateRefreshToken(createPayload(user), user.getUsername());
            String expirationDate = jwtUtil.extractExpiration(accessToken).toString();

            ResLoginDto resLoginDto = new ResLoginDto();
            resLoginDto.setToken(accessToken);
            resLoginDto.setRefreshToken(refreshToken);
            resLoginDto.setExp(expirationDate);
            return ResponseEntity.ok(new ResTemplateDto(resLoginDto, ConstantVariables.SUCCESS_MESSAGE));
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getReason());
        }
    }

    @Override
    public ResponseEntity<ResTemplateDto> refreshToken(String refreshToken) {
        try {
            if (Boolean.FALSE.equals(jwtUtil.validateRefreshToken(refreshToken))) {
                throw new AuthorizationException(ExceptionMessages.BEARER_TOKEN_INVALID);
            }

            if (Boolean.TRUE.equals(jwtUtil.isRefreshTokenExpired(refreshToken))) {
                throw new AuthorizationException(ExceptionMessages.JWT_TOKEN_IS_EXPIRED);
            }

            String subject = jwtUtil.extractUsername(refreshToken);
            String newAccessToken = jwtUtil.generateToken(refreshToken, subject);
            String expirationDate = jwtUtil.extractExpiration(newAccessToken).toString();

            ResLoginDto resLoginDto = new ResLoginDto();
            resLoginDto.setToken(newAccessToken);
            resLoginDto.setExp(expirationDate);
            return ResponseEntity.ok(new ResTemplateDto(resLoginDto, ConstantVariables.SUCCESS_MESSAGE));
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getReason());
        }
    }

    private Map<String, Object> createPayload(User user) {
        List<String> roles = new ArrayList<>();
        for (Role role : user.getRole()) {
            roles.add(role.getName());
        }

        Map<String, Object> claims = new LinkedHashMap<>();
        claims.put("id", user.getId());
        claims.put("email", user.getEmail());
        claims.put("username", user.getUsername());
        claims.put("name", user.getName());
        claims.put("profilePicture", user.getProfilePicture());
        claims.put("roles", roles);
        return claims;
    }

    @Override
    public ResponseEntity<ResTemplateDto> validateToken(String authToken) {
        // token validation
        if (!StringUtils.hasText(authToken))
            throw new AuthorizationException(ExceptionMessages.TOKEN_FOR_VALIDATE_IS_MISSING);

        if (authToken.length() < 8)
            throw new AuthorizationException(ExceptionMessages.BEARER_TOKEN_INVALID);

        String token = "";
        if (authToken.startsWith(ConstantVariables.BEARER))
            token = authToken.substring(7, authToken.length());

        ResValidateTokenDto resValidateTokenDto = new ResValidateTokenDto();

        //validate JWT token is expired
        if (jwtUtil.isTokenExpired(token))
            throw new AuthorizationException(ExceptionMessages.JWT_TOKEN_IS_EXPIRED);

        if (!token.isBlank() && jwtUtil.validateToken(token)) {
            String username = jwtUtil.extractUsername(token);

            User userDetails = userService.getByEmailOrUsername(username);
            if (userDetails != null) {
                resValidateTokenDto = userMapper.userToResValidateTokenDto(userDetails);
            }
        }

        return new ResponseEntity<ResTemplateDto>(new ResTemplateDto(resValidateTokenDto, ConstantVariables.SUCCESS_MESSAGE), HttpStatus.OK);
    }

}