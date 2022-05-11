package com.example.demo.src.post;


import com.example.demo.config.BaseException;
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

import static com.example.demo.config.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@Service
public class PostService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final PostDao postDao;
    private final PostProvider userProvider;
    private final JwtService jwtService;


    @Autowired
    public PostService(PostDao postDao, PostProvider userProvider, JwtService jwtService) {
        this.postDao = postDao;
        this.userProvider = userProvider;
        this.jwtService = jwtService;

    }




}
