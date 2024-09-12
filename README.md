# Payment Transaction System Rest API
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white) ![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=Spring&logoColor=white)  ![H2 Database](https://img.shields.io/badge/H2%20Database-018bff?style=for-the-badge&logoColor=white) 

I developed a Rest API for a simplified payment platform. This Rest API was built by using ** Spring Boot and Java **. On this platform, users can deposit money and make transfers between themselves. There are 2 types of users: common users and merchants. Both have wallets with money and can make transfers between them.

I used some libraries for this Rest API such **Spring Web, Spring Data JPA, Validation, and H2 Database**.

## Requirements

Below are some business rules that were important for the functioning:

•	For both types of users, This API allows to store the Full Name, Document (Or CPF), Email, and Password. Document and Emails are unique in the system. Therefore, only one registration with the same Document or Email is allowed.

•	Users can send money (make transfers) to merchants and between users.

•	Merchants can only receive transfers; they do not send money to anyone.

•	This API validates that the user has sufficient balance before making a transfer.

•	Before completing a transfer, an external authorization service is consulted. It is required this mock URL https://util.devi.tools/api/v2/authorize to simulate the service using the GET method.

The service may return any of the following responses:
```
{"status" : "success", "data" : { "authorization" : true }}
OR
{"status" : "fail", "data" : { "authorization" : false }}
```

•	The transfer operation is a transaction (i.e., it will be rolled back in any case of inconsistency), and the money will be returned to the payer's wallet.

•	This service is a RESTful.

## Transfer Operation Endpoint 

```
POST /transactions
Content-Type: application/json
{
  "value": 100.0,
  "payer": 4,
  "payee": 15
}
```

This project was inspired by the assessment test available at the following link:
https://github.com/PicPay/picpay-desafio-backend

## Database Config
For test this API, an external Database is not necessary because an embedded Database (H2 Database) was used with the following configuration properties:

- Name: payment_transaction_system_db
- Username: sa
- Password:

## Development Tools
This Rest API was built with:

- Spring Boot version: 3.3.3
- Java version: 17

## System Class Diagram

![PaymentTransactionSystemClassDiagram](https://github.com/user-attachments/assets/a83c4021-965c-4d64-ab53-a1b8c2ea0136)



