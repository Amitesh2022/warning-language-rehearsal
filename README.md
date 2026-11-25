# Warning Language Rehearsal

Rehearse accessible multilingual municipal messages before an exercise begins.

## Live demo

[Watch the recorded walkthrough](docs/demo.webm). Run both services below to try it yourself.

## Screenshots

![Work queue](docs/screenshot.png)
![Record editor](docs/editor.png)

## Main features

- Find records by title, owner, context or notes. Filter by language and sort by title or due date.
- Create and edit records with a named owner, context, deadline and notes.
- See stage counts and read the workflow playbook.

## Technology used

Vue, TypeScript, Spring Boot, JDBC and H2.

## Installation instructions

Use Node.js 24, Java 21 and Maven. From `backend/` run `mvn spring-boot:run`. From the project root run `npm ci` and `npm run dev`. Open the address printed by the frontend.

## Commercial licensing/contact

Contact [Amitesh2022](https://github.com/Amitesh2022) for commercial licensing.

## Business problem and users

Rehearse accessible multilingual municipal messages before an exercise begins. This workspace gives the team maintaining the queue and its reviewers the same view. [Official background](https://interoperable-europe.ec.europa.eu/collection/rolling-plan-ict-standardisation/emergency-communications-and-public-warning-systems-rp-2026).

## Key workflows

Search or filter the queue. Open a record, change its stage or handoff notes and save. Use the playbook to check what the receiving team needs.

## Framework highlights

Vue uses a single-file component, Composition API, scoped state subscription and computed lists.

## Java backend highlights

Spring Boot separates API, service and JDBC repository code. It validates input, exposes a health endpoint and stores records in a local H2 file database. HTTP integration tests exercise search, create, edit and invalid requests.

## Architecture and state flow

The frontend requests `/api/records` through its development proxy. The Java service validates writes and returns the stored record. The frontend updates the queue from that response. The playbook and editor have separate hash URLs.

## Accessibility and responsive behaviour

The app uses labelled fields, visible keyboard focus, text stages and live save and error messages. Records and filters stack on smaller screens.

## Verification commands

- `npm test`, and `npm run build`.
- `cd backend && mvn verify`.

## Structure

- `src/`: frontend, view state and tests.
- `backend/`: Spring Boot service, database schema and tests.
- `docs/`: screenshots and walkthrough.

## Tradeoffs

This is a single-team sample workspace, not an official service. Records are fictional. H2 keeps local setup simple; the service is local-only and does not include sign-in, external delivery or production-level audit controls.

## Roadmap

Add team sign-in, change history, and import and export for handoff records.
