package com.zukxu.upload_big_file.callable;


import com.zukxu.upload_big_file.context.UploadContext;
import com.zukxu.upload_big_file.enu.UploadModeEnum;
import com.zukxu.upload_big_file.po.FileUpload;
import com.zukxu.upload_big_file.po.FileUploadRequest;

import java.util.concurrent.Callable;

public class FileCallable implements Callable<FileUpload> {

    private UploadModeEnum mode;

    private FileUploadRequest param;

    public FileCallable(UploadModeEnum mode,
                        FileUploadRequest param) {

        this.mode = mode;
        this.param = param;
    }

    @Override
    public FileUpload call() {
        return UploadContext.INSTANCE.getInstance(mode).sliceUpload(param);
    }

}
