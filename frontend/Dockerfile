FROM nginx:alpine

COPY dist /usr/share/nginx/html
COPY /nginx/conf.d /etc/nginx/conf.d

EXPOSE 3080

CMD ["nginx", "-g", "daemon off;"]