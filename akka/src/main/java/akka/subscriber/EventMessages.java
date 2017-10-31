package akka.subscriber;

import java.io.Serializable;

/**
 * 定义几个子系统间传递的消息
 */
public interface EventMessages {


    class EventMessage implements Serializable {

        private static final long serialVersionUID = 7700567442706159321L;
    }

    /**
     * 内存中的Nginx的日志
     */
    class RawNginxRecord extends EventMessage {

        private static final long serialVersionUID = 1580080393836627143L;
        private String sourceHost;
        private String line;

        public RawNginxRecord(String sourceHost, String line) {
            this.sourceHost = sourceHost;
            this.line = line;
        }

        public String getLine() {
            return line;
        }

        public String getSourceHost() {
            return sourceHost;
        }
    }

    /**
     * 解析出了事件内容的Nginx记录
     */
    class NginxRecord extends EventMessage {

        private static final long serialVersionUID = -1734011304188217638L;
        private String sourceHost;
        private String line;
        private String eventCode;

        public NginxRecord(String sourceHost, String line, String eventCode) {
            this.sourceHost = sourceHost;
            this.line = line;
            this.eventCode = eventCode;
        }

        public String getSourceHost() {
            return sourceHost;
        }

        public String getLine() {
            return line;
        }

        public String getEventCode() {
            return eventCode;
        }
    }

    /**
     * 通过了拦截器的日志记录
     */
    class FilteredRecord extends EventMessage {

        private static final long serialVersionUID = -9043362136625128295L;
        private String sourceHost;
        private String line;
        private String eventCode;
        private String logDate;
        private String realIp;

        public FilteredRecord(String sourceHost, String line, String eventCode, String logDate, String realIp) {
            this.sourceHost = sourceHost;
            this.line = line;
            this.eventCode = eventCode;
            this.logDate = logDate;
            this.realIp = realIp;
        }

        public String getSourceHost() {
            return sourceHost;
        }

        public String getLine() {
            return line;
        }

        public String getEventCode() {
            return eventCode;
        }

        public String getLogDate() {
            return logDate;
        }

        public String getRealIp() {
            return realIp;
        }
    }

    /**
     * 子系统注册的消息
     */
    final class Registration implements Serializable {
    }
}
