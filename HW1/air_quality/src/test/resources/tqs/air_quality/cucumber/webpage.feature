Feature: Webpage
	Scenario: Navigate through the Air Quality page
		Given I access the "localhost:8080" url
		When I click on the "Current" link
		And I search for the "Current" data for the city "Castelo Branco"
		Then I should see the "Current" data for the city "Castelo Branco"
		When I click on the "Forecast" link
		And I search for the "Forecast" data for the city "Castelo Branco"
		Then I should see the "Forecast" data for the city "Castelo Branco"
		When I click on the "History" link
		And I search for the "History" data for the city "Castelo Branco"
		Then I should see the "History" data for the city "Castelo Branco"
		When I click on the "Cache Statistics" link
		Then I should see the Cache Statistics data
		Then I quit the browser
