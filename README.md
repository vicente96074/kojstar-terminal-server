# Install OpenJDK 17
```bash
# Install OpenJDK 17
sudo apt-get install openjdk-17-jdk -y

# See java version
java -version
```

# Set systemd variables and services

- Open your terminal.
- Create a directory for kojstar-terminal configuration files:

```bash
# Create the configuration directory
sudo mkdir -p /etc/kojstar-terminal

# Create the environment variables file
sudo nano /etc/kojstar-terminal/kt.env

```

- Add the following content to kt.env:
```bash
# File: kt.env
####################################################################################################
####################################################################################################
#                               KOJSTAR TERMINAL ENVIRONMENT VARIABLES
####################################################################################################
####################################################################################################

# Internal ENV
IP_SERVER="192.168.1.233"
MYSQL_API="jdbc:mysql"
MYSQL_PORT="3306"
MYSQL_OTHER_PARAMS="useSSL=false&serverTimezone=America/Guatemala&useLegacyDateTimeCode=false&allowPublicKeyRetrieval=true"

### Email sender configuration
EMAIL_SENDER_SYSTEM_NICK="Kojstar T. "
EMAIL_SENDER_DOMAIN="kojstarterminal.com"

### MongoDB configuration
MONGODB_API="mongodb"
MONGODB_PORT="27017"

# External ENV
KOJSTAR_TERMINAL_SSL_KEY_ALIAS="alias"
KOJSTAR_TERMINAL_SSL_KEY_STORE="/path/to/key/store.p12"
KOJSTAR_TERMINAL_SSL_KEY_PASSWORD="ssl-password"

KOJSTAR_TERMINAL_AUTH_SERVICE_DB_URL="${MYSQL_API}://${IP_SERVER}:${MYSQL_PORT}/kt_auth?${MYSQL_OTHER_PARAMS}"
KOJSTAR_TERMINAL_OAUTH2_SERVICE_DB_URL="${MYSQL_API}://${IP_SERVER}:${MYSQL_PORT}/kt_auth?${MYSQL_OTHER_PARAMS}"
KOJSTAR_TERMINAL_STORE_SERVICE_DB_URL="${MYSQL_API}://${IP_SERVER}:${MYSQL_PORT}/kt_store?${MYSQL_OTHER_PARAMS}"
KOJSTAR_TERMINAL_TEST_SERVICE_DB_URL="${MYSQL_API}://${IP_SERVER}:${MYSQL_PORT}/kt_test?${MYSQL_OTHER_PARAMS}"

KOJSTAR_TERMINAL_MYSQL_USERNAME="username"
KOJSTAR_TERMINAL_MYSQL_PASSWORD="mysql-password"
KOJSTAR_TERMINAL_TIME_ZONE="America/Guatemala"

KOJSTAR_TERMINAL_EMAIL_USERNAME="brevo-login-username"
KOJSTAR_TERMINAL_EMAIL_PASSWORD="brevo-secret"
KOJSTAR_TERMINAL_EMAIL_HOST="smtp-relay.brevo.com"
KOJSTAR_TERMINAL_EMAIL_PORT="587"
KOJSTAR_TERMINAL_EMAIL_PROTOCOL="smtp"

KOJSTAR_TERMINAL_EMAIL_SENDER_SYSTEM="${EMAIL_SENDER_SYSTEM_NICK}System <system@${EMAIL_SENDER_DOMAIN}>"
KOJSTAR_TERMINAL_EMAIL_SENDER_ADMINISTRATOR="${EMAIL_SENDER_SYSTEM_NICK}Admin <admin@${EMAIL_SENDER_DOMAIN}>"
KOJSTAR_TERMINAL_EMAIL_SENDER_SUPPORT="${EMAIL_SENDER_SYSTEM_NICK}Support <support@${EMAIL_SENDER_DOMAIN}>"
KOJSTAR_TERMINAL_EMAIL_SENDER_NOTIFICATION="${EMAIL_SENDER_SYSTEM_NICK}Notification <notification@${EMAIL_SENDER_DOMAIN}>"
KOJSTAR_TERMINAL_EMAIL_SENDER_NEWS="${EMAIL_SENDER_SYSTEM_NICK}News <news@${EMAIL_SENDER_DOMAIN}>"

KOJSTAR_TERMINAL_SPRING_REDIS_HOST="${IP_SERVER}"
KOJSTAR_TERMINAL_SPRING_REDIS_PORT="6379"
KOJSTAR_TERMINAL_SPRING_REDIS_PASSWORD="redis-password"

KOJSTAR_TERMINAL_OAUTH2_HOST_ISSUER="http://localhost:9000" # https://your-domain.com:9000

KOJSTAR_TERMINAL_CORS_ALLOWED_ORIGINS="http://localhost:4200,https://${IP_SERVER}:4200,https://your_domai.com,https://www.your_domain.com" # On production, use the your_domain.com
KOJSTAR_TERMINAL_CORS_ALLOWED_METHODS="GET,POST,PUT,DELETE,OPTIONS" # Allowed methods
KOJSTAR_TERMINAL_CORS_ALLOWED_HEADERS="x-requested-with, authorization, Content-Type, Authorization, credential, X-XSRF-TOKEN, X-Device-User-Agent, X-Device-IP" # Allowed headers
KOJSTAR_TERMINAL_CORS_MAX_AGE="3600" # 1 hour

VAULT_ADDR=http://127.0.0.1:8200

KOJSTAR_TERMINAL_VAULT_ROLE_ID="role-id"
KOJSTAR_TERMINAL_VAULT_SECRET_ID="secret-id"

KOJSTAR_TERMINAL_GOOGLE_CLIENT_ID="google-client-id"
KOJSTAR_TERMINAL_GOOGLE_CLIENT_SECRET="google-client-secret"
KOJSTAR_TERMINAL_GOOGLE_REDIRECT_URI="http://localhost:9000/login/oauth2/code/google" # https://your-domain.com:9000/login/oauth2/code/google

KOJSTAR_TERMINAL_GITHUB_CLIENT_ID="github-client-id"
KOJSTAR_TERMINAL_GITHUB_CLIENT_SECRET="github-client-secret"
KOJSTAR_TERMINAL_GITHUB_REDIRECT_URI="http://localhost:9000/login/oauth2/code/github" # https://your-domain.com:9000/login/oauth2/code/github

KOJSTAR_TERMINAL_JWT_KEY_STORE="/path/to/your/keystore.jks"
KOJSTAR_TERMINAL_JWT_KEY_PASSWORD="jwt-key-password"
KOJSTAR_TERMINAL_JWT_SECRET="secretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecret"
KOJSTAR_TERMINAL_JWT_EXPIRATION="43200"

KOJSTAR_TERMINAL_MONGODB_HOST="${IP_SERVER}"
KOJSTAR_TERMINAL_MONGODB_PORT="${MONGODB_PORT}"
KOJSTAR_TERMINAL_MONGODB_DATABASE="kojstar_terminal_logs"
KOJSTAR_TERMINAL_MONGODB_USERNAME="user_server"
KOJSTAR_TERMINAL_MONGODB_PASSWORD="password"
KOJSTAR_TERMINAL_MONGODB_AUTH_DB="admin"

KOJSTAR_TERMINAL_MEDIA_LOCATION="/home/augusto/Documentos/Projects/kojstar-terminal/files/images/"
```

