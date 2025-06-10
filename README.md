# karting2
Karting RM 2 - TINGESO

Para crear las imagenes vamos a la ruta de un ms y ejecutamos:

docker build -t usuario_docker/nombre_imagen . 

---
docker build -t claubells/config-server .
docker push claubells/config-server

docker build -t claubells/eureka-server .
docker push claubells/eureka-server

docker build -t claubells/gateway-server .
docker push claubells/gateway-server

docker build -t claubells/loyaltydiscount-service .
docker push claubells/loyaltydiscount-service

docker build -t claubells/peoplediscount-service .
docker push claubells/peoplediscount-service

docker build -t claubells/rack-service .
docker push claubells/rack-service

docker build -t claubells/rates-service .
docker push claubells/rates-service

docker build -t claubells/report-service .
docker push claubells/report-service

docker build -t claubells/reservation-service .
docker push claubells/reservation-service

docker build -t claubells/specialdaydiscount-service .
docker push claubells/specialdaydiscount-service

---
Para crear las imagenes con docker compose:
---
docker compose build

docker compose push



Se debe tener instalado minikube y lo iniciamos con docker desde WSL2:
---
minikube start --driver=docker
---

Para sesiones futuras:
---
minikube start
---

Ahora levantamos los archivos:
---
kubectl apply -f /mnt/c/Users/claud/Desktop/karting2/deployment
---

Pero si queremos en orden:
---
kubectl apply -f /mnt/c/Users/claud/Desktop/karting2/backend/deployment/postgres-config-map.yaml
kubectl apply -f /mnt/c/Users/claud/Desktop/karting2/backend/deployment/postgres-secrets.yaml
kubectl apply -f /mnt/c/Users/claud/Desktop/karting2/backend/deployment/loyaltydiscount-db-deployment-service.yaml
kubectl apply -f /mnt/c/Users/claud/Desktop/karting2/backend/deployment/peoplediscount-db-deployment-service.yaml
kubectl apply -f /mnt/c/Users/claud/Desktop/karting2/backend/deployment/rates-db-deployment-service.yaml
kubectl apply -f /mnt/c/Users/claud/Desktop/karting2/backend/deployment/reservation-db-deployment-service.yaml
kubectl apply -f /mnt/c/Users/claud/Desktop/karting2/backend/deployment/specialdaydiscount-db-deployment-service.yaml

kubectl apply -f /mnt/c/Users/claud/Desktop/karting2/backend/deployment/config-server-deployment-service.yaml
kubectl get pods
---

Para obtener los pods:
kubectl get pods

Este comando borra
---
kubectl delete deployments --all

kubectl delete configmap --all

kubectl delete services --all

kubectl delete secrets --all

kubectl delete pvc --all

kubectl delete pv --all

kubectl delete all --all
---

Para entrar a la aplicación:
1. Obtenemos la ip
---
minikube ip
---
2. http://<minikube-ip>:30777
