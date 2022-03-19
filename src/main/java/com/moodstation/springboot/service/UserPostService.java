package com.moodstation.springboot.service;

import com.moodstation.springboot.dto.PostImgDto;
import com.moodstation.springboot.dto.UserPostDto;
import com.moodstation.springboot.entity.Keyword;
import com.moodstation.springboot.entity.PostImg;
import com.moodstation.springboot.entity.User;
import com.moodstation.springboot.entity.UserPost;
import com.moodstation.springboot.repository.KeywordRepository;
import com.moodstation.springboot.repository.UserPostRepository;
import com.moodstation.springboot.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final S3Service s3Service;

    public Long addPostWithPhoto(Long userId,UserPostDto userPostDto, PostImgDto postImgDto) {
        postImgDto.setFileFullPath("https://" + s3Service.CLOUD_FRONT_DOMAIN_NAME + "/" + postImgDto.getFilePath());

        userPostDto.setPostImg(modelMapper.map(postImgDto,PostImg.class));
        User findUser = userRepository.findById(userId).get();
        userPostDto.setUser(findUser);
        Long savedPostId = userPostRepository.save(modelMapper.map(userPostDto, UserPost.class)).getId();
        addKeywords(savedPostId, userId, userPostDto.getKeywords());
        return savedPostId;
    }

    public Long addPost(Long userId,UserPostDto userPostDto) {
        User findUser = userRepository.findById(userId).get();
        userPostDto.setUser(findUser);
        Long savedPostId = userPostRepository.save(modelMapper.map(userPostDto, UserPost.class)).getId();
        addKeywords(savedPostId,userId, userPostDto.getKeywords());
        return savedPostId;
    }

    public void addKeywords(Long postId, Long userId, List<String> keywordList) {
        User user = userRepository.findById(userId).get();
        for (String word : keywordList) {
            Keyword keyword = Keyword.builder()
                    .userPost(UserPost.builder().id(postId).build())
                    .content(word)
                    .user(user)
                    .isShare("T")
                    .build();

            keywordRepository.save(keyword);
        }
    }


    public List<Keyword> getKeywords(Long uid){
        User findUser = userRepository.findById(uid).get();

        return keywordRepository.findByUser(findUser);
    }

    public List<UserPost> getUserPosts(Long uid){
        User findUser = userRepository.findById(uid).get();

        return userPostRepository.findByUser(findUser);
    }
}
