package any.com.anyrxbus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import any.com.rxbus.RxBus;
import any.com.rxbus.RxConsumer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RxBus.getInstance().post(new RxTextEvent("tests"));

        RxBus.getInstance().subscribe(this, RxTextEvent.class, new RxConsumer<RxTextEvent>() {
            @Override
            public void handler(RxTextEvent o) {

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unRegister(this);
        RxBus.getInstance().onDestroy();
    }
}
