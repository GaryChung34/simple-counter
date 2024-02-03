package com.garytool.counter;

import com.garytool.counter.models.DayRecord;
import com.garytool.counter.models.StudyUnit;
import com.garytool.counter.ui.SmallBattery;
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
import java.util.List;

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
                KeyStroke keyStroke = screen.readInput();
                if(keyStroke != null && keyStroke.getCharacter() == 'q') {
                    break;
                } else if(keyStroke != null && keyStroke.getKeyType() == KeyType.Enter) {
                    boolean testFlag = false;
                    Timer timer = new Timer();
                    countScreen(timer);
                    timer.start();
                    while(true) {
                        keyStroke = screen.readInput();
                        if(keyStroke != null && keyStroke.getKeyType() == KeyType.Enter) {
                            if(!timer.isSuspend()) {
                                timer.suspend();
                                testFlag = true;
                                updateNote("timer is suspended, press enter to resume ...");
                            } else {
                                timer.resume();
                                testFlag = false;
                                updateNote("press enter to pause, s to end ...");
                            }
                        } else if(keyStroke != null && keyStroke.getCharacter() == 's') {
                            timer.complete();
                            jsonService.update(timer);
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
        TerminalPosition startPoint = new TerminalPosition(1, 1);
        screen.clear();
        textGraphics.putString(startPoint, " ==== Study Counter ==== ");
        textGraphics.putString(startPoint.withRelative(-1, 1), "current time: " + new Timer().getStartTimeStr());
//        textGraphics.putString(startPoint.withRelative(-1, 1), "current time: testing");
        textGraphics.putString(startPoint.withRelative(-1, 2), "press enter to start, q to end ...");
        textGraphics.putString(startPoint.withRelative(-1, 3), getTodayUnit());
        screen.refresh();
    }

    public void countScreen(Timer timer) throws IOException {
        TerminalPosition startPoint = new TerminalPosition(1, 1);
        screen.clear();
        textGraphics.putString(startPoint, " ==== Start Counting ==== ");
        textGraphics.putString(startPoint.withRelative(-1, 1), "start time: " + timer.getStartTimeStr());
        textGraphics.putString(startPoint.withRelative(-1, 2), "end time: " + timer.getStartTimeStr());
//        textGraphics.putString(startPoint.withRelative(-1, 1), "start time: testing");
//        textGraphics.putString(startPoint.withRelative(-1, 2), "end time: testing");
        textGraphics.putString(startPoint.withRelative(-1, 3), "time slot: " + timer.getTimeSlot(true));
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

    public String getTodayUnit() throws IOException {
        DayRecord dayRecord = jsonService.readTodayLog();
        List<StudyUnit> units = dayRecord.getUnits();
        StringBuilder sb = new StringBuilder();
        units.forEach(unit -> {
            SmallBattery battery = new SmallBattery(unit);
            sb.append(unit.getTimeSlot()).append(battery.toBatteryStr()).append(" ");
        });
        return sb.toString();
    }
}
