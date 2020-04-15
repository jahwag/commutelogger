# commutelogger-backend
This repository contains the backend for CommuteLogger.

## Project setup
```
mvn clean package
```

### Running
```
mvn spring:run -DAPP_CLIENT_ID=<id> -DAPP_ALLOWED_GROUPS=commute-logger-users -DAPP_CLIENT_SECRET=<secret> -DDATASOURCE_URL=jdbc:postgresql://<host>:5432/ -DDATASOURCE_USERNAME=<user> -DDATASOURCE_PASSWORD=<pass>
```

Backend is served on http://localhost:8080 by default.

## User management
### Users
To use CommuteLogger, a user must be a member of the group defined in property 
`azure.activedirectory.user-group.allowed-groups`. 

All members of this group will receive the `ROLE_USER` authority in the Security Context.

### Admins
A column `admin` has been prepared in the database for use in the REST API. It can only be set manually via direct query to database.

A corresponding `ROLE_ADMIN` for access control has not been implemented (yet).