- Set ownership and permissions for security:
```bash
# Change ownership to root
sudo chown -R root:root /etc/kojstar-terminal

# Set permissions to read and write for owner only
sudo chmod 600 /etc/kojstar-terminal/*.env

# Verify permissions
ls -la /etc/kojstar-terminal/
```

# Create service files
- Create systemd service files for user service
```bash
sudo nano /etc/systemd/system/kt-user-service.service
# Add the content contained in kt-user-service.service file
```
- Create systemd service files for auth service
```bash
sudo nano /etc/systemd/system/kt-auth-service.service
# Add the content contained in kt-auth-service.service file
```
- Create systemd service files for oauth2 service
```bash
  sudo nano /etc/systemd/system/kt-oauth2-service.service
  # Add the content contained int kt-oauth2-service.service file
```
- Start and enable the services
```bash
# Reload systemd to recognize new service files
sudo systemctl daemon-reload

# Start and enable services
sudo systemctl start kt-user-service
sudo systemctl enable kt-user-service

# Start and enable auth service
sudo systemctl start kt-auth-service
sudo systemctl enable kt-auth-service

# Start and enable oauth2 service
sudo systemctl start kt-oauth2-service
sudo systemctl enable kt-oauth2-service
```
- Show logs
```bash
sudo systemctl status kt-user-service
```
```bash
sudo systemctl status kt-auth-service
```
```bash
sudo systemctl status kt-oauth2-service
```
- Show real-time logs
```bash
sudo journalctl -u kt-user-service -f
```

