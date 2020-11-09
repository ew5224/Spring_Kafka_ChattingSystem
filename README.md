
# Setting Up Kafka

- bin 폴더의 .sh 파일들 중 실행

## Start Zookeeper and Kafka Broker

-   Kafka 2.6.0부터 zookeeper removal을 제공하지만 일단 zookeeper를 통해 broker 제어를 한다. (향후 수정)

```
./zookeeper-server-start.sh ../config/zookeeper.properties
```

- server.properties의 configuration 수정

```
listeners=PLAINTEXT://localhost:9092
auto.create.topics.enable=false
```

-   Kafka Broker 실행

```
./kafka-server-start.sh ../config/server.properties
```

## Console로 topic 생성

```
./kafka-topics.sh --create --topic test-topic -zookeeper localhost:2181 --replication-factor 1 --partitions 4
```

## Console로 Producer 실행

### Without Key

```
./kafka-console-producer.sh --broker-list localhost:9092 --topic test-topic
```

### With Key
- Key를 통해 value들을 분류하고, 동일 partition에 넣고 싶으면 key를 이용하면 된다.

```
./kafka-console-producer.sh --broker-list localhost:9092 --topic test-topic --property "key.separator=-" --property "parse.key=true"
```

## Console Consumer로 Consumer 실행

### Without Key

```
./kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test-topic --from-beginning
```

### With Key

```
./kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test-topic --from-beginning -property "key.separator= - " --property "print.key=true"
```

### With Consumer Group

```
./kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test-topic --group <group-name>
```

## Setting Up Multiple Kafka Brokers

- 새로운 **server.properties** 를 만들어서 해당 properties를 통해 실행시켜주는 방식으로 한다.

- 우선 local 환경에서 실행하기 때문에 localhost의 port #만 변경해준다.

```
broker.id=<unique-broker-d>
listeners=PLAINTEXT://localhost:<unique-port>
log.dirs=/tmp/<unique-kafka-folder>
auto.create.topics.enable=false
```

- 예시

```
broker.id=1
listeners=PLAINTEXT://localhost:9093
log.dirs=/tmp/kafka-logs-1
auto.create.topics.enable=false
```

### Starting up the new Broker

- 동일한 sh 에 **server.properties** 만 변경하여 실행해준다.

```
./kafka-server-start.sh ../config/server-1.properties
```

```
./kafka-server-start.sh ../config/server-2.properties
```

## List the topics in a cluster

```
./kafka-topics.sh --zookeeper localhost:2181 --list
```

## Describe topic

```
./kafka-topics.sh --zookeeper localhost:2181 --describe
```

- topic 설정해서 describe

```
./kafka-topics.sh --zookeeper localhost:2181 --describe --topic <topic-name>
```

## replica insync의 min config 변경
```
./kafka-topics.sh --alter --zookeeper localhost:2181 --topic library-events --config min.insync.replicas=2
```

## Delete a topic

```
./kafka-topics.sh --zookeeper localhost:2181 --delete --topic test-topic
```
## consumer groups 확인

```
./kafka-consumer-groups.sh --bootstrap-server localhost:9092 --list
```

### Consumer Groups and their Offset

```
./kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group console-consumer-27773
```

## Commit Log 내용 확인

```
./kafka-run-class.sh kafka.tools.DumpLogSegments --deep-iteration --files /tmp/kafka-logs/test-topic-0/00000000000000000000.log
```

## Setting Minimum Insync Replica

```
./kafka-configs.sh --alter --zookeeper localhost:2181 --entity-type topics --entity-name test-topic --add-config min.insync.replicas=2
```
