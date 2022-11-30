import React from 'react'

import './App.css' // import the css file to enable your styles.

import { framework } from './framework'
import Plotly from 'plotly.js-dist'

/**
 * Define the type of the props field for a React component
 */
// eslint-disable-next-line
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
  private initialized: boolean = false

  /**
   * @param props has type Props
   */
  constructor (props: Props) {
    super(props)
    /**
     * state has type Visualization Plugin as specified in the class inheritance.
     */
    this.state = {
      plotData: { data: [{}], layout: {} },
      dataPlugin: 'not selected',
      visPlugin: 'not selected',
      instruction: '',
      registeredDataPlugins: [],
      registeredVisualizationPlugins: [],
      frozen: false
    }
  }

  /**
   * This function will call after the HTML is rendered.
   * We update the initial state by creating a new game.
   * @see https://reactjs.org/docs/react-component.html#componentdidmount
   */

  componentDidMount (): void {
    /**
     * setState in DidMount() will cause it to render twice which may cause
     * this function to be invoked twice. Use initialized to avoid that.
     */
    if (!this.initialized) {
      this.initialized = true
    }
  }

  // eslint-disable-next-line
  componentWillMount (): void {
    // eslint-disable-next-line
    this.register()
  }

  register = async (): Promise<any> => {
    const response = await fetch('/register')
    const json = await response.json()
    this.setState({
      registeredDataPlugins: json.dataplugins,
      registeredVisualizationPlugins: json.visplugins
    })
  }

  setInstructionContent (content: string): void {
    const instruction = document.getElementById('instruction')
    if (instruction !== null) {
      instruction.innerHTML = content
    }
  }

  getDataPlugin (ind: number): React.MouseEventHandler {
    return async (e) => {
      // eslint-disable-next-line
      const response = await fetch('/dataplugin?i=' + ind)
      const json = await response.json()
      const dataplugins = document.getElementById('data_options')
      const searchbar = document.getElementById('search')
      if (dataplugins !== null && searchbar !== null) {
        dataplugins.style.display = 'none'
        searchbar.style.display = 'inline'
      }
      this.setInstructionContent('Please enter your keywords')
      this.setState({
        dataPlugin: json.name,
        instruction: json.instruction
      }
      )
    }
  }

  submitKeyword (): React.MouseEventHandler {
    return async (e) => {
      this.setState({ instruction: 'Please wait', frozen: true }
      )
      const keyword: string = (document.getElementById('keyword') as HTMLInputElement).value
      // eslint-disable-next-line
      const response = await fetch('/submitdata?keyword=' + keyword)
      this.setInstructionContent('Please select a Visualization plugin')
      const visplugins = document.getElementById('vis_options')
      const searchbar = document.getElementById('search')
      if (visplugins !== null && searchbar !== null) {
        visplugins.style.display = 'inline'
        searchbar.style.display = 'none'
      }
      this.setState({ instruction: 'Please select a Visualization plugin', frozen: false })
    }
  }

  getVisPlugin (ind: number): React.MouseEventHandler {
    return async (e) => {
      // eslint-disable-next-line
      const response = await fetch('/visplugin?i=' + ind)
      const json = await response.json()
      this.setState({ plotData: json }, this.drawPlot)
    }
  }

  drawPlot (): void {
    const plugins = document.getElementById('options')
    const state = document.getElementById('state')
    const instruction = document.getElementById('instruction')
    if (plugins !== null && state !== null && instruction !== null) {
      plugins.style.display = 'none'
      state.style.display = 'none'
      instruction.style.display = 'none'
    }
    if (this.state.plotData !== undefined) {
      // eslint-disable-next-line
      Plotly.newPlot('PlotlyTest',
        this.state.plotData.data,
        this.state.plotData.layout)
    }
  }

  createDataButton (plugin: string, idx: number): React.ReactNode {
    return (
      <div key={idx}>
        <button className='dropbtn' onClick={this.getDataPlugin(idx)}>DataPlugin {plugin}</button>
      </div>
    )
  }

  createVisButton (plugin: string, idx: number): React.ReactNode {
    return (
      <div key={idx}>
        <button className='dropbtn' onClick={this.getVisPlugin(idx)}>VisPlugin {plugin}</button>
      </div>
    )
  }

  handleRefreshPage = (): void => {
    if (!this.state.frozen) {
      window.location.reload()
    }
  }

  /**
   * The only method you must define in a React.Component subclass.
   * @returns the React element via JSX.
   * @see https://reactjs.org/docs/react-component.html
   */
  render (): React.ReactNode {
    /**
     * We use JSX to define the template. An advantage of JSX is that you
     * can treat HTML elements as code.
     * @see https://reactjs.org/docs/introducing-jsx.html
     */
    // eslint-disable-next-line
    return (
      <div>
        <b><div id='instruction'>Please Select a Data Plugin</div></b>

        <div id='state'>
          Data Plugin: <p id='plugin'>{this.state.dataPlugin}</p> <br />
          Visualization Plugin: <p id='plugin'>{this.state.visPlugin}</p> <br />
          Instruction: {this.state.instruction}
        </div>

        <div id='options'>
          <div id='data_options'>
            {this.state.registeredDataPlugins.map((plugin, i) => this.createDataButton(plugin, i))}
          </div>
          <div id='search'>
            Enter the keyword: <input type='text' id='keyword' defaultValue='' />
            <button onClick={this.submitKeyword()}>Search</button>
          </div>
          <div id='vis_options'>
            {this.state.registeredVisualizationPlugins.map((plugin, i) => this.createVisButton(plugin, i))}
          </div>
        </div>

        <div id='PlotlyTest' />
        <button className='dropbtn' onClick={this.handleRefreshPage}>Return</button>
      </div>
    )
  }
}

export default App
