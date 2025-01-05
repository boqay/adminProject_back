package com.example.demo.member.controller;

import com.example.demo.common.response.CustomApiResponse;
import com.example.demo.member.dto.JoinRequestDto;
import com.example.demo.member.service.JoinService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "회원가임", description = "회원가입 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/join")
public class JoinController {
    private final JoinService joinService;

    @PostMapping("/process")
    @Operation(summary = "Example API Summary", description = "Your description")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {@Content(schema = @Schema(implementation = JoinRequestDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
    })
    public CustomApiResponse joinProcess(JoinRequestDto joinRequestDto) {
        return joinService.joinProcess(joinRequestDto);
    }
}
