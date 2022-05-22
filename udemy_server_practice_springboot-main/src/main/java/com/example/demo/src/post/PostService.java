package com.example.demo.src.post;


import com.example.demo.config.BaseException;
import com.example.demo.src.post.model.GetPostRes;
import com.example.demo.src.post.model.PatchPostReq;
import com.example.demo.src.post.model.PostPostReq;
import com.example.demo.src.post.model.PostPostRes;
import com.example.demo.src.user.model.DeleteUserReq;
import com.example.demo.src.user.model.PatchUserReq;
import com.example.demo.src.user.model.PostUserReq;
import com.example.demo.src.user.model.PostUserRes;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@Service
public class PostService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final PostDao postDao;
    private final PostProvider postProvider;
    private final JwtService jwtService;


    @Autowired
    public PostService(PostDao postDao, PostProvider postProvider, JwtService jwtService) {
        this.postDao = postDao;
        this.postProvider = postProvider;
        this.jwtService = jwtService;

    }

    public PostPostRes createPost(int userIdx, PostPostReq postPostReq) throws BaseException {

        try {
            int postIdx = postDao.insertPost(userIdx, postPostReq.getContent());
            for(int i = 0; i < postPostReq.getPostImgUrlList().size(); i++){
                postDao.insertPostImg(postIdx, postPostReq.getPostImgUrlList().get(i));
            }
            return new PostPostRes(postIdx);
        } catch (Exception exception) {
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void modifyPost(int userIdx, int postIdx, PatchPostReq patchPostReq) throws BaseException {

        if(postProvider.checkUserExist(userIdx) == 0){
            throw new BaseException(USERS_EMPTY_USER_ID);
        }

        if(postProvider.checkPostExist(postIdx) == 0){
            throw new BaseException(POST_EMPTY_POST_ID);
        }
        try {
            int result = postDao.updatePost(postIdx, patchPostReq.getContent());
            System.out.println(result);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_POST);
            }

        } catch (Exception exception) {
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }

    }

    public void deletePost(int postIdx) throws BaseException {

        if(postProvider.checkPostExist(postIdx) == 0){
            throw new BaseException(POST_EMPTY_POST_ID);
        }
        try {
            int result = postDao.deletePost(postIdx);
            if(result == 0){
                throw new BaseException(DELETE_FAIL_POST);
            }

        } catch (Exception exception) {
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }



}
