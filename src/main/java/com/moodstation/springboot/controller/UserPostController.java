package com.moodstation.springboot.controller;

import com.moodstation.springboot.dto.PostImgDto;
import com.moodstation.springboot.dto.UserPostDto;
import com.moodstation.springboot.service.S3Service;
import com.moodstation.springboot.service.UserPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class UserPostController {
    private final UserPostService userPostService;
    private final S3Service s3Service;


    @PostMapping("/{uid}")
    public ResponseEntity addPost(
            MultipartFile file,
            PostImgDto postImgDto,
            @ModelAttribute UserPostDto userPostDto,
            @PathVariable String uid) throws IOException {

        Long postNo;
        if (file!=null) {
            postImgDto.setImgName(file.getName());
            postImgDto.setFilePath(s3Service.upload(postImgDto.getFilePath(),file));

            postNo = userPostService.addPostWithPhoto(userPostDto,postImgDto);
        }
        else{
            postNo = userPostService.addPost(userPostDto);
        }

        return new ResponseEntity<>(postNo, HttpStatus.OK);
    }
}
