# Template de autorização e autenticação usando o MT-Login
Essa aplicação se trata de um template que é uma implementação prática para autenticação de usuários para o uso nas aplicações da  Secretaria de Desenvolvimento Econômico do Estado de Mato Grosso usando o MT-Login.
# Requerimentos
É necessário ter instalado o [JDK-22](https://www.oracle.com/java/technologies/downloads/#java22) ou o [OpenJDK-22](https://jdk.java.net/archive/) e a ferramenta de implantação de aplicações, o Docker.
### Instalação do docker
#### Windows
* Baixe o instalador do Docker Desktop para Windows [aqui](https://www.docker.com/products/docker-desktop/).
* Execute o instalador e siga as instruções.
* Após a instalação, inicie o Docker Desktop.
#### Linux
* Instale o Docker Engine no Ubuntu seguindo as instruções nesse [guia](https://www.digitalocean.com/community/tutorials/how-to-install-and-use-docker-on-ubuntu-22-04).
* Instale o Docker Compose.
Para a distribuição Ubuntu, siga esse [guia](https://www.digitalocean.com/community/tutorials/how-to-install-and-use-docker-compose-on-ubuntu-22-04)

Também é necessário criar um arquivo ```.env```, o qual terá todas as variáveis de ambiente da aplicação.
``` 
SPRING_PROFILES_ACTIVE=

DB_NAME=
DB_USER=
DB_PASS=
DB_EXTERNAL_PORT=
DB_PORT=
DB_HOST=

DOCKER_HUB_USER=
API_VERSION=
# ORIGEM CORS DEVEM SER SEPARADAS POR VÍRGULA
CORS_ALLOWED_ORIGINS=

API_CONTAINER_NAME=
API_EXTERNAL_PORT=
API_PORT=

JWKS_URI=
```
## Implantação

Na pasta raiz, execute os seguinte comando para criação da imagem da aplicação:

```bash
docker compose build
```
Após montada, faça a implantação da aplicação usando o seguinte comando:
```bash
docker compose up -d
```
## Customização

Na classe ```CustomJwtAuthenticationTokenConverter.java``` - ```Linha 56```, é usado o nome de uma Role padrão para inserir no contexto da sessão, no caso está implementado como ROLE_USER. Caso necessário mude este valor.
```
.roles(List.of(roleService.findByNomeOrElseThrowNotFound("ROLE_USER")))
```