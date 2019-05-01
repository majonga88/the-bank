# THE BANK

A standalone and lightweight Rest API powered by Jetty Server

The goal is to provide a apis allowing to provide money transfer / withdraw / deposit

## Table of Contents

- [Project Structure](#project-structure)
- [How to](#how-to)
    - [Run](#run)
    - [CURL](#curl)
        - [User part](#user-part)
            - [Creation](#creation)
        - [Account part](#account-part)
            - [Creation](#creation)
            - [Deposit](#deposit)


## Project Structure

```
.
├── account (rest api)
├── user (rest api)
└── data.sql (data of application)
```

Out of the box, we've got :

- JDK 11
- Jetty server / Jersey
- Database : H2
- Testing :
    - Dao test with H2/junit
    - Api test with jersey test framework/junit

## How to

### Run

Need Java 11 to launch

```bash
    mvn clean install
```

```bash
    java -jar the-bank-1.0.0-SNAPSHOT.jar YourPath/data.sql  
```

### CURL

#### User part

##### Creation

Create a user
```bash
curl -X POST \
  http://localhost:8080/api/users \
  -H 'Content-Type: application/json' \
  -H 'cache-control: no-cache' \
  -d '{
	"username": "dgreenwich",
	"firstName": "Daniel",
	"lastName": "Greenwich",
	"email": "d.greenwich@mail.com"
}'
```
---
Verify the user created
```bash
curl -X GET \
  http://localhost:8080/api/users/dgreenwich \
  -H 'cache-control: no-cache'
```

**Waiting result**
```json
{
    "id": 4,
    "username": "dgreenwich",
    "firstName": "Daniel",
    "lastName": "Greenwich",
    "email": "d.greenwich@mail.com"
}
```

#### Account part

##### Creation

Create an account for the created user
```bash
curl -X POST \
  http://localhost:8080/api/accounts \
  -H 'Content-Type: application/json' \
  -H 'cache-control: no-cache' \
  -d '{
	"username":"dgreenwich",
	"name":"Bank Account",
	"balance":"100",
	"currency":"EUR"
}'
```
---
Verify the account created
```bash
curl -X GET \
  http://localhost:8080/api/accounts/6 \
  -H 'cache-control: no-cache'
```

**Waiting result**
```json
{
    "id": 6,
    "username": "dgreenwich",
    "name": "Bank Account",
    "balance": 100,
    "currency": "EUR"
}
```

##### Deposit

Make a deposit on his created account
```bash
curl -X PUT \
  http://localhost:8080/api/accounts/6/deposit/100 \
  -H 'cache-control: no-cache'
```
---
Verify the deposit on account
```bash
curl -X GET \
  http://localhost:8080/api/accounts/6 \
  -H 'cache-control: no-cache'
```

**Waiting result**
```json
{
    "id": 6,
    "username": "dgreenwich",
    "name": "Bank Account",
    "balance": 200,
    "currency": "EUR"
}
```

##### Transfer

And make a money transfer to an existing account
```bash
curl -X POST \
  http://localhost:8080/api/accounts/transfer \
  -H 'Content-Type: application/json' \
  -H 'cache-control: no-cache' \
  -d '{
	"to": "6",
	"from": "3",
	"amount": "100",
	"currency": "EUR"
}'
```
---
Verify the debited account
```bash
curl -X GET \
  http://localhost:8080/api/accounts/3 \
  -H 'cache-control: no-cache'
```

**Waiting result**
```json
{
    "id": 3,
    "username": "jdeer",
    "name": "Bank Account",
    "balance": 200,
    "currency": "EUR"
}
```
---
Verify the credited account
```bash
curl -X GET \
  http://localhost:8080/api/accounts/6 \
  -H 'cache-control: no-cache'
```

**Waiting result**
```json
{
    "id": 6,
    "username": "dgreenwich",
    "name": "Bank Account",
    "balance": 100,
    "currency": "EUR"
}
```
