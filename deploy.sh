(cd simplybanking-ui && ng build --prod)
(cd simplybanking-ui/dist && tar czf simplybanking-ui.tar.gz simplybanking-ui)
(cd simplybanking-api/ && ./gradlew bootJar)
(cd simplybanking-infra/ && terraform apply -auto-approve)