@feature-tag @crud
Feature: Instance of CRUD cucumber flow

  Scenario Outline: Test for e2e CRUD operation
    Given we have a booking request
      | firstname   | lastname   | additionalneeds   | depositpaid   | checkin   | checkout   | totalprice   |
      | <firstname> | <lastname> | <additionalneeds> | <depositpaid> | <checkin> | <checkout> | <totalprice> |

    When  we send the request to create booking api
    Then  HTTP response status code should be 200
    And validate that response has bookingid

    When we store the booking from create response
    When we "retrieve" previously created booking
    Then  HTTP response status code should be 200

    When we "update" previously created booking
    Then  HTTP response status code should be 200

    When we "delete" previously created booking
    Then  HTTP response status code should be 201

    Examples:
      | firstname | lastname | additionalneeds | depositpaid | checkin    | checkout   | totalprice |
      | Penny     | Timothy  | mineral water   | true        | 2024-01-01 | 2024-02-01 | 555        |
      | Tommy     | Penny    | Breakfast       | false       | 2024-01-01 | 2024-02-01 | 600        |