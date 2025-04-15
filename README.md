### Setup env vars:
* In Intellij next to the run application button, there is a button with 3 dots
* Click on it, then press "Edit"
* Then when a window pops up, press "Modify Options"
* Then press on "Environment variables" (under the "Operating System" section)
* In the Environment Variables field, enter: DB_USERNAME=replace_me;DB_PASSWORD=replace_me;

### To run psql:
* docker compose -f docker-compose.yml up --build -d

### To monitor the db
* Goto localhost:5050/browser ( or just in docker desktop click the redirect )
* If asks to setup initial password, just set it to admin or whatever you want
* Then add new server
* In general tab: name your database = psql-db
* Connection tab: Hostname/Address - enter the docker container name (should be "springboot_postgres")
* Port - 5432