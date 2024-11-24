package com.hyfata.sonichub.terminal;

import com.hyfata.sonichub.Bot;
import com.hyfata.sonichub.SonicHub;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;

public class TerminalManager {
    public Terminal terminal;
    public LineReader lineReader;
    private Bot bot;

    public TerminalManager() throws IOException {
        terminal = TerminalBuilder.terminal();
        lineReader = LineReaderBuilder.builder().terminal(terminal).build();
    }

    public void startInputThread(Bot bot) {
        this.bot = bot;

        Thread messageThread = new Thread(() -> {
            while (true) {
                String line = lineReader.readLine("> ");
                onInput(line);
            }
        });
        messageThread.start();
    }

    private void onInput(String line) {
        if (line.isEmpty())
            return;

        SonicHub.LOG.info("입력받음 : {}", line);
    }
}
