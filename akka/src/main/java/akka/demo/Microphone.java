package akka.demo;

import java.io.Serializable;

/**
 * @author hq
 * @Description: 麦克风
 * @date 2017年10月17日
 * @since v1.0.0
 */
public class Microphone implements Serializable{

    public final String who;

    public Microphone(String who) {
        this.who = who;
    }
}
