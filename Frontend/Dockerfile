FROM node:14-alpine

WORKDIR /usr/src/app

COPY package*.json ./
RUN npm install

COPY ./src ./src
COPY ./public ./public

RUN npm install -g serve
RUN npm run build

EXPOSE 3000
CMD ["serve", "-l", "3000", "-s", "build"]