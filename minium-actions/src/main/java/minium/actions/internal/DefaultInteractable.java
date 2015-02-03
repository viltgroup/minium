package minium.actions.internal;

import platypus.Mixin;
import minium.Elements;
import minium.Offsets.Offset;
import minium.actions.Interactable;
import minium.actions.InteractionPerformer;
import minium.actions.Keys;

public class DefaultInteractable extends Mixin.Impl implements Interactable {

    private final InteractionPerformer performer;

    public DefaultInteractable(InteractionPerformer performer) {
       this.performer = performer;
    }

    @Override
    public void close() {
        performer.close(asElements());
    }

    @Override
    public void scrollIntoView() {
        performer.scrollIntoView(asElements());
    }

    @Override
    public void clear() {
        performer.clear(asElements());
    }

    @Override
    public void submit() {
        performer.submit(asElements());
    }

    @Override
    public void keyDown(Keys keys) {
        performer.keyDown(asElements(), keys);
    }

    @Override
    public void keyUp(Keys keys) {
        performer.keyUp(asElements(), keys);
    }

    @Override
    public void sendKeys(CharSequence... keys) {
        performer.sendKeys(asElements(), keys);
    }

    @Override
    public void clickAndHold() {
        performer.clickAndHold(asElements());
    }

    @Override
    public void clickAndHold(Offset offset) {
        performer.clickAndHold(asElements(), offset);
    }

    @Override
    public void release() {
        performer.release(asElements());
    }

    @Override
    public void release(Offset offset) {
        performer.release(asElements(), offset);
    }

    @Override
    public void click() {
        performer.click(asElements());
    }

    @Override
    public void doubleClick() {
        performer.doubleClick(asElements());
    }

    @Override
    public void moveTo() {
        performer.moveTo(asElements());
    }

    @Override
    public void moveTo(Offset offset) {
        performer.moveTo(asElements(), offset);
    }

    @Override
    public void contextClick() {
        performer.contextClick(asElements());
    }

    @Override
    public void dragAndDrop(Elements target) {
        performer.dragAndDrop(asElements(), target);
    }

    @Override
    public void clickAll() {
        performer.clickAll(asElements());
    }

    @Override
    public void type(CharSequence text) {
        performer.type(asElements(), text);
    }

    @Override
    public void fill(CharSequence text) {
        performer.fill(asElements(), text);
    }

    @Override
    public void check() {
        performer.check(asElements());
    }

    @Override
    public void uncheck() {
        performer.uncheck(asElements());
    }

    @Override
    public void select(String text) {
        performer.select(asElements(), text);
    }

    @Override
    public void deselect(String text) {
        performer.deselect(asElements(), text);
    }

    @Override
    public void selectVal(String text) {
        performer.selectVal(asElements(), text);
    }

    @Override
    public void deselectVal(String text) {
        performer.deselectVal(asElements(), text);
    }

    @Override
    public void selectAll() {
        performer.selectAll(asElements());
    }

    @Override
    public void deselectAll() {
        performer.deselectAll(asElements());
    }

    @Override
    public void waitUntilClosed() {
        performer.waitUntilClosed(asElements());
    }

    private Elements asElements() {
        return this.as(Elements.class);
    }

}
