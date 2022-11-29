import { processedData } from "./data";


interface framework {
    processedData: processedData
    dataPlugin: string
    vizPlugin: string
    instruction: string
    registeredVisualizationPlugins: string[]
    registeredDataPlugins: string[]
  }

export type { framework }