# HASHICORP VAULT SETUP GUIDE
###### vault -version "Vault v1.21.1 (2453aac2638a6ae243341b4e0657fd8aea1cbf18), built 2025-11-18T13:04:32Z"

- Step 1: Complete cleaning and system preparation
```bash
# 1. Stop services, delete processes and the 'vault' user
sudo systemctl stop vault.service || true
sudo systemctl disable vault.service || true
sudo rm -f /etc/systemd/system/vault.service

sudo killall vault || true
sudo pkill -u vault || true
sleep 2
sudo userdel vault || true
```
```bash
# 2. Remove the package, binaries, configuration, and data
sudo apt-get remove --purge vault -y || true
sudo rm -rf /etc/vault.d
sudo rm -f /etc/vault.d/vault-init.txt
sudo rm -rf /var/lib/vault
sudo rm -f /usr/share/keyrings/hashicorp-archive-keyring.gpg
sudo rm -f /etc/apt/sources.list.d/hashicorp.list
sudo rm -f /etc/kojstar-terminal/vault-rotate-secret.sh
```
```bash
# 3. Install essential dependencies and reload systemd
sudo apt update && sudo apt install -y curl gpg
sudo systemctl daemon-reload
```
- Step 2: Install HashiCorp Vault and create user and directories
```bash
# 1. Add the HashiCorp repository and install Vault
curl -fsSL https://apt.releases.hashicorp.com/gpg | sudo gpg --dearmor -o /usr/share/keyrings/hashicorp-archive-keyring.gpg
echo "deb [signed-by=/usr/share/keyrings/hashicorp-archive-keyring.gpg] https://apt.releases.hashicorp.com $(lsb_release -cs) main" | sudo tee /etc/apt/sources.list.d/hashicorp.list
sudo apt update && sudo apt install vault -y
```
```bash
# 2. Create user and directories
sudo useradd --system --home /etc/vault.d --shell /bin/false vault
sudo mkdir --parents /etc/vault.d /var/lib/vault/data
sudo chown -R vault:vault /etc/vault.d /var/lib/vault/data
```
- Step 3: File configuration for Vault and Systemd Service
```bash
# 1. Vault Configuration File: /etc/vault.d/vault.hcl
sudo nano /etc/vault.d/vault.hcl
```
- Contenido de vault.hcl:
```bash
listener "tcp" {
address     = "0.0.0.0:8200"
tls_disable = 1
}
storage "file" {
path = "/var/lib/vault/data"
}
api_addr     = "http://127.0.0.1:8200"
cluster_addr = "http://127.0.0.1:8201"
ui = true
```
- Systemd Service File: /etc/systemd/system/vault.service
```bash
sudo nano /etc/systemd/system/vault.service
```
- Contenido de vault.service (Incluye Auto-Unseal):
```bash
[Unit]
Description="HashiCorp Vault - A tool for managing secrets"
Documentation=https://developer.hashicorp.com/vault/docs
Requires=network-online.target
After=network-online.target
ConditionFileNotEmpty=/etc/vault.d/vault.hcl

[Service]
User=vault
Group=vault
Environment=VAULT_ADDR=http://127.0.0.1:8200
Environment=HOME=/etc/vault.d

AmbientCapabilities=CAP_IPC_LOCK
CapabilityBoundingSet=CAP_SYSLOG CAP_IPC_LOCK
NoNewPrivileges=yes
LimitMEMLOCK=infinity
ExecStart=/usr/bin/vault server -config=/etc/vault.d/vault.hcl
ExecReload=/bin/kill -HUP $MAINPID

KillMode=process
KillSignal=SIGINT
Restart=on-failure
RestartSec=5

[Install]
WantedBy=multi-user.target
```
Step 4: Initilialization and Unsealing of Vault
```bash
# 1. Recharge and enable the service
sudo systemctl daemon-reload
sudo systemctl enable vault.service
```

