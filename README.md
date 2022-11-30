
# When and Where? Time-spatial analytical visualization of events

## Description
### Motivation

GEO map, time series and GEO-time visualizations can provide intuitive insights regarding data like travel logs and chronicles. However, such data sources often have 2 problems. 

1. The date format is messy.

2. Time and GEO information are buried in text.

Our framework and plugins combined can solve this problem. The data plugins can ensure a clean date format. Our framework can extract time and spatial information from selected texts with an NLP technique called Named Entity Recognition (NER). After the time and location data are processed, the framework will pass them to the frontend and perform data visualization in an interactive way. 


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

## Usage

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

	



## Credits

This project is the group work of Yifan Zhou, Yanyu Chen and Ruoyu Zhang. 






