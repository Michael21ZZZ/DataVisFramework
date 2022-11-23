import React from 'react';

import './App.css'; // import the css file to enable your styles.
import { GameState, Cell } from './game';
import BoardCell from './Cell';

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
class App extends React.Component<Props, GameState> {
  private initialized: boolean = false;

  /**
   * @param props has type Props
   */
  constructor(props: Props) {
    super(props)
    /**
     * state has type GameState as specified in the class inheritance.
     */
     this.state = {
      cells: [],
      name : "A Game Framework",
      footer : "No game is running",
      plugins : [],
      numColStyle : "auto",
      currentPlayer : "",
      gameOverMsg : "",
      showDropdown: false,
    };
  }

  /**
   * Use arrow function, i.e., () => {} to create an async function,
   * otherwise, 'this' would become undefined in runtime. This is
   * just an issue of Javascript.
   */

  async start(){
  const response = await fetch("start");

  const json = await response.json();
  this.setState({ plugins: json["plugins"],})
}

/**
 * play will generate an anonymous function that the component
 * can bind with.
 * @param x 
 * @param y 
 * @returns 
 */
  play(x: number, y: number): React.MouseEventHandler {
  return async (e) => {
    // prevent the default behavior on clicking a link; otherwise, it will jump to a new page.
    e.preventDefault();
    const response = await fetch(`/play?x=${x}&y=${y}`)
    const json = await response.json();
    this.setState({ cells: json["cells"], 
                    plugins: json["plugins"], 
                    name: json["name"],
                    footer:json["footer"], 
                    currentPlayer : json["currentPlayer"],
                    numColStyle : json["numColStyle"],
                    gameOverMsg : json["gameOverMsg"] 
                  })
    }
  }

  choosePlugin(i: number): React.MouseEventHandler {
    return async (e) => {
      e.preventDefault();
      const response = await fetch(`/plugin?i=${i}`)
      const json = await response.json();

      this.setState({ cells: json["cells"], 
                      plugins: json["plugins"], 
                      name: json["name"],
                      footer:json["footer"],
                      numColStyle : json["numColStyle"],
                      currentPlayer : json["currentPlayer"],
                      gameOverMsg : json["gameOverMsg"],
                      showDropdown: false, 
                    })
    }
  }


  createCell(cell: Cell, index: number): React.ReactNode {
    if (cell.playable)
      /**
       * key is used for React when given a list of items. It
       * helps React to keep track of the list items and decide
       * which list item need to be updated.
       * @see https://reactjs.org/docs/lists-and-keys.html#keys
       */
      return (
        <div key={index}>
          <a href='/' onClick={this.play(cell.x, cell.y)}>
            <BoardCell cell={cell}></BoardCell>
          </a>
        </div>
      )
    else
      return (
        <div key={index}><BoardCell cell={cell}></BoardCell></div>
      )
  }

  createInstructions() {
    if (this.state.gameOverMsg) {
      return (
        <div id="game_over_message">{this.state.gameOverMsg}</div>
      )
    }
    else if (this.state.currentPlayer) {
      return (
        <div id="current_player_name">Current player is {this.state.currentPlayer}</div>
      )
    }
    else {
      return (
        <div></div>
      )
    }
  }

  createDropdown() {
    if (this.state.plugins.length === 0) {
      return (
        <span>No games loaded</span>
      )
    }
    else {
      return (
        <div>
          {this.state.plugins.map((plugin, index) => this.createPlugin(plugin.name, index))}
        </div>
      )
    }
  }

  createPlugin(name: string, index: number) {
    return (
      <div key={index}>
        <a href='/' onClick={this.choosePlugin(index)}>{name}</a>
      </div>
    )
  }

  toggleDropdown = () => {
    this.setState({showDropdown: !this.state.showDropdown})
  }

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
      this.start();
      this.initialized = true;
    }
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
        <div id="game_name">{this.state.name}</div>

        {this.createInstructions()}

        <div id="board" style={{gridTemplateColumns: this.state.numColStyle.valueOf()}}>
          {this.state.cells.map((cell, i) => this.createCell(cell, i))}
        </div>

        <div id="footer">{this.state.footer}</div>

        <div id="bottombar">
          <button className="dropbtn" onClick={/* get the function, not call the function */this.toggleDropdown}>New Game</button>
          <div id="dropdown-content" className={this.state.showDropdown ? "show" : "hide"}>
            {this.createDropdown()}
          </div>
       </div>

      </div>
    );
  }
}

export default App;
