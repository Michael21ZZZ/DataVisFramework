import { type } from "os"
import { VisualizationPlugin } from "./plugin"

interface frameworkState {
    registeredPlugins: VisualizationPlugin[]
}

export type {frameworkState}