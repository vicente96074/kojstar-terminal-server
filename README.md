# Edit environment variables

To edit environment variables on your system, follow the instructions below based on your operating system:

# Linux

- Open your terminal.
- Use a text editor to open the `.bashrc` file located in your home directory. For example, you can use `nano`
- ```bash
  nano ~/.bashrc
  ```
- Add the following lines

```bash
# Kojstar Terminal Environment Variables
export KOJSTAR_TERMINAL_SSL_KEY_ALIAS="your_ssl_key_alias"
export KOJSTAR_TERMINAL_SSL_KEY_STORE="your_ssl_key_store_path"
export KOJSTAR_TERMINAL_SSL_KEY_PASSWORD="your_ssl_key_password"
export KOJSTAR_TERMINAL_SSL_KEY_PASSWORD="your_ssl_key_password"
export KOJSTAR_TERMINAL_AUTH_SERVICE_DB_URL="your_database_url"
export KOJSTAR_TERMINAL_MYSQL_USERNAME="your_database_username"
export KOJSTAR_TERMINAL_MYSQL_PASSWORD="your_database_password"
export KOJSTAR_TERMINAL_TIME_ZONE="your_time_zone"

export KOJSTAR_TERMINAL_EMAIL_HOST="your_email_host"
export KOJSTAR_TERMINAL_EMAIL_PORT="your_email_port"
export KOJSTAR_TERMINAL_EMAIL_PROTOCOL="your_email_protocol"
export KOJSTAR_TERMINAL_EMAIL_USERNAME="your_email_username"
export KOJSTAR_TERMINAL_EMAIL_PASSWORD="your_email_password"
export KOJSTAR_TERMINAL_EMAIL_SENDER="your_email_sender"

export KOJSTAR_TERMINAL_SPRING_REDIS_HOST="your_redis_host"
export KOJSTAR_TERMINAL_SPRING_REDIS_PORT="your_redis_port"
export KOJSTAR_TERMINAL_SPRING_REDIS_PASSWORD="your_redis_password"
export KOJSTAR_TERMINAL_JWT_SECRET="your_jwt_secret"
export KOJSTAR_TERMINAL_JWT_EXPIRATION="your_jwt_expiration_time"
export KOJSTAR_TERMINAL_SETTINGS_JSON_FILE_PATH="your_settings_json_file_path"
export KOJSTAR_TERMINAL_OAUTH2_HOST_ISSUER="your_oauth2_host_issuer"

KOJSTAR_TERMINAL_CORS_ALLOWED_ORIGINS="http://localhost:4200,https://192.168.1.233:4200,https://your_domai.com,https://www.your_domain.com" # On production, use the your_domain.com
KOJSTAR_TERMINAL_CORS_ALLOWED_METHODS="GET,POST,PUT,DELETE,OPTIONS" # Allowed methods
KOJSTAR_TERMINAL_CORS_ALLOWED_HEADERS="x-requested-with, authorization, Content-Type, Authorization, credential, X-XSRF-TOKEN, X-Device-User-Agent, X-Device-IP" # Allowed headers
KOJSTAR_TERMINAL_CORS_MAX_AGE="3600" # 1 hour
```

- Save the file and exit the text editor.
- To apply the changes, run:

```bash
source ~/.bashrc
```

# Set systemd variables and services

- Open your terminal.

- Create a directory for kojstar-terminal configuration files:

```bash
sudo mkdir -p /etc/kojstar-terminal
sudo nano /etc/kojstar-terminal/kt.env

# Add the Kojstar Terminal environment variables to kt.env file
```

- Set ownership and permissions for security:
```bash
# Cambiar propietario a root
sudo chown -R root:root /etc/kojstar-terminal

# Permisos restrictivos (solo root puede leer)
sudo chmod 600 /etc/kojstar-terminal/*.env

# Verificar
ls -la /etc/kojstar-terminal/
```

- Apply changes to systemd service files:

```bash
# 1. Recargar systemd
sudo systemctl daemon-reload

# 2. Reiniciar el servicio
sudo systemctl restart kt-user-service

# 3. Ver el estado
sudo systemctl status kt-user-service

# 4. Ver logs en tiempo real
sudo journalctl -u kt-user-service -f
```

# Create service file for auth-service

```bash
  sudo nano /etc/systemd/system/kt-auth-service.service
  # Add the content contained in kt-auth-service.service file
  
  sudo nano /etc/systemd/system/kt-user-service.service
  # Add the content contained in kt-user-service.service file
  
  sudo systemctl daemon-reload
  
  sudo systemctl start kt-auth-service
  sudo systemctl enable kt-auth-service
  sudo systemctl start kt-user-service
  sudo systemctl enable kt-user-service
```
