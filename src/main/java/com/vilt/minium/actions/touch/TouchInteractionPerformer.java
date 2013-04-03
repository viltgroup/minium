package com.vilt.minium.actions.touch;

import com.vilt.minium.WebElements;
import com.vilt.minium.actions.InteractionPerformer;

/**
 * The Class TouchInteractionPerformer.
 */
public class TouchInteractionPerformer extends InteractionPerformer {
	
	// from org.openqa.selenium.interactions.touch.TouchActions
	/**
	 * Single tap.
	 *
	 * @param elements the elements
	 */
	public void singleTap(WebElements elements) {
		perform(new SingleTapInteraction(elements));
	}
	
	/**
	 * Down.
	 *
	 * @param elements the elements
	 * @param x the x
	 * @param y the y
	 */
	public void down(WebElements elements, int x, int y) {
		perform(new TouchDownInteraction(elements, x, y));
	}
	
	/**
	 * Up.
	 *
	 * @param elements the elements
	 * @param x the x
	 * @param y the y
	 */
	public void up(WebElements elements, int x, int y) {
		perform(new TouchUpInteraction(elements, x, y));
	}
	
	/**
	 * Move.
	 *
	 * @param elements the elements
	 * @param x the x
	 * @param y the y
	 */
	public void move(WebElements elements, int x, int y) {
		perform(new TouchMoveInteraction(elements, x, y));
	}
	
	/**
	 * Double tap.
	 *
	 * @param elements the elements
	 */
	public void doubleTap(WebElements elements) {
		perform(new DoubleTapInteraction(elements));
	}
	
	/**
	 * Long press.
	 *
	 * @param elements the elements
	 */
	public void longPress(WebElements elements) {
		perform(new LongPressInteraction(elements));
	}
	
	/**
	 * Scroll.
	 *
	 * @param elements the elements
	 * @param xOffset the x offset
	 * @param yOffset the y offset
	 */
	public void scroll(WebElements elements, int xOffset, int yOffset) {
		perform(new TouchScrollInteraction(elements, xOffset, yOffset));
	}
	
	/**
	 * Flick.
	 *
	 * @param elements the elements
	 * @param xSpeed the x speed
	 * @param ySpeed the y speed
	 */
	public void flick(WebElements elements, int xSpeed, int ySpeed) {
		perform(new FlickInteraction(elements, xSpeed, ySpeed));
	}
	
	/**
	 * Flick.
	 *
	 * @param elements the elements
	 * @param xOffset the x offset
	 * @param yOffset the y offset
	 * @param speed the speed
	 */
	public void flick(WebElements elements, int xOffset, int yOffset, int speed) {
		perform(new FlickInteraction(elements, xOffset, yOffset, speed));
	}
}
