import React from 'react';

import './App.css'; // import the css file to enable your styles.
import { pluginData } from './data';
import { framework } from './framework';
import { plugin1 } from './plugins/plugin1';
import { plugin2 } from './plugins/plugin2';
import { plugin3 } from './plugins/plugin3';

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

  viztest (): void {
    let data: pluginData = {"coreData":[{"lng":-79.9958864,"location":"Pittsburgh","time":"2022-05-12","text":"I had a donut.","lat":40.44062479999999},{"lng":-74.0059728,"location":"New York","time":"2022-06-12","text":"I went to the Met.","lat":40.7127753},{"lng":-87.6297982,"location":"Chicago","time":"2023-09-29","text":"Peter and I went to Ed Sheeran's concert.","lat":41.8781136},{"lng":-79.9958864,"location":"Pittsburgh","time":"2024-02-1","text":"The Civil War 2 broke out.","lat":40.44062479999999}],
    "locationFreq":{"New York":1,"Chicago":1,"Pittsburgh":2}}
    let work: framework = new framework()
    work.registerPlugin(new plugin3())
    work.selectPlugin(0).renderData(data)
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
      <button className="dropbtn" onClick={/* Call the function */this.viztest}>Visualization Test 1</button>
      <div id='PlotlyTest'></div>
      </div>
    );
  }
}

export default App;
