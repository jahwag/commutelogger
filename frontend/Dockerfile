# build stage
FROM node:13-alpine as build-stage
ARG NGINX_CONF="nginx.conf"
WORKDIR /app
COPY package*.json ./
RUN yarn install
COPY ./ .
COPY ${NGINX_CONF} nginx.conf
RUN yarn build

# production-stage
FROM nginx as production-stage
RUN mkdir /app
COPY --from=build-stage /app/dist /app
COPY --from=build-stage /app/nginx.conf /etc/nginx/nginx.conf
EXPOSE 80