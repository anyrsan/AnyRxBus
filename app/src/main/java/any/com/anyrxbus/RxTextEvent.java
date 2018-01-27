package any.com.anyrxbus;

import any.com.rxbus.RxEvent;

/**
 * Created by anyrsan on 2018/1/27.
 */

public class RxTextEvent extends RxEvent {

    public String text;

    public RxTextEvent(String text) {
        this.text = text;
    }
}
