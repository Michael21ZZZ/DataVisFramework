import Plotly from 'plotly.js-dist';
import { pluginData } from "../data";
import { framework } from '../framework';
import { VisualizationPlugin } from "../plugin";

class plugin3 implements VisualizationPlugin {
    #framework: framework | null = null

    register (framework: framework): void {
        this.#framework = framework
    };

    renderData (data: pluginData): void {

        let time: string[] = [];
        let lontitude: number[] = [];
        let latitude: number[] = [];
        data.coreData.forEach(element => {
            time.push(element.time)
            lontitude.push(element.lng)
            latitude.push(element.lat)
        });

        var processedData: [{}] = [
            {
                type: "heatmap",
                z: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12],
                x: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12],
                y: [ "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December" ],
                text: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]
            }
        ];

        Plotly.newPlot("PlotlyTest", processedData)
    }

}

export {plugin3}