package minium.visual;

import java.util.Iterator;

import minium.visual.internal.SikuliInitializer;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Match;
import org.sikuli.script.Screen;

import com.google.common.collect.Iterators;

public class SikuliTest {
    public static void main(String[] args) throws FindFailed {
        SikuliInitializer.init();
        Screen screen = new Screen();
        Iterator<Match> matches = screen.findAllText("Rui Figueira");
        Match match = Iterators.getNext(matches, null);
        match.click();
    }
}

