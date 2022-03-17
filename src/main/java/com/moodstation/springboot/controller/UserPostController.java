package com.moodstation.springboot.controller;

import com.moodstation.springboot.dto.PostImgDto;
import com.moodstation.springboot.dto.UserPostDto;
import com.moodstation.springboot.entity.Keyword;
import com.moodstation.springboot.entity.UserPost;
import com.moodstation.springboot.service.S3Service;
import com.moodstation.springboot.service.UserPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/emotion")
@RequiredArgsConstructor
public class UserPostController {
    private final UserPostService userPostService;
    private final S3Service s3Service;
    private static final Long userId=1L;


    @PostMapping()
    public ResponseEntity addPost(
            MultipartFile file,
            PostImgDto postImgDto,
            @ModelAttribute UserPostDto userPostDto
            ) throws IOException {

        Long postNo;
        if (file != null) {
            postImgDto.setImgName(file.getName());
            postImgDto.setFilePath(s3Service.upload(postImgDto.getFilePath(), file));

            postNo = userPostService.addPostWithPhoto(userId, userPostDto, postImgDto);
        } else {
            postNo = userPostService.addPost(userId,userPostDto);
        }

        return new ResponseEntity<>(postNo, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<Map<String,Object>> getPostList(){
        List<Keyword> keywords = userPostService.getKeywords(userId);
        List<UserPost> userPosts = userPostService.getUserPosts(userId);
        Map<String, Object> result = new HashMap<>();
        result.put("keywords", keywords);
        result.put("userPosts", userPosts);

        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping("/{pid}")
    public ResponseEntity getPostDetail(@PathVariable Long pid){
        UserPost findPost = userPostService.getUserPostDetail(pid);

        return new ResponseEntity(findPost, HttpStatus.OK);
    }

    @DeleteMapping("/{pid}")
    public ResponseEntity removePostDetail(@PathVariable Long pid){
        userPostService.removeUserPost(pid);
        return new ResponseEntity(HttpStatus.OK);
    }
}
