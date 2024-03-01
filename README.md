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