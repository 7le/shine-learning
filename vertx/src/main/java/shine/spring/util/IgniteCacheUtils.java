package shine.spring.util;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.eviction.fifo.FifoEvictionPolicy;
import org.apache.ignite.configuration.CacheConfiguration;
import shine.ignite.example.sql.Store;
import shine.spring.dao.model.Video;

import javax.cache.configuration.Factory;
import javax.cache.configuration.FactoryBuilder;
import javax.cache.expiry.*;
import java.util.concurrent.TimeUnit;

/**
 * IgniteCacheUtils
 * Created by 7le on 2017/11/27
 */
public class IgniteCacheUtils {

    private static IgniteCache<String, String> cache;

    private static IgniteCache<Integer, Video> videoCache;

    private static <K, V> IgniteCache<K, V> getCache(Ignite ignite) {
        CacheConfiguration<K, V> ccfg = newConfig("cache", 2 * 60 * 60L, CachePolicy.CREATED_EXPIRY_POLICY);
        return ignite.getOrCreateCache(ccfg);
    }

    private static IgniteCache getVideoCache(Ignite ignite) {
        CacheConfiguration<Integer, Video> cacheCfg = new CacheConfiguration<>("videoCache");

        // Set atomicity as transaction, since we are showing transactions in example.
        //cacheCfg.setAtomicityMode(TRANSACTIONAL);
        // 设置 JDBC store.
        cacheCfg.setCacheStoreFactory(FactoryBuilder.factoryOf(Store.class));
        cacheCfg.setIndexedTypes(Integer.class, Video.class);
        cacheCfg.setReadThrough(true);
        cacheCfg.setWriteThrough(true);

        return ignite.getOrCreateCache(cacheCfg);
    }

    private static <K, V> CacheConfiguration<K, V> newConfig(String name, long durationAmount, CachePolicy policy) {

        // 缓存配置
        CacheConfiguration<K, V> cacheCfg = new CacheConfiguration<K, V>();
        cacheCfg.setName(name);

        // 堆内缓存先进先出删除策略，参数1000表示堆内最多存储1000条记录
        cacheCfg.setOnheapCacheEnabled(true);
        cacheCfg.setEvictionPolicy(new FifoEvictionPolicy(1000));

        //设置节点备份数量
        cacheCfg.setBackups(2);

        cacheCfg.setExpiryPolicyFactory(getExpiryPolicy(policy, durationAmount));
        return cacheCfg;
    }

    private static Factory<ExpiryPolicy> getExpiryPolicy(CachePolicy policy, long durationAmount) {
        switch (policy) {
            case CREATED_EXPIRY_POLICY:
                return CreatedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS, durationAmount));
            case TOUCHED_EXPIRY_POLICY:
                return TouchedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS, durationAmount));
            case MODIFIED_EXPIRY_POLICY:
                return ModifiedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS, durationAmount));
            default:
                return CreatedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS, durationAmount));
        }
    }

    /**
     * 初始化ignite缓存
     *
     * @param ignite
     */
    public static void initIgniteCache(Ignite ignite) {
        cache = getCache(ignite);
        videoCache = getVideoCache(ignite);
    }

    public static void putCache(String key, String value) {
        cache.put(key, value);
    }

    public static String getCache(String key) {
        return cache.get(key);
    }

    /**
     * 缓存策略
     */
    private enum CachePolicy {
        CREATED_EXPIRY_POLICY("create"),
        TOUCHED_EXPIRY_POLICY("touch"),
        MODIFIED_EXPIRY_POLICY("update");
        private String desc;

        private CachePolicy(String desc) {
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

    }

    public static IgniteCache getVideo(){
        return videoCache;
    }
}
