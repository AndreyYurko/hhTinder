# Vacanter
The Vacanter is a mobile application for matching employers with potential employees. Moreover, it also has a Desktop version for Employers.
<img src="https://user-images.githubusercontent.com/55112338/197409494-8b04a651-0cd0-43fc-a703-a393672411d6.svg" width="300" height="300">
# Sprints
- 1 sprint: 16.09.22-7.10.2022 ✅
- 2 sprint: 7.10.2022-28.10.2022 ✅
- 3 sprint: 28.10-18.11.2022 ⏳
- 4 sprint: 18.11-09.12.2022 ❌
# Build and run the container only for FastAPI(app.py)
Build the docker image using:
```
docker build . -t main 
```
Check whether you have successfully build the image:
```
docker images  
```
Generate the docker container using:
```
docker run --name main -p 8000:8000 main 
```
Check that your image is running:
```
docker ps -a 
```
Some usefull commands:

- ```docker stop main``` - stop the docker container
- ```docker rm main``` - remove image (before rebuild image step in case of error)
# Run the containers for FastAPI(app.py), Prometheus, Grafana
Run all 3 containers
```
docker compose up
```
Now you have access to those three containers and their respective ports:
- FastAPI(app.py): http://localhost:8000
- Prometheus: http://localhost:9090
- Grafana: http://localhost:3000

To acces Grafana username ```admin``` password ```pass@123```

On the FastAPI, you can access ```localhost:8000/metrics``` endpoint to see the data Prometheus is scraping from it.

Example of Grafana visualization metrics:
![image](https://user-images.githubusercontent.com/55112338/202467963-4f1c8e44-f560-4c03-8f2f-2155e906d7b4.png)
