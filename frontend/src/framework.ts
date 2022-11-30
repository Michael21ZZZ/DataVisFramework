import { plotData } from './data'

interface framework {
  plotData: plotData
  dataPlugin: string
  visPlugin: string
  instruction: string
  registeredVisualizationPlugins: string[]
  registeredDataPlugins: string[]
  frozen: boolean
}

export type { framework }
