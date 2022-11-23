interface GameState {
  name: String;
  footer: String;
  cells: Cell[];
  plugins: Plugin[];
  numColStyle: String;
  currentPlayer: String;
  gameOverMsg: String;
  showDropdown: boolean;
}
  
interface Cell {
  text: string;
  playable: boolean;
  x: number;
  y: number;
}

interface Plugin {
    name: string;
}
  
export type { GameState, Cell, Plugin }