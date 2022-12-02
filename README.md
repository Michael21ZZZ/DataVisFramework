
# When and Where? Time-spatial analytical visualization of events

## Description
### Motivation


When describing an event, time and space information are always deeply coupled. However, this information is always buried in texts and there is little tool to help visualize it. Besides, time and location from different sources can be messy to handle. 

Our framework can extract time, location and event information from selected texts and perform data visualization in an interactive way. It can extract data from different sources with Named Entity Recognition (NER). Visualization plugins like location-event, time series, and location-time can provide intuitive insights regarding data like travel logs and chronicles. 

For data plugins, we implemented Wikipedia plugin, Manual Input plugin and Twitter plugin for illustration. You can find instructions when you select each plugin. For visualization plugins, there are Geolocation plugin, Timeline plugin and Event frequency plugin. 


## Installation
### Replace the Geocoding API key
Before set up the server, please set the following field with your own Google Geocoding API key:
```
./backend/src/main/java/edu/cmu/cs214/hw6/NLP/GoogleGeoCoding.apiKey = "YourAPIKey"
```
The instructions for getting the Google Geocoding API key can be found [here](https://developers.google.com/maps/documentation/geocoding/get-api-key).



### Set Up Backend Server
To install the necessary packages, in the backend folder, run
```
mvn clean install
```

Then, in the backend folder, run

```
mvn exec:exec
```
This will start the Java server at http://localhost:8080.


### Set Up Frontend Server
In the front-end folder, run

```
npm install
```
then, run
```
npm start
```

This will start the front-end server at http://localhost:3000. You can update the front-end code as the server is running in the development mode (i.e., npm start). It will automatically recompile and reload.

## Usage
1. Select a Data Plugin from the list. 
![1](https://user-images.githubusercontent.com/91205016/205211570-dfe99448-936c-4b14-951b-17bc9dbe9d05.png)

2. Type keyword in the search box and press 'search'.
![image](https://user-images.githubusercontent.com/91205016/205212208-491c2d54-89bc-4f72-999e-d2521f8251f5.png)


3. Select visualization plugins from the list. 
![3](https://user-images.githubusercontent.com/91205016/205211639-05943aa9-2c29-4977-95f8-4f14d300e9e8.png)

4. You can get the visualization result now! You can return to the front page by clicking 'return'. 
![image](https://user-images.githubusercontent.com/91205016/205211981-fdd66d86-eed0-4d8d-8592-487b669ff7db.png)

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
```
public interface VisPlugin {
    /**
     * Further process the data. The output should align with the plotly documentation 
     * https://plotly.com/javascript/reference/index/
     * 
     * @param processedData
     * @return the result should include data and layout {"data": JSONArray[], "layout":JSONObject}
     */
    public JSONObject prepVis(ProcessedData processedData);
    
    /**
     * Set the layout of this vis, make change to the local variable layout
     */
    public void setLayout();

    /**
     * A helper function that format the processed data into a JSONArray required by plotly
     */
    public void formatData();

    /**
     * Register the framework to the plugin
     * @param framework
     */
    public void onRegister(WorkFlowFramework framework);

    /**
     * Gets the name of the plug-in game.
     * @return Name for specific plugins
     */
    String getPluginName();
}
```


## Data Exchange
**Data Plugin sends data to Backend Framework**
```
record UnProcessedData(boolean isTabular, boolean hasTime, boolean hasLocation, String textData, JSONArray tabularData)
{
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
	
## Data Exchange
**Data Plugin sends data to Backend Framework**
```
record UnProcessedData(boolean isTabular, boolean hasTime, boolean hasLocation, String textData, JSONArray tabularData){
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




## Credits

This project is the group work of Yifan Zhou, Yanyu Chen and Ruoyu Zhang. 






