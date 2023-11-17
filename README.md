# Kinokuniya Audiobook SOAP Service

This repository is intended to be a SOAP service for the Kinokuniya Audiobook Website created with JAX-WS and MySQL. This service mainly handles the CRUD operations for subscription data and connects between the PHP app and REST service.

## Installation
1. Clone the repository
2. Build docker image using command located at `/script/init.sh`
3. Go to the config repository and run the command `docker-compose up -d`

## Database
![Database](./screenshots/soap-database.png)

## API Endpoints
All endpoints are accessed through the `http://localhost:8001/api/subscribe` URL.
| Service | Description | Author | NIM |
| --- | --- | --- | --- |
| createSubscribe | Create a new subscription | Enrique Alifio Ditya | 13521142 |
| approveSubscribe | Approve a subscription | Enrique Alifio Ditya | 13521142 |
| rejectSubscribe | Reject a subscription | Enrique Alifio Ditya | 13521142 |
| getAllReqSubscribe | Get all subscription requests | Enrique Alifio Ditya | 13521142 |
| getAllAuthorBySubID | Get all authors by subscription ID | Enrique Alifio Ditya | 13521142 |
| checkStatus | Check subscription status | Enrique Alifio Ditya | 13521142 |
| getAllSubscribers | Get all subscribers | Enrique Alifio Ditya | 13521142 |