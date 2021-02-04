package edu.cooper.ece366;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/** Hello world! */
public class App {
  public static void main(String[] args) throws ExecutionException, InterruptedException {
    // Java Collection API
    // Set -> *HashSet, TreeSet, LinkedHashSet, SortedSet ...
    // List -> *ArrayList, LinkedList, Vector ...
    // Map -> *HashMap, TreeMap, SortedMap ...

    // construct collections in various ways
    List<String> strings = List.of("a", "b", "c", "ab", "bc", "ac");

    // some functional interfaces - Function, Consumer, Predicate, Supplier
    // You can implement any interface inline. Depending on the scope, you may want to declare in
    // its own class.
    Function<String, Integer> toInteger =
        new Function<>() {
          @Override
          public Integer apply(final String s) {
            return Integer.valueOf(s);
          }
        };

    Consumer<String> stringConsumer =
        new Consumer<>() {
          @Override
          public void accept(final String s) {
            System.out.println(s);
          }
        };

    Predicate<String> equalsYeetPredicate =
        new Predicate<>() {
          @Override
          public boolean test(final String s) {
            return s.equals("yeet");
          }
        };

    Supplier<Long> timeSupplier =
        new Supplier<Long>() {
          @Override
          public Long get() {
            return System.currentTimeMillis();
          }
        };

    // some lambdas - lambda body {} (with return), lambda, method reference
    Function<String, Integer> toInt = Integer::valueOf;

    Consumer<String> consumer = System.out::println;

    Predicate<String> equalsYeet = s -> s.equals("yeet");

    Supplier<Long> timeSupp = System::currentTimeMillis;

    // streams using map, filter, collect
    List<String> singleCharacters =
        strings.stream().filter(s -> s.length() == 1).collect(Collectors.toList());
    List<String> doubled = strings.stream().map(App::doubleString).collect(Collectors.toList());

    // collect to map, groupby
    Map<String, String> stringStringMap =
        strings.stream().collect(Collectors.toMap(Function.identity(), App::doubleString));

    // map entryset

    // threads (implements Runnable vs extends Thread)
    Runnable yo = () -> System.out.println("yo");
    new Thread(yo).start();

    // start multiple threads, demo out of order
    new MyThread("yeet").start();
    new Thread(new MyOtherThread("deet")).start();

    // thread pool, executors, futures
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    Future<String> yo1 = executorService.submit(() -> {
      System.out.println("yo");
      return "yo";
    });
    yo1.get();

    // completablefuture / completionstage
    CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(
        () -> {
          // operation that blocks IO
          return "hey";
        },
        executorService);

    // sockets / http
    try {
//      Socket localhost = new Socket("localhost", 8080);
      ServerSocket serverSocket = new ServerSocket(8000);
      System.out.println("listening on port 8000");

      while (true) {
        Socket accept = serverSocket.accept();
        BufferedReader inputReader =
            new BufferedReader(new InputStreamReader(accept.getInputStream()));
        BufferedWriter outputWriter =
            new BufferedWriter(new OutputStreamWriter(accept.getOutputStream()));

        String line = inputReader.readLine();
        while (!line.isEmpty()) {
          // do something with response
          line = inputReader.readLine();
          System.out.println(line);
        }
        String response = "{\"response\": \"hey\"}";

        outputWriter.write("HTTP/1.1 200 OK\r\n");
        outputWriter.write("content-length: " + response.length());
        outputWriter.write("\r\n\r\n");
        outputWriter.write(response);

        outputWriter.close();
        inputReader.close();
        accept.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static String doubleString(final String s) {
    if (s.length() == 1) {
      return String.format("%s%s", s, s);
    } else {
      return s;
    }
  }

  static class MyThread extends Thread {

    private final String s;

    public MyThread(String s) {
      this.s = s;
    }

    @Override
    public void run() {
      IntStream.range(0, 10).forEach(i -> System.out.println("value is: " + s));
    }
  }

  static class MyOtherThread implements Runnable {

    private final String s;

    public MyOtherThread(String s) {
      this.s = s;
    }

    @Override
    public void run() {
      IntStream.range(0, 10).forEach(i -> System.out.println("other value is: " + s));
    }
  }
}
