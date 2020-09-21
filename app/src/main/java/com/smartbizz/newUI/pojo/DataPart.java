package com.smartbizz.newUI.pojo;


import com.smartbizz.Util.FileUtil;

import java.io.File;

public class DataPart {
    private String filePath;
    private byte[] content;
    private File file;
    private String type;

    public DataPart(String filePath) {
        this.file = new File(filePath);
        this.filePath = filePath;
        content = FileUtil.getByteArray(file);
        type = FileUtil.getFileExtensionType(file.getAbsolutePath());
    }

    public String getFileName() {
        return filePath;
    }

    public byte[] getContent() {
        return content;
    }

    public String getType() {
        return type;
    }
}
