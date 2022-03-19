package com.moodstation.springboot.service;

import com.moodstation.springboot.dto.PostImgDto;
import com.moodstation.springboot.entity.PostImg;
import com.moodstation.springboot.repository.PostImgRepository;
import com.moodstation.springboot.repository.UserPostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostImgService {
    private final UserPostRepository userPostRepository;
    private final PostImgRepository postImgRepository;
    private final S3Service s3Service;
    private final ModelMapper modelMapper;

//    public PostImgDto getPostImg(Long id){
//        PostImg postImg = userPostRepository.findById(id).get().getPostImg();
//
//        return PostImgDto.builder()
//                .id(postImg.getId())
//                .imgName(postImg.getImgName())
//                .filePath(postImg.getFilePath())
//                .fileFullPath(postImg.getFileFullPath())
//                .build();
//    }
}
