const API_URL = 'http://localhost:8080/api';

const currentButton = document.getElementById('current_btn');
const forecastButton = document.getElementById('forecast_btn');
const historyButton = document.getElementById('history_btn');
const resultsDiv = document.getElementById("results");

const sections = {
	'current': document.querySelector('#current'),
	'forecast': document.querySelector('#forecast'),
	'history': document.querySelector('#history'),
	'cache': document.querySelector('#cache')
};

function clearresults() {
	document.getElementById("results").innerHTML = "";
	document.getElementById("results").style.display = "none";
}

function showResults() {
	document.getElementById("results").style.display = "block";
}

currentButton.addEventListener('click', () => {
	clearresults();
	const city = document.getElementById('city_current').value;
	let source = document.getElementById('source_current').value;
	let url = API_URL + '/airquality/current/' + city
	if (source === 'openweather') {
		url += '?source=' + source;
		source = "OpenWeather";
	}
	else {
		source = "WeatherBit";
	}
	const locationInfo = document.createElement("p");
	const coords = document.createElement("p");
	const sourceP = document.createElement("p");
	locationInfo.style.fontWeight = "bold";
	coords.style.fontWeight = "bold";
	sourceP.style.fontWeight = "bold";
	const table = document.createElement("table");
	table.style.textAlign = "center";
	const tableHeader = document.createElement("tr");
	tableHeader.innerHTML = "<th>AQI</th><th>O3</th><th>SO2</th><th>NO2</th><th>CO</th><th>PM10</th><th>PM25</th>";
	const tableRow = document.createElement("tr");
	const otherInfoTable = document.createElement("table");

	fetch(url)
		.then(response => response.json())
		.then(jsonData => {
			if (source === "WeatherBit") {
				locationInfo.textContent = `${jsonData.city_name}, ${jsonData.country_code}`;
				coords.textContent = `lat: ${jsonData.lat}, long: ${jsonData.lon}`;
				sourceP.textContent = `Source: ${source}`;
				const data = jsonData.data[0];
				tableRow.innerHTML = `<td>${data.aqi}</td><td>${data.o3}</td><td>${data.so2}</td><td>${data.no2}</td><td>${data.co}</td><td>${data.pm10}</td><td>${data.pm25}</td>`;
				otherInfoTable.innerHTML = `<tr><th>Mold Level</th><td style="text-align: right">${data.mold_level}</td></tr><tr><th>Pollen Level Grass</th><td style="text-align: right">${data.pollen_level_grass}</td></tr><tr><th>Pollen Level Weed</th><td style="text-align: right">${data.pollen_level_weed}</td></tr><tr><th>Pollen Level Tree</th><td style="text-align: right">${data.pollen_level_tree}</td></tr><tr><th>Predominant Pollen Type</th><td style="text-align: right">${data.predominant_pollen_type}</td></tr>`;
			}
			showResults();
		})
		.catch(() => {
			locationInfo.textContent = `${city}`;
			coords.textContent = `lat: N/A, long: N/A`;
			sourceP.textContent = `Source: ${source}`;
			tableRow.innerHTML = '<td>N/A</td><td>N/A</td><td>N/A</td><td>N/A</td><td>N/A</td><td>N/A</td><td>N/A</td>';
			showResults();
		});
	
	table.appendChild(tableHeader);
	table.appendChild(tableRow);
	resultsDiv.appendChild(locationInfo);
	resultsDiv.appendChild(coords);
	resultsDiv.appendChild(sourceP);
	resultsDiv.appendChild(table);
	resultsDiv.appendChild(otherInfoTable);
});

forecastButton.addEventListener('click', () => {
	clearresults();
	const city = document.getElementById('city_forecast').value;
	let source = document.getElementById('source_forecast').value;
	let url = API_URL + '/airquality/forecast/' + city
	if (source === 'openweather') {
		url += '?source=' + source;
		source = "OpenWeather";
	}
	else {
		source = "WeatherBit";
	}
	const locationInfo = document.createElement("p");
	const coords = document.createElement("p");
	const sourceP = document.createElement("p");
	locationInfo.style.fontWeight = "bold";
	coords.style.fontWeight = "bold";
	sourceP.style.fontWeight = "bold";
	const table = document.createElement("table");
	table.style.textAlign = "center";
	const tableHeader = document.createElement("tr");
	tableHeader.innerHTML = "<th>AQI</th><th>O3</th><th>SO2</th><th>NO2</th><th>CO</th><th>PM10</th><th>PM25</th><th>Date</th>";
	const otherInfoTable = document.createElement("table");

	fetch(url)
		.then(response => response.json())
		.then(jsonData => {
			if (source === "WeatherBit") {
				locationInfo.textContent = `${jsonData.city_name}, ${jsonData.country_code}`;
				coords.textContent = `lat: ${jsonData.lat}, long: ${jsonData.lon}`;
				sourceP.textContent = `Source: ${source}`;
				for (let i = 0; i < jsonData.data.length; i++) {
					const data = jsonData.data[i];
					const tableRow = document.createElement("tr");
					const momentDate = moment(data.timestamp_local);
					tableRow.innerHTML = `<td>${data.aqi}</td><td>${data.o3}</td><td>${data.so2}</td><td>${data.no2}</td><td>${data.co}</td><td>${data.pm10}</td><td>${data.pm25}</td><td>${momentDate.format("HH[h]mm, DD/MM/YYYY")}</td>`;
					table.appendChild(tableRow);
				}
			}
			showResults();
		})
		.catch(() => {
			locationInfo.textContent = `${city}`;
			coords.textContent = `lat: N/A, long: N/A`;
			sourceP.textContent = `Source: ${source}`;
			const tableRow = document.createElement("tr");
			tableRow.innerHTML = '<td>N/A</td><td>N/A</td><td>N/A</td><td>N/A</td><td>N/A</td><td>N/A</td><td>N/A</td><td>N/A</td>';
			table.appendChild(tableRow);
			showResults();
		});
	
	table.appendChild(tableHeader);
	resultsDiv.appendChild(locationInfo);
	resultsDiv.appendChild(coords);
	resultsDiv.appendChild(sourceP);
	resultsDiv.appendChild(table);
	resultsDiv.appendChild(otherInfoTable);
});

