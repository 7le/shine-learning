package akka.eventbus;

/**
 * 定义消息类型
 */
public interface AllKindsOfMusic {

    class Jazz implements AllKindsOfMusic {
        final public String artist;

        public Jazz(String artist) {
            this.artist = artist;
        }
    }

    class Electronic implements AllKindsOfMusic {
        final public String artist;

        public Electronic(String artist) {
            this.artist = artist;
        }
    }
}
