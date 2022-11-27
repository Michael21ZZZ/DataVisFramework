import { VisualizationPlugin } from "./plugin"
const fs = require('fs');

class framework {
    #registeredPlugins: VisualizationPlugin[] = []
  
    constructor() {
      this.#registeredPlugins = []
    }
  
    /**
     * Registers a new {@link VisualizationPlugin} with the framework
     */
    registerPlugin(plugin: VisualizationPlugin): void {
      this.#registeredPlugins.push(plugin)
    }
  
    selectPlugin(pluginIdx: number): VisualizationPlugin {
      const plugin = this.#registeredPlugins[pluginIdx]
      return plugin
    }

    registerPlugins(dir: string): void {
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
            this.#registeredPlugins.push(new plugin())
        }
    }
  
  }

  export { framework }