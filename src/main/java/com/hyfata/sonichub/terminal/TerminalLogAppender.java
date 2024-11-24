package com.hyfata.sonichub.terminal;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.ThrowableProxyUtil;
import ch.qos.logback.core.AppenderBase;
import com.hyfata.sonichub.SonicHub;
import org.jline.reader.LineReader;
import org.jline.utils.InfoCmp.Capability;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TerminalLogAppender extends AppenderBase<ILoggingEvent> {

    @Override
    protected void append(ILoggingEvent eventObject) {
        if (SonicHub.getTerminalManager().terminal != null) {
            // set time format
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            String formattedDate = dateFormat.format(new Date(eventObject.getTimeStamp()));

            // logger name
            String loggerName = eventObject.getLoggerName();
            if (loggerName.contains(".")) {
                loggerName = loggerName.substring(loggerName.lastIndexOf('.') + 1);
            }

            // set Log message
            String message = eventObject.getFormattedMessage();

            // exception message
            String exceptionMessage = "";
            if (eventObject.getThrowableProxy() != null) {
                exceptionMessage = ThrowableProxyUtil.asString(eventObject.getThrowableProxy());
            }

            // result message
            String logOutput = String.format("[%s] [%s] [%s]: %s%s",
                    formattedDate,
                    eventObject.getLevel(),
                    loggerName,
                    message,
                    exceptionMessage.isEmpty() ? "" : "\n" + exceptionMessage);
            try {
                print(logOutput);
            } catch (Exception e) {
                addError("Error writing to terminal", e);
            }
        }
    }

    private void print(String string) {
        // move cursor to front
        SonicHub.getTerminalManager().terminal.writer().print("\033[1G");
        SonicHub.getTerminalManager().terminal.flush();

        // remove current line
        SonicHub.getTerminalManager().terminal.puts(Capability.clr_eol);
        SonicHub.getTerminalManager().terminal.writer().println(string);
        SonicHub.getTerminalManager().terminal.flush();

        // allow user input to continue
        SonicHub.getTerminalManager().lineReader.callWidget(LineReader.REDRAW_LINE);
        SonicHub.getTerminalManager().lineReader.callWidget(LineReader.REDISPLAY);
    }
}
