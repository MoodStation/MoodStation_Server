package com.moodstation.springboot.service;

import com.moodstation.springboot.dto.PostImgDto;
import com.moodstation.springboot.entity.PostImg;
import com.moodstation.springboot.entity.UserPost;
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


    public void updatePostImg(Long pid, PostImgDto postImgDto) {
        //기존에 이미지 있었을 때
        if (userPostRepository.findById(pid).get().getPostImg() != null) {
            //기존 이미지 지움
            postImgRepository.deleteById(userPostRepository.findById(pid).get().getPostImg().getId());
            deletePostImg(pid);
            //이미지 들어왔으면
            if (postImgDto != null) {
                PostImg postImg = postImgRepository.findById(userPostRepository.findById(pid).get().getPostImg().getId()).get();
                postImg.changeBookImg(postImgDto.getImgName(),
                        postImgDto.getFilePath(),
                        postImgDto.getFileFullPath());
            }
        }
        //기존에 이미지 없었을 때
//        else{
//            postImgRepository.save(modelMapper.map(postImgDto, PostImg.class));
//        }



//            //저장했던 사진이 있으면
//            if (postImgId != null) {
//
//                PostImg postImg = postImgRepository.findById(postImgId).get();
//                postImg.changeBookImg(postImgDto.getImgName(),
//                        postImgDto.getFilePath(),
//                        postImgDto.getFileFullPath());
//                postImgRepository.save(postImg);
//            }
//            //저장했던 사진이 없으면
//            else {
//            }
    }

    public void deletePostImg(Long pid) {
        UserPost findUserPost = userPostRepository.findById(pid).get();
        postImgRepository.deleteById(findUserPost.getPostImg().getId());
        s3Service.delete(findUserPost.getPostImg().getFilePath());
    }
}
