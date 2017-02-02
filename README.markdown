## Overview
For this course we will read the part of the [online training at android.com](https://developer.android.com/training/index.html) and most of the book "Kotlin for Android developers" and learn Kotlin while developing the Android app from the book. When done reading the book, we will do a project creating another Android app using e-conomic data. All code should be saved in this repository through pull request, that are reviewed by developers in the mobile team.

### Plan of attack
- Read and write all code at https://developer.android.com/training/index.html under 'Getting started'
- Read "Kotlin for Android developers" and implement the app they walk you through
- Do the project described below.

## Curriculum

- Kotlin
    - be able to explain 
        - classes
        - flow controls
        - structs
        - inheritance
        - scope
        - lambdas
        - extensions
        - function extensions
        - companion objects
        - optionals
        - constants
        - variables
        - generics
        - enum
        - exceptions
        - unit tests
        - delegate properties
        - lateinit
        - converting Java to Kotlin
        - functional programming in Kotlin
        - using the REPL 
        - etc       
- Android Studio and project setup
    - be able to give an overview of the IDE and Android project setup. Structure, navigating the IDE, build and run an app
- Gradle
    - be able to explain the build process and interplay of the different .gradle files
    - be able to explain how external libraries are included with Gradle
- Git and GitHub
    - be able to use staging, committing, branching, merging, pull requests and reviews.
- Intents and Activities
    - be able to use using intent between own and other activities
    - be able to use activity life cycles
    - be able to use permission model
    - be able to use list view -> detail view and basic layouts
- Adapters
- Fragments
- BroadcastReceivers
- SharedPreferences
- Unit testing
- RecyclerView
- Networking
    - be able to perform HTTP requests using OkHTTP
- 3rd party
    - be able to use anko
    - be able to "insert a few other cool libs here"

## Project
We build an Andoird app using the [e-conomic REST API](https://restapi.e-conomic.com/). The API documentation can be found [here](http://restdocs.e-conomic.com/). The app will be a light version of our [existing invoicing app](https://github.com/e-conomic/econ-android-sales/) for Android, that can edit a subset of the data on draft invoices.

#### Requirements
- hard-coded to use a e-conomic test agreement - i.e. no login or authentication
- CRUD on /invoices/drafts
- Read on /customers /products plus some more
- Unit tests
- The app is responsive and uses threading where needed
- UI adapts to small, mid, and large screen. (tablet not a requirement)
- Incorporates ~95% of the things in the curriculum

## Resources
http://www.vogella.com/tutorials/android.html   
https://kotlinlang.org/

    
