package com.chaolj.core.fileProvider;

import com.alibaba.fastjson.TypeReference;
import com.chaolj.core.MyConst;
import com.chaolj.core.MyUser;
import org.springframework.context.ApplicationContext;
import java.util.List;
import com.chaolj.core.MyApp;
import com.chaolj.core.commonUtils.myDto.DataResultDto;
import com.chaolj.core.commonUtils.myServer.Interface.IFileServer;
import com.chaolj.core.commonUtils.myServer.Models.FileObjectDto;

public class MyFileProvider implements IFileServer {
    private ApplicationContext applicationContext;
    private MyFileProviderProperties properties;

    public MyFileProvider(ApplicationContext applicationContext, MyFileProviderProperties properties) {
        this.applicationContext = applicationContext;
        this.properties = properties;
    }

    private String getHost() {
        return this.properties.getServerHostUrl();
    }

    private String getSSOUserToken() {
        return MyUser.getCurrentUserToken();
    }

    @Deprecated
    public DataResultDto<String> CreatePath(String path) {
        return DataResultDto.error("不支持的方法");
    }

    @Deprecated
    public DataResultDto<String> MovePath(String sourcePath, String targetPath) {
        return DataResultDto.error("不支持的方法");
    }

    @Deprecated
    public DataResultDto<String> DeletePath(String path) {
        return DataResultDto.error("不支持的方法");
    }

    public DataResultDto<String> DeleteFile(String path, String fileName) {
        DataResultDto<String> myresult;

        var url = this.getHost() + "/api/file/deletefile/";
        try {
            myresult = MyApp.Http().url(url)
                    .header(MyConst.HEADERKEY_TOKEN, this.getSSOUserToken())
                    .buildGet().query("path", path).query("fileName", fileName)
                    .execute().toJavaObject(new TypeReference<>() {});
        } catch (Exception ex) {
            myresult = DataResultDto.Empty();
            myresult.setResult(false);
            myresult.setMessage("FileServer.DeleteFile，处理失败！" + ex.getMessage());
            myresult.setData("");
        }

        return myresult;
    }

    public DataResultDto<FileObjectDto> GetFile(String path, String fileName) {
        DataResultDto<FileObjectDto> myresult;

        var url = this.getHost() + "/api/file/getfile/";
        try {
            myresult = MyApp.Http().url(url)
                    .header(MyConst.HEADERKEY_TOKEN, this.getSSOUserToken())
                    .buildGet().query("path", path).query("fileName", fileName)
                    .execute().toJavaObject(new TypeReference<>() {});
        } catch (Exception ex) {
            myresult = DataResultDto.Empty();
            myresult.setResult(false);
            myresult.setMessage("FileServer.DeleteFile，处理失败！" + ex.getMessage());
            myresult.setData(null);
        }

        return myresult;
    }

    public DataResultDto<List<FileObjectDto>> GetFiles(String path, boolean recursive, String searchPattern) {
        DataResultDto<List<FileObjectDto>> myresult;

        var url = this.getHost() + "/api/file/getfiles/";
        try {
            myresult = MyApp.Http().url(url)
                    .header(MyConst.HEADERKEY_TOKEN, this.getSSOUserToken())
                    .buildGet()
                    .query("path", path)
                    .query("recursive", String.valueOf(recursive))
                    .query("searchPattern", searchPattern)
                    .execute().toJavaObject(new TypeReference<>() {});
        } catch (Exception ex) {
            myresult = DataResultDto.Empty();
            myresult.setResult(false);
            myresult.setMessage("FileServer.GetFiles，处理失败！" + ex.getMessage());
            myresult.setData(null);
        }

        return myresult;
    }

    public DataResultDto<List<FileObjectDto>> GetFiles(String path) {
        return this.GetFiles(path, true, "*.*");
    }

    public DataResultDto<FileObjectDto> Upload(String path, String fileName, byte[] fileBytes) {
        DataResultDto<FileObjectDto> myresult;

        var url = this.getHost() + "/api/file/upload/";
        try {
            myresult = MyApp.Http().url(url)
                    .header(MyConst.HEADERKEY_TOKEN, this.getSSOUserToken())
                    .buildPostBytes().query("path", path).query("fileName", fileName)
                    .file(fileBytes, fileName)
                    .execute().toJavaObject(new TypeReference<>() {});
        } catch (Exception ex) {
            myresult = DataResultDto.Empty();
            myresult.setResult(false);
            myresult.setMessage("FileServer.Upload，处理失败！" + ex.getMessage());
            myresult.setData(null);
        }

        return myresult;
    }

    public DataResultDto<byte[]> Download(String path, String fileName) {
        DataResultDto<byte[]> myresult;

        var url = this.getHost() + "/api/file/download/";
        try {
            var fileBytes = MyApp.Http().url(url)
                    .header(MyConst.HEADERKEY_TOKEN, this.getSSOUserToken())
                    .buildGet().query("path", path).query("fileName", fileName)
                    .execute().getBodyBytes();

            myresult = DataResultDto.Empty();
            myresult.setResult(true);
            myresult.setData(fileBytes);
        } catch (Exception ex) {
            myresult = DataResultDto.Empty();
            myresult.setResult(false);
            myresult.setMessage("FileServer.Download，处理失败！" + ex.getMessage());
            myresult.setData(null);
        }

        return myresult;
    }

    public DataResultDto<byte[]> GetThumbnail(String path, String fileName, Integer width, Integer height) {
        DataResultDto<byte[]> myresult;

        var url = this.getHost() + "/api/file/getthumbnail/";
        try {
            var fileBytes = MyApp.Http().url(url)
                    .header(MyConst.HEADERKEY_TOKEN, this.getSSOUserToken())
                    .buildGet()
                    .query("path", path)
                    .query("fileName", fileName)
                    .query("width", width)
                    .query("height", height)
                    .execute().getBodyBytes();

            myresult = DataResultDto.Empty();
            myresult.setResult(true);
            myresult.setData(fileBytes);
        } catch (Exception ex) {
            myresult = DataResultDto.Empty();
            myresult.setResult(false);
            myresult.setMessage("FileServer.GetThumbnail，处理失败！" + ex.getMessage());
            myresult.setData(null);
        }

        return myresult;
    }

    public DataResultDto<byte[]> GetThumbnail(String path, String fileName) {
        return this.GetThumbnail(path, fileName, 128, 128);
    }
}
