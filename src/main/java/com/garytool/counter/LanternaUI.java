package com.garytool.counter;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import javax.swing.*;
import java.io.IOException;

public class LanternaUI {
    private DefaultTerminalFactory defaultTerminalFactory;
    private Screen screen;
    private Terminal terminal;
    private TextGraphics textGraphics;
    private JsonService jsonService;

    public LanternaUI() {

        this.defaultTerminalFactory = new DefaultTerminalFactory();
        try {
            this.jsonService = new JsonService();
            Terminal terminal = this.defaultTerminalFactory.createTerminal();
            this.screen = new TerminalScreen(terminal);
            this.textGraphics = screen.newTextGraphics();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void runUI() {
        try {
            screen.startScreen();

            while(true) {
                homeScreen();
                KeyStroke keyStroke = screen.pollInput();
                if(keyStroke != null && keyStroke.getCharacter() == 'q') {
                    break;
                } else if(keyStroke != null && keyStroke.getKeyType() == KeyType.Enter) {
                    boolean testFlag = false;
                    while(true) {
//                        Timer timer = new Timer();
//                        timer.start();
                        countScreen();
                        keyStroke = screen.readInput();
                        if(keyStroke != null && keyStroke.getKeyType() == KeyType.Enter) {
                            if(!testFlag) {
//                                timer.suspend();
                                testFlag = true;
                                updateNote("timer is suspended, press enter to resume ...");
                            } else {
//                                timer.resume();
                                testFlag = false;
                                updateNote("press enter to pause, stop to end ...");
                            }
                        } else if(keyStroke != null && keyStroke.getCharacter() == 's') {
//                            timer.complete();
//                            jsonService.update(timer);
                            break;
                        }
                        Thread.yield();
                    }
                }
                Thread.yield();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        finally{
            if(screen != null) {
                try {
                    screen.close();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void homeScreen() throws IOException {
        TextGraphics textGraphics = screen.newTextGraphics();
        TerminalPosition startPoint = new TerminalPosition(1, 1);
        textGraphics.putString(startPoint, " ==== Study Counter ==== ");
//        textGraphics.putString(startPoint.withRelative(-1, 1), "current time: " + new Timer().getStartTimeStr());
        textGraphics.putString(startPoint.withRelative(-1, 1), "current time: testing");
        textGraphics.putString(startPoint.withRelative(-1, 2), "press enter to start, q to end ...");
        screen.refresh();
    }

    public void countScreen() throws IOException {
        TerminalPosition startPoint = new TerminalPosition(1, 1);
        screen.clear();
        textGraphics.putString(startPoint, " ==== Start Counting ==== ");
//        textGraphics.putString(startPoint.withRelative(-1, 1), "start time: " + new Timer().getStartTimeStr());
//        textGraphics.putString(startPoint.withRelative(-1, 2), "end time: " + new Timer().getStartTimeStr());
        textGraphics.putString(startPoint.withRelative(-1, 1), "start time: testing");
        textGraphics.putString(startPoint.withRelative(-1, 2), "end time: testing");
        textGraphics.putString(startPoint.withRelative(-1, 3), "time slot: ");
        textGraphics.putString(startPoint.withRelative(-1, 4), "press enter to pause, s to stop ...");
        screen.refresh();
    }

    public void updateNote(String msg) throws IOException {
        TerminalSize terminalSize = screen.getTerminalSize();
        TerminalPosition startLine = new TerminalPosition(0, 5);
        TerminalPosition endLine = startLine.withRelative(terminalSize.getColumns() - 1, 0);
        textGraphics.drawLine(startLine, endLine, ' ');
        screen.refresh();
        textGraphics.putString(startLine, msg);
        screen.refresh();
    }
}
