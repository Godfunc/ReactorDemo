package com.godfunc;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class FluxCreateTest {

    @Test
    public void createFluxByjust() {
        // 一个元素
        Flux.just("hello")
                .subscribe(System.out::println);
        // 多个元素
        Flux.just(0, 1, 2, 3, 4, 5)
                .subscribe(System.out::println);
    }

    @Test
    public void createFluxByGenerate() {
        Flux.generate(sink -> {
            sink.next("hello");
            sink.complete();
        }).subscribe(System.out::println);
    }

    @Test
    public void createFluxByCreate() {
        Flux.create(sink -> {
            sink.next("hello");
            sink.complete();
        }).subscribe(System.out::println);
    }

    @Test
    public void createFluxByArray() {
        Flux.fromArray(new String[]{"hello", "world"})
                .subscribe(System.out::println);
    }

    @Test
    public void createFluxByRang() {
        Flux.range(1, 10)
                .subscribe(System.out::println);
    }

    @Test
    public void createByInterval() throws InterruptedException {
        // 从0开始生成间隔固定时间生成一个Long类型的数
        Flux.interval(Duration.ofSeconds(1))
                .subscribe(System.out::println);
        TimeUnit.SECONDS.sleep(30);
    }

    @Test
    public void testBuffer() {
        // 产生100个数，20个为一组
        Flux.range(1, 100)
                .buffer(20)
                .subscribe(System.out::println);
    }

    @Test
    public void intervalTest() throws InterruptedException {
        Flux.interval(Duration.ofMillis(100))
                .buffer(Duration.ofMillis(1001))
                // .take(2)
                .subscribe(System.out::println);
        TimeUnit.SECONDS.sleep(60);
    }

    @Test
    public void bufferUtilTest() {
        Flux.range(1, 10)
                .bufferUntil(i -> i % 2 == 0)
                .subscribe(System.out::println);
    }

    @Test
    public void bufferWhileTest() {
        Flux.range(1, 10)
                .bufferWhile(i -> i % 2 == 0)
                .subscribe(System.out::println);
    }

    @Test
    public void zipTest() {
        Flux.just("a", "b")
                .zipWith(Flux.just("c", "d"))
                .subscribe(System.out::println);
    }

    @Test
    public void zipTest2() {
        Flux.just("a", "b")
                .zipWith(Flux.just("c", "d"), (s1, s2) -> String.format("%s-%s", s1, s2))
                .subscribe(System.out::println);
    }

    @Test
    public void takeTest() {
        Flux.range(1, 100).take(10).subscribe(System.out::println);
    }

    @Test
    public void takeTest2() {
        Flux.range(1, 10)
                .takeLast(10)
                .subscribe(System.out::println);
    }

    @Test
    public void takeTest3() {
        Flux.range(1, 100)
                .takeWhile(i -> i < 10)
                .subscribe(System.out::println);
    }

    @Test
    public void takeTest4() {
        Flux.range(1, 100)
                .takeUntil(i -> i == 10)
                .subscribe(System.out::println);
    }

    @Test
    public void reduceTest() {
        // 进行累加操作
        Flux.range(1, 100)
                .reduce((x, y) -> x + y).subscribe(System.out::println);
    }

    @Test
    public void reduceTest2() {
        // 第一个参数是给一个初始值，然后在这个初始值下进行累加
        Flux.range(1, 100)
                .reduceWith(() -> 100, (x, y) -> x + y)
                .subscribe(System.out::println);
    }

    @Test
    public void concatTest() {
        Flux.just(1,2)
                .concatWith(Mono.error(new IllegalStateException()))
                .subscribe(System.out::println, System.err::println);
    }

    @Test
    public void concatTest2() {
        Flux.just(1,2)
                .concatWith(Mono.error(new IllegalStateException()))
                .onErrorReturn(0)
                .subscribe(System.out::println);
    }




}
