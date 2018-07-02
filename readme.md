# Useful Commands

sudo mysql --host=127.0.0.1 --port=3307 --user=root --protocol=tcp --password=root

curl --header "Content-Type: application/json" --request POST --data '{"numIterations":2,"liveCellsPercent":60}' localhost:8080/camel/push

http://localhost:8161/