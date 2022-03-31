ARG APP_TENANT_ID
ARG APP_CLIENT_ID
ARG APP_CLIENT_SECRET
ARG APP_ALLOWED_GROUPS
ARG APP_REDIRECT_URI

# ============================================================================
# Generate resources stage
# ============================================================================
FROM node:12-alpine as generate-resources-stage
ARG APP_TENANT_ID
ARG APP_CLIENT_ID
ARG APP_REDIRECT_URI
ENV VUE_APP_TENANT_ID=$APP_TENANT_ID
ENV VUE_APP_CLIENT_ID=$APP_CLIENT_ID
ENV VUE_APP_REDIRECT_URI=$APP_REDIRECT_URI
RUN : "${VUE_APP_TENANT_ID:?APP_TENANT_ID must be set.}" \
    : "${VUE_APP_CLIENT_ID:?APP_CLIENT_ID must be set.}" \
     : "${VUE_APP_REDIRECT_URI:?VUE_APP_REDIRECT_URI must be set.}"

WORKDIR /app
COPY ./frontend/package*.json ./
RUN yarn install
COPY ./frontend .
RUN yarn build

# ============================================================================
# Build backend stage
# ============================================================================
FROM maven:alpine as build-stage
COPY ./backend .
COPY --from=generate-resources-stage /app/dist ./src/main/resources/public
RUN mvn clean package

# ============================================================================
# Production stage
# ============================================================================
FROM adoptopenjdk/openjdk8:alpine-slim as production-stage

COPY --from=build-stage target/*.jar app.jar
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]