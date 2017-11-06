package shine.spring.dao;

import org.apache.ibatis.annotations.Param;
import shine.spring.dao.model.Video;

import java.util.List;

public interface VideoMapper {

    int deleteByPrimaryKey(Integer vid);

    int insert(Video record);

    int insertSelective(Video record);

    Video selectByPrimaryKey(Integer vid);

    int updateByPrimaryKeySelective(Video record);

    int updateByPrimaryKey(Video record);

    List<Video> page(@Param("name") String name);

    int delete(Integer vid);
}