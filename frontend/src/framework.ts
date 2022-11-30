import { plotData } from "./data";


interface framework {
    plotData: plotData
    dataPlugin: string
    visPlugin: string
    instruction: string
    registeredVisualizationPlugins: string[]
    registeredDataPlugins: string[]
  }

export type { framework }

