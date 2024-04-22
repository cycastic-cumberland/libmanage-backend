FROM alpine:latest as build

ENV GLIBC_VERSION=2.27-r0 \
    GRAALVM_VERSION=1.0.0-rc5 \
    JAVA_HOME=/usr/lib/jvm/graalvm-ce-1.0.0-rc5 \
    PATH=/usr/lib/jvm/graalvm-ce-1.0.0-rc5/bin:$PATH

RUN apk --no-cache add ca-certificates wget gcc zlib zlib-dev libc-dev

RUN mkdir /usr/lib/jvm; \
    wget "https://github.com/oracle/graal/releases/download/vm-${GRAALVM_VERSION}/graalvm-ce-${GRAALVM_VERSION}-linux-amd64.tar.gz"; \
    tar -zxC /usr/lib/jvm -f graalvm-ce-${GRAALVM_VERSION}-linux-amd64.tar.gz; \
    rm -f graalvm-ce-${GRAALVM_VERSION}-linux-amd64.tar.gz

RUN wget -q -O /etc/apk/keys/sgerrand.rsa.pub https://alpine-pkgs.sgerrand.com/sgerrand.rsa.pub \
    &&  wget "https://github.com/sgerrand/alpine-pkg-glibc/releases/download/$GLIBC_VERSION/glibc-$GLIBC_VERSION.apk" \
    &&  apk --no-cache add "glibc-$GLIBC_VERSION.apk" \
    &&  rm "glibc-$GLIBC_VERSION.apk" \
    &&  wget "https://github.com/sgerrand/alpine-pkg-glibc/releases/download/$GLIBC_VERSION/glibc-bin-$GLIBC_VERSION.apk" \
    &&  apk --no-cache add "glibc-bin-$GLIBC_VERSION.apk" \
    &&  rm "glibc-bin-$GLIBC_VERSION.apk" \
    &&  wget "https://github.com/sgerrand/alpine-pkg-glibc/releases/download/$GLIBC_VERSION/glibc-i18n-$GLIBC_VERSION.apk" \
    &&  apk --no-cache add "glibc-i18n-$GLIBC_VERSION.apk" \
    &&  rm "glibc-i18n-$GLIBC_VERSION.apk"

# Set the working directory in the container
WORKDIR /app

# Copy the Maven project files (pom.xml) to the container
COPY pom.xml .

# Download the project dependencies, which will be cached for future builds
RUN native-image-install -q --host-java-cmd=/usr/local/bin/java
RUN gu install native-image

# Copy the source code to the container
COPY src src

# Build the native image
RUN mvn clean package -Pnative -DskipTests

# Use an Alpine Linux-based image as the runtime base
FROM alpine:latest

# Set the working directory in the container
WORKDIR /app

# Copy the native executable from the build stage to the runtime container
COPY --from=build /app/target/your-application .

# Expose the port your Spring Boot application listens on (if needed)
EXPOSE 8080

# Command to run the native executable
CMD ["./your-application"]
