#language: en

Feature: buy products on the website

  Scenario Outline: check product purchase
    Given the user access to the web site
    And user login
      | user   | password   |
      | <user> | <password> |
    When user adds the products to the cart
      | amount   |
      | <amount> |
    Then user can view the products added to the shopping cart
      | amount   |
      | <amount> |
    And user fills out the purchase form
      | firstName   | lastName   | postalCode   |
      | <firstName> | <lastName> | <postalCode> |
    And user can Checkout until confirmation
      | confirmationMessage   |
      | <confirmationMessage> |

    Examples:
      | user          | password     | amount | firstName | lastName | postalCode | confirmationMessage       |
      #@externaldata@./src/test/resources/datadriven/Data.csv@false
      | standard_user | secret_sauce | 2      | Josue     | Presley  | 5105       | Thank you for your order! |
      | standard_user | secret_sauce | 3      | Steven    | Marley   | 5106       | Thank you for your order! |
      | standard_user | secret_sauce | 4      | Chad      | Smith    | 5107       | Thank you for your order! |














































