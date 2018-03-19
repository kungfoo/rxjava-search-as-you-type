FROM openjdk:alpine

ADD build/*.tar /
RUN ln -sf reactive-server-* reactive-server
COPY build/version.txt /version.txt

EXPOSE 8080 8081

CMD ["/reactive-server/bin/reactive-server", "server", "reactive-server/config.yml"]
