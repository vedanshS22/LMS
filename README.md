# Learner Management System

A Spring Boot based backend application to manage learners, cohorts, and courses using DTO and Mapper pattern with proper entity relationships.

---

## Overview

This project simulates a backend for a Learning Management System (LMS). It demonstrates clean architecture, REST APIs, entity relationships, DTO usage, and mapping logic.

---

## Tech Stack

Java 17  
Spring Boot  
Spring Data JPA  
Hibernate  
Gradle  
MySQL / H2  

---

## Architecture

Controller → Service → Repository → Database  
DTO → Mapper → Entity  

---

## Entity Relationship Diagram (ER)

```mermaid
erDiagram

    LEARNER {
        Long learnerId
        String learnerName
        String learnerEmail
        String learnerPhone
    }

    COHORT {
        Long cohortId
        String cohortName
        String cohortDescription
    }

    COURSE {
        Long courseId
        String courseName
        String courseDescription
    }

    LEARNER }o--o{ COHORT : enrolled_in
    COURSE ||--o{ COHORT : contains

Features
Create and manage learners
Assign learners to cohorts
Create and manage courses
Maintain relationships between entities
DTO-based API responses
Mapper layer for conversion
Exception handling
DTO Layer

LearnerDTO

learnerId
learnerName
learnerEmail
learnerPhone
cohorts

CohortDTO

cohortId
cohortName
cohortDescription
Mapper Layer

Used to convert:

Learner → LearnerDTO
Cohort → CohortDTO
Core Logic
Checks if learner exists using email
Avoids duplicate entries
Assigns learners to cohort
Maintains relationships
API Endpoints

POST /cohort/{cohortId}/learner
POST /learner
GET /learners
GET /learner/{id}
POST /course

Setup Instructions

Clone the repository

git clone https://github.com/vedanshS22/LMS

cd LMS

Run project

Windows:
gradlew.bat bootRun

Mac/Linux:
./gradlew bootRun

Build

gradlew build

Run jar

java -jar build/libs/*.jar

Future Improvements
Add JWT Authentication
Add pagination
Add Swagger docs
Add frontend

## Entity Relationship Diagram (ER)

        +------------------+           +------------------+
        |     LEARNER      |           |      COHORT      |
        +------------------+           +------------------+
        | learnerId (PK)   |           | cohortId (PK)    |
        | learnerName      |           | cohortName       |
        | learnerEmail     |           | description      |
        | learnerPhone     |           +------------------+
        +------------------+                    ^
                 |                               |
                 |                               |
                 +-----------<------------------>+
                        MANY TO MANY

        +------------------+
        |      COURSE      |
        +------------------+
        | courseId (PK)    |
        | courseName       |
        | description      |
        +------------------+
                 |
                 |
                 v
        +------------------+
        |      COHORT      |
        +------------------+

        ONE COURSE → MANY COHORTS

## UML Class Diagram

+------------------------+
|        Learner         |
+------------------------+
| - learnerId : Long     |
| - learnerName : String |
| - learnerEmail:String  |
| - learnerPhone:String  |
+------------------------+
| + getters/setters      |
+------------------------+
          ^
          |
          |  many-to-many
          |
+------------------------+
|        Cohort          |
+------------------------+
| - cohortId : Long      |
| - cohortName : String  |
| - description : String |
+------------------------+
| + getters/setters      |
+------------------------+
          ^
          |
          | many-to-one
          |
+------------------------+
|        Course          |
+------------------------+
| - courseId : Long      |
| - courseName : String  |
| - description : String |
+------------------------+

## API Flow Diagram

Client
  |
  v
+----------------------+
|      Controller      |
+----------------------+
          |
          v
+----------------------+
|       Service        |
+----------------------+
          |
          v
+-----------------------------+
| Check learner by email      |
+-----------------------------+
      |                |
     Yes              No
      |                |
      v                v
+-----------+    +-------------+
| Fetch DB  |    | Create New  |
| Learner   |    | Learner     |
+-----------+    +-------------+
      \              /
       \            /
        v          v
     +----------------------+
     | Assign to Cohort     |
     +----------------------+
               |
               v
     +----------------------+
     | Save to Database     |
     +----------------------+
               |
               v
     +----------------------+
     | Convert to DTO       |
     +----------------------+
               |
               v
           Response
