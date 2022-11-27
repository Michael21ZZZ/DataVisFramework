import Plotly from 'plotly.js-dist';
import { pluginData } from "../data";
import { framework } from '../framework';
import { VisualizationPlugin } from "../plugin";

class plugin2 implements VisualizationPlugin {
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
                type: "scattermapbox",
                z: time,
                lon: lontitude,
                lat: latitude,
            }
        ];

        var layout = {mapbox: {style: 'stamen-terrain'}};

        Plotly.newPlot("PlotlyTest", processedData, layout)
    }

}

export {plugin2}