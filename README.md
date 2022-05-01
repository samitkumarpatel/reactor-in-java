## Reactor

Reactor is a fully non-blocking reactive programming foundation for the JVM, with efficient demand management. It integrates directly with the Java 8 functional APIs, notably CompletableFuture, Stream, and Duration. It offers composable asynchronous sequence APIs — Flux (for [N] elements) and Mono (for [0|1] elements) — and extensively implements the Reactive Streams specification

## Prerequisites
Reactor Core runs on Java 8 and above.

## Reactive Stream
These are the 4 interface deals with complete reactive-ness
* Publisher
````java
   public interface Publisher<T> {
        void subscribe(Flow.Subscriber<? super T> var1);
   }
````
* Subscriber
````java
    public interface Subscriber<T> {
        void onSubscribe(Subscription var1);
        void onNext(T var1);
        void onError(Throwable var1);
        void onComplete();
    }
````
* Subscription

I connects Publisher and Subscriber
````java
public interface Subscription {
    void request(long var1);
    
    void cancel();
}
````
* Processor
We don't interactive with this.
````java
public interface Processor<T,R> extends Subscriber<T>, Publisher<R> {
    
}
````

## Introduction

Project Reactor implements the above four interface

### Flux & Mono
* These are reactive types that implements Reactive Streams specification
* Part of reactive-core module
* Flux represents 0 to N elements
* Mono represents 0 to 1 elements

````text
data --> operator --> result data
````
