package com.zukxu.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Image业务类
 * </p>
 *
 * @author xupu
 * @since 2025/4/28 15:20:49
 */
@Service
public class ImageService {
    public List<String> fileToImage(MultipartFile file) {
        List<String> list = new ArrayList<String>();
        return list;
    }
}
