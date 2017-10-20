package akka.remote.server;

/**
 * @author hq
 * @Description: Result
 * @date 2017年10月20日
 * @since v1.0.0
 */
public class Result {

    private String word;
    private Integer time;

    public Result(String word, Integer time) {
        this.word = word;
        this.time = time;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }
}
