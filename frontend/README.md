# commutelogger-frontend
This repository contains the frontend for CommuteLogger.

## Project setup
```
yarn install
```

### Compiles and hot-reloads for development
```
yarn serve
```

Frontend is served on http://localhost by default.

### Compiles and minifies for production
```
yarn build --mode production
```

### Environment variables
You can set your own environment variables locally in `.env.local`. This file will be excluded by Git.

The following environment variables must be specified:
```
VUE_APP_CLIENT_ID=<client-id>;
VUE_APP_TENANT_ID=<tenant-id>;
VUE_APP_REDIRECT_URI=<redirect-uri>
```