package shine.ignite.example.sql;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ignite.cache.store.CacheStoreAdapter;
import org.apache.ignite.lang.IgniteBiInClosure;
import org.jetbrains.annotations.Nullable;
import shine.spring.dao.model.Video;
import shine.spring.util.SpringUtils;

import javax.cache.Cache;
import javax.cache.integration.CacheLoaderException;
import javax.cache.integration.CacheWriterException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

/**
 * Store demo for video
 * Created by 7le on 2017/11/28
 */
public class Store extends CacheStoreAdapter<Integer, Video> {

    private DruidDataSource dataSource = (DruidDataSource) SpringUtils.getBean("dataSource");

    @Override
    public void loadCache(IgniteBiInClosure<Integer, Video> clo, @Nullable Object... args) throws CacheLoaderException {
        System.out.println(">> Loading cache from store...");
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("select * from video")) {
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        Video video = new Video(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                                rs.getInt(5), rs.getDate(6), rs.getString(7), rs.getString(8));
                        clo.apply(video.getVid(), video);
                    }
                }
            }
        } catch (SQLException e) {
            throw new CacheLoaderException("Failed to load values from cache store.", e);
        }
    }

    @Override
    public Video load(Integer integer) throws CacheLoaderException {
        System.out.println(">> Loading person from store...");
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("select * from video where id = ?")) {
                st.setString(1, integer.toString());
                ResultSet rs = st.executeQuery();
                return rs.next() ? new Video(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getInt(5), rs.getDate(6), rs.getString(7), rs.getString(8)): null;
            }
        } catch (SQLException e) {
            throw new CacheLoaderException("Failed to load values from cache store.", e);
        }
    }

    @Override
    public Map<Integer, Video> loadAll(Iterable<? extends Integer> iterable) throws CacheLoaderException {
        return null;
    }

    @Override
    public void write(Cache.Entry<? extends Integer, ? extends Video> entry) throws CacheWriterException {

    }

    @Override
    public void writeAll(Collection<Cache.Entry<? extends Integer, ? extends Video>> collection) throws CacheWriterException {

    }

    @Override
    public void delete(Object o) throws CacheWriterException {

    }

    @Override
    public void deleteAll(Collection<?> collection) throws CacheWriterException {

    }

    @Override
    public void sessionEnd(boolean commit) throws CacheWriterException {

    }
}
