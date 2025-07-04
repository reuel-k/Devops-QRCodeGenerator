cd qr*
mvn clean package
cd ..
docker build -t qr-code-generator .
docker rm -f qr-app
docker run -d --name qr-app --network=qr-monitoring -p 1111:1111 qr-code-generator
docker logs qr-app
