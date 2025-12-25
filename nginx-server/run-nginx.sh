#!/bin/bash
docker run -p 8080:80 -d --name my-react-nginx \
  -v $(pwd)/my-nginx-conf:/etc/nginx/conf.d:ro \
  -v $(pwd)/data:/usr/share/nginx/html:ro
  -v $(pwd)/my-nginx-conf:/etc/nginx/conf.d:ro \
\
  my-react-nginx-server

