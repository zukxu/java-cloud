package com.zukxu.upload_big_file.strategy;

import com.zukxu.upload_big_file.po.FileUpload;
import com.zukxu.upload_big_file.po.FileUploadRequest;

public interface SliceUploadStrategy {

    FileUpload sliceUpload(FileUploadRequest param);

}
