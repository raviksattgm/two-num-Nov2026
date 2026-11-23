# Spec: Add two numbers

Status: Draft (v0.2)
Owner: Business Analyst

## Summary
A web service that returns the sum of two numbers supplied by the caller.

## User stories
- **US-1:** As an API client, I want to send two whole numbers and receive their sum, so that I can use the result in my application.
- **US-2:** As an API client, I want a clear error when my input is invalid, so that I can correct the request.

## Acceptance criteria

```gherkin
Feature: Add two numbers

  @AC-1
  Scenario: Add two positive integers
    When the client requests the sum of 2 and 3
    Then the response status is 200
    And the sum is 5

  @AC-2
  Scenario: Add negative integers
    When the client requests the sum of -7 and 4
    Then the response status is 200
    And the sum is -3

  @AC-3
  Scenario: Add zero
    When the client requests the sum of 0 and 0
    Then the response status is 200
    And the sum is 0

  @AC-4
  Scenario: Reject non-numeric input
    When the client requests the sum of "abc" and 3
    Then the response status is 400
    And the error message says the value "abc" is not a valid whole number

  @AC-5
  Scenario: Reject a missing number
    When the client requests a sum with only one number, 3
    Then the response status is 400
    And the error message says both numbers are required

  @AC-6
  Scenario: Reject a result that is too large
    When the client requests the sum of 9223372036854775807 and 1
    Then the response status is 400
    And the error message says the result is out of range

  @AC-7
  Scenario: Reject decimal input
    When the client requests the sum of 1.5 and 2
    Then the response status is 400
    And the error message says the value "1.5" is not a valid whole number
```

## Non-functional requirements
- **NFR-1:** Responses within 200 ms at the 95th percentile under light load.
- **NFR-2:** A health endpoint so the cloud platform can check the service is up.
- **NFR-3:** Runs within the cloud provider's free tier at expected demo traffic.

## Decisions (resolved open questions)
1. Whole numbers only; decimals are rejected (AC-7).
2. 64-bit signed integer range is sufficient; no arbitrary size.
3. Clients are the public internet; no authentication.

## Change log
- v0.1 (2026-11-22): initial draft.
- v0.2 (2026-11-22): resolved open questions (whole numbers, 64-bit range, public, no auth).
