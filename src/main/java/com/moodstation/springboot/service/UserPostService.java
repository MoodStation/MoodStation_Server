package com.moodstation.springboot.service;

import com.moodstation.springboot.dto.PostImgDto;
import com.moodstation.springboot.dto.UserPostDto;
import com.moodstation.springboot.entity.Keyword;
import com.moodstation.springboot.entity.PostImg;
import com.moodstation.springboot.entity.UserPost;
import com.moodstation.springboot.repository.KeywordRepository;
import com.moodstation.springboot.repository.UserPostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserPostService {
    private final UserPostRepository userPostRepository;
    private final KeywordRepository keywordRepository;
    private final ModelMapper modelMapper;

    public Long addPostWithPhoto(UserPostDto userPostDto, PostImgDto postImgDto) {
        userPostDto.setPostImg(modelMapper.map(postImgDto,PostImg.class));
        Long savedPostId = userPostRepository.save(modelMapper.map(userPostDto, UserPost.class)).getId();
        addKeywords(savedPostId, userPostDto.getKeywords());
        return savedPostId;
    }

    public Long addPost(UserPostDto userPostDto) {
        Long savedPostId = userPostRepository.save(modelMapper.map(userPostDto, UserPost.class)).getId();
        addKeywords(savedPostId, userPostDto.getKeywords());
        return savedPostId;
    }

    public void addKeywords(Long postId, List<String> keywordList) {
        for (String word : keywordList) {
            Keyword keyword = Keyword.builder()
                    .userPost(UserPost.builder().id(postId).build())
                    .content(word)
                    .isShare("T")
                    .build();

            keywordRepository.save(keyword);
        }
    }


}
