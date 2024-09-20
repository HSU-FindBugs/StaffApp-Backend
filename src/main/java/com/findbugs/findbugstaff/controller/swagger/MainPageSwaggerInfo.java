package com.findbugs.findbugstaff.controller.swagger;

import com.findbugs.findbugstaff.dto.MainPage.MainPageResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "메인화면 페이지", description = "메인화면 페이지 API")
public interface MainPageSwaggerInfo {

    @Operation(
            summary = "메인화면 페이지",
            description = "메인화면 페이지를 조회하는 API 입니다"
    )
    @GetMapping("main/{id}")
    public ResponseEntity<MainPageResponseDto> getMainPage(@PathVariable("id") Long id);

}
