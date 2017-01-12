package br.com.monitoratec.app.util;

import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.Observer;

/**
 * Created by falvojr on 1/11/17.
 */
public class RxJavaSamples {
    public static void main(String[] args) {

        // 1. Creates a list
        final List<String> list = Arrays.asList("Android", "Ubuntu", "Mac OS");
        // 2. Creates the Observable
        final Observable<List<String>> listObservable = Observable.just(list);
        // 3. Registers the Observer
        listObservable.subscribe(new Observer<List<String>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(List<String> list) {
                System.out.println(list);
            }
        });

        // Filter even numbers
        Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .filter(integer -> integer % 2 == 0)
                .subscribe(System.out::println);

        // Iterating with "forEach"
        Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .forEach(System.out::println);

        // Group by
        Observable.just(1, 2, 3, 4, 5)
                .groupBy(integer -> integer % 2 == 0)
                .subscribe(grouped -> {
                    grouped.toList().subscribe(integers -> {
                        String out = integers + " (" + grouped.getKey() + ")";
                        System.out.println(out);
                    });
                });

        // Take only the first N values emitted
        Observable.just(1, 2, 3, 4, 5)
                .take(2)
                .subscribe(System.out::println);

        // First
        Observable.just(1, 2, 3, 4, 5)
                .first()
                .subscribe(System.out::println);

        // Last
        Observable.just(1, 2, 3, 4, 5)
                .last()
                .subscribe(System.out::println);

        // Map
        Observable.just("Hello world!")
                .map(String::hashCode)
                .subscribe(i -> System.out.println(Integer.toString(i)));

        // Map/Reduce
        Observable.from(Arrays.asList("Hello", "Streams", "Not")).
                filter(s -> s.contains("e")).
                map(String::toUpperCase).
                reduce(new StringBuilder(), StringBuilder::append).
                subscribe(System.out::print, e -> {
                }, () -> System.out.println("!"));

    }
}
