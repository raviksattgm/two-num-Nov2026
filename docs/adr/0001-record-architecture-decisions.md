# ADR-0001: Record architecture decisions

- **Status:** Accepted
- **Date:** 2026-11-22

## Context
We need a lightweight, versioned record of significant design decisions so that people and agents can see why the system is the way it is.

## Decision
We will keep Architecture Decision Records in `docs/adr/`, numbered sequentially (`NNNN-short-title.md`), one decision per record, using this template:

- Title, Status (Proposed / Accepted / Superseded by ADR-NNNN), Date
- Context: the forces at play
- Decision: what we decided
- Consequences: what becomes easier or harder
- Alternatives considered

An ADR is never edited after acceptance except to change its status; a new decision supersedes it with a new ADR.

## Consequences
- Decisions are reviewable in pull requests like code.
- The architect agent drafts ADRs as Proposed; the user accepts them.
