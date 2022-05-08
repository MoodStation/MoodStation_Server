package com.moodstation.springboot.controller;

import com.moodstation.springboot.dto.*;
import com.moodstation.springboot.jwt.JwtGenerator;
import com.moodstation.springboot.service.PostImgService;
import com.moodstation.springboot.service.S3Service;
import com.moodstation.springboot.service.UserPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/emotion")
@RequiredArgsConstructor
public class UserPostController {
    private final UserPostService userPostService;
    private final PostImgService postImgService;
    private final S3Service s3Service;
    private final JwtGenerator jwtGenerator;


    @PostMapping()
    public ResponseEntity addPost(
            MultipartFile file,
            PostImgDto postImgDto,
            @ModelAttribute UserPostDto userPostDto,
            HttpServletRequest request
            ) throws IOException {
        String accessToken = jwtGenerator.resolveToken(request);
        if(accessToken==null) return new ResponseEntity(HttpStatus.FORBIDDEN);

        if (file != null) {
            postImgDto.setImgName(file.getName());
            postImgDto.setFilePath(s3Service.upload(postImgDto.getFilePath(), file));

            userPostService.addPostWithPhoto(accessToken, userPostDto, postImgDto);
        } else {
            userPostService.addPost(accessToken,userPostDto);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<Map<String,Object>> getPostList(
            @PathVariable Optional<Integer> page,
            UserPostSearchDto userPostSearchDto,
            HttpServletRequest request) {
        String accessToken = jwtGenerator.resolveToken(request);
        if(accessToken==null) return new ResponseEntity(HttpStatus.FORBIDDEN);

        int size = userPostSearchDto.getSearchDate().lengthOfMonth();
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, size);

        Map<String, Object> result = userPostService.getUserPosts(accessToken, userPostSearchDto, pageable);

        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping("/{pid}")
    public ResponseEntity getPostDetail(
            HttpServletRequest request,
            @PathVariable Long pid){
        String accessToken = jwtGenerator.resolveToken(request);
        if(accessToken==null) return new ResponseEntity(HttpStatus.FORBIDDEN);

        UserPostResponseDto findPost = userPostService.getUserPostDetail(pid);

        return new ResponseEntity(findPost, HttpStatus.OK);
    }

    @DeleteMapping("/{pid}")
    public ResponseEntity removePostDetail(
            HttpServletRequest request,
            @PathVariable Long pid){
        String accessToken = jwtGenerator.resolveToken(request);
        if(accessToken==null) return new ResponseEntity(HttpStatus.FORBIDDEN);

        userPostService.removeUserPost(pid);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/{pid}")
    public ResponseEntity updatePostDetail(
                                           HttpServletRequest request,
                                           @PathVariable Long pid,
                                           MultipartFile file,
                                           PostImgDto postImgDto,
                                           @ModelAttribute UserPostDto userPostDto) throws IOException {
        String accessToken = jwtGenerator.resolveToken(request);
        if(accessToken==null) return new ResponseEntity(HttpStatus.FORBIDDEN);

        if (file != null) {
            postImgDto.setImgName(file.getName());
            postImgDto.setFilePath(s3Service.upload(postImgDto.getFilePath(), file));
            postImgService.updatePostImg(pid, postImgDto);
            userPostService.updateUserPostWithPhoto(accessToken, userPostDto, postImgDto, pid);
        } else {
            userPostService.updateUserPost(accessToken, userPostDto, pid);
        }

        return new ResponseEntity(HttpStatus.OK);

    }
}
