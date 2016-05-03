package com.ttnd.reactivedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.widget.OnTextChangeEvent;
import rx.android.widget.WidgetObservable;
import rx.functions.Action1;
import rx.functions.Func1;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /**
         * just operator of observable
         */

        Observable<String> myObservable = Observable.just("Hello"); // Emits "Hello"


        Observer<String> myObserver = new Observer<String>() {
            @Override
            public void onCompleted() {
                // Called when the observable has no more data to emit
            }

            @Override
            public void onError(Throwable e) {
                // Called when the observable encounters an error
            }

            @Override
            public void onNext(String s) {
                // Called each time the observable emits data
                Log.d("MY OBSERVER..........", s);
            }
        };

        Subscription mySubscription = myObservable.subscribe(myObserver);


        /**
         * from operator of Observable
         * Action1 interface
         */


        Observable<Integer> myArrayObservable = Observable.from(new Integer[]{1, 2, 3, 4, 5, 6}); // Emits each item of the array, one at a time

        myArrayObservable.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer i) {
                Log.d("My Action...........", String.valueOf(i)); // Prints the number received
            }
        });


        /**
         * Map operator of observable
         * Func1 interface
         */

        myArrayObservable = myArrayObservable.map(new Func1<Integer, Integer>() { // Input and Output are both Integer
            @Override
            public Integer call(Integer integer) {
                return integer * integer; // Square the number
            }
        });


        /**
         * Skip() operator of observable
         */

        myArrayObservable
                .skip(2) // Skip the first two items
                .filter(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) { // Ignores any item that returns false
                        return integer % 2 == 0;
                    }
                });

        Observable<String> helloObservable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {
                        sub.onNext("Hello, World!..................");
                        sub.onCompleted();
                    };
                });
    }

    @Override
    protected void onStart() {
        super.onStart();

//        Observable<OnTextChangeEvent> userNameText =
//                WidgetObservable.text((EditText) findViewById(R.id.edtUserName));

       /* userNameText.subscribe(new Observer<OnTextChangeEvent>() {
            @Override
            public void onCompleted() {
                System.out.println("Here in On Complition............");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("Here is error...............");
            }

            @Override
            public void onNext(OnTextChangeEvent onTextChangeEvent) {
                Log.d("[Rx]", onTextChangeEvent.toString());

            }
        });*/

//        userNameText.subscribe( e -> Log.d("[Rx]...........", e.text().toString()));
//        userNameText.filter( e -> e.text().length() > 4)
//                .subscribe( e -> Log.d("[Rx]", e.text().toString()));
    }
}
