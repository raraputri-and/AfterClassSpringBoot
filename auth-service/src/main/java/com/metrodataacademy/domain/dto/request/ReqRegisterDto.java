package com.metrodataacademy.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReqRegisterDto {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String username;

    @NotBlank
    private String name;

    @NotBlank
    private String alamat;

    @NotBlank
    private String jenisKelamin;

    @NotNull
    private LocalDate tanggalLahir;

    @NotBlank
    @Size(min = 8)
    private String password;
}
