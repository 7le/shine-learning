package com.java.httpClient;

public class RequestResult {
    private String content; // 返回内容
    private int returnCode; // 返回码
    private byte[] stream; //用于返回二进制文件。如图像。
    private String contentType;

    public String getContentType() {
        return contentType;
    }
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    public byte[] getStream() {
        return stream;
    }
    public void setStream(byte[] stream) {
        this.stream = stream;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public int getReturnCode() {
        return returnCode;
    }
    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    public boolean isBinaryStream() {
        //FIXME:暂时只添加image的支持
        return contentType != null && contentType.startsWith("image");
    }
}
