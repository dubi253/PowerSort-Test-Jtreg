# Jtreg Test for PowerSort

## Deprecated

Already integrated into my openjdk fork repository at https://github.com/dubi253/openjdk/tree/powersort/test/jdk/java/util/PowerSort


```bash
~/Documents/COMP390/openjdk/build/linux-x86_64-server-release/jdk/bin/javac -d ./out/production/PowerSort-Test-Jtreg -cp ./src/ -Xlint:unchecked ./src/PowerSortTest.java
```


Run the test with the following command:
```bash
taskset -c 0 ~/Documents/COMP390/openjdk/build/linux-x86_64-server-release/jdk/bin/java -XX:+UnlockDiagnosticVMOptions -XX:-TieredCompilation -cp ./out/production/PowerSort-Test-Jtreg/ PowerSortTest 
```


Jtreg

```bash
jtreg -verbose:all -jdk:../openjdk/build/linux-x86_64-server-release/jdk/ ./src/PowerSortTest.java 
```

## Flame Graphs Visualisation

Install AsyncProfiler at https://github.com/async-profiler/async-profiler

Enable perf_event_paranoid and disable kptr_restrict for async-profiler to work

```bash
sudo sysctl kernel.perf_event_paranoid=1 && sudo sysctl kernel.kptr_restrict=0
```

List all Java processes

```bash
jps -l
```

Run AsyncProfiler and generate flame graph

```bash
./asprof -d 400s -f ./tmp.html [PID]
```
