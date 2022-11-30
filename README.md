
# When and Where? Time-spatial analytical visualization of events

## Description
### Motivation

GEO map, time series and GEO-time visualizations can provide intuitive insights regarding data like travel logs and chronicles. However, such data sources often have 2 problems. 

1. The date format is messy.

2. Time and GEO information are buried in text.

Our framework and plugins combined can solve this problem. The data plugins can ensure a clean date format. Our framework can extract time and spatial information from selected texts with an NLP technique called Named Entity Recognition (NER). After the time and location data are processed, the framework will pass them to the frontend and perform data visualization in an interactive way. 


## Installation

To install the necessary packages, in the backend folder, run
```
mvn clean install
```
In the frontend folder, run
```
npm install
```

## Usage

### Set Up Frontend Server
In the front-end folder, run

```
npm install
```
Then rename the file:
```
./node_modules/@types/plotly.js
```

to
```
./node_modules/@types/plotly.js-dist
```

then, run

```
npm start
```

This will start the front-end server at http://localhost:3000. You can update the front-end code as the server is running in the development mode (i.e., npm start). It will automatically recompile and reload.

Provide instructions and examples for use. Include screenshots as needed.

To add a screenshot, create an `assets/images` folder in your repository and upload your screenshot to it. Then, using the relative filepath, add it to your README using the following syntax:

    ```md
    ![alt text](assets/images/screenshot.png)
    ```
### Set Up Backend Server 
Either run the Java backend by using your IDE or by typing 

```
mvn exec:exec
```
in the back-end folder. This will start the Java server at http://localhost:8080.



### GUI usage
1. Select a Data Plugin
2. Search keyword and submits
3. Select Visualization and gets the plot


	
## Plugin Interfaces
### Data Plugin Interface
```
public interface DataPlugin {

    /**
     * Called (only once) when the plug-in is first registered with the
     * framework, giving the plug-in a chance to perform any initial set-up
     * before the game has begun (if necessary).
     */
    void onRegister(WorkFlowFramework framework);
    
    /**
     * Every class that implements this interface should have a field of 
     * JSONObject to store the data to be processed by framework
     * This method will return that field of JSONObject to the framework
     */
    UnProcessedData getData();

    /**
     * Perform search based on searchTerm
     * @param searchTerm Words used for search
     */
    void search(SearchTerm searchTerm);

    /**
     * Get the instruction for this plugin
     * @return Instruction for specific plugins
     */
    String getPluginInstructions();

    /**
     * Gets the name of the plug-in game.
     * @return Name for specific plugins
     */
    String getPluginName();
 
}
```
### Data Visualization Interface	

	
## Data Exchange
**Data Plugin sends data to Backend Framework**
```
{
	record UnProcessedData(boolean isTabular, boolean hasTime, boolean hasLocation, String textData, JSONArray tabularData)
“isTabular”: boolean (whether the data is recorded in text or tabular format),
“hasTime”: boolean (whether the tabular data has a time column), 
“hasLocation”: boolean (whether the tabular data has a time column),
“textData”: String (data in text format),
“tabularData”: [
				{“time”: String, 
				"location": String, 
				"text": String},
				{“time”: String, 
				"location": String, 
				"text": String},
				...
				]
}
```

**Backend Framework sends data to DataVis Plugin**
```
{
“coreData”: {[
  “time”: String,
  “location”: String, 
  “lat”: double,
  “lng: double,
  “text”: String],
  ...
              }
“locationFreq”: {
  “location”: String,
  “freq”: int}
              }
}
```

**DataVis Plugin sends data to Frontend Framework**
```
{
	data: {},
	layout: {}
}
```

## App API

### Register All Plugins for Initialization

**Request**

```
GET /register
```

**Response**
```
{
"dataplugins": String[],
"visplugins": String[]
}
```

### Select Data Plugin

**Request**

```
GET /dataplugin?i=0
```

**Response**
```
{
	"datapluginname": String,
	"instruction": String
}
```


### Submit Data (Data Plugin sends data to the framework)
**Request**
```
GET /submitdata/?keyword=XX
```
**Response**
```
"datasubmitsuccess": boolean
```

### Select Visualization Plugin, framework sends data and layout to the frontend, and frontend plots
**Request**
```
GET /visplugin?i=0
```
**Response**
```
{
"data": {},
"layout": {}
}
```





## Credits

This project is the group work of Yifan Zhou, Yanyu Chen and Ruoyu Zhang. 






