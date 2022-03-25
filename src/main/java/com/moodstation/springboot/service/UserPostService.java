package com.moodstation.springboot.service;

import com.moodstation.springboot.dto.PostImgDto;
import com.moodstation.springboot.dto.UserPostDto;
import com.moodstation.springboot.dto.UserPostListResponseDto;
import com.moodstation.springboot.dto.UserPostResponseDto;
import com.moodstation.springboot.entity.Keyword;
import com.moodstation.springboot.entity.PostImg;
import com.moodstation.springboot.entity.User;
import com.moodstation.springboot.entity.UserPost;
import com.moodstation.springboot.repository.KeywordRepository;
import com.moodstation.springboot.repository.PostImgRepository;
import com.moodstation.springboot.repository.UserPostRepository;
import com.moodstation.springboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserPostService {
    private final UserPostRepository userPostRepository;
    private final KeywordRepository keywordRepository;
    private final UserRepository userRepository;
    private final PostImgRepository postImgRepository;
    private final ModelMapper modelMapper;
    private final S3Service s3Service;
    private final PostImgService postImgService;

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


    public List<String> getKeywords(Long pid){
        UserPost userPost = userPostRepository.findById(pid).get();
        List<Keyword> keywords = keywordRepository.findByUserPost(userPost);

        List<String> result = new ArrayList<>();
        for (Keyword keyword : keywords) {
            result.add(keyword.getContent());
        }

        return result;
    }

    public List<UserPostListResponseDto> getUserPosts(String accessToken){
        List<UserPost> posts
                = userPostRepository.findByUser(userRepository.findByAccessToken(accessToken));

        List<UserPostListResponseDto> result = new ArrayList<>();
        for (UserPost post : posts) {
            UserPostListResponseDto userPost = UserPostListResponseDto.builder()
                    .postId(post.getId())
                    .postImg(post.getPostImg())
                    .regDate(post.getRegDate())
                    .content(post.getContent())
                    .keywords(getKeywords(post.getId()))
                    .build();
            result.add(userPost);
        }
        return result;
    }

    public UserPostResponseDto getUserPostDetail(Long pid){
        UserPost userPost = userPostRepository.findById(pid).get();
        UserPostResponseDto userPostResponseDto = UserPostResponseDto.builder()
                .postId(userPost.getId())
                .postImg(userPost.getPostImg())
                .color(userPost.getColor())
                .regDate(userPost.getRegDate())
                .keywords(getKeywords(pid))
                .build();

        return userPostResponseDto;
    }

    public void removeUserPost(Long pid){
        UserPost userPost= userPostRepository.findById(pid).get();
        keywordRepository.deleteByUserPost(userPost);
        s3Service.delete(userPost.getPostImg().getFilePath());
        userPostRepository.deleteById(pid);
    }

    public void updateUserPostWithPhoto(UserPostDto userPostDto, PostImgDto postImgDto, Long pid){
        postImgDto.setFileFullPath("https://" + s3Service.CLOUD_FRONT_DOMAIN_NAME + "/" + postImgDto.getFilePath());
        userPostDto.setPostImg(modelMapper.map(postImgDto,PostImg.class));
        UserPost findUserPost = userPostRepository.findById(pid).get();

//        userPostDto.setPostImg(modelMapper.map(postImgDto,PostImg.class));

        //기존 키워드 삭제
        keywordRepository.deleteByUserPost(findUserPost);
        addKeywords(pid,userPostDto.getUser().getId(),userPostDto.getKeywords());

        findUserPost.changeUserPost(
                userPostDto.getRegDate(),
                userPostDto.getColor(),
                userPostDto.getContent(),
                userPostDto.getPostImg()
        );
    }


    public void updateUserPost(UserPostDto userPostDto, Long pid) {
        UserPost findUserPost = userPostRepository.findById(pid).get();

        if (findUserPost.getPostImg().getId()!=null) {
            postImgService.deletePostImg(pid);
            s3Service.delete(findUserPost.getPostImg().getFilePath());
        }

        //기존 키워드 삭제
        keywordRepository.deleteByUserPost(findUserPost);
        addKeywords(pid,userPostDto.getUser().getId(),userPostDto.getKeywords());

        findUserPost.changeUserPost(
                userPostDto.getRegDate(),
                userPostDto.getColor(),
                userPostDto.getContent(),
                userPostDto.getPostImg()
        );
    }

}
