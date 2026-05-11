# Build stage
FROM node:20-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
# Exportamos la app de Expo para la plataforma web
RUN npx expo export --platform web

# Run stage
FROM nginx:alpine
# Expo exporta a la carpeta 'dist' por defecto
COPY --from=build /app/dist /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
