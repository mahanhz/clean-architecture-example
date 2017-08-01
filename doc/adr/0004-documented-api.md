# 4. Documented API

Date: 25-July-2017

## Status

Accepted

## Context

We need to build an API that other systems can use.

The API needs to be documented so other developers are not spending time asking how to use it.

## Decision

We will use Spring HATEOAS to produce a navigable REST service.

Along with this we will use Swagger to generate documentation about our REST services.

## Consequences

Consumers use the navigable links to acquaint themselves with what calls our services provide.

They then use the Swagger documentation to make sample calls.

Any questions about our services are no longer basic "how do I do this?" questions.

## Made by

Mahan Hashemizadeh