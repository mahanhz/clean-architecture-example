# 6. Avoid API versioning

Date: 01-August-2017

## Status

Supersedes [5. API Versioning](0005-api-versioning.md)

## Context

Code base is becomes too complicated.

Maintaining and supporting multiple API versions is painful and expensive.

## Decision

We will avoid API versioning.

We will not introduce breaking changes.

## Consequences

Developers are happier, and the codebase is once again easier to reason about.

It's difficult to keep all the changes in the API forever, so we need metrics and to measure usage to identify when a feature can be removed.

## Made by

Mahan Hashemizadeh