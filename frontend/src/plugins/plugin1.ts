import Plotly from 'plotly.js-dist';
import { pluginData } from "../data";
import { framework } from '../framework';
import { VisualizationPlugin } from "../plugin";

class plugin1 implements VisualizationPlugin {
    #framework: framework | null = null

    register (framework: framework): void {
        this.#framework = framework
    };

    renderData (data: pluginData): void {
        var layout: {} = {
            dragmode: "zoom",
            mapbox: { style: "open-street-map", center: { lat: 38, lon: -90 }, zoom: 3},
            margin: { r: 0, t: 0, b: 0, l: 0 },
        };

        let texts: string[] = [];
        let lontitude: number[] = [];
        let latitude: number[] = [];
        data.coreData.forEach(element => {
            texts.push(element.location + "<br>" + element.text)
            lontitude.push(element.lng)
            latitude.push(element.lat)
        });
        

        var processedData: [{}] = [
            {
                type: "scattermapbox",
                text: texts,
                lon: lontitude,
                lat: latitude,
                marker: { color: "fuchsia", size: 4}
            }
        ];
        Plotly.newPlot("PlotlyTest", processedData, layout)
    }

}

export {plugin1}