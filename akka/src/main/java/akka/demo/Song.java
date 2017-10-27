package akka.demo;

import java.io.Serializable;

/**
 * @author 7le
 * @Description: 歌手唱的歌
 * @date 2017年10月17日
 * @since v1.0.0
 */
public class Song implements Serializable {

    public final String song;

    public Song(String song) {
        this.song = song;
    }
}