package com.example.app.domain.dto;

import java.time.LocalDateTime;


import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Builder
public class MemoDto {

    @Min(value=10,message="최소 숫자는 10이상 이어야합니다.")
    @Max(value=1000,message="최대 숫자는 1000이하 이어야합니다.")
    @NotNull(message="Id를 입력하세요")
    private Integer id;

    @NotBlank(message="Text를 입력하세요.")
    private String text;

//    @NotBlank(message="Writer를 입력하세요.")
//    @Email(message="example@example.com에 맞게 입력하세요")
//    private String writer;
//
//    @Future(message="현재 날짜 기준 이후날짜를 선택하셔야 됩니다")
//    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm" )	// 이거해야 매핑됨...
//    private LocalDateTime regdate; 						// yyyy-MM-dd HH:mm:ss

}