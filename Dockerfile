FROM oracle/graalvm-ce:1.0.0-rc13 as graalvm
COPY . /home/app/micronaut-app
WORKDIR /home/app/micronaut-app
RUN ./build-native-image.sh

FROM frolvlad/alpine-glibc
EXPOSE 8080
COPY --from=graalvm /home/app/micronaut-app .
ENTRYPOINT ["./micronaut-app"]
