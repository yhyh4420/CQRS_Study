package observability;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.StackTraceElementProxy;
import ch.qos.logback.core.LayoutBase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class JsonLayout extends LayoutBase<ILoggingEvent> {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final DateTimeFormatter dateTimeFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSXXX")
                    .withZone(ZoneId.of("Asia/Seoul"));

    private final boolean includeCallerData = false;
    private final boolean includeContext = true;
    private final boolean includeMDC = true;


    @Override
    public String doLayout(ILoggingEvent iLoggingEvent) {
        ObjectNode log = objectMapper.createObjectNode();
        log.put("thread", iLoggingEvent.getThreadName());
        IThrowableProxy throwableProxy = iLoggingEvent.getThrowableProxy();
        if (throwableProxy != null) {
            log.put("exception_class", throwableProxy.getClassName());
            log.put("exception_message", throwableProxy.getMessage());

            StackTraceElementProxy[] stackTrace = throwableProxy.getStackTraceElementProxyArray();
            if (stackTrace != null && stackTrace.length > 0) {
                StackTraceElementProxy stackTraceElement = stackTrace[0];
                StackTraceElement ste = stackTraceElement.getStackTraceElement();
                log.put("exception_location", ste.getFileName());
                log.put("exception_line", ste.getLineNumber());
            }
        }
        log.put("timestamp", dateTimeFormatter.format(Instant.ofEpochMilli(iLoggingEvent.getTimeStamp())));
        log.put("level", iLoggingEvent.getLevel().toString());
        log.put("message", iLoggingEvent.getMessage());

        if (isIncludeMDC() && iLoggingEvent.getMDCPropertyMap() != null) {
            ObjectNode mdc = objectMapper.createObjectNode();
            for (Map.Entry<String, String> entry : iLoggingEvent.getMDCPropertyMap().entrySet()) {
                mdc.put(entry.getKey(), entry.getValue());
            }
            log.set("mdc", mdc);
        }
        try {
            return objectMapper.writeValueAsString(log) + "\n";
        } catch (JsonProcessingException e) {
            addError("Error while trying to serialize log", e);
            return "Error while trying to serialize log" + "\n";
        }
    }



    private boolean isIncludeMDC() {
        return includeMDC;
    }


}
