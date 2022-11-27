import React from 'react';

import './App.css'; // import the css file to enable your styles.
import { pluginData } from './data';
import { framework} from './framework';
import { plugin1 } from './plugins/plugin1';
import { plugin2 } from './plugins/plugin2';
import { plugin3 } from './plugins/plugin3';
import * as fs from 'fs';
import { VisualizationPlugin } from './plugin';
import {loadPlugin} from './pluginloader'

/**
 * Define the type of the props field for a React component
 */
interface Props { }

/**
 * Using generics to specify the type of props and state.
 * props and state is a special field in a React component.
 * React will keep track of the value of props and state.
 * Any time there's a change to their values, React will
 * automatically update (not fully re-render) the HTML needed.
 * 
 * props and state are similar in the sense that they manage
 * the data of this component. A change to their values will
 * cause the view (HTML) to change accordingly.
 * 
 * Usually, props is passed and changed by the parent component;
 * state is the internal value of the component and managed by
 * the component itself.
 */
class App extends React.Component<Props, framework> {
  private initialized: boolean = false;
  private PLUGIN_DIR = 'plugins'

  /**
   * @param props has type Props
   */
  constructor(props: Props) {
    super(props)
    /**
     * state has type Visualization Plugin as specified in the class inheritance.
     */
    this.state = {
      registeredPlugins: []}
  }

  /**
   * Use arrow function, i.e., () => {} to create an async function,
   * otherwise, 'this' would become undefined in runtime. This is
   * just an issue of Javascript.
   */

  /**
   * This function will call after the HTML is rendered.
   * We update the initial state by creating a new game.
   * @see https://reactjs.org/docs/react-component.html#componentdidmount
   */

  componentDidMount(): void {
    /**
     * setState in DidMount() will cause it to render twice which may cause
     * this function to be invoked twice. Use initialized to avoid that.
     */
    if (!this.initialized) {
      this.initialized = true;
    }
  }

  loadPlugins (): React.MouseEventHandler {
    return async (e) => {
      e.preventDefault()
      this.setState({registeredPlugins: []})
      this.setState({registeredPlugins: [new plugin1(), new plugin2(), new plugin3()]})
    }
  }

  viztest (ind: number): React.MouseEventHandler {
    return async (e) => {
    let data: pluginData = {"coreData":[{"lng":-79.9958864,"location":"Pittsburgh","time":"2022-05-12","text":"I had a donut.","lat":40.44062479999999},{"lng":-74.0059728,"location":"New York","time":"2022-06-12","text":"I went to the Met.","lat":40.7127753},{"lng":-87.6297982,"location":"Chicago","time":"2023-09-29","text":"Peter and I went to Ed Sheeran's concert.","lat":41.8781136},{"lng":-79.9958864,"location":"Pittsburgh","time":"2024-02-1","text":"The Civil War 2 broke out.","lat":40.44062479999999}],
    "locationFreq":{"New York":1,"Chicago":1,"Pittsburgh":2}}
    this.state.registeredPlugins[ind].renderData(data)
  }}

  createVizButton (idx: number): React.ReactNode {
    return (<div><button className="dropbtn" onClick={this.viztest(idx)}>VizPlugin {idx+1}</button></div>)
  }

  refreshPage = () =>{
    window.location.reload();
 }


  /**
   * The only method you must define in a React.Component subclass.
   * @returns the React element via JSX.
   * @see https://reactjs.org/docs/react-component.html
   */
  render(): React.ReactNode {
    /**
     * We use JSX to define the template. An advantage of JSX is that you
     * can treat HTML elements as code.
     * @see https://reactjs.org/docs/introducing-jsx.html
     */
    return (
      <div>
        <button className="dropbtn" onClick={this.loadPlugins()}>Load visualization plugins</button>
        <div id='vizplugin'>
          {this.state.registeredPlugins.map((
            plugin, i
          ) => this.createVizButton(i))}
        </div>
        <div id='PlotlyTest'></div>
        <button className="dropbtn" onClick={this.refreshPage}>Return</button>
      </div>
    );
  }
}

export default App;
