# karting2
Karting RM 2 - TINGESO

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
# 1. Secrets y ConfigMaps PRIMERO
kubectl apply -f /mnt/c/Users/claud/Desktop/karting2/deployment/postgres-secrets.yaml
kubectl apply -f /mnt/c/Users/claud/Desktop/karting2/deployment/postgres-config-map.yaml

# 2. Verifica que se crearon
kubectl get secrets
kubectl get configmaps

# 3. Aplica las BASES DE DATOS
kubectl apply -f /mnt/c/Users/claud/Desktop/karting2/deployment/rates-db-deployment-service.yaml
kubectl apply -f /mnt/c/Users/claud/Desktop/karting2/deployment/reservation-db-deployment-service.yaml
kubectl apply -f /mnt/c/Users/claud/Desktop/karting2/deployment/loyaltydiscount-db-deployment-service.yaml
kubectl apply -f /mnt/c/Users/claud/Desktop/karting2/deployment/peoplediscount-db-deployment-service.yaml
kubectl apply -f /mnt/c/Users/claud/Desktop/karting2/deployment/specialdaydiscount-db-deployment-service.yaml

# 4. Espera que las DBs estén Running (esto toma un momento)
kubectl get pods -w

# 5. Aplica Config Server y Eureka
kubectl apply -f /mnt/c/Users/claud/Desktop/karting2/deployment/config-server-deployment-service.yaml
kubectl apply -f /mnt/c/Users/claud/Desktop/karting2/deployment/eureka-server-deployment-service.yaml

# 6. Espera que estén Running
kubectl get pods -w
# CTRL+C cuando config-server y eureka-server estén "Running"

# 7. Aplica los MICROSERVICIOS
kubectl apply -f /mnt/c/Users/claud/Desktop/karting2/deployment/rates-deployment-service.yaml
kubectl apply -f /mnt/c/Users/claud/Desktop/karting2/deployment/reservation-deployment-service.yaml
kubectl apply -f /mnt/c/Users/claud/Desktop/karting2/deployment/loyaltydiscount-deployment-service.yaml
kubectl apply -f /mnt/c/Users/claud/Desktop/karting2/deployment/peoplediscount-deployment-service.yaml
kubectl apply -f /mnt/c/Users/claud/Desktop/karting2/deployment/specialdaydiscount-deployment-service.yaml
kubectl apply -f /mnt/c/Users/claud/Desktop/karting2/deployment/rack-deployment-service.yaml
kubectl apply -f /mnt/c/Users/claud/Desktop/karting2/deployment/report-deployment-service.yaml

# 8. Finalmente Gateway y Frontend
kubectl apply -f /mnt/c/Users/claud/Desktop/karting2/deployment/gateway-server-deployment-service.yaml
kubectl apply -f /mnt/c/Users/claud/Desktop/karting2/deployment/frontend-deployment-service.yaml


## Version mas rapida:
cd /mnt/c/Users/claud/Desktop/karting2/deployment

# Aplica todo en orden
kubectl apply -f postgres-secrets.yaml

kubectl apply -f postgres-config-map.yaml

kubectl apply -f .

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
Para reiniciar los pods en CrashLoopBackOff:

kubectl get pods | grep CrashLoopBackOff | awk '{print $1}' | xargs -r kubectl delete pod



Para entrar a la aplicación:
---
minikube service frontend-deployment