```bash
# --- Key Initialization (Single Manual Intervention) ---
# 1. Start the service (Now it will not fail, it will only start sealing)
sudo systemctl start vault.service
sleep 5

# 2. Configure the address
export VAULT_ADDR='http://127.0.0.1:8200'

# 3. Initialize with 1 Share and 1 Threshold
# IMMEDIATELY COPY THE ROOT TOKEN AND UNSEAL KEY THAT APPEAR ON SCREEN
vault operator init -key-shares=5 -key-threshold=3
```
- Manual unlock (Use the ONLY Unseal Key you just COPYED)
- Example: vault operator unseal 8A5pAtDnCt1ysghmsUXOY5Bww4R5DZ1w6LnxVRektyau
```bash
# You will be asked to enter the Unseal Key 3 times (Threshold = 3)
vault operator unseal <PASTE_THE_UNSEAL_KEY_HERE>
vault operator unseal <PASTE_THE_UNSEAL_KEY_HERE>
vault operator unseal <PASTE_THE_UNSEAL_KEY_HERE>
```
- Check status (Should show Sealed: false)
```bash
vault status
```
- Step 5: Transit engine configuration for MySQL data encryption
- Log in (use the Root Token you COPIED in step 3)
```bash
vault login <PASTE_THE_ROOT_TOKEN_HERE>
```
- Transit Engine Configuration
```bash
# Enable the Transit Engine
vault secrets enable transit

# Create the encryption key and configure automatic rotation
vault write -f transit/keys/mysql-app-data
vault write transit/keys/mysql-app-data policy=rotation period=720h

echo "--- CONFIGURATION COMPLETE AND VALIDATED ---"
```
- Step 6: Creation and configuration of AppRole for Spring Boot application
- Create the Access Policy (transit-policy.hcl)
```bash
sudo nano /etc/vault.d/transit-policy.hcl
```
- Contents of transit-policy.hcl:
```bash
path "transit/encrypt/mysql-app-data" {
  capabilities = ["update"]
}

path "transit/decrypt/mysql-app-data" {
  capabilities = ["update"]
}

path "transit/rewrap/mysql-app-data" {
  capabilities = ["update"]
}

path "transit/keys/mysql-app-data" {
  capabilities = ["read"]
}

path "auth/token/lookup-self" {
  capabilities = ["read"]
}
```
- Writing the Policy in Vault
```bash
vault policy write mysql-transit-app /etc/vault.d/transit-policy.hcl
```
- Enable and Create the AppRole
```bash
# Enable the AppRole engine
vault auth enable approle

# Create the application role
vault write auth/approle/role/mysql-app-role \
    token_ttl=720h \
    token_max_ttl=720h \
    secret_id_ttl=24h \
    policies="mysql-transit-app" \
    bind_secret_id=true
```
- Obtain the AppRole Credentials ("The Keys")
```bash
# Obtain the Role ID
vault read auth/approle/role/mysql-app-role/role-id
# Get the Secret ID
vault write -f auth/approle/role/mysql-app-role/secret-id
```

