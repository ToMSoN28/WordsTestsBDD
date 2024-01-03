Feature: Tests of rest api

  Scenario: Get list of all words
    When Request GET on endpoint /words/
    Then Code response 200 and json with "not" empty list


  Scenario: Get empty list of words
    When Request GET on endpoint /words/
    Then Code response 200 and json with "" empty list


  Scenario: Get exist word with ID=1234
    Given wordID = "202"
    When Request GET on endpoint /words/id
    Then Code response 200 and json with "word, definition, example"

  Scenario: Get word with ID=4321, word not exist
    Given wordID = "4321"
    When Request GET on endpoint /words/id
    Then Code response 404

  Scenario: Get word with ID=a234, id is incorrect
    Given wordID = "a234"
    When Request GET on endpoint /words/id
    Then Code response 400

  Scenario: Get word with ID=-1234, id is incorrect
    Given wordID = "-1234"
    When Request GET on endpoint /words/id
    Then Code response 400

  Scenario: Search part of words, correct part
    Given Correct part "test"
    When Request GET on endpoint /words/search/fragment
    Then Code response 200 and json with "not" empty list

  Scenario:  Search part of word, not match
    Given Correct part "xy1"
    When Request GET on endpoint /words/search/fragment
    Then Code response 404

  Scenario: Correct adding word
    Given Word "Test1"
    Given Definition "Test1 test1"
    Given Example "testy1"
    Given Password "password"
    When Request POST on endpoint /words/ with json
    Then Code response 201 and json with new id
    Then Get word with "new_id" and compare with given inputs

  Scenario: Adding word, incorrect json format
    Given Word "Test1"
    Given Definition "Test1 test1"
    Given Example "testy1"
    Given Password "password"
    When Request POST on endpoint /words/ with incorrect json formant
    Then Code response 404

  Scenario: Adding word, incorrect password
    Given Word "Test1"
    Given Definition "Test1 test1"
    Given Example "testy1"
    Given Password "password1"
    When Request POST on endpoint /words/ with json
    Then Code response 403

  Scenario: Correct update word
    Given wordID = "202"
    Given Word "Test1"
    Given Definition "Test2 test2"
    Given Example "testy1"
    Given Password "password"
    When Request PUT on endpoint /words/id with json
    Then Code response 200
    Then Get word with "1234" and compare with given inputs


  Scenario: Update existing word with incorrect json
    Given wordID = "202"
    Given Word "Test1"
    Given Definition "lolol"
    Given Example "testy1"
    Given Password "password"
    When Request PUT on endpoint /words/id with incorrect json
    Then Code response 400

  Scenario: Update with empty word
    Given wordID = "202"
    Given Word ""
    Given Definition ""
    Given Example ""
    Given Password "password"
    Given Get actual word
    When Request PUT on endpoint /words/id with json
    Then Code response 200
    Then Compare with latest version

  Scenario: Update not existing word with id = 4321
    Given wordID = "4321"
    Given Word "Test1"
    Given Definition "Test1 test1"
    Given Example "testy1"
    Given Password "password"
    When Request PUT on endpoint /words/id with json
    Then Code response 404

  Scenario: Update with incorrect id format
    Given wordID = "a234"
    Given Word "Test1"
    Given Definition "Test1 test1"
    Given Example "testy1"
    Given Password "password"
    When Request PUT on endpoint /words/id with json
    Then Code response 400

  Scenario: Update with not existing id
    Given wordID = "-1234"
    Given Word "Test1"
    Given Definition "Test1 test1"
    Given Example "testy1"
    Given Password "password"
    When Request PUT on endpoint /words/id with json
    Then Code response 400

  Scenario: Update with incorrect password
    Given wordID = "1234"
    Given Word "Test1"
    Given Definition "Test1 test1"
    Given Example "testy1"
    Given Password "password1"
    When Request PUT on endpoint /words/id with json
    Then Code response 403

  Scenario: Correct delete word
    Given wordID = "702"
    Given Password "password"
    When Request DELETE on endpoint /words/id
    Then Code response 200

  Scenario: Delete not exist word id
    Given wordID = "4321"
    Given Password "password"
    When Request DELETE on endpoint /words/id
    Then Code response 404

  Scenario: Delete incorrect id format
    Given wordID = "a234"
    Given Password "password"
    When Request DELETE on endpoint /words/id
    Then Code response 400

  Scenario: Delete not existing id
    Given wordID = "-1234"
    Given Password "password"
    When Request DELETE on endpoint /words/id
    Then Code response 400

  Scenario: Delete incorrect password
    Given wordID = "1234"
    Given Password "password1"
    When Request DELETE on endpoint /words/id
    Then Code response 403