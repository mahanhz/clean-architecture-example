# 5. API versioning

Date: 28-July-2017

## Status

Superseded by [6. Avoid API Versioning](0006-avoid-api-versioning.md)

## Context

Different consumers are making request for information in different formats.

## Decision

Rather than duplicate the data in the response, we will version our api and allow consumers to choose which version best suits their needs.

We will use a custom Media Type (application/vnd.example.clean.v1+json) to version at the resource level.

## Consequences

Consumers get the exact format they want.

Consumers now need to know when accept header to pass so that they get the expected response.

Our codebase is a lot more complicated.

## Made by

Mahan Hashemizadeh
