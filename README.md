# JMS ActiveMQ CRUD Application
`This is a term project. It does not exhibit my current programming skills`

This is an example application which integrates 3 independent modules asynchronously using JMS communication mechanisms. The three modules are:
* Client application
* Authorisation Backend Server
* ELK Stack to monitor client and server applications

We have used ActiveMQ to implement JMS communication betweent the three modules. Mode details about the implementation and architecture can be found [here](./docs.pdf)

## Dependencies
* [Java 8](https://www.oracle.com/java/technologies/java8.html)
* [Spring Boot Framework](https://spring.io/projects/spring-boot)
* [ActiveMQ](https://activemq.apache.org/)
* [JMS](https://www.oracle.com/java/technologies/java-message-service.html)
* [Log4j](https://logging.apache.org/log4j/2.x/)
* [MongoDB](https://www.mongodb.com/)
* [ELK Stack](https://www.elastic.co/what-is/elk-stack)


## Run Instructions
* Make sure you have activemq installed and running. Check by visiting `http://localhost:8161/`
* Run elasticsearch, kibana in seperate windows.
* Make sure to change your activemq jar file path in the `logstash.conf` file replace entry corresponding to `require_jars`.
* Run logstash with `logstash.conf` file in this repo.
* You might have to install a plugin like - `bin/logstash-plugin install logstash-input-jms` from your logstash installed directory.
* Go to your Kibana discover tab and create an index - `logstash-*`
* Make sure you have MongoDB running.

Now, everything should be setup.

* Run the spring app using `mvn spring-boot:run`
* Hopefully, everything runs fine. Now, visit `localhost:8090/available`, this sends a message to the queue. Check your queue by visiting `localhost:8161`, you should see a queue with the name `helloworld.q`.
* Now, visit you kibana discover tab, you should see the message there as well. This completes the app -> activemq -> logstash -> elasticsearch -> kibana cycle.
* Now, visit, `localhost:8090/save`, this should save a few entries in mongodb. Access mongoshell to check if it was store. DB name would be `test` and collection name `customer`.
* Visit `localhost:8090/client` to access the client page.
* Test user data can be generated using the provided [script](./generate_data.py). It generates fake data of [this](./customer_data.csv) format. This data can be fed into mongo db using the command `mongoimport --type csv -d test -c faker --headerline customer_data.csv`.
* Now, we can use the different APIs exposed by [Server](./src/main/java/com/jmscrudapp/SpringJmsApplication.java) to
    * `fetch`, `login`, `logout`, `authenticate` user via REST HTTP
    * `fetch`, `login`, `logout`, `authenticate` user via JMS
    * `fetch`, `login`, `logout`, `authenticate` user via JMS client application simulation.
* System can be monitored on the dashboards of `kibana` and `activemq`.

