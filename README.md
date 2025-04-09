### To run psql:
* docker compose -f docker-compose.yml up -d

### To monitor the db
* Goto localhost:5050/browser ( or just in docker desktop click the redirect )
* If asks to setup initial password, just set it to admin or smth
* Then add new server
* In general tab: name your db whatever you want
* Connection tab: Hostname/Address - enter the docker container name
* Port - 5432
* Username - root
* Password - root@111


px env vars/secrets hardcodinam viska krc