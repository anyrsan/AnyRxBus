package any.com.rxbus;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by anyrsan on 2017/12/18.
 */

public class RxBus {

    private static volatile RxBus mRxBus;

    private BehaviorSubject behaviorSubject;  //

    private Map<Object, Disposable> disposableMap;

    private RxBus() {
        behaviorSubject = BehaviorSubject.create();

        disposableMap = new HashMap<>();
    }

    public static RxBus getInstance() {
        if (mRxBus == null) {
            synchronized (RxBus.class) {
                if (mRxBus == null) {
                    mRxBus = new RxBus();
                }
            }
        }
        return mRxBus;
    }


    /**
     * @param key
     * @param clz
     * @param consumer
     * @param <T>
     */
    public <T> void subscribe(Object key, Class<T> clz, RxConsumer<T> consumer) {
        Disposable disposable = behaviorSubject.ofType(clz).subscribe(consumer);
        disposableMap.put(key, disposable);
    }


    /**
     * @param key
     */
    public void unRegister(Object key) {
        Disposable disposable = disposableMap.get(key);
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    /**
     *
     * @param event
     */
    public void post(RxEvent event) {
        behaviorSubject.onNext(event);
    }

    /**
     */
    public void unregisterAll() {
        for (Disposable disposable : disposableMap.values()) {
            if (disposable != null && !disposable.isDisposed()) {
                disposable.dispose();
            }
        }
        disposableMap.clear();
    }

    /**
     *
     */
    public void onDestroy() {
        unregisterAll();
        behaviorSubject.onComplete();
    }
}
