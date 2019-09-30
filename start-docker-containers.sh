docker stop simplybanking_postgres || true && docker rm simplybanking_postgres || true;
docker stop simplybanking_mongo || true && docker rm simplybanking_mongo || true;
docker stop simplybanking_redis || true && docker rm simplybanking_redis || true;
docker run -d -p 5432:5432 --name=simplybanking_postgres -e POSTGRES_PASSWORD=password postgres
docker run -d -p 27017:27017 --name=simplybanking_mongo --restart=unless-stopped mongo:bionic
docker run -d -p 6379:6379 --name=simplybanking_redis --restart=unless-stopped redis

