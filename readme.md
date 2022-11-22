Install dependencies and you should be good to go!

Here is a sample run on `n = 1000` `m = n (n - 1) / 2`

We are using JAVA [JMH](https://jenkov.com/tutorials/java-performance/jmh.html) to do `proper` benchmarking

|       Benchmark                | Mode  | Cnt   | Score    | Error   | Units  |
|-------------------------------|-------|-------|----------|---------|--------|
|(Sol1) Standard Heap implementation  | thrpt | 5| 1227.306 | 320.018 | ops/ms |
|(Sol2) Heap replacement when adding  | thrpt | 5 | 806.666  | 83.976  | ops.ms |