- Configuration in Spring Boot
- Add the following environment variables to the `/etc/kojstar-terminal/kt.env` file:
```bash
# 1. Spring Vault Configuration
spring.vault.scheme=http
spring.vault.host=127.0.0.1
spring.vault.port=8200

# 2. AppRole Authentication Configuration
spring.vault.authentication=approle
spring.vault.app-role.role-id=<THE_ROLE_ID_YOU_COPIATED>
spring.vault.app-role.secret-id=<THE_SECRET_ID_QUE_COPIATED>
```
- Manual rotation of encryption keys in Vault
- Prerequisites
```bash
# 1. Secure the connection
export VAULT_ADDR='http://127.0.0.1:8200'

# 2. Log in (you must have pasted the Root Token or a development token)
vault login <YOUR_TOKEN>
```
- Manual Rotation Command
```bash
# Run the rotation of the 'mysql-app-data' key
vault write -f transit/keys/mysql-app-data/rotate
echo "--- FULL KEY ROTATION ---"
```
- Rotation Verification
```bash
# Get information about the key (look for the 'latest_version' field)
vault read transit/keys/mysql-app-data
echo "--- COMPLETE ROTATION VERIFICATION ---"
```
- Step 8: Secret ID Rotation Script and Cron Job
```bash
# Create file /usr/local/bin/vault-rotate-secret.sh
sudo nano /usr/local/bin/vault-rotate-secret.sh
```
- /usr/local/bin/vault-rotate-secret.sh content
```bash
#!/bin/bash
# Run this script as a cron job every 23 hours.
set -e

# Vault environment configuration
VAULT_ADDR="http://127.0.0.1:8200"
VAULT_TOKEN="<TU_ROOT_O_ADMIN_TOKEN_CON_PERMISOS>"
ROLE_NAME="mysql-app-role"
ENV_FILE="/etc/kojstar-terminal/kt.env"
BASHRC_FILE="~/.bashrc"

# 1. Generate new Secret ID
NEW_SECRET=$(curl -s -H "X-Vault-Token: $VAULT_TOKEN" \
-X POST "$VAULT_ADDR/v1/auth/approle/role/$ROLE_NAME/secret-id" | \
grep -o '"secret_id":"[^"]*' | cut -d'"' -f4)

if [ -z "$NEW_SECRET" ]; then
echo "$(date) ERROR: Failed to generate Secret ID. Vault unavailable or invalid token." >> /var/log/vault_rotation.log
exit 1
fi

# 2. Update the kt.env file with the new Secret ID
# We use 'sed' to replace only the line containing SECRET_ID
sudo sed -i "/KOJSTAR_TERMINAL_VAULT_SECRET_ID=/c\KOJSTAR_TERMINAL_VAULT_SECRET_ID=\"$NEW_SECRET\"" $ENV_FILE

# 3. Apply the changes to the system environment (without restarting the service)
# NOTE: This only works if your service file (Step 3.2) is configured
# to use 'EnvironmentFile'.
sudo systemctl daemon-reload

# 4. Notify systemd of an environment change (If necessary)
# In Spring Cloud Vault, if the token expires, it will attempt to use the last valid Secret ID.
# Since the Secret ID lasts for 24 hours, we don't need to restart the application.
echo "$(date) INFO: Secret ID for $ROLE_NAME successfully rotated and injected." >> /var/log/vault_rotation.log
```

- Save and Grant Permissions
```bash
# 1. Save the script (assuming you used nano or vim)
# 2. Grant execute permissions
sudo chmod +x /usr/local/bin/vault-rotate-secret.sh
```
- Configure the task with crontab
```bash
sudo crontab -e
```
- Add the Cron Task
```bash
# Run the script every day at 3:00 AM (and then every 23 hours).
0 3 * * * /usr/local/bin/vault-rotate-secret.sh >> /var/log/cron.log 2>&1

# Alternatively, run every 5 minutes for testing purposes
# */5 * * * * /usr/local/bin/vault-rotate-secret.sh >> /var/log/cron.log 2>&1
```
- To read role id
```bash
vault read auth/approle/role/mysql-app-role/role-id
```
- To read secret id
```bash
vault write -f auth/approle/role/mysql-app-role/secret-id
```

# INSTALL AND CONFIGURE MYSQL SERVER
- Install MySQL Server
```bash
sudo apt update
sudo apt install mysql-server
```
- Secure MySQL Installation
```bash
sudo mysql_secure_installation
# Follow the prompts to set root password and secure installation
```
- Login to MySQL with root user
```bash
sudo mysql
```
- Alter user root with native password
```bash
ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'your-root-password';
FLUSH PRIVILEGES;
```
- Create a new MySQL user and grant privileges
```bash
CREATE USER 'username'@'127.0.0.1' IDENTIFIED WITH mysql_native_password BY 'your-password';
GRANT ALL PRIVILEGES ON *.* TO 'username'@'127.0.0.1' WITH GRANT OPTION;
FLUSH PRIVILEGES;
EXIT;
```
- Restart MySQL Service
```bash
sudo systemctl restart mysql
sudo systemctl enable mysql
sudo systemctl status mysql
```
- Create Mysql Database
```bash
DROP DATABASE IF EXISTS kt_store;
CREATE DATABASE IF NOT EXISTS kt_store;

DROP DATABASE IF EXISTS kt_auth;
CREATE DATABASE IF NOT EXISTS kt_auth;

DROP DATABASE IF EXISTS kt_test;
CREATE DATABASE IF NOT EXISTS kt_test;
```

