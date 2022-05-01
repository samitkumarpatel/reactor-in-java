## Reactor

Reactor is a fully non-blocking reactive programming foundation for the JVM, with efficient demand management. It integrates directly with the Java 8 functional APIs, notably CompletableFuture, Stream, and Duration. It offers composable asynchronous sequence APIs — Flux (for [N] elements) and Mono (for [0|1] elements) — and extensively implements the Reactive Streams specification




## Prerequisites
Reactor Core runs on Java 8 and above.


## Introduction

Reactive programming is an asynchronous programming paradigm concerned with data streams and the propagation of change. This means that it becomes possible to express static (e.g. arrays) or dynamic (e.g. event emitters) data streams with ease via the employed programming language(s).

As a first step in the direction of reactive programming, Microsoft created the Reactive Extensions (Rx) library in the .NET ecosystem. Then RxJava implemented reactive programming on the JVM

The reactive programming paradigm is often presented in object-oriented languages as an extension of the Observer design pattern. You can also compare the main reactive streams pattern with the familiar Iterator design pattern, as there is a duality to the Iterable-Iterator pair in all of these libraries. One major difference is that, while an Iterator is pull-based, reactive streams are push-based.

In addition to pushing values, the error-handling and completion aspects are also covered in a well defined manner. A Publisher can push new values to its Subscriber (by calling onNext) but can also signal an error (by calling onError) or completion (by calling onComplete). Both errors and completion terminate the sequence. This can be summed up as follows:

```
onNext x 0..N [onError | onComplete]
```


## From Imperative to Reactive Programming

Reactive libraries, such as Reactor, aim to address these drawbacks of “classic” asynchronous approaches on the JVM while also focusing on a few additional aspects:

- Composability and readability:
  > By “composability”, we mean the ability to orchestrate multiple asynchronous tasks, in which we use results from previous tasks to feed input to subsequent ones. Alternatively, we can run several tasks in a fork-join style. In addition, we can reuse asynchronous tasks as discrete components in a higher-level system

- Data as a flow manipulated with a rich vocabulary of operators

- Nothing happens until you subscribe

- Backpressure or the ability for the consumer to signal the producer that the rate of emission is too high

- High level but high value abstraction that is concurrency-agnostic

## Operators
In Reactor, operators are the workstations in our assembly analogy. Each operator adds behavior to a Publisher and wraps the previous step’s Publisher into a new instance. The whole chain is thus linked, such that data originates from the first Publisher and moves down the chain, transformed by each link. Eventually, a Subscriber finishes the process.

In Reactor, when you write a Publisher chain, data does not start pumping into it by default. Instead, you create an abstract description of your asynchronous process (which can help with reusability and composition).

By the act of subscribing, you tie the Publisher to a Subscriber, which triggers the flow of data in the whole chain. This is achieved internally by a single request signal from the Subscriber that is propagated upstream, all the way back to the source Publisher.


## Flux, an Asynchronous Sequence of 0-N Items

A Flux<T> is a standard Publisher<T> that represents an asynchronous sequence of 0 to N emitted items, optionally terminated by either a completion signal or an error. As in the Reactive Streams spec, these three types of signal translate to calls to a downstream Subscriber’s onNext, onComplete, and onError methods.

With this large scope of possible signals, Flux is the general-purpose reactive type. Note that all events, even terminating ones, are optional: no onNext event but an onComplete event represents an empty finite sequence, but remove the onComplete and you have an infinite empty sequence (not particularly useful, except for tests around cancellation). Similarly, infinite sequences are not necessarily empty. For example, Flux.interval(Duration) produces a Flux<Long> that is infinite and emits regular ticks from a clock.


```
Flux<String> seq1 = Flux.just("foo", "bar", "foobar");

List<String> iterable = Arrays.asList("foo", "bar", "foobar");
Flux<String> seq2 = Flux.fromIterable(iterable);
```

## Mono, an Asynchronous 0-1 Result

A Mono<T> is a specialized Publisher<T> that emits at most one item via the onNext signal then terminates with an onComplete signal (successful Mono, with or without value), or only emits a single onError signal (failed Mono).

Most Mono implementations are expected to immediately call onComplete on their Subscriber after having called onNext. Mono.never() is an outlier: it doesn’t emit any signal, which is not technically forbidden although not terribly useful outside of tests. On the other hand, a combination of onNext and onError is explicitly forbidden.

Mono offers only a subset of the operators that are available for a Flux, and some operators (notably those that combine the Mono with another Publisher) switch to a Flux. For example, Mono#concatWith(Publisher) returns a Flux while Mono#then(Mono) returns another Mono.

Note that you can use a Mono to represent no-value asynchronous processes that only have the concept of completion (similar to a Runnable). To create one, you can use an empty Mono<Void>.

```
Mono<String> noData = Mono.empty(); 

Mono<String> data = Mono.just("foo");

Flux<Integer> numbersFromFiveToSeven = Flux.range(5, 3); 
```