# Project Setup Guide

This guide will walk you through setting up your environment, running the database, and managing Docker containers.

---

## 1: Set Environment Variables

1. In **IntelliJ**, next to the green "Run" button, click the button with **three dots (`...`)**.
2. Click **"Edit Configurations"**.
3. In the window that appears, click **"Modify Options"**.
4. Select **"Environment Variables"** (under the "Operating System" section).
5. In the Environment Variables field, paste the following:

   ```
   DB_USERNAME=replace_me;DB_PASSWORD=replace_me;
   ```

---

## 2: Create the PostgreSQL Database

1. Run the following command in your terminal:

   ```bash
   docker compose -f docker-compose.yml up --build -d
   ```

2. This builds and starts the database in the background.
3. You only need to run this command **once** to create the database.

> ⚠️ **Note for Windows users**: Make sure Docker Desktop is **open** while working.  
> ✅ **MacOS/Linux users**: The containers will run automatically after you boot your computer.

---

## 3: Stop or Delete Containers

### To **stop** the containers:

```bash
docker compose -f docker-compose.yml down
```

### To **delete** the containers (and clean everything up):

```bash
docker system prune -a
```

> ⚠️ This will remove **all** stopped containers, networks, images, and build cache. Be careful!

---

> ⚠️ **Note for Windows users**: Docker desktop has the, stop and delete buttons near the containers.

## 4: Monitor the Database (pgAdmin)

1. Go to [http://localhost:5050/browser](http://localhost:5050/browser)  
   *(or open Docker Desktop and click the redirect icon)*

2. If asked to set an initial password, use something like:

   ```
   admin
   ```

3. Add a new server:
   - **General tab**:  
     - Name: `psql-db`
   - **Connection tab**:  
     - Hostname/Address: `springboot_postgres`  
     - Port: `5432`

---

 ## 5: To access swagger

 Go to: localhost:{your_port}/swagger-ui/index.html