# MONGODB CONFIG
- MongoDB
```bash
# Import the public key used by the package management system
curl -fsSL https://www.mongodb.org/static/pgp/server-7.0.asc | sudo gpg --dearmor -o /usr/share/keyrings/mongodb-server-7.0.gpg
echo "deb [ arch=amd64,arm64 signed-by=/usr/share/keyrings/mongodb-server-7.0.gpg ] https://repo.mongodb.org/apt/ubuntu $(lsb_release -cs)/mongodb-org/7.0 multiverse" | sudo tee /etc/apt/sources.list.d/mongodb-org-7.0.list

# Install MongoDB
sudo apt update
sudo apt install -y mongodb-org

# Start and Enable MongoDB Service
sudo systemctl start mongod
sudo systemctl enable mongod
sudo systemctl status mongod
```
- Create admin user
```bash
mongosh # Connect to MongoDB without user and password
use admin
```
- Create the admin user with userAdminAnyDatabase role
```bash
db.createUser({
  user: "admin",
  pwd: "strong_password",
  roles: [ { role: "userAdminAnyDatabase", db: "admin" } ]
})
```
- Exit MongoDB shell
```bash
mongosh --username admin --password 'strong_password' --authenticationDatabase admin
```
- Create application user and assign roles
```bash
sudo nano /etc/mongod.conf
```
- Add user authentication and allow remote connections
```bash
# Add the following lines under the 'security' section
security:
  authorization: enabled

bindIp: 127.0.0.1
```
- Restart MongoDB to apply changes
```bash
sudo systemctl restart mongod
```
- Login with admin user
```bash
db.createUser({
  user: "user_server",
  pwd: "password",
  roles: [
    { role: "readWrite", db: "test" },
  ]
})
```
- Create application database and assign roles
```bash
db.grantRolesToUser(
  "user_server",
  [
    {
      role: "readWrite",
      db: "kojstar_terminal_logs"
    }
  ]
)
```
- Exit MongoDB shell
```bash
exit
```
- Restart MongoDB to apply changes
```bash
sudo systemctl restart mongod

sudo ufw deny 27017
```

# Installa and configure Redis
```bash
# Update packages
sudo apt update

# Install Redis
sudo apt install redis-server -y

# Enable and start the service
sudo systemctl enable redis-server
sudo systemctl start redis-server

# Check status
sudo systemctl status redis-server
```
- Configure Redis for password authentication
```bash
# Configure Redis to require a password
sudo nano /etc/redis/redis.conf
```
- Add the following lines to redis.conf
```bash
# Enforce protected mode and set a password
protected-mode yes

# Set a strong password
requirepass your-redis-password

# Supervise Redis with systemd
supervised systemd
```

- Restart Redis to apply changes
```bash
sudo systemctl restart redis-server

sudo ufw deny 6379
```

# Oauth2 Key.jks
- To generate the key.jks file, run the following command

```bash
# 1. Create keystore and generate RSA key (valid for 10 years)
keytool -genkeypair \
  -alias jwt-key \
  -keyalg RSA \
  -keysize 2048 \
  -validity 3650 \
  -keystore keystore.jks \
  -storepass your-password \
  -keypass your-password \
  -dname "CN=, OU=, O=, L=, S=, C="

# 2. Export the public key (optional, for verification)
keytool -exportcert \
  -alias jwt-key \
  -keystore keystore.jks \
  -storepass your-password \
  -file public-key.cer

# 3. View content (optional)
keytool -list -v \
  -keystore keystore.jks \
  -storepass your-password
```

# Generate PPK from Puttygen
```bash
sudo apt update
sudo apt install putty-tools
```

- Generate PPK and public key
```bash
# Generate new PPK key
puttygen -t rsa -b 2048 -C "vps-user" -o private-key.ppk

# Extrct public key
puttygen private-key.ppk -L
```

- ppk to pem
```bash
# Convert PPK to PEM
puttygen private-key.ppk -O private-openssh -o private-key.pem

# Set permissions
chmod 400 private-key.pem
```

