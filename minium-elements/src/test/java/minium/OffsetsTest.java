package minium;

import static minium.Offsets.at;
import static minium.Offsets.HorizontalReference.RIGHT;
import static minium.Offsets.VerticalReference.BOTTOM;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import minium.Offsets.Offset;
import minium.Offsets.ParseException;

import org.junit.Test;

public class OffsetsTest {
    @Test
    public void parse_ok() {
        Offset rb1 = at("right bottom");
        Offset rb2 = at("right+20% bottom+25%");
        Offset rb3 = at("right-20% bottom-25%");
        Offset rb4 = at("right+5px bottom+2px");
        Offset rb5 = at("right-5px bottom-2px");

        Offset tl1 = at("left top");
        Offset tl2 = at("left+20% top+25%");
        Offset tl3 = at("left-20% top-25%");
        Offset tl4 = at("left+5px top+2px");
        Offset tl5 = at("left-5px top-2px");

        Offset cc1 = at("center center");
        Offset cc2 = at("center+20% center+25%");
        Offset cc3 = at("center-20% center-25%");
        Offset cc4 = at("center+5px center+2px");
        Offset cc5 = at("center-5px center-2px");

        Offset ii1 = at("-inf -inf");
        Offset ii2 = at("+inf -inf");
        Offset ii3 = at("-inf +inf");

        Rectangle r = new Rectangle(200, 100, 150, 160);

        assertThat(rb1.apply(r), equalTo(new Point(350, 260)));
        assertThat(rb2.apply(r), equalTo(new Point(380, 300)));
        assertThat(rb3.apply(r), equalTo(new Point(320, 220)));
        assertThat(rb4.apply(r), equalTo(new Point(355, 262)));
        assertThat(rb5.apply(r), equalTo(new Point(345, 258)));

        assertThat(tl1.apply(r), equalTo(new Point(200, 100)));
        assertThat(tl2.apply(r), equalTo(new Point(230, 140)));
        assertThat(tl3.apply(r), equalTo(new Point(170, 60)));
        assertThat(tl4.apply(r), equalTo(new Point(205, 102)));
        assertThat(tl5.apply(r), equalTo(new Point(195, 98)));

        assertThat(cc1.apply(r), equalTo(new Point(275, 180)));
        assertThat(cc2.apply(r), equalTo(new Point(305, 220)));
        assertThat(cc3.apply(r), equalTo(new Point(245, 140)));
        assertThat(cc4.apply(r), equalTo(new Point(280, 182)));
        assertThat(cc5.apply(r), equalTo(new Point(270, 178)));

        assertThat(ii1.apply(r), equalTo(new Point(Integer.MIN_VALUE, Integer.MIN_VALUE)));
        assertThat(ii2.apply(r), equalTo(new Point(Integer.MAX_VALUE, Integer.MIN_VALUE)));
        assertThat(ii3.apply(r), equalTo(new Point(Integer.MIN_VALUE, Integer.MAX_VALUE)));
    }

    @Test(expected = ParseException.class)
    public void parse_vertical_first_fail() {
        at("top right");
    }

    @Test(expected = ParseException.class)
    public void parse_invalid_fail() {
        at("right20px top");
    }

    @Test
    public void to_string() {
        assertThat(at(RIGHT.plus(20).percent(), BOTTOM.minus(25).pixels()).toString(), equalTo("right+20% bottom-25px"));
    }
}
