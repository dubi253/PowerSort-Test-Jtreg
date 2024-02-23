Make java version 23-ea

```bash
~/Documents/COMP390/openjdk/build/linux-x86_64-server-release/jdk/bin/javac -d ./out/production/PowerSort-Test-Jtreg -cp ./src/ -Xlint:unchecked ./src/PowerSortTest.java
```


Run the test with the following command:
```bash
taskset -c 0 ~/Documents/COMP390/openjdk/build/linux-x86_64-server-release/jdk/bin/java -XX:+UnlockDiagnosticVMOptions -XX:-TieredCompilation -cp ./out/production/PowerSort-Test-Jtreg/ PowerSortTest 
```