### Mysql tunnel
```bash
# Mysql tunnel
ssh -i /path/to/your/private-key.pem -L 3307:127.0.0.1:3306 decembertries@ip-vps
```

### MongoDB tunnel
```bash
# MongoDB tunnel
ssh -i /path/to/your/private-key.pem -L 27018:12700:27017 decembertries@ip-vps
```

### Redis tunnel
```bash
# Redis tunnel
ssh -i /path/to/your/private-key.pem -L 6380:127.0.0.1:6379 decembertries@ip-vps
```

### Tunel all
```bash
ssh -i /path/to/your/private-pem \
    -L 3307:127.0.0.1:3306 \
    -L 27018:127.0.0.1:27017 \
    -L 6380:127.0.0.1:6379 \
    decembertries@ip-vps -N
```

### Meaning of the ports
- 3307: Local port for MySQL
- 27018: Local port for MongoDB
- 6380: Local port for Redis
- N : No execute remote commands
- -i : Identity file (private key)

# Nginx Installation Guide as a Reverse Proxy with SSL for Kojstar Terminal
Step 1: Nginx Installation
```bash
# Update packages and install Nginx
sudo apt update
sudo apt install nginx -y

# Check Nginx status
sudo systemctl status nginx
```

- Step 2: Firewall Configuration
```bash
# Allow HTTP and HTTPS traffic
sudo ufw allow 'Nginx Full'
sudo ufw enable
sudo ufw status
```
- Step 3: Install Certbot for SSL Certificates
```bash
# Install Certbot and the Nginx plugin
sudo apt-get install certbot
```

- Obtain SSL certificates
```bash
# Temporarily stop Nginx
sudo systemctl stop nginx

sudo certbot certonly --standalone \
-d kojstarterminal.com \
--agree-tos -m admin@kojstarterminal.com

# Restart Nginx
sudo systemctl start nginx
```
- Step 4: Configure Nginx as a Reverse Proxy
```bash
# Create configuration file for Kojstar Terminal
sudo nano /etc/nginx/sites-available/kojstarterminal.com  
```

- Contents of the kojstarterminal.com file
```bash
# HTTP to HTTPS redirection
server {
    listen 80;
    server_name kojstarterminal.com;
    return 301 https://$host$request_uri;
}

# Main HTTPS configuration
server {
    listen 443 ssl;
    server_name kojstarterminal.com;

    # SSL certificates (using Let's Encrypt)
    ssl_certificate /etc/letsencrypt/live/kojstarterminal.com/fullchain.pem;
    ssl_certificate_key /etc/letsencrypt/live/kojstarterminal.com/privkey.pem;
    ssl_protocols TLSv1.2 TLSv1.3;

    # SSL Optimization (optional but recommended)
    ssl_session_cache shared:SSL:10m;
    ssl_session_timeout 10m;
    ssl_ciphers 'ECDHE-ECDSA-AES128-GCM-SHA256:ECDHE-RSA-AES128-GCM-SHA256';
    ssl_prefer_server_ciphers on;

    # Root for the Angular application
    root /var/www/html;
    index index.html;

    # Angular configuration (root path)
    location / {
        try_files $uri $uri/ /index.html;
        add_header Cache-Control "no-cache, no-store, must-revalidate";
        add_header Pragma "no-cache";
        expires 0;
    }

    # Cache for static assets
    location ~* \.(js|css|png|jpg|jpeg|gif|ico|json|woff2|ttf)$ {
        expires 1y;
        access_log off;
        add_header Cache-Control "public";
    }
    
    # 1. Proxy for User API
    location /user-api/ {

        rewrite ^/user-api/(.*)$ /$1 break;

        proxy_pass http://127.0.0.1:8080/;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }

    # 2. Proxy for Auth API
    location /auth-api/ {

        rewrite ^/auth-api/(.*)$ /$1 break;

        proxy_pass http://127.0.0.1:9001/;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
}
```
- Step 5: Enable the Nginx Configuration
```bash
# Enable the site and disable the default site
sudo ln -s /etc/nginx/sites-available/kojstarterminal.com /etc/nginx/sites-enabled/
sudo rm /etc/nginx/sites-enabled/default
# Test Nginx configuration
sudo nginx -t
# Restart Nginx to apply changes
sudo systemctl restart nginx
```