historyButton.addEventListener('click', () => {
	clearresults();
	const city = document.getElementById('city_history').value;
	let source = document.getElementById('source_history').value;
	const start_date = document.getElementById('start_date').value;
	const end_date = document.getElementById('end_date').value;
	let url = API_URL + '/airquality/history/' + city;
	if (start_date !== "" && end_date !== "") {
		url += '/' + start_date + '/' + end_date;
	}
	if (source === 'openweather') {
		url += '?source=' + source;
		source = "OpenWeather";
	}
	else {
		source = "WeatherBit";
	}
	const locationInfo = document.createElement("p");
	const coords = document.createElement("p");
	const sourceP = document.createElement("p");
	locationInfo.style.fontWeight = "bold";
	coords.style.fontWeight = "bold";
	sourceP.style.fontWeight = "bold";
	const table = document.createElement("table");
	table.style.textAlign = "center";
	const tableHeader = document.createElement("tr");
	tableHeader.innerHTML = "<th>AQI</th><th>O3</th><th>SO2</th><th>NO2</th><th>CO</th><th>PM10</th><th>PM25</th><th>Date</th>";
	const otherInfoTable = document.createElement("table");

	fetch(url)
		.then(response => response.json())
		.then(jsonData => {
			if (source === "WeatherBit") {
				locationInfo.textContent = `${jsonData.city_name}, ${jsonData.country_code}`;
				coords.textContent = `lat: ${jsonData.lat}, long: ${jsonData.lon}`;
				sourceP.textContent = `Source: ${source}`;
				for (let i = jsonData.data.length-1; i > -1; i--) {
					const data = jsonData.data[i];
					const tableRow = document.createElement("tr");
					const momentDate = moment(data.timestamp_local);
					tableRow.innerHTML = `<td>${data.aqi}</td><td>${data.o3}</td><td>${data.so2}</td><td>${data.no2}</td><td>${data.co}</td><td>${data.pm10}</td><td>${data.pm25}</td><td>${momentDate.format("HH[h]mm, DD/MM/YYYY")}</td>`;
					table.appendChild(tableRow);
				}
			}
			showResults();
		})
		.catch(() => {
			locationInfo.textContent = `${city}`;
			coords.textContent = `lat: N/A, long: N/A`;
			sourceP.textContent = `Source: ${source}`;
			const tableRow = document.createElement("tr");
			tableRow.innerHTML = '<td>N/A</td><td>N/A</td><td>N/A</td><td>N/A</td><td>N/A</td><td>N/A</td><td>N/A</td><td>N/A</td>';
			table.appendChild(tableRow);
			showResults();
		});
	
	table.appendChild(tableHeader);
	resultsDiv.appendChild(locationInfo);
	resultsDiv.appendChild(coords);
	resultsDiv.appendChild(sourceP);
	resultsDiv.appendChild(table);
	resultsDiv.appendChild(otherInfoTable);
});

function updateCache() {
	fetch(API_URL + '/cache/stats')
		.then(response => response.json())
		.then(data => {
			document.getElementById('requests').textContent = data.requests;
			document.getElementById('hits').textContent = data.hits;
			document.getElementById('misses').textContent = data.misses;
		})
		.catch(() => {});
}

function onHashChange() {
	const hash = window.location.hash.substring(1);
	const section = sections[hash];
	if (section) {
		for (const s in sections) {
			sections[s].style.display = 'none';
		}
		section.style.display = 'block';
	}
	if (hash === 'cache') {
		updateCache();
	}
	clearresults();
}

window.addEventListener('hashchange', onHashChange);

const links = document.querySelectorAll('.sidebar nav ul li a');
for (const link of links) {
	link.addEventListener('click', (event) => {
		event.preventDefault();
		const hash = link.getAttribute('href');
		window.location.hash = hash;
	});
}

onHashChange();
