import Plotly from 'plotly.js';
import React from 'react';

import './App.css'; // import the css file to enable your styles.
import { VisualizationPlugin } from './plugin';

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
class App extends React.Component<Props, VisualizationPlugin> {
  private initialized: boolean = false;

  /**
   * @param props has type Props
   */
  constructor(props: Props) {
    super(props)
    /**
     * state has type Visualization Plugin as specified in the class inheritance.
     */
     this.state = {
      register: undefined,
      renderData: null
    };
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
    var data: [{}] = [
			{
				type: "scattermapbox",
				text: "PIT",
				lon: [-40],
				lat: [80],
				marker: { color: "fuchsia", size: 4}
			}
		];

		var layout: {} = {
			dragmode: "zoom",
			mapbox: { style: "open-street-map", center: { lat: 38, lon: -90 }, zoom: 3},
			margin: { r: 0, t: 0, b: 0, l: 0 }
		};

		Plotly.newPlot("PlotlyTest", data, layout);
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
      <button className="dropbtn" onClick={/* Call the function */this.viztest}>Visualization Test</button>
      <div id='PlotlyTest'></div>
      </div>
    );
  }
}

export default App;
