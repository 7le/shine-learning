package shine.spring.service;

import org.springframework.web.multipart.MultipartFile;
import shine.spring.dao.model.Video;

import java.util.List;


public interface VideoService {

    /**
     * 列表页
     * @param name
     * @return
     */
    String page(String name);

    /**
     * 查询单个
     * @param vid
     * @return
     */
    String selectOne(Integer vid);

    /**
     * 上传
     * @param file
     */
    void upload(Integer id, MultipartFile file, String name) throws Exception;

    /**
     * 上传
     * @param file
     */
    void transCode(Integer id, MultipartFile file) throws Exception;

    /**
     * 删除
     */
    void delete(Integer vid) throws Exception;
}
