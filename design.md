# **When and Where? Time-spatial analytical visualization of events** 

## Domain
When describing an event, time and space information are always deeply coupled. However, this information is always buried in texts and there is little tool to help visualize it. Our framework can extract time and spatial information from selected texts and perform data visualization in an interactive way. It can extract time and space information in texts from different sources with Named Entity Recognition (NER). If the data source provides default time or space information, the framework would merge these data together. The framework would transform the information into key-value pairs: (example keys: time, space, event), and send these pairs to the visualization plugins in JSON. 
Data plugins are responsible for extracting texts from data sources based on user-configurable requirements. To help the framework better formulate data, the plugins would also store texts along with key indicators (like hasTimeInfo or hasSpaceInfo). The framework would then get this JSON file and process it based on these key indicators. We’ve designed two data plugins:

**Wikipedia plugins** take in a keyword provided by users. The plugin would use Wikipedia’s API to extract texts from the search result. The framework would get a JSON file including the text and other information. 

**Twitter plugin** takes in a keyword provided by users. The plugin would use Twitter’s API to extract a complete response from Twitter. The framework would get a JSON file including formatted Twitter response. 

Visualization plugins would take the JSON file sent by the framework and visualize the interactions between time, space and event. We’ve designed two visualization plugins:

**When-and-where plugin** indicates the location of all events across the timeline. You can move along the timeline to see what’s happening and where it happens. 

**Heatmap plugin** indicates the frequency of events happening across the timeline. It’s similar to Github’s famous heatmap for commits. 

## Generality vs specificity

As is described above, the core functionality of our framework’s visualization is to provide a way to restructure data (either in the form of texts or tabular data) in time and space, whose typical use cases include presenting a biography, planing a trip, and tracking a route with a map and timeline. When the dataset does not have original time/space data, the Named Entity Recognition (NER) API developed by Stanford’s CoreNLP enables humongous potential generality.

Although theoretically any text can be input into this framework for visualization, such an unbinded generality may not always be meaningful when the input text has no clear partitions in terms of time or space. For example, there is no point visualizing a poem or a scientific research in a time-spatial analysis. To decrease the possibility of meaningless input, when inputting raw strings, developers of data plugins should try to validate that the incoming data sources inherently contain data or space entities that can be extracted. This practice can improve some degree of specificity. 

The major reuse of this framework is using CoreNLP to generate time, latitude and longitude for event partitions. With this functionality, visualization can easily focuses on presenting events in a spatial-time manner. There can be various combinations of visualization for different scenarios.

## Project structure

hw6 Project  

- Back-end
  - Core	
    - Framework	
    - Data Plugin Interface  
  - Plugins	
    - …    
  - App.java  
- Front-end    
  - Core    	
    - Framework UI	
    - Visualization Plugin Interface    
  - PluginLoader    
  - index.tsx    
  - Plugins      
    - …

The following is the overall workflow of this framework.

- GUI ---(data, dataOp)---> Backend
	- The user selects the data source plugin and provide texts to generate/request text. The data plugin process the data and sends the json file.	
- GUI <---(analysisResult)--- Backend
	- The Backend performs NLP and sends the processed data to the frontend.
- GUI.getVisPlugIn(visOp).render(analysisResult)
	- The Frontend uses the VisPlugin to render a picture.

![img](https://lh4.googleusercontent.com/hzSrE4ln6sk9CKi7ZkQhsI_eQ5KEnSLFuzoSup9pTVzNI5aT639FZkpk7raF89QAnADkiTV7so7iFZ2HIBCv0SRDruHiSpj45U3dnNsW-1eS4Nz3PLymZ71KsQiXJmsFRJisC6HaHhpR2NeOkBNTL8Ss01vPhB3496Wb_mv0T0sptG6uVwbDyI84jT0YAw)

The following is how the Framework processes input data through CoreNLP. It goes through several conditional statementes to decide whether to perform NLP process. The endpoint of this flow chart is a processed dataframe containing three key columns: text, time, location.

![img](https://lh5.googleusercontent.com/9uALSFxd-NKJYBZYKKgj18_51XdyCmQkJgE4grJ3_007u6Jqxr-_GLxBQ1cqsg-4npt_MGTLoQ4uh9OtM-XHfQAef8j8-rQj0kj0yIWoFARI2QCbIj55fDlo59RR-K-NUACiYaiYLhxMPoUPdVLG9VHU0582GvltmIh0GiVx1S8Ux-S98ei2B_jAzqZu2w)

## Plugin interfaces

### Data Plugin

```java
public interface DataPlugin {

/**
 * Called (only once) when the plug-in is first registered with the
 * framework, giving the plug-in a chance to perform any initial set-up
 * before the game has begun (if necessary).
 */
public void register(Framework framework);

/**
 * Every class that implements this interface should have a field of 
 * JSONObject to store the data to be processed by framework
 * This method will return that field of JSONObject to the framework
 */
public JSONObject getData();
    
}
```

```
/**
 * Example structure for the stored data to be processed by framework
 */
{
“has_time”: boolean,
“has_location”: boolean,
“Text_or_tabular”: String,
“Text_data”: String,
“Tabluar_data”: [
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

### Visualization Plugin

```java
public interface VisulizationPlugin {

/**
 * Called (only once) when the plug-in is first registered with the
 * framework, giving the plug-in a chance to perform any initial set-up
 * before the game has begun (if necessary).
 */
void register(Framework UI framework);

/**
 * Use the data from passed from the framework,
 * under a configured setting in JSON format,
 * renders the data in the front-end
 */
HTMLElement renderData(JSONObject data, JSONObjet config);

}
```

