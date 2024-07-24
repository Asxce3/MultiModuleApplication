package com.example.CommentService.service.commentUtils;

import org.springframework.stereotype.Component;


@Component
public class CommentUtils {

    public boolean checkScore(int score) {
        if (0 < score && score < 6) {
            return true;
        }
        return false;
    }


}


