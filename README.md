# Create Database
```bash
DROP DATABASE IF EXISTS kt_store;
CREATE DATABASE IF NOT EXISTS kt_store;

DROP DATABASE IF EXISTS kt_auth;
CREATE DATABASE IF NOT EXISTS kt_auth;
```

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
export KOJSTAR_TERMINAL_OAUTH2_SERVICE_DB_URL="your_database_url"

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

export KOJSTAR_TERMINAL_CORS_ALLOWED_ORIGINS="http://localhost:4200,https://192.168.1.233:4200,https://your_domai.com,https://www.your_domain.com" # On production, use the your_domain.com
export KOJSTAR_TERMINAL_CORS_ALLOWED_METHODS="GET,POST,PUT,DELETE,OPTIONS" # Allowed methods
export KOJSTAR_TERMINAL_CORS_ALLOWED_HEADERS="x-requested-with, authorization, Content-Type, Authorization, credential, X-XSRF-TOKEN, X-Device-User-Agent, X-Device-IP" # Allowed headers
export KOJSTAR_TERMINAL_CORS_MAX_AGE="3600" # 1 hour

export KOJSTAR_TERMINAL_VAULT_ROLE_ID="your_vault_role_id"
export KOJSTAR_TERMINAL_VAULT_SECRET_ID="your_secret_id"

export KOJSTAR_TERMINAL_GOOGLE_CLIENT_ID="your-google-client-id"
export KOJSTAR_TERMINAL_GOOGLE_CLIENT_SECRET="your-google-client-secret"
export KOJSTAR_TERMINAL_GOOGLE_REDIRECT_URI="your-redirect-uri"
export KOJSTAR_TERMINAL_GITHUB_CLIENT_ID="your-google-client-id"
export KOJSTAR_TERMINAL_GITHUB_CLIENT_SECRET="your-google-client-secret"
export KOJSTAR_TERMINAL_GITHUB_REDIRECT_URI="your-redirect-uri"

export KOJSTAR_TERMINAL_JWT_KEY_STORE="your-jwt-key-store"
export KOJSTAR_TERMINAL_JWT_KEY_PASSWORD="your-jwt-key-password"
export KOJSTAR_TERMINAL_JWT_SECRET="your-jwt-secret"
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
# 4. Ver logs en tiempo real
sudo journalctl -u kt-user-service -f
```

# Create service file for auth-service

```bash
  sudo nano /etc/systemd/system/kt-auth-service.service
  # Add the content contained in kt-auth-service.service file
  
  sudo nano /etc/systemd/system/kt-user-service.service
  # Add the content contained in kt-user-service.service file
  
  sudo nano /etc/systemd/system/kt-oauth2-service.service
  # Add the content contained int kt-oauth2-service.service file
  
  sudo systemctl daemon-reload
  
  sudo systemctl start kt-auth-service
  sudo systemctl enable kt-auth-service
  sudo systemctl start kt-user-service
  sudo systemctl enable kt-user-service
  sudo systemctl start kt-oauth2-service
  sudo systemctl enable kt-oauth2-service
  
  sudo journalctl -u kt-user-service -f
```

# 🚀 GUÍA DE CONFIGURACIÓN DE HASHICORP VAULT 🛠️
# vault -version "Vault v1.21.1 (2453aac2638a6ae243341b4e0657fd8aea1cbf18), built 2025-11-18T13:04:32Z"

# ----------------------------------------------------------------------
# PASO 1: LIMPIEZA TOTAL Y PREPARACIÓN DEL SISTEMA
# ----------------------------------------------------------------------
```bash
# 1. Detener servicios, eliminar procesos y el usuario 'vault'
sudo systemctl stop vault.service || true
sudo systemctl disable vault.service || true
sudo rm -f /etc/systemd/system/vault.service

sudo killall vault || true
sudo pkill -u vault || true
sleep 2
sudo userdel vault || true

# 2. Eliminar el paquete, binarios, configuración y datos
sudo apt-get remove --purge vault -y || true
sudo rm -rf /etc/vault.d
sudo rm -f /etc/vault.d/vault-init.txt
sudo rm -rf /var/lib/vault
sudo rm -f /usr/share/keyrings/hashicorp-archive-keyring.gpg
sudo rm -f /etc/apt/sources.list.d/hashicorp.list
sudo rm -f /etc/kojstar-terminal/vault-rotate-secret.sh

# 3. Instalar dependencias esenciales y recargar systemd
sudo apt update && sudo apt install -y curl gpg
sudo systemctl daemon-reload
```

# ----------------------------------------------------------------------
# PASO 2: INSTALACIÓN DE VAULT Y PREPARACIÓN DE DIRECTORIOS
# ----------------------------------------------------------------------
```bash
# 1. Agregar el repositorio de HashiCorp e instalar Vault
curl -fsSL https://apt.releases.hashicorp.com/gpg | sudo gpg --dearmor -o /usr/share/keyrings/hashicorp-archive-keyring.gpg
echo "deb [signed-by=/usr/share/keyrings/hashicorp-archive-keyring.gpg] https://apt.releases.hashicorp.com $(lsb_release -cs) main" | sudo tee /etc/apt/sources.list.d/hashicorp.list
sudo apt update && sudo apt install vault -y

