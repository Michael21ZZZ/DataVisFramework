
# When and Where? Time-spatial analytical visualization of events

## Description
### Motivation


When describing an event, time and space information are always deeply coupled. However, this information is always buried in texts and there is little tool to help visualize it. Besides, time and location from different sources can be messy to handle. 

Our framework can extract time, location and event information from selected texts and perform data visualization in an interactive way. It can extract data from different sources with Named Entity Recognition (NER). Visualization plugins like location-event, time series, and location-time can provide intuitive insights regarding data like travel logs and chronicles. 

For data plugins, we implemented Wikipedia plugin, Manual Input plugin and Twitter plugin for illustration. You can find instructions when you select each plugin. For visualization plugins, there are Geolocation plugin, Timeline plugin and Event frequency plugin. 


## Installation
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
1. Select a Data Plugin and read the instructions.
2. Type keyword in the search box and press 'Submit'.
3. Select visualization plugins and get the visualization result. 

	
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
    public JSONObject prepVis(JSONObject processedData);
    
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






