import { processedData } from "./data";
import { VisualizationPlugin } from "./plugin";
const fs = require('fs');


class framework {
    processedData: processedData | undefined
    dataPlugin: string = ""
    vizPlugin: string = ""
    instruction: string = ""
    registeredPlugins: VisualizationPlugin[] = []
  
    constructor() {
      this.registeredPlugins = []
    }
  
    /**
     * Registers a new {@link VisualizationPlugin} with the framework
     */
     private registerPlugin(plugin: VisualizationPlugin): void {
      this.registeredPlugins.push(plugin)
    }
  
    private selectPlugin(pluginIdx: number): VisualizationPlugin {
      const plugin = this.registeredPlugins[pluginIdx]
      return plugin
    }

    private registerPlugins(dir: string): void {
        var plugins: string[] = [];
        var loc: string = './' + dir + '/'
        fs.readdir(loc, (err: any, files: string[]) => {
            files.forEach((file: string) => {
                console.log("debug")
              plugins.push(file);
            });
          });

        for (const pluginFile of plugins) {
            const plugin = require(`${pluginFile}`)
            this.registeredPlugins.push(new plugin())
        }
    }
  
  }

export type { framework }

