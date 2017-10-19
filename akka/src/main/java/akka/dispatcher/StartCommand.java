package akka.dispatcher;

import java.io.Serializable;

/**
 * @author hq
 * @date 2017年10月19日
 * @since v1.0.0
 */
public class StartCommand implements Serializable {

    private int actorCount = 0;

    public StartCommand() {
    }

    public StartCommand(int actorCount) {
        this.actorCount = actorCount;
    }

    public int getActorCount() {
        return actorCount;
    }

    public void setActorCount(int actorCount) {
        this.actorCount = actorCount;
    }
}