# 2. Crear usuario y directorios
sudo useradd --system --home /etc/vault.d --shell /bin/false vault
sudo mkdir --parents /etc/vault.d /var/lib/vault/data
sudo chown -R vault:vault /etc/vault.d /var/lib/vault/data
```

# ----------------------------------------------------------------------
# PASO 3: CONFIGURACIÓN DE ARCHIVOS (APLICAR EL CONTENIDO DE ABAJO)
# ----------------------------------------------------------------------
```bash
# 1. Archivo de Configuración de Vault: /etc/vault.d/vault.hcl
sudo nano /etc/vault.d/vault.hcl
```

# Contenido de vault.hcl:
# --------------------
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
# --------------------


# 2. Archivo de Servicio de Systemd: /etc/systemd/system/vault.service
```bash
sudo nano /etc/systemd/system/vault.service
```
# Contenido de vault.service (Incluye Auto-Unseal):
# --------------------
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
# --------------------

# ----------------------------------------------------------------------
# PASO 4: INICIALIZACIÓN Y ACTIVACIÓN DEL SERVICIO
# ----------------------------------------------------------------------
```bash
# 1. Recargar y habilitar el servicio
sudo systemctl daemon-reload
sudo systemctl enable vault.service

# --- Inicialización de Claves (Única Intervención Manual) ---

# 1. Iniciar el servicio (Ahora no fallará, solo iniciará sellado)
sudo systemctl start vault.service
sleep 5

# 2. Configurar la dirección
export VAULT_ADDR='http://127.0.0.1:8200'

# 3. 🚨 Inicializar con 1 Share y 1 Threshold 🚨
# COPIA INMEDIATAMENTE EL ROOT TOKEN Y EL UNSEAL KEY QUE APARECEN EN PANTALLA
vault operator init -key-shares=5 -key-threshold=3
```

# 4. Desbloqueo manual (Usa la ÚNICA Unseal Key que acabas de COPIAR)
# Ejemplo: vault operator unseal 8A5pAtDnCt1ysghmsUXOY5Bww4R5DZ1w6LnxVRektyau
```bash
# Se te pedirá que ingreses la Unseal Key 3 veces (Threshold = 3)
vault operator unseal <PEGA_AQUÍ_LA_UNSEAL_KEY>
vault operator unseal <PEGA_AQUÍ_LA_UNSEAL_KEY>
vault operator unseal <PEGA_AQUÍ_LA_UNSEAL_KEY>
```

# 5. Verificar estado (Debe mostrar Sealed: false)
```bash
vault status
```

# ----------------------------------------------------------------------
# PASO 5: CONFIGURACIÓN DEL MOTOR TRANSIT PARA CIFRADO DE DATOS
# ----------------------------------------------------------------------
# 1. Iniciar sesión (usa el Root Token que COPIASTE en el paso 3)
```bash
vault login <PEGA_AQUÍ_EL_ROOT_TOKEN>
```

```bash
# 2. Habilitar el Motor Transit
vault secrets enable transit

# 3. Crear la clave de cifrado y configurar la rotación automática
vault write -f transit/keys/mysql-app-data
vault write transit/keys/mysql-app-data policy=rotation period=720h

echo "--- ✅ CONFIGURACIÓN COMPLETA Y VALIDADA ✅ ---"
```

# ----------------------------------------------------------------------
# PASO 6: CREACIÓN Y CONFIGURACIÓN DE APP ROLE PARA KOJSTAR TERMINAL
# ----------------------------------------------------------------------
# 1. Crear la Política de Acceso (transit-policy.hcl)
```bash
sudo nano /etc/vault.d/transit-policy.hcl
```
# Contenido de transit-policy.hcl:
# --------------------
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

# 2. Escribir la Política en Vault
```bash
vault policy write mysql-transit-app /etc/vault.d/transit-policy.hcl
```

# 3. Habilitar y Crear el AppRole
```bash
# Habilitar el motor AppRole
vault auth enable approle

# Crear el rol de la aplicación
vault write auth/approle/role/mysql-app-role \
    token_ttl=720h \
    token_max_ttl=720h \
    secret_id_ttl=24h \
    policies="mysql-transit-app" \
    bind_secret_id=true
