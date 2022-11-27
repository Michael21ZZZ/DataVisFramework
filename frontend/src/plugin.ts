import { pluginData } from "./data";
import { framework } from "./framework";

interface VisualizationPlugin {
  renderData: (data: pluginData) => void;

}

export type { VisualizationPlugin}