```

# 4. Obtener las Credenciales del AppRole (Las "Claves")
```bash
# Obtener el Role ID
vault read auth/approle/role/mysql-app-role/role-id
# Obtener el Secret ID
vault write -f auth/approle/role/mysql-app-role/secret-id
```

# 5. Configuración en Spring Boot
- Agrega las siguientes variables de entorno en el archivo `/etc/kojstar-terminal/kt.env`:

```bash
# 1. Configuración de Spring Vault
spring.vault.scheme=http
spring.vault.host=127.0.0.1
spring.vault.port=8200

# 2. Configuración de Autenticación AppRole
spring.vault.authentication=approle
spring.vault.app-role.role-id=<EL_ROLE_ID_QUE_COPIASTE>
spring.vault.app-role.secret-id=<EL_SECRET_ID_QUE_COPIASTE>
```

# ----------------------------------------------------------------------
# PASO 7: ROTACIÓN MANUAL DE CLAVES DE CIFRADO PARA TESTING
# ----------------------------------------------------------------------

# 1. Requisitos Previos
```bash
# 1. Asegurar la conexión
export VAULT_ADDR='http://127.0.0.1:8200'

# 2. Iniciar sesión (debes haber pegado el Root Token o un token de desarrollo)
vault login <TU_TOKEN>
```

# 2. Comando de Rotación Manual
```bash
# Ejecuta la rotación de la clave 'mysql-app-data'
vault write -f transit/keys/mysql-app-data/rotate
echo "--- 🔄 ROTACIÓN DE CLAVES COMPLETA 🔄 ---"
```

# 3. Verificación de la Rotación
```bash
# Obtener información sobre la clave (busca el campo 'latest_version')
vault read transit/keys/mysql-app-data
echo "--- ✅ VERIFICACIÓN DE ROTACIÓN COMPLETA ✅ ---"
```

# ----------------------------------------------------------------------
# PASO 8: ROTACIÓN DE SECRET ID E INYECCIÓN DE ENVIRONMENT
# ----------------------------------------------------------------------
```bash
# Create file /usr/local/bin/vault-rotate-secret.sh
sudo nano /usr/local/bin/vault-rotate-secret.sh
```
# /usr/local/bin/vault-rotate-secret.sh content
```bash
#!/bin/bash
# Ejecutar este script como un cron job cada 23 horas.
set -e

# Configuración del entorno de Vault
VAULT_ADDR="http://127.0.0.1:8200"
VAULT_TOKEN="<TU_ROOT_O_ADMIN_TOKEN_CON_PERMISOS>"
ROLE_NAME="mysql-app-role"
ENV_FILE="/etc/kojstar-terminal/kt.env"
BASHRC_FILE="~/.bashrc"

# 1. Generar nuevo Secret ID
NEW_SECRET=$(curl -s -H "X-Vault-Token: $VAULT_TOKEN" \
-X POST "$VAULT_ADDR/v1/auth/approle/role/$ROLE_NAME/secret-id" | \
grep -o '"secret_id":"[^"]*' | cut -d'"' -f4)

if [ -z "$NEW_SECRET" ]; then
echo "$(date) ERROR: Fallo al generar Secret ID. Vault no disponible o token inválido." >> /var/log/vault_rotation.log
exit 1
fi

# 2. 🚨 Actualizar el archivo kt.env con el nuevo Secret ID 🚨
# Usamos 'sed' para reemplazar solo la línea que contiene SECRET_ID
sudo sed -i "/KOJSTAR_TERMINAL_VAULT_SECRET_ID=/c\KOJSTAR_TERMINAL_VAULT_SECRET_ID=\"$NEW_SECRET\"" $ENV_FILE

# 3. Aplicar los cambios al entorno del sistema (sin reiniciar el servicio)
# NOTA: Esto solo funciona si tu archivo de servicio (Paso 3.2) está configurado
# para usar 'EnvironmentFile'.
sudo systemctl daemon-reload

# 4. Notificar a systemd de un cambio en el entorno (Si es necesario)
# En Spring Cloud Vault, si el token expira, intentará usar el último Secret ID válido.
# Como el Secret ID dura 24 horas, no necesitamos reiniciar la aplicación.

echo "$(date) INFO: Secret ID para $ROLE_NAME rotado e inyectado con éxito." >> /var/log/vault_rotation.log
```

# Paso A: Guardar y Dar Permisos
```bash
# 1. Guardar el script (asumiendo que lo hiciste con nano o vim)
# 2. Dar permisos de ejecución
sudo chmod +x /usr/local/bin/vault-rotate-secret.sh
```

# Paso B: Configurar la Tarea con crontab
```bash
sudo crontab -e
```

# Paso C: Añadir la Tarea Cron
```bash
# Ejecutar el script cada día a las 3:00 AM (y luego cada 23 horas).
0 3 * * * /usr/local/bin/vault-rotate-secret.sh >> /var/log/cron.log 2>&1
# */5 * * * * /usr/local/bin/vault-rotate-secret.sh >> /var/log/cron.log 2